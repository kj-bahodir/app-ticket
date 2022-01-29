package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import ai.ecma.appticket.enums.OrderTypeEnum;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
@SQLDelete(sql = "update orders set deleted=true where id=?")
@Where(clause = "deleted=false")
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})

public class Order extends AbsEntity {

    private Double orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]", name = "tickets")
    private String[] tickets;

    private boolean finished = false;

    @Enumerated(EnumType.STRING)
    private OrderTypeEnum type;

    private UUID parentOrderId;

    public Order(Double orderPrice, User user, String[] tickets, boolean finished, OrderTypeEnum type) {
        this.orderPrice = orderPrice;
        this.user = user;
        this.tickets = tickets;
        this.finished = finished;
        this.type = type;
    }
}
