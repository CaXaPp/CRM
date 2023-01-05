package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    public static final int MINUTE_IN_DAY = 1439;
    private final ApplicationService applicationService;
    private final ProductService productService;
    private final ClientSourceService clientSourceService;
    private final ApplicationStatusService statusService;

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

    @ResponseBody
    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpStatus updateApplication(@Valid ApplicationFormDto application) {
        applicationService.updateApplication(application);
        return HttpStatus.OK;
    }

    @GetMapping("/all/date")
    public ResponseEntity<ArrayList<Float>> applicationsCountToday(
            @RequestParam(value = "startDate", required = false) String date1,
            @RequestParam(value = "endDate", required = false) String date2) {

        LocalDateTime start = LocalDateTime.parse(date1);
        LocalDateTime end = LocalDateTime.parse(date2);

        ArrayList<Float> sumOfPrice = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            if (i == 1 || i == 2 || i == 3 || i == 6) {
                sumOfPrice.add(applicationService.getApplicationByPriceAndStatus(start, end, (long) i));
                sumOfPrice.add(applicationService.getApplicationCount(start, end, (long) i));
            }
        }

        return new ResponseEntity<>(sumOfPrice, HttpStatus.OK);
    }
}
