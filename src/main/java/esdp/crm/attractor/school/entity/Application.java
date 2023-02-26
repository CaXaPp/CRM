package esdp.crm.attractor.school.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javers.core.metamodel.annotation.PropertyName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "applications")
public class Application extends BaseEntity {
    @Column(name = "name", nullable = false)
    @PropertyName("Имя клиента")
    private String name;

    @Column(name = "company", nullable = false)
    @PropertyName("Название компании")
    private String company;

    @ManyToOne
    @PropertyName("Продукт")
    private Product product;

    @Column(name = "description")
    @PropertyName("Примечания")
    private String description;

    @Column(name = "phone", nullable = false)
    @PropertyName("Телефон")
    private String phone;

    @Column(name = "email", nullable = false)
    @PropertyName("Почта")
    private String email;

    @Column(name = "address")
    @PropertyName("Адрес")
    private String address;

    @ManyToOne
    @PropertyName("Сотрудник")
    private User employee;

    @Column(name = "price")
    @PropertyName("Цена")
    private BigDecimal price;

    @ManyToOne
    @PropertyName("Источник")
    private ClientSource source;

    @ManyToOne
    @PropertyName("Статус")
    private ApplicationStatus status;

    @Column(name = "date", nullable = false)
    @PropertyName("Дата создания")
    private LocalDateTime createdAt;
}
