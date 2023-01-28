package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.TaskFormDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final OperationService operationService;
    private final TaskTypeService taskTypeService;
    private final UserService userService;
    private final ApplicationService applicationService;

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
    public ResponseEntity<List<Task>> getApplicationForEdit(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTasksByApplicationId(id), HttpStatus.OK);
    }

    @GetMapping("/task/over")
    public ResponseEntity<List<Object[]>> getOverdueTask(Principal principal) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Optional<User> user = userService.findByEmail(principal.getName());

        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(taskService.getOverdueTask(localDateTime), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(taskService.getOverdueTaskByUserId(localDateTime, user.get().getId()), HttpStatus.OK);
        }
    }
    @GetMapping("/task/active")
    public ResponseEntity<List<Object[]>> getAllActiveTask(Principal principal) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Optional<User> user = userService.findByEmail(principal.getName());

        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(taskService.getAllActiveTask(localDateTime), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(taskService.getAllActiveTaskByUserId(localDateTime, user.get().getId()), HttpStatus.OK);
        }
    }
}
