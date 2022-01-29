package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundReqDto {
    @NotBlank(message = "Karta raqam bo'sh bo'lmasligi kerak!")//not blank probel ham qo'ymaslik uchun ishlatiladi
    @Pattern(regexp = "[0-9]{16}", message = "Noto'g'ri karta raqam kiritildi!")
    private String cardNumber;

    @NotEmpty(message = "Ticket id lari bo'sh bo'lmasligi kerak!") //@notempty collectionlar uchun foydalaniladi.
    private List<UUID> ticketIdList;

}
