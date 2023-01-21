package esdp.crm.attractor.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "departments_funnels",
            joinColumns = {@JoinColumn(name = "funnels_id")},
            inverseJoinColumns = {@JoinColumn(name = "department_id")}
    )
    @JsonIgnore
    private Set<Funnel> funnels = new HashSet<>();
}
