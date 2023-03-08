package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<UserDto>> getAllEmployee() {
        return new ResponseEntity<>(userService.getAllEmployee(), HttpStatus.OK);
    }

    @GetMapping("/not-admin")
    public ResponseEntity<List<UserDto>> getAllNotInAdmin() {
        return new ResponseEntity<>(userService.getAllNotInAdmin(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/auth_user")
    public ResponseEntity<UserDto> getUserRole(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.findByEmail(user.getEmail()), HttpStatus.OK);
    }

}
