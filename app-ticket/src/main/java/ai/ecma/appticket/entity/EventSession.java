package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update event_session set deleted=true where id=?")
@Where(clause = "deleted=false")
public class EventSession extends AbsEntity {

    @ManyToOne
    private Event event;

    private Timestamp startTime;

    private Timestamp endTime;

    private Boolean active=true;

    public EventSession(Event event, Timestamp startTime, Timestamp endTime) {
        this.event = event;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
