package esdp.crm.attractor.school.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "application_statuses")
public class ApplicationStatus extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
}