package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update ticket set deleted=true where id=?")
@Where(clause = "deleted=false")
@Table(name = "ticket", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"section", "row", "event_session_id","name"})
})
public class Ticket extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JoinColumn(name = "event_session_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EventSession eventSession;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String row;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private SeatStatusEnum status;

    private Double price;

    private Boolean active=true;

    public Ticket(User user, EventSession eventSession, String section, String row, String name, SeatStatusEnum status, Double price) {
        this.user = user;
        this.eventSession = eventSession;
        this.section = section;
        this.row = row;
        this.name = name;
        this.status = status;
        this.price = price;
    }
}
