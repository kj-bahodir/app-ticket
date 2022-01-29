package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BronTariffResDto {
    private  UUID id;
    private Double lifeTime;
    private Double percent;
    private boolean disable;
    private Double finishTime;
}
