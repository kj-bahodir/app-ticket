package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSpeakerReqDto {

    @NotNull(message = "Tadbir bo'sh bo'lmasligi kerak")
    private UUID eventId;

    @NotNull(message = "Speaker bo'sh bo'lmasligi kerak")
    private UUID speakerId;

    @NotNull(message = "Asosiy bo'sh bo'lmasligi kerak")
    private boolean main;
}
