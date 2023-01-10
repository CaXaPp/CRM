package esdp.crm.attractor.school.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.javers.core.metamodel.annotation.PropertyName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "funnels")
public class Funnel extends BaseEntity {
    @Column(name = "name", nullable = false)
    @PropertyName("Название воронки")
    private String name;

    @Override
    public int hashCode() {
        return super.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Funnel that = (Funnel) obj;
        return super.getId().equals(that.getId());
    }
}
