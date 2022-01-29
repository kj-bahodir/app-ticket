package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface AuthService extends UserDetailsService {

    UserDetails loadById(UUID id);

    ApiResult<TokenDto> signIn(LoginDto loginDto);

    ApiResult<?> checkPhoneNumber(PhoneNumberDto phoneNumberDto);

    ApiResult<RegisterDto> checkCode(CodeDto codeDto);

    ApiResult<TokenDto> signUp(SignUpDto signUpDto);

    ApiResult<TokenDto> refreshToken(TokenDto tokenDto);

}
