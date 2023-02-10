package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.entity.TaskType;
import esdp.crm.attractor.school.mapper.TaskTypeMapper;
import esdp.crm.attractor.school.repository.TaskTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskTypeService {
    private final TaskTypeRepository taskTypeRepository;
    private final TaskTypeMapper taskTypeMapper;

    public List<TaskTypeDto> findAll() {
        var tasks = taskTypeRepository.findAll();
        return tasks.stream()
                .map(taskTypeMapper::toTaskTypeDto)
                .collect(Collectors.toList());
    }
    public TaskType findById(Long id){
       return taskTypeRepository.findById(id).orElse(null);
    }
}
