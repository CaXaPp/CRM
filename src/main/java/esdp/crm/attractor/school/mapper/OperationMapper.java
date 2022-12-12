package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.OperationDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.repository.DealRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperationMapper {
    private final UserRepository userRepository;
    private final DealRepository dealRepository;

    public OperationDto toTaskDto(Task task) {
        return OperationDto.builder()
                .id(task.getId())
                .name(task.getName())
                .deal(dealRepository.getById(task.getId()))
                .employee(userRepository.getById(task.getId()))
                .createdAt(task.getCreatedAt())
                .deadline(task.getDeadline())
                .build();

    }
}
