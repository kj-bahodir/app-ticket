package ai.ecma.appticket.payload.ticket;

import ai.ecma.appticket.entity.EventSession;
import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketReqDto {

    private UUID userId;
    @NotNull(message = "Tadbir session bo'sh bo'lmasligi kerak")
    private UUID eventSessionId;
    @NotBlank(message = "Section bo'sh bo'lmasligi kerak")
    private String section;
    @NotBlank(message = "Qator bo'sh bo'lmasligi kerak")
    private String row;
    @NotBlank(message = "Name bo'sh bo'lmasligi kerak")
    private String name;
    @NotNull(message = "Status bo'sh bo'lmasligi kerak")
    private SeatStatusEnum status;
    @Positive(message = "Price bo'sh bo'lmasligi kerak")
    private Double price;

}
