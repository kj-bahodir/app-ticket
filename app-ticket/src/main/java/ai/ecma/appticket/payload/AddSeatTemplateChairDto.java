package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddSeatTemplateChairDto {
    @NotNull(message = "")
    private UUID seatTemplateId;
    @NotBlank(message = "")
    private List<SeatTemplateChairReqDto> seatTemplateChairReqDtoList;
}
