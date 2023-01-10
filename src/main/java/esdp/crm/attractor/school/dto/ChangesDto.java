package esdp.crm.attractor.school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangesDto {
    private String property;
    private String oldRecord;
    private String newRecord;
}
