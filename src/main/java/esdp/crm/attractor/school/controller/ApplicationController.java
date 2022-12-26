package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    public static final int MINUTE_IN_DAY = 1439;
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

    @GetMapping("/all/dashboard/today")
    public ResponseEntity<List<Application>> applicationsCountToday() {
        LocalDateTime start = LocalDateTime.now().minusDays(1).plusMinutes(MINUTE_IN_DAY - (LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute());
        LocalDateTime end = LocalDateTime.now().plusDays(1).minusMinutes((LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute());
        return new ResponseEntity<>(applicationService.getAllForDashboardByDate(start, end), HttpStatus.OK);
    }

    @GetMapping("/all/dashboard/yesterday")
    public ResponseEntity<List<Application>> applicationsCountYesterday() {
        LocalDateTime start = LocalDateTime.now().minusDays(2).plusMinutes(1439 - (LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute());
        LocalDateTime end = LocalDateTime.now().minusMinutes((LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute());
        return new ResponseEntity<>(applicationService.getAllForDashboardByDate(start, end), HttpStatus.OK);yukuuh
    }

    @GetMapping("/all/dashboard/week")
    public ResponseEntity<List<Application>> applicationsCountWeek() {
        LocalDateTime start = LocalDateTime.now().minusDays(8).plusMinutes(1439 - (LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute());
        LocalDateTime end = LocalDateTime.now().minusMinutes((LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute());
        return new ResponseEntity<>(applicationService.getAllForDashboardByDate(start, end), HttpStatus.OK);
    }

    @GetMapping("/all/dashboard/month")
    public ResponseEntity<List<Application>> applicationsCountMonth() {
        LocalDateTime start = LocalDateTime.now().minusDays(31).plusMinutes(1439 - (LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute());
        LocalDateTime end = LocalDateTime.now();
        return new ResponseEntity<>(applicationService.getAllForDashboardByDate(start, end), HttpStatus.OK);
    }
}
