package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateResDto {

    private UUID id;

    private String name;

    private String schemaUrl;

    private UUID schemaId;
}
