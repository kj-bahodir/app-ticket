package ai.ecma.appticket.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
@NoArgsConstructor
public class PhoneNumberDto {
    @NotNull(message = "Telefon raqamini kiritmadingiz!" )
    @Pattern(regexp = "[+][9][9][8][0-9]{9}",message = "Telefon raqamining formatini xato kiritdingiz!")
    private String phoneNumber;
}
