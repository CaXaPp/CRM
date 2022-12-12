package esdp.crm.attractor.school.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "deals")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    private DealStatus status;
}
