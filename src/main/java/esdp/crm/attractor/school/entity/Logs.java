package esdp.crm.attractor.school.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
