package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import ai.ecma.appticket.enums.BronStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update bron set deleted=true where id=?")
@Where(clause = "deleted=false")
public class Bron extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @OneToOne(fetch = FetchType.LAZY)
    private PaymentTicket paymentTicket;

    private Timestamp expireTime;

    @Enumerated(EnumType.STRING)
    private BronStatusEnum bronStatusEnum;

}
