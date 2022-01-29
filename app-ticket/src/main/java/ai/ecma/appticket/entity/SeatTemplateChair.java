package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
import ai.ecma.appticket.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update seat_template_chair set deleted=true where id=?")
@Where(clause = "deleted=false")
@Table(name = "seat_template_chair", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"section", "row", "seat_template_id","name"})
})
public class SeatTemplateChair extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "seat_template_id")
    private SeatTemplate seatTemplate;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private SeatStatusEnum status;

    private String section;

    private String row;

    private String name;
}
