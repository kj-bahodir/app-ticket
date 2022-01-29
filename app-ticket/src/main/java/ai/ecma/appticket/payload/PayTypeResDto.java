package ai.ecma.appticket.payload;

import ai.ecma.appticket.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayTypeResDto {

    private UUID id;

    private String name;

    private PayTypeEnum payTypeEnum;
}
