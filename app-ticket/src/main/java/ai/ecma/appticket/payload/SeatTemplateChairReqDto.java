package ai.ecma.appticket.payload;

import ai.ecma.appticket.entity.SeatTemplate;
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
public class SeatTemplateChairReqDto {

    @Positive(message = "0 dan katta raqam kiriting!")
    private Double price;

    @NotBlank(message = "Status bo'sh bo'lmasligi kerak!")
    private SeatStatusEnum status;

    @NotBlank(message = "Section bo'sh bo'lmasligi kerak!")
    private String section;

    @NotNull(message = "Row bo'sh bo'lmasligi kerak!")
    private String row;

    @NotBlank(message = "Name bo'sh bo'lmasligi kerak!")
    private String name;

    private UUID seatTemplateId;
}
