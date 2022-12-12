package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.ClientSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private Long id;

    private String name;

    private String company;

    private String product;

    private String phone;

    private String email;

    private LocalDateTime createdAt;

    private String source;
}
