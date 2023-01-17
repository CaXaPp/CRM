package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.DepartmentService;
import esdp.crm.attractor.school.service.RoleService;
import esdp.crm.attractor.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        return new ModelAndView("login");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<String> getUsernameByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return new ResponseEntity<>(user.get().getFirstName() + " " + user.get().getSurname(), HttpStatus.OK);
    }

}
