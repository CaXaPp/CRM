package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.repository.TaskTypeRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TaskMapper {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TaskTypeRepository taskTypeRepository;

    @Mapping(target="employee", expression = "java(userRepository.getById(task.getId()))")
    @Mapping(target = "type", expression = "java(taskTypeRepository.getById(task.getId()))")
    public abstract TaskDto toTaskDto(Task task);
}
