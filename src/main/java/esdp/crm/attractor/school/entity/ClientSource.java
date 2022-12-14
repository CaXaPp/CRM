package esdp.crm.attractor.school.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client_sources")
public class ClientSource extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
}
