package ai.ecma.appticket.payload;

import ai.ecma.appticket.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSessionResDto {

    private UUID id;

    private EventResDTO event;

    private Timestamp startTime;

    private Timestamp endTime;
}
