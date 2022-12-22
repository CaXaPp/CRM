package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.request.TaskFormDto;
import esdp.crm.attractor.school.dto.TaskDto;
import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.ApplicationService;
import esdp.crm.attractor.school.service.RedirectUtil;
import esdp.crm.attractor.school.service.TaskService;
import esdp.crm.attractor.school.service.TaskTypeService;
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
    private final TaskTypeService taskTypeService;

    private final ApplicationService applicationService;

    @GetMapping("/task")
    public ModelAndView task(@AuthenticationPrincipal User principal) {
        List<TaskDto> tasks = taskService.findAll();
        return new ModelAndView("tasks")
                .addObject("tasks", tasks);
    }

    @GetMapping("/addTask")
    public ModelAndView createTask(@AuthenticationPrincipal User user) {
        List<TaskTypeDto> tasks = taskTypeService.findAll();
        return new ModelAndView("/deal")
                .addObject("user", user)
                .addObject("tasks", tasks);
    }
    @GetMapping("/save")
    public ModelAndView createUser(@AuthenticationPrincipal User principal) {
        return new ModelAndView("/deal")
                .addObject("taskDto", new TaskFormDto());
    }

    @PostMapping(value = "/save")
    public ModelAndView createUser(@ModelAttribute("taskDto") @Valid TaskFormDto dto,
                                   @AuthenticationPrincipal User principal) {
        Task task = taskService.create(dto, principal);
        return RedirectUtil.redirect("/");
    }


}
