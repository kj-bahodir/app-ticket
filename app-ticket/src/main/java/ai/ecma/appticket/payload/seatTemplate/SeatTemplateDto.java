package ai.ecma.appticket.payload.seatTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateDto {

    private UUID id;

    private String schemaUrl;

    private Set<SectionDto> sections;
}
