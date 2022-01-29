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
@SQLDelete(sql = "update ticket_history set deleted=true where id=?")
@Where(clause = "deleted=false")
public class TicketHistory extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private SeatStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
