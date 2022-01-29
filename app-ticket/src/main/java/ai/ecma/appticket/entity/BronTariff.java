package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update bron_tariff set deleted=true where id=?")
@Where(clause = "deleted=false")
@Check(constraints = "percent > 0 and life_time>0")
public class BronTariff extends AbsEntity {

    private Double lifeTime;

    private Double percent;

    private boolean disable;

    private Double finishTime;  // Bu soatda va konsertni birinchi kunigacha bo'lgan vaqt

    private Timestamp expireTime; // O'lish vaqti

    public BronTariff(Double lifeTime, Double percent, boolean disable, Double finishTime) {
        this.lifeTime = lifeTime;
        this.percent = percent;
        this.disable = disable;
        this.finishTime = finishTime;
    }
}
