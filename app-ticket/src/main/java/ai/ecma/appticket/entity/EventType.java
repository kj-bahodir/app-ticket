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

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "update event_type set deleted=true where id=?")
@Where(clause = "deleted=false")
public class EventType extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

}
