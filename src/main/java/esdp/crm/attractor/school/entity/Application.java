package esdp.crm.attractor.school.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "applications")
public class Application extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company", nullable = false)
    private String company;

    @ManyToOne
    private Product product;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address")
    private String address;

    @ManyToOne
    private User employee;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    private ClientSource source;

    @ManyToOne
    private ApplicationStatus status;

    @Column(name = "date", nullable = false)
    private LocalDateTime createdAt;
}
