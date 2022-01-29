package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BronTariffReqDto {
    @Positive(message = "Lifetime bo'sh bo'lmasligi kerak")
    private Double lifeTime;

    @Positive(message = "Percent bo'sh bo'lmasligi kerak")
    private Double percent;

    @NotNull(message = "Disable field bo'sh bo'lmasligi kerak")
    private boolean disable;

    @Positive(message = "FinishTime bo'sh bo'lmasligi kerak")
    private Double finishTime;


}
