package ai.ecma.appticket.payload.seatTemplate;

import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChairEditDto {

    @NotNull(message = "Id bo'sh bo'lmasligi kerak")
    private UUID id;

    private Double price;

    private SeatStatusEnum seatStatusEnum;
}
