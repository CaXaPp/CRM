package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.request.TaskFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.TaskType;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.repository.TaskTypeRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final UserMapper userMapper;
    private final ApplicationMapper applicationMapper;

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .description(task.getDescription())
                .quotes(task.getQuotes())
                .result(task.getResult())
                .application(applicationMapper.toDto(task.getApplication()))
                .employee(userMapper.toUserDto(task.getEmployee()))
                .type(task.getType())
                .createdAt(task.getCreatedAt())
                .deadline(task.getDeadline())
                .build();
    }

    public Task toEntity(Application application, LocalDateTime deadline, User user,
                         TaskType type, String description) {
        return Task.builder()
                .application(application)
                .employee(user)
                .deadline(deadline)
                .createdAt(LocalDateTime.now())
                .description(description)
                .type(type)
                .build();
    }
}
