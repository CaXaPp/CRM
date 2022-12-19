package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.DepartmentService;
import esdp.crm.attractor.school.service.RoleService;
import esdp.crm.attractor.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/create")
    public ModelAndView createUser(@AuthenticationPrincipal User principal) {
        List<DepartmentDto> departments = departmentService.getAll();
        return new ModelAndView("register")
                .addObject("roles", roleService.getAll())
                .addObject("departments",departments);
    }

    @PostMapping(value = "/create")
    public String createUser(@Valid @ModelAttribute RegisterFormDto registerFormDto) {
        UserDto user = userService.createUser(registerFormDto);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        return new ModelAndView("login");
    }

}
