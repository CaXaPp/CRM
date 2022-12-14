package esdp.crm.attractor.school.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationFormDto {
    private Long id;
    private String name;
    private String company;
    private Long productId;
    private String phone;
    private String email;
    private String address;
    private Long sourceId;
    private Long employeeId;
    private LocalDateTime createdAt;
}
