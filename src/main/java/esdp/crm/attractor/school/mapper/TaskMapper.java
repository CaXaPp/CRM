package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.repository.DealRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final UserRepository userRepository;
    private final DealRepository dealRepository;

    public TaskDto toTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .deal(dealRepository.getById(task.getId()))
                .employee(userRepository.getById(task.getId()))
                .createdAt(task.getCreatedAt())
                .deadline(task.getDeadline())
                .build();

    }
}
