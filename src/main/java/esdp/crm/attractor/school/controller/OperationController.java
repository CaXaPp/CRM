package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.AddOperationDto;
import esdp.crm.attractor.school.dto.OperationDto;
import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.mapper.OperationMapper;
import esdp.crm.attractor.school.service.OperationService;
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
public class OperationController {

    private final OperationService operationService;
    private final OperationMapper operationMapper;
    private final UserService userService;

    @GetMapping("/create")
    public ModelAndView createUser(@AuthenticationPrincipal User principal) {
        return new ModelAndView("/myTasks")
                .addObject("taskDto", new AddOperationDto());
    }

    @PostMapping(value = "/create")
    public String createTask(@Valid @ModelAttribute AddOperationDto addOperationDto, Task task2) {
        OperationDto task = operationMapper.toTaskDto(task2);
        return "redirect:/login";
    }

//    @GetMapping("/view")
//    public ModelAndView view(@AuthenticationPrincipal User principal,
//                             @RequestParam ("id") Task task) {
//        List<User> users = userService.findAllDevelopers();
//        return new ModelAndView("/viewTask")
//                .addObject("task", OperationMapper.to(task))
//                .addObject("principal", principal)
//                .addObject("developers", developers)
//                .addObject("workLogs", task.getWorkLogsList());
//    }


}
