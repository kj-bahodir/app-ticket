package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResDto {
    private List<UUID> payBackIdList;
    private String cardNumber;
    private Double price;
    private List<UUID> ticketIdList;
}
