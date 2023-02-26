package esdp.crm.attractor.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFormDto {
    private Long id;
    private String name;
    private Long departmentId;
}
