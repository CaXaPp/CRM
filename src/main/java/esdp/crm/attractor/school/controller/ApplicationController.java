package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.service.ApplicationService;
import esdp.crm.attractor.school.service.ApplicationStatusService;
import esdp.crm.attractor.school.service.ClientSourceService;
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

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ProductService productService;
    private final ClientSourceService clientSourceService;
    private final ApplicationStatusService applicationStatusService;

    @GetMapping("/save")
    public ModelAndView getLandingPage() {
        return new ModelAndView("landing")
                .addObject("products", productService.getAll())
                .addObject("sources", clientSourceService.getAll());
    }

    @PostMapping("/save")
    public String postApplicationFromLandingPage(@Valid @ModelAttribute esdp.crm.attractor.school.dto.request.ApplicationFormDto application) {
        applicationService.save(application);
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
        model.addAttribute("products", productService.getAll());
        model.addAttribute("statuses", applicationStatusService.getAll());
        return "editApplication";
    }

    @PostMapping("/edit/{id}")
    public String saveApplicationAfterEdit(@PathVariable Long id,
                                           @Valid @ModelAttribute ApplicationFormDto form) {
        applicationService.save(form);
        return "redirect:/application/edit/" + id;
    }

}
