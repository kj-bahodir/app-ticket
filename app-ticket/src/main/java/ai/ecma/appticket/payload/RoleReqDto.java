package ai.ecma.appticket.payload;

import ai.ecma.appticket.enums.PermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleReqDto {

    @NotBlank(message = "Name bo'sh bo'lmasligi kerak!")
    private String name;

    private String description;

    @NotEmpty(message = "Permission bo'sh bo'lmasligi kerak!")
    private Set<PermissionEnum> permissions;
}
