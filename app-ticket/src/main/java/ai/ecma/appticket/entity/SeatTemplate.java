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
@SQLDelete(sql = "update seat_template set deleted=true where id=?")
@Where(clause = "deleted=false")
public class SeatTemplate extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne
    private Attachment schema;

    @JsonIgnore
    @OneToMany(mappedBy = "seatTemplate",cascade = CascadeType.ALL)
    private List<SeatTemplateChair> seatTemplateChairList;

    public SeatTemplate(String name, Attachment schema) {
        this.name = name;
        this.schema = schema;
    }
}
