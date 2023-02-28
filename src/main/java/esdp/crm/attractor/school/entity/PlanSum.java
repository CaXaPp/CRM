package esdp.crm.attractor.school.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "plan_sums")
public class PlanSum extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    private Department department;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum;
}
