package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.service.ApplicationService;
import esdp.crm.attractor.school.service.ClientSourceService;
import esdp.crm.attractor.school.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final ApplicationService applicationService;
    private final ProductService productService;
    private final ClientSourceService clientSourceService;

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/client")
    public ModelAndView getLandingPage() {
        return new ModelAndView("landing")
                .addObject("products", productService.getAll())
                .addObject("sources", clientSourceService.getAll());
    }

    @PostMapping("/client/save")
    public String postApplicationFromLandingPage(@Valid @ModelAttribute ApplicationFormDto application) {
        applicationService.save(application);
        return "redirect:/client";
    }
}
