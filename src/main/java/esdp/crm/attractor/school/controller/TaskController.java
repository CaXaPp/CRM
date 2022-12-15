package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.AddTaskDto;
import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.mapper.TaskMapper;
import esdp.crm.attractor.school.service.TaskService;
import esdp.crm.attractor.school.service.UserService;
import lombok.RequiredArgsConstructor;
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
    private final TaskMapper taskMapper;
    private final UserService userService;

    @GetMapping("/create")
    public ModelAndView createUser(@AuthenticationPrincipal User principal) {
        List<TaskDto> tasks = taskService.findAll();
        return new ModelAndView("/myTasks")
                .addObject("tasks",tasks)
                .addObject("taskDto", new AddTaskDto());
    }

//    @PostMapping(value = "/create")
//    public String createTask(@Valid @ModelAttribute AddTaskDto addTaskDto, Task task2) {
//        TaskDto task = taskMapper.toTaskDto(task2);
//        return "redirect:/login";
//    }


//    @GetMapping("/view")
//    public ModelAndView view(@AuthenticationPrincipal User principal,
//                             @RequestParam ("id") Task task) {
//        List<User> users = userService.findAllDevelopers();
//        return new ModelAndView("/viewTask")
//                .addObject("task", TaskMapper.to(task))
//                .addObject("principal", principal)
//                .addObject("developers", developers)
//                .addObject("workLogs", task.getWorkLogsList());
//    }


}
