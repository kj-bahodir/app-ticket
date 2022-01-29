package ai.ecma.appticket.payload.seatTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDto {

    private String name;

    private Set<RowDto> rows;
}
