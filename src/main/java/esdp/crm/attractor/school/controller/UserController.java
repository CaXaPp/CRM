package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.dto.RegisterFormDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.entity.Department;
import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.DepartmentService;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DepartmentService departmentService;

    @GetMapping("/create")
    public ModelAndView createUser(@AuthenticationPrincipal User principal) {
        Role[] roles = Role.values();
        List<DepartmentDto> departments = departmentService.findAll();
        return new ModelAndView("register")
                .addObject("roles",roles)
                .addObject("departments",departments);
    }

    @PostMapping(value = "/create")
    public String createUser(@Valid @ModelAttribute RegisterFormDto registerFormDto) {
        UserDto user = userService.createUser(registerFormDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        return new ModelAndView("login");
    }

}
