package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.dto.request.TaskFormDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.TaskType;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.mapper.TaskMapper;
import esdp.crm.attractor.school.repository.TaskRepository;
import esdp.crm.attractor.school.repository.TaskTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper taskMapper;
    private final TaskTypeRepository taskTypeRepository;

    public List<TaskDto> findAll() {
        var tasks = repository.findAll();
        return tasks.stream()
                .map(taskMapper::toTaskDto)
                .collect(Collectors.toList());
    }

    public Task assignUser(Task task, User user) {
        task.setEmployee(user);
        return repository.save(task);
    }

    public Task edit(Task task, String name, TaskType status) {
        task.setName(name);
        task.setType(status);
        return repository.save(task);
    }


    public Task create(TaskFormDto dto, User principal){
        Task task = new Task();
        task.setCreatedAt(dto.getCreatedTime());
        task.setName(dto.getName());
        task.setEmployee(principal);
        task.setType(taskTypeRepository.getById(task.getId()));
        return repository.save(task);
    }

}
