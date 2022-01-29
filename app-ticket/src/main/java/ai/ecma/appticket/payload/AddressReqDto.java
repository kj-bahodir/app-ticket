package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressReqDto {

    private double lat;

    private double lon;

    @NotBlank(message = "Address ning name bo'sh bulmasligi kerak")
    private String name;

    @NotBlank(message = "Target bo'sh bulmasligi kerak. Bunda binoning aniq mo'ljali bulishi kerak ")
    private String target;

    @NotNull(message = "Manzilning rasmi bo'sh bo'lmasligi kerak!")
    private UUID photoId;
}
