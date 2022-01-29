package ai.ecma.appticket.entity;

import ai.ecma.appticket.entity.template.AbsEntity;
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
@SQLDelete(sql = "update event set deleted=true where id=?")
@Where(clause = "deleted=false")
public class Event extends AbsEntity {

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    private Address address;

    @ManyToOne
    private EventType eventType;

    @OneToOne
    private Attachment schema;

    @OneToOne
    private Attachment banner;

    @OneToOne(fetch = FetchType.LAZY)
    private BronTariff bronTariff;

    public Event(String name, String description, Address address, EventType eventType, Attachment schema, Attachment banner) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.eventType = eventType;
        this.schema = schema;
        this.banner = banner;
    }
}
