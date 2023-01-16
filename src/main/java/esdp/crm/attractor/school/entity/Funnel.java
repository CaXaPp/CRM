package esdp.crm.attractor.school.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.javers.core.metamodel.annotation.PropertyName;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "funnel")
    private List<ApplicationStatus> statuses;

    @ManyToMany
    @JoinTable(
            name = "departments_funnels",
            joinColumns = {@JoinColumn(name = "funnels_id")},
            inverseJoinColumns = {@JoinColumn(name = "department_id")}
    )
    private List<Department> departments;
}
