package esdp.crm.attractor.school.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "application_statuses")
public class ApplicationStatus extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
}