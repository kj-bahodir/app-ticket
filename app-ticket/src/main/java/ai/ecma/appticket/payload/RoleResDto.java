package ai.ecma.appticket.payload;

import ai.ecma.appticket.enums.PermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResDto {
    private UUID id;

    private String name;

    private String description;

    private Set<PermissionEnum> permissions;
}
