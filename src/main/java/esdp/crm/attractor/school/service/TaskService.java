package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.request.TaskFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.TaskType;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.TaskMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import esdp.crm.attractor.school.repository.TaskRepository;
import esdp.crm.attractor.school.repository.TaskTypeRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskTypeRepository taskTypeRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final TaskMapper taskMapper;

    public List<TaskDto> findAll() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public Task assignUser(Task task, User user) {
        task.setEmployee(user);
        return taskRepository.save(task);
    }

    public Task edit(Task task, String name, TaskType status) {
//        task.setName(name);
        task.setType(status);
        return taskRepository.save(task);
    }


    public TaskDto create(TaskFormDto form) throws NotFoundException {
        Application application = applicationRepository.findById(form.getOperationId())
                .orElseThrow(() -> new NotFoundException("Operation with id " + form.getOperationId() + " not found!"));
        User employee = userRepository.findById(form.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee with id " + form.getEmployeeId() + " not found!"));
        TaskType taskType = taskTypeRepository.findById(form.getTypeId())
                .orElseThrow(() -> new NotFoundException("Task type with id " + form.getTypeId() + " not found!"));
        Task task = taskMapper.toEntity(application, form.getDeadline(), employee, taskType, form.getDescription());
        Task saved = taskRepository.save(task);
        return taskMapper.toDto(saved);
    }

    public List<Task> getTasksByApplicationId(Long id) {
        return taskRepository.findAllById(id);
    }

}
