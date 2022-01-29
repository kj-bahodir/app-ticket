package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSessionReqDto {

    @NotNull(message = "EventId bo'sh bo'lmasligi kerak")
    private UUID eventId;

    @NotNull(message = "Boshlanish vaqti bo'sh bo'lmasligi kerak")
    private Timestamp startTime;

    @NotNull(message = "Tugash vaqti bo'sh bo'lmasligi kerak")
    private Timestamp endTime;

    @NotNull(message = "seatTemplateId bo'sh bo'lmasligi kerak")
    private UUID seatTemplateId;

//    public EventSessionReqDto(@NotNull(message = "EventId bo'sh bo'lmasligi kerak") UUID eventId, @NotNull(message = "Boshlanish vaqti bo'sh bo'lmasligi kerak") Timestamp startTime, @NotNull(message = "Tugash vaqti bo'sh bo'lmasligi kerak")
//            Timestamp endTime) {
//        this.eventId = eventId;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
}
