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
public class ChairAddDTO {

    @NotNull(message = "seatTemplateId bo'sh bo'lmasligi kerak")
    private UUID seatTemplateId;

    @NotNull(message = "Price bo'sh bulmasligi kerak")
    private Double price;

    @NotNull(message = "SeatStatusEnum bo'sh bulmasligi kerak")
    private SeatStatusEnum seatStatusEnum;

    @NotNull(message = "sectionName bo'sh bulmasligi kerak")
    private String sectionName;

    @NotNull(message = "rowName bo'sh bulmasligi kerak")
    private String rowName;
}
