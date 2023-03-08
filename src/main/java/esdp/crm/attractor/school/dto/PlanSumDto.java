package esdp.crm.attractor.school.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanSumDto {
    private Long id;
    private DepartmentDto department;
    private BigDecimal sum;
}
