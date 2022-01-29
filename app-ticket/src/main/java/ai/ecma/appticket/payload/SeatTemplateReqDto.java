package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateReqDto {
    private UUID id;

    @NotBlank(message = "SeatTemplate nomi bo'sh bo'lmasligi kerak")
    private String name;

    @NotNull(message = "schema id bo'sh bo'lmasligi kerak")
    private UUID schemaId;
}
