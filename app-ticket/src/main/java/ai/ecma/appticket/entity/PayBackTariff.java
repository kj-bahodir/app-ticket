package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update pay_back_tariff set deleted=true where id=?")
@Where(clause = "deleted=false")
public class PayBackTariff extends AbsEntity {

    private double reminingHour;

    private double percent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;
}
