package ai.ecma.appticket.payload;

import ai.ecma.appticket.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResDto {

    private UUID id;

    private Double orderPrice;

    private UUID userId;

    private String[] tickets;

    private boolean finished=false;

    private OrderTypeEnum type;
}
