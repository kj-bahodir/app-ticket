package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayBackTariffDto {

    private UUID id;
    @NotNull(message = "Soatni kiritmadingiz!")
    private double reminingHour;
    @NotNull(message = "Foizni kiritmadingiz!")
    private double percent;
    @NotNull(message = "Eventni kiritmadingiz")
    private UUID eventId;

}
