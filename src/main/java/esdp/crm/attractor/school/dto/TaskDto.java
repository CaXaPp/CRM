package esdp.crm.attractor.school.dto;

import esdp.crm.attractor.school.entity.TaskType;
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
public class TaskDto {
    private Long id;

    private String name;

    private User employee;

    private TaskType type;

    private LocalDateTime createdAt;

    private LocalDateTime deadline;

}
