package esdp.crm.attractor.school.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_types")
public class TaskType extends BaseEntity{
    @Column(name = "name")
    private String name;
}
