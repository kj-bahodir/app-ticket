package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSpeakerResDto {

    private UUID eventId;

    private UUID speakerId;

    private boolean main;
}
