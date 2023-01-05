package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsDto {
    private Long id;
    private LocalDateTime date;
    private String description;
    private UserDto employee;
    private String status;
}
