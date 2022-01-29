package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.entity.VerificationCode;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.repository.AttachmentRepository;
import ai.ecma.appticket.repository.RoleRepository;
import ai.ecma.appticket.repository.UserRepository;
import ai.ecma.appticket.repository.VerificationCodeRepository;
import ai.ecma.appticket.security.JWTProvider;
import ai.ecma.appticket.utils.AppConstant;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final TwilioService twilioService;
    private final RoleRepository roleRepository;
    private final AttachmentRepository attachmentRepository;

    @Value(value = "${verificationCodeLimit}")
    private int codeLimit;
    @Value(value = "${verificationCodeTime}")
    private long codeTimeLimit;
    @Value(value = "${verificationCodeExpiredTime}")
    private Long verificationCodeExpiredTime;

    public AuthServiceImpl(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, JWTProvider jwtProvider, VerificationCodeRepository verificationCodeRepository, TwilioService twilioService, RoleRepository roleRepository, AttachmentRepository attachmentRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.verificationCodeRepository = verificationCodeRepository;
        this.twilioService = twilioService;
        this.roleRepository = roleRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public ApiResult<TokenDto> signIn(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String refreshToken = jwtProvider.generateTokenFromId(user.getId(), false);
            String accessToken = jwtProvider.generateTokenFromId(user.getId(), true);
            return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
        } catch (Exception e) {
            throw new RestException(HttpStatus.UNAUTHORIZED, "Password or username wrong!");
        }
    }

    @Override
    public ApiResult<?> checkPhoneNumber(PhoneNumberDto phoneNumberDto) {
        Timestamp pastTime = new Timestamp(System.currentTimeMillis() - codeTimeLimit);
        Long countSendingSmsCode = verificationCodeRepository.countAllByPhoneNumberAndCreatedAtAfter(phoneNumberDto.getPhoneNumber(), pastTime);
        if (countSendingSmsCode >= codeLimit)
            throw new RestException(HttpStatus.TOO_MANY_REQUESTS, "Ko`p urinish qildingiz, birozdan keyin urinib ko`ring!");
        String code = generateCode();
        boolean sendVerificationCode = twilioService.sendVerificationCode(code, phoneNumberDto.getPhoneNumber());
//        if (!sendVerificationCode)
//            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Server xatoligi qayta urinib ko`ring!");
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setPhoneNumber(phoneNumberDto.getPhoneNumber());
        verificationCodeRepository.save(verificationCode);
        return ApiResult.successResponse("Sizning telefon raqamingizga sms code jo`natildi.");
    }

    @Override
    public ApiResult<RegisterDto> checkCode(CodeDto codeDto) {
        Timestamp pastTime = new Timestamp(System.currentTimeMillis() - verificationCodeExpiredTime);
        VerificationCode verificationCode = verificationCodeRepository.getByCondition(
                        codeDto.getPhoneNumber(), codeDto.getCode(), pastTime)
                .orElseThrow(() -> new RestException(HttpStatus.BAD_REQUEST, "Xato code Yubordiz"));
        Optional<User> optionalUser = userRepository.findByPhoneNumber(codeDto.getPhoneNumber());
        String accessToken = null;
        String refreshToken = null;
        boolean registered = false;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            accessToken = jwtProvider.generateTokenFromId(user.getId(), true);
            refreshToken = jwtProvider.generateTokenFromId(user.getId(), false);
            registered = true;
        }
        verificationCode.setConfirmed(true);
        verificationCodeRepository.save(verificationCode);
        return ApiResult.successResponse(new RegisterDto(accessToken, refreshToken, registered));
    }

    @Override
    public ApiResult<TokenDto> signUp(SignUpDto signUpDto) {
        boolean confirmed = verificationCodeRepository.existsByPhoneNumberAndCodeAndConfirmedTrue(signUpDto.getPhoneNumber(), signUpDto.getCode());
        if (!confirmed)
            throw new RestException(HttpStatus.BAD_REQUEST, "SMS codeni xato kiritdingiz!");

        User user = new User(
                signUpDto.getFirstName(),
                signUpDto.getLastName(),
                signUpDto.getPhoneNumber(),
                "bu password",
                roleRepository.findByName(AppConstant.USER).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Role toplilmadi")),
                signUpDto.getAttachmentId() != null
                        ? attachmentRepository.findById(signUpDto.getAttachmentId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Rasm toplilmadi"))
                        : null,
                true
        );
        userRepository.save(user);
        String accessToken = jwtProvider.generateTokenFromId(user.getId(), true);
        String refreshToken = jwtProvider.generateTokenFromId(user.getId(), false);
        return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
    }

    @Override
    public ApiResult<TokenDto> refreshToken(TokenDto tokenDto) {
        try {
            jwtProvider.validateToken(tokenDto.getAccessToken());
            return ApiResult.successResponse(tokenDto);
        } catch (ExpiredJwtException e) {
            try {
                jwtProvider.validateToken(tokenDto.getRefreshToken());
                UUID userId = UUID.fromString(jwtProvider.getIdFromToken(tokenDto.getRefreshToken()));
                return ApiResult.successResponse(new TokenDto(
                        jwtProvider.generateTokenFromId(userId, true),
                        jwtProvider.generateTokenFromId(userId, false)
                ));
            } catch (Exception ex) {
                throw new RestException(HttpStatus.UNAUTHORIZED, "Refresh token buzligan");
            }
        } catch (Exception e) {
            throw new RestException(HttpStatus.UNAUTHORIZED, "Access token buzligan");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(s).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public UserDetails loadById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public String generateCode() {
        String code = String.valueOf((int) (Math.random() * 10000000));
        code = code.substring(0, 6);
        System.out.println(code);
        return code;
    }
}
