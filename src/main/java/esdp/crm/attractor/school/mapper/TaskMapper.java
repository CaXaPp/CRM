package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.AddTaskDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.repository.TaskTypeRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final UserRepository userRepository;
    private final TaskTypeRepository taskTypeRepository;

    public TaskDto toTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .employee(userRepository.getById(task.getId()))
                .createdAt(task.getCreatedAt())
                .deadline(task.getDeadline())
                .type(taskTypeRepository.getById(task.getId()))
                .description(task.getDescription())
                .build();

    }
}
