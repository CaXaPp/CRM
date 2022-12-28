package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.TaskFormDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.OperationService;
import esdp.crm.attractor.school.service.TaskService;
import esdp.crm.attractor.school.service.TaskTypeService;
import esdp.crm.attractor.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final OperationService operationService;
    private final TaskTypeService taskTypeService;
    private final UserService userService;

    @GetMapping
    public ModelAndView task(@AuthenticationPrincipal User principal) {
        List<TaskDto> tasks = taskService.findAll();
        List<ApplicationDto> operations = operationService.getAll(principal);
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
}
