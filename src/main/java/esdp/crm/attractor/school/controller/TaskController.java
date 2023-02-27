package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.TaskFormDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.mapper.TaskMapper;
import esdp.crm.attractor.school.service.OperationService;
import esdp.crm.attractor.school.service.TaskService;
import esdp.crm.attractor.school.service.TaskTypeService;
import esdp.crm.attractor.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final OperationService operationService;
    private final TaskTypeService taskTypeService;
    private final UserService userService;

    private final TaskMapper taskMapper;

    @GetMapping
    public ModelAndView getTasks(@AuthenticationPrincipal User principal) {
        List<TaskDto> tasks = taskService.findAll();
        List<ApplicationDto> operations = (List<ApplicationDto>) operationService.getAll(principal, 0L).get("operations");
        List<UserDto> employees = userService.findAllByUser(principal);
        List<TaskTypeDto> types = taskTypeService.findAll();
        return new ModelAndView("tasks")
                .addObject("tasks", tasks)
                .addObject("operations", operations)
                .addObject("employees", employees)
                .addObject("types", types);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @ModelAttribute TaskFormDto form) {
        return ResponseEntity.ok(taskService.create(form));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskFormDto> getApplicationForEdit(@PathVariable Long id) {
        return ResponseEntity.ok().body(taskMapper.toTaskFormDto(taskService.getTasksById(id)));
    }

    @PutMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> editTask(@PathVariable(value = "id") Task task, @RequestBody TaskFormDto taskDto) {
        return new ResponseEntity<>(taskService.editTask(task, taskDto), HttpStatus.OK);
    }

    @GetMapping("/task/over")
    public ResponseEntity<List<TaskDto>> getOverdueTask(@AuthenticationPrincipal User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (!Objects.equals(user.getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(taskService.getOverdueTask(localDateTime), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(taskService.getOverdueTaskByUserId(localDateTime, user.getId()), HttpStatus.OK);
        }
    }
    @GetMapping("/task/active")
    public ResponseEntity<List<TaskDto>> getAllActiveTask(@AuthenticationPrincipal User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (!Objects.equals(user.getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(taskService.getAllActiveTask(localDateTime), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(taskService.getAllActiveTaskByUserId(localDateTime, user.getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/tasks-by-id/{id}")
    public ResponseEntity<List<TaskDto>> getTasksByApplicationId(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTasksByApplicationId(id), HttpStatus.OK);
    }
}
