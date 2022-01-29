package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeakerReqDto {
    @NotBlank(message = "Speakerning ism familiyasi bo'sh bulmasligi kerak")
    private String fullName;

    @NotEmpty
    @NotNull(message = "Speakerning hech bo'lmasa bitta mutaxassisligi bulishi kerak")
    private Set<UUID> specializationsId;

    private String description;

    @NotNull(message = "PhotoId bo'sh bulmasligi kerak")
    private UUID photoId;
}
