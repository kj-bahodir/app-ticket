package ai.ecma.appticket.payload.seatTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowDeleteDto {
    @NotBlank(message = "Seat Template Id bo'sh bo'lmasligi kerak!")
    private UUID seatTemplateId;

    @NotBlank(message = "Section nomi bo'sh bo'lmasligi kerak!")
    private String sectionName;

    @NotBlank(message = "Row nomi bo'sh bo'lmasligi kerak!")
    private String rowName;
}
