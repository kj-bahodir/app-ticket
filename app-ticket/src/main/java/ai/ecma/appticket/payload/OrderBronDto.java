package ai.ecma.appticket.payload;

import ai.ecma.appticket.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBronDto {
    @NotNull
    private UUID orderId;

    @NotEmpty(message = "Chipta id lari bo'sh bo'lmasin")
    private List<UUID> tickets;
}
