package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.TaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskTypeMapper {

    public TaskTypeDto toTaskDto(TaskType type) {
        return TaskTypeDto.builder()
                .id(type.getId())
                .name(type.getName())
                .build();


    }
}
