package ai.ecma.appticket.payload;

import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateChairResDto {

    private UUID id;

    private Double price;

    private SeatStatusEnum status;

    private String section;

    private String row;

    private String name;
}
