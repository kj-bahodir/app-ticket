package ai.ecma.appticket.payload.seatTemplate;

import ai.ecma.appticket.payload.SeatTemplateChairResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowDto {
    private String name;

    private Set<SeatTemplateChairResDto> chairs;
}
