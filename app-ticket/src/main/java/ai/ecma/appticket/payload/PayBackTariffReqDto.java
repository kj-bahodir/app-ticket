package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayBackTariffReqDto {

    @NotNull(message = "Soatni kiritmadingiz!")
    private double reminingHour;
    @NotNull(message = "Foizni kiritmadingiz!")
    private double percent;
    @NotNull(message = "Event idini kiritmadingiz!")
    private UUID eventId;

}
