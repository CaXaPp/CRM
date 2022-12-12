package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.Deal;
import esdp.crm.attractor.school.entity.TaskType;
import esdp.crm.attractor.school.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    private Long id;

    private String name;

    private Deal deal;

    private User employee;

    private TaskType type;

    private LocalDateTime createdAt;

    private LocalDateTime deadline;

}
