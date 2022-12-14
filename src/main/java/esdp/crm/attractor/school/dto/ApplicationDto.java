package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.ClientSource;
import esdp.crm.attractor.school.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDto {
    private Long id;

    private String name;

    private String company;

    private ProductDto product;

    private String phone;

    private String email;

    private String address;

    private UserDto employee;

    private BigDecimal price;

    private ClientSource source;

    private ApplicationStatus status;

    private LocalDateTime createdAt;
}
