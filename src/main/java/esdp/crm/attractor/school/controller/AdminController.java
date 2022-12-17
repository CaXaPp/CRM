package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.DepartmentService;
import esdp.crm.attractor.school.service.ProductService;
import esdp.crm.attractor.school.service.RoleService;
import esdp.crm.attractor.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final RoleService roleService;
    private final DepartmentService departmentService;

    @GetMapping
    public ModelAndView adminPage(@AuthenticationPrincipal User principal) {
        return new ModelAndView("admin");
    }

    @GetMapping("/create/user")
    public ModelAndView createUser(@AuthenticationPrincipal User principal) {
        List<DepartmentDto> departments = departmentService.getAll();
        return new ModelAndView("register")
                .addObject("roles", roleService.getAll())
                .addObject("departments",departments);
    }

    @PostMapping(value = "/create/user")
    public String createUser(@Valid @ModelAttribute RegisterFormDto registerFormDto) {
        UserDto user = userService.createUser(registerFormDto);
        return "redirect:/create/user";
    }

    @GetMapping("/create/product")
    public ModelAndView createProduct() {
        return new ModelAndView("createProduct");
    }

    @PostMapping("/create/product")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto) {
        productService.save(productDto);
        return "redirect:/admin";
    }
}
