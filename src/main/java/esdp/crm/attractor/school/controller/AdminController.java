package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.dto.RoleDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.FunnelFormDto;
import esdp.crm.attractor.school.dto.request.ProductFormDto;
import esdp.crm.attractor.school.dto.request.RegisterFormDto;
import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.ProductExistsException;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private final FunnelService funnelService;
    private final StatusService statusService;

    @GetMapping
    public ModelAndView adminPage(@AuthenticationPrincipal User principal) {
        return new ModelAndView("admin");
    }

    @GetMapping("/create/user")
    public ModelAndView createUser(@AuthenticationPrincipal User principal) {
        return new ModelAndView("register")
                .addObject("roles", roleService.getAll())
                .addObject("departments",departmentService.getAll())
                .addObject("action", "creation");
    }

    @PostMapping(value = "/create/user")
    public String createUser(@Valid @ModelAttribute RegisterFormDto registerFormDto) {
        UserDto user = userService.save(registerFormDto);
        return "redirect:/admin";
    }

    @GetMapping("/create/product")
    public ModelAndView createProduct() {
        return new ModelAndView("createProduct").addObject("departments", departmentService.getAll());
    }

    @PostMapping("/create/product")
    public String createProduct(@Valid @ModelAttribute ProductFormDto form) throws ProductExistsException {
        productService.save(form);
        return "redirect:/admin";
    }

    @GetMapping("/create/funnel")
    public ModelAndView createFunnel() {
        return new ModelAndView("funnelCreate").addObject("departments", departmentService.getAll());
    }

    @PostMapping("/create/funnel")
    public String createFunnel(@Valid @ModelAttribute FunnelFormDto form) {
        funnelService.save(form);
        return "redirect:/admin";
    }

    @GetMapping("/create/department")
    public ModelAndView createDepartment() {
        return new ModelAndView("createDepartment");
    }

    @PostMapping("/create/department")
    public String createDepartment(@Valid @ModelAttribute DepartmentDto dto) {
        return "redirect:/admin";
    }

    @GetMapping("/find/user")
    public ModelAndView findUser() {
        return new ModelAndView("findUser")
                .addObject("users", userService.findAll());
    }

    @GetMapping("/edit/user/{id}")
    public ModelAndView editUser(@PathVariable Long id) {
        return new ModelAndView("editUser").addObject("form", userService.getUserForm(id))
                .addObject("roles", roleService.getAll())
                .addObject("departments" , departmentService.getAll())
                .addObject("statuses" , statusService.getAll());
    }

    @PostMapping("/edit/user/{id}")
    public String editUser(@Valid @ModelAttribute RegisterFormDto dto) {
        userService.editUser(dto);
        return "redirect:/admin/edit/user/{id}";
    }

}
