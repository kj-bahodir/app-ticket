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
public class SectionEditDto {

    @NotNull(message = "seatTemplateId bo'sh bo'lmasligi kerak")
    private UUID seatTemplateId;

    @NotBlank(message = "OldName bo'sh bo'lmasligi kerak")
    private String oldName;

    @NotBlank(message = "NewName bo'sh bo'lmasligi kerak")
    private String newName;

    private Double price;

    private SeatStatusEnum seatStatusEnum;
}
