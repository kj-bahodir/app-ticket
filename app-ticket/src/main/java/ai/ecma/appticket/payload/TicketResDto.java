package ai.ecma.appticket.payload;

import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketResDto {
    private UUID id;
    private UUID userId;
    private UUID eventSessionId;
    private String section;
    private String row;
    private String name;
    private SeatStatusEnum status;
    private Double price;

}
