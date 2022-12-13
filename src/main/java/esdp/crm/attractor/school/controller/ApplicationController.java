package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import esdp.crm.attractor.school.service.ApplicationService;
import esdp.crm.attractor.school.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final ProductService productService;

    @GetMapping("/save")
    public ModelAndView getLandingPage() {
        return new ModelAndView("landing")
                .addObject("products", productService.getAllProductsDto());
    }

    @PostMapping("/save")
    public String postApplicationFromLandingPage(@Valid @ModelAttribute ApplicationDto applicationDto) {
        applicationDto.setCreatedAt(LocalDateTime.now());
        applicationService.save(applicationDto);
        return "redirect:/application/save";
    }

    @GetMapping("/all")
    public String applications(Model model,
                               @PageableDefault(sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Application> page = applicationService.getAll(pageable);

        model.addAttribute("page", page);
        return "applications";
    }

    @GetMapping("/edit/{id}")
    public String editApplication(@PathVariable Long id, Model model) {
        model.addAttribute("application", applicationService.getApplicationById(id));
        model.addAttribute("products", productService.getAllProductsDto());
        return "editApplication";
    }

    @PostMapping("/edit/{id}")
    public String saveApplicationAfterEdit(@PathVariable Long id,
                                           @Valid @ModelAttribute ApplicationDto applicationDto) {

        applicationRepository.deleteById(id);
        applicationDto.setId(id);
        applicationService.save(applicationDto);

        return "redirect:/application";
    }

}
