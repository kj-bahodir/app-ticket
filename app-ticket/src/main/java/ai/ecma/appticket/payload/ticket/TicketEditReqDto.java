package ai.ecma.appticket.payload.ticket;

import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketEditReqDto {
    @NotNull
    private UUID userId;
    @NotBlank(message = "Section bo'sh bo'lmasligi kerak")
    @NotNull(message = "Status bo'sh bo'lmasligi kerak")
    private SeatStatusEnum status;
    @Positive(message = "Price bo'sh bo'lmasligi kerak")
    private Double price;
}
