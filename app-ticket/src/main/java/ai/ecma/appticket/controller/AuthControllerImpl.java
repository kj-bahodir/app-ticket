package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ApiResult<TokenDto> signIn(LoginDto loginDto) {
        return authService.signIn(loginDto);
    }

    @Override
    public ApiResult<?> checkPhoneNumber(@RequestBody @Valid PhoneNumberDto phoneNumberDto) {
        return authService.checkPhoneNumber(phoneNumberDto);
    }

    @Override
    public ApiResult<RegisterDto> checkCode(CodeDto codeDto) {
        return authService.checkCode(codeDto);
    }

    @Override
    public ApiResult<TokenDto> signUp(SignUpDto signUpDto) {
        return authService.signUp(signUpDto);
    }

    @Override
    public ApiResult<TokenDto> refreshToken(TokenDto tokenDto) {
        return authService.refreshToken(tokenDto);
    }
}
