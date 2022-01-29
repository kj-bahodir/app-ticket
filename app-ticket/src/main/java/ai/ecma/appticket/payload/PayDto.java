package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDto {

    @NotNull(message = "Buyurtma id bo'sh bo'lmasligi kerak")
    private UUID orderId;

    @Positive(message = "Buyurtma narxi bo'sh bo'lmasligi kerak")
    private Double price;
}
