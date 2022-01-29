package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update speaker set deleted=true where id=?")
@Where(clause = "deleted=false")
public class Speaker extends AbsEntity {

    private String fullName;

    @JoinColumn(name ="speaker_specialization")
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Specialization> specializations;

    @Column(columnDefinition = "text")
    private String description;

    @OneToOne
    private Attachment photo;

}
