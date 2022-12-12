package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.service.ApplicationService;
import esdp.crm.attractor.school.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ProductService productService;

    @GetMapping
    public ModelAndView getLandingPage() {
        return new ModelAndView("landing")
                .addObject("products", productService.getAllProductsDto());
    }

    @PostMapping
    public String postApplicationFromLandingPage(@Valid @ModelAttribute ApplicationDto applicationDto) {
        applicationDto.setCreatedAt(LocalDateTime.now());
        System.out.println(applicationDto.getName());
        System.out.println(applicationDto.getProduct());
        System.out.println(applicationDto.getEmail());
        System.out.println(applicationDto.getSource());
        applicationService.save(applicationDto);
        return "redirect:/application";
    }
}
