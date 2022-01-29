package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update verification_code set deleted=true where id=?")
@Where(clause = "deleted=false")
public class VerificationCode extends AbsEntity {

    private String phoneNumber;

    private String code;

    private boolean confirmed;
}
