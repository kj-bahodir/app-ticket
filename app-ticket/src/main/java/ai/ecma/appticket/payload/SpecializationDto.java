package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecializationDto {

    private UUID id;

    @NotBlank(message = "Mutaxassislikni kiriting")
    private String name;
}
