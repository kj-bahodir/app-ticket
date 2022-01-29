package ai.ecma.appticket.payload.seatTemplate;

import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowEditDto {

    @NotNull(message = "seatTemplateId bo'sh bo'lmasligi  kerak")
    private UUID seatTemplateId;

    @NotBlank(message = "Section bo'sh bo'lmasligi  kerak")
    private String section;

    @NotBlank(message = "Row bo'sh bo'lmasligi  kerak")
    private String row;

    private Double price;

    private SeatStatusEnum seatStatusEnum;
}
