package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import ai.ecma.appticket.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update pay_type set deleted=true where id=?")
@Where(clause = "deleted=false")
public class PayType extends AbsEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private PayTypeEnum payTypeEnum;
}
