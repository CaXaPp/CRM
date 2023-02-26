package esdp.crm.attractor.school.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "logs")
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime date;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "application_status_id")
    private ApplicationStatus status;
}
