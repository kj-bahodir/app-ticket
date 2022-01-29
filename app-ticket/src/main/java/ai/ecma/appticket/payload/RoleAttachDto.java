package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAttachDto {

    @NotNull(message = "Role id bo'sh bo'lmasligi kerak!")
    private UUID roleId;

    @NotEmpty(message = "User  bo'sh bo'lmasligi kerak!")
    private Set<UUID> userIds;
}
