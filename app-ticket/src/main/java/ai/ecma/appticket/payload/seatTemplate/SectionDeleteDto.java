package ai.ecma.appticket.payload.seatTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDeleteDto {
    @NotBlank(message = "Seat Template Id bo'sh bo'lmasligi kerak!")
    private UUID seatTemplateId;

    @NotBlank(message = "Section nomi bo'sh bo'lmasligi kerak!")
    private String sectionName;
}
