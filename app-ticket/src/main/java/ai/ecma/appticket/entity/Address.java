package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update address set deleted=true where id=?")
@Where(clause = "deleted=false")
public class Address extends AbsEntity {

    private double lat;

    private double lon;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String target;

    @OneToOne
    private Attachment photo;


}
