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
public class EventReqDto {
    @NotBlank(message = "Tadbir nomi bo'sh bo'lmasligi kerak!")
    private String name;
    private String description;
    @NotNull(message = "Address bo'sh bo'lmasligi kerak!")
    private UUID addressId;
    @NotNull(message = "Tadbir turi bo'sh bo'lmasligi kerak!")
    private UUID eventTypeId;
    @NotNull(message = "Tadbir sxemasi bo'sh bo'lmasligi kerak!")
    private UUID schemaId;
    @NotNull(message = "Tadbir banneri bo'sh bo'lmasligi kerak!")
    private UUID bannerId;

    @NotNull(message = "BronTariff bo'sh bo'lmasligi kerak!")
    private UUID bronTariffId;
}
