package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.*;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(RestConstant.AUTH_CONTROLLER)
@Tag(name = "Tekshiruvdan o'tish operatsiyalari",description = "Autentifikatsiya")
public interface AuthController {

    @Operation(summary = "Tizimga kirish")
    @PostMapping("/sign-in")
    ApiResult<TokenDto> signIn(@RequestBody @Valid LoginDto loginDto);

    @Operation(summary = "Telefonga sms code kelishi uchun foydalanuvchi raqamini kiritish")
    @PostMapping("/check-phone-number")
    ApiResult<?> checkPhoneNumber(@RequestBody @Valid PhoneNumberDto phoneNumberDto);

    @Operation(summary = "Telefonga kelgan sms codeni to'g'ri ekanligini tekshirish")
    @PostMapping("/check-code")
    ApiResult<RegisterDto>checkCode(@RequestBody @Valid CodeDto codeDto);

    @Operation(summary = "Ro'yhatdan o'tish")
    @PostMapping("/sign-up")
    ApiResult<TokenDto> signUp(@RequestBody @Valid SignUpDto signUpDto);

    @Operation(summary = "Access token buzilganda refresh token yordamida yangilash")
    @PostMapping("/refresh-token")
    ApiResult<TokenDto> refreshToken(@RequestBody TokenDto tokenDto);
}
