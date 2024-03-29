package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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
    private final TaskTypeService taskTypeService;
    private final ApplicationService applicationService;
    private final UserService userService;

    public List<TaskDto> findAll() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> findAllByUserId(Long id) {
        var tasks = taskRepository.findAllByEmployee_Id(id);
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
        Task task = taskMapper.toEntity(application, form.getDeadline(), employee, taskType, form.getDescription(), form.getResult());
        Task saved = taskRepository.save(task);
        return taskMapper.toDto(saved);
    }

    public Task getTasksById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
    public Task editTask(Task task, TaskFormDto taskDto){
        task.setApplication(applicationService.findById(taskDto.getOperationId()));
        task.setDeadline(taskDto.getDeadline());
        task.setType(taskTypeService.findById(taskDto.getTypeId()));
        task.setDescription(taskDto.getDescription());
        task.setResult(taskDto.getResult());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskDto> getTasksByApplicationId(Long id) {
        return taskRepository.findAllByApplication_Id(id).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> getOverdueTask(LocalDateTime now) {
        return taskRepository.findAllByDeadlineBefore(now).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> getOverdueTaskByUserId(LocalDateTime now, Long userId) {
        return taskRepository.findAllByDeadlineBeforeAndEmployee_Id(now, userId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> getAllActiveTask(LocalDateTime now) {
        return taskRepository.findAllByDeadlineAfter(now).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> getAllActiveTaskByUserId(LocalDateTime now, Long userId) {
        return taskRepository.findAllByDeadlineAfterAndEmployee_Id(now, userId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }
}
