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
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TaskMapper {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TaskTypeRepository taskTypeRepository;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected ApplicationMapper applicationMapper;

    @Mapping(target="employee", expression = "java(userMapper.toUserDto(task.getEmployee()))")
    @Mapping(target = "application", expression = "java(applicationMapper.toDto(task.getApplication()))")
    public abstract TaskDto toDto(Task task);

    @Mapping(target = "employee", expression = "java(userMapper.toUser(taskDto.getEmployee()).get())")
    @Mapping(target = "application", expression = "java(applicationMapper.toEntity(taskDto.getApplication()))")
    public abstract Task toEntity(TaskDto taskDto);

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
