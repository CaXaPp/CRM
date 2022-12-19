package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ProductService productService;
    private final ClientSourceService clientSourceService;

    @GetMapping
    public String mainApplications() {
        return "applications";
    }

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
    public ResponseEntity<List<Application>> applications() {
        return new ResponseEntity<>(applicationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ApplicationDto> getApplicationForEdit(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.getApplicationById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/edit/{id}")
    public void updateApplication(@PathVariable Long id,
                                  @RequestParam("company") String company,
                                  @RequestParam("price") BigDecimal price,
                                  @RequestParam("product") Long productId,
                                  @RequestParam("name") String name,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("email") String email,
                                  @RequestParam("address") String address,
                                  @RequestParam("employee") Long employeeId,
                                  @RequestParam("status") Long statusId) {

        applicationService.updateApplication(id, company, price, productId, name, phone, email, address, employeeId, statusId);
    }

}
