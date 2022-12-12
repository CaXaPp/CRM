package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.Product;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import esdp.crm.attractor.school.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @GetMapping
    public String applications(Model model,
                               @PageableDefault(sort = {"name"}, direction = Sort.Direction.ASC)Pageable pageable) {
        Page<Application> page = applicationService.getAll(pageable);

        model.addAttribute("page", page);
        return "applications";
    }

    @RequestMapping("/edit/{id}")
    public String editApplication(@PathVariable Long id, Model model) {
        model.addAttribute("application", applicationService.getApplicationById(id));
        return "editApplication";
    }

    @PostMapping("/edit")
    public String reSave(@RequestParam("id") Long id,
                         @RequestParam("company")String company,
                         @RequestParam("price") BigDecimal price,
                         @RequestParam("product")String product,
                         @RequestParam("name")String name,
                         @RequestParam("phone")String phone,
                         @RequestParam("email")String email,
                         @RequestParam("status")String status,
                         @RequestParam("employee")String employee,
                         RedirectAttributes attributes) {
        if(!userRepository.existsByEmail(email)) {
            attributes.addFlashAttribute("errorText", "This email already exists");
            String url = "redirect:/edit/";
            return url + '{' + id + '}';
        }
        applicationRepository.deleteById(id);

        Product product1 = Product.builder()
                .name(product)
                .price(price)
                .build();

        Application application = Application.builder()
                .id(id)
                .name(name)
                .company(company)
                .product(product1)
                .phone(phone)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();

        applicationService.save(application);

        return "editApplication";
    }

}
