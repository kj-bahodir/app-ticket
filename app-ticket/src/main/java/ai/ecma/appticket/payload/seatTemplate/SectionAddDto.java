package ai.ecma.appticket.payload.seatTemplate;

import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionAddDto {


    @NotNull(message = "seatTemplateId bo'sh bo'lmasligi kerak")
    private UUID seatTemplateId;

    @NotBlank(message = "Name bo'sh bulmasligi kerak")
    private String name;

    @NotNull(message = "Price bo'sh bulmasligi kerak")
    private Double price;

    @NotNull(message = "SeatStatusEnum bo'sh bulmasligi kerak")
    private SeatStatusEnum seatStatusEnum;

    @Positive(message = "0 dan katta bulishi kerak")
    private int rowCount;

    @Positive(message = "0 dan katta bulishi kerak")
    private int chairCountPerRow;

}
