package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update payment set deleted=true where id=?")
@Where(clause = "deleted=false")
public class Payment extends AbsEntity {

    @Column(nullable = false)
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private PayType payType;

    @JsonIgnore
    @OneToMany(mappedBy = "payment",cascade = CascadeType.ALL)
    private List<PaymentTicket> paymentTickets;

    public Payment(Double amount, Order order, PayType payType) {
        this.amount = amount;
        this.order = order;
        this.payType = payType;
    }
}
