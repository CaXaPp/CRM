package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.ApplicationDetailsAndStatusDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ProductService productService;
    private final ClientSourceService clientSourceService;
    private final ApplicationStatusService applicationStatusService;
    private final String employee_role = "ROLE_EMPLOYEE";
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
    public String postApplicationFromLandingPage(@Valid @ModelAttribute ApplicationFormDto application) {
        applicationService.save(application);
        return "redirect:/application/save";
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationDto>> applications() {
        return new ResponseEntity<>(applicationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/all-sort-by-id")
    public ResponseEntity<List<ApplicationDto>> getAllSortById() {
        return new ResponseEntity<>(applicationService.getAllSortedById(), HttpStatus.OK);
    }

    @GetMapping("/all-active")
    public ResponseEntity<List<ApplicationDto>> getAllActive() {
        return new ResponseEntity<>(applicationService.getAllActive(), HttpStatus.OK);
    }

    @GetMapping("/all-free")
    public ResponseEntity<List<ApplicationDto>> getAllFreeApplication() {
        return new ResponseEntity<>(applicationService.getAllFreeApplications(), HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ApplicationDto> getApplicationForEdit(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.getApplicationById(id), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateApplication(@Valid ApplicationFormDto application) {
        applicationService.updateApplication(application);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteApplication(@RequestParam(value = "id", required = false) Long id) {
        applicationService.deleteApplication(id);
        return new ResponseEntity<>("Application deleted", HttpStatus.OK);
    }

    @GetMapping("/set-status")
    public ResponseEntity<HttpStatus> setStatus(@RequestParam(value = "application", required = false) String applicationId,
                                @RequestParam(value = "status", required = false) String status) {
        applicationService.updateStatus(applicationStatusService.getStatusById(Long.parseLong(status)), applicationService.findById(Long.parseLong(applicationId)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all/date")
    public ResponseEntity<List<ApplicationDetailsAndStatusDto>> applicationsCountToday(
            @RequestParam(value = "startDate", required = false) String date1,
            @RequestParam(value = "endDate", required = false) String date2,
            @RequestParam(value = "parameter", required = false) String parameter,
            @AuthenticationPrincipal User user) {

        String allDepartments = "all";
        String myDepartment = "my";
        parameter = parameter.equals(allDepartments) ? null : parameter.equals(myDepartment) ? myDepartment : parameter;

        if (!Objects.equals(user.getRole().getValue(), employee_role)) {
            if (parameter == null) {
                return new ResponseEntity<>(applicationService.findAllSumAndCountByApplication(LocalDateTime.parse(date1), LocalDateTime.parse(date2)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(applicationService.findAllSumAndCountByApplicationAndValue(LocalDateTime.parse(date1), LocalDateTime.parse(date2), Long.parseLong(parameter)), HttpStatus.OK);
            }
        } else {
            if (Objects.equals(parameter, myDepartment)) {
                return new ResponseEntity<>(applicationService.findAllSumAndCountByApplicationByEmployeeId(LocalDateTime.parse(date1), LocalDateTime.parse(date2), user.getId()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(applicationService.findAllSumAndCountByApplicationAndValue(LocalDateTime.parse(date1), LocalDateTime.parse(date2), user.getDepartment().getId()), HttpStatus.OK);
            }
        }
    }

    @GetMapping("/section/active")
    public ResponseEntity<List<ApplicationDto>> findAllActiveApplicationForToday(@AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), employee_role)) {
            return new ResponseEntity<>(applicationService.findAllActiveApplicationForToday(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllActiveApplicationForTodayByUserId(user.getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/section/complete")
    public ResponseEntity<List<ApplicationDto>> findAllCompletedDealForToday(@AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), employee_role)) {
            return new ResponseEntity<>(applicationService.findAllCompletedDealsOnToday(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllCompletedDealsOnTodayByUserId(user.getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/section/source")
    public ResponseEntity<List<ApplicationDto>> findAllSourceOfApplication(@AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), employee_role)) {
            return new ResponseEntity<>(applicationService.findAllSourceOfApplication(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllSourceOfApplicationByUserId(user.getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/section/deal")
    public ResponseEntity<List<ApplicationDto>> findAllDealOfApplication(@AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), employee_role)) {
            return new ResponseEntity<>(applicationService.findAllDealOfApplication(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllDealOfApplicationByUserId(user.getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/section/failure")
    public ResponseEntity<List<ApplicationDto>> findAllFailureApplication(@AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), employee_role)) {
            return new ResponseEntity<>(applicationService.findAllFailureApplication(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllFailureApplicationByUserId(user.getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/sort/by-date")
    public ResponseEntity<List<ApplicationDto>> getAllApplicationByDateSort(@RequestParam(value = "startDate", required = false) String date1,
                                                                         @RequestParam(value = "endDate", required = false) String date2,
                                                                            @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.getAllApplicationByDate(LocalDateTime.parse(date1), LocalDateTime.parse(date2), status), HttpStatus.OK);
    }

    @GetMapping("/sort/by-id")
    public ResponseEntity<List<ApplicationDto>> sortById(@RequestParam(value = "start", required = false) String date1,
                                                               @RequestParam(value = "end", required = false) String date2,
                                                         @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.sortById(LocalDateTime.parse(date1), LocalDateTime.parse(date2), status), HttpStatus.OK);
    }

    @GetMapping("/sort/by-company")
    public ResponseEntity<List<ApplicationDto>> sortByCompanyName(@RequestParam(value = "start", required = false) String date1,
                                                               @RequestParam(value = "end", required = false) String date2,
                                                                  @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.sortByCompanyName(LocalDateTime.parse(date1), LocalDateTime.parse(date2), status), HttpStatus.OK);
    }

    @GetMapping("/sort/by-price")
    public ResponseEntity<List<ApplicationDto>> sortByPrice(@RequestParam(value = "start", required = false) String date1,
                                                         @RequestParam(value = "end", required = false) String date2,
                                                            @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.sortByPrice(LocalDateTime.parse(date1), LocalDateTime.parse(date2), status), HttpStatus.OK);
    }

    @GetMapping("/sort/by-product")
    public ResponseEntity<List<ApplicationDto>> sortByProduct(@RequestParam(value = "start", required = false) String date1,
                                                           @RequestParam(value = "end", required = false) String date2,
                                                              @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.sortByProduct(LocalDateTime.parse(date1), LocalDateTime.parse(date2), status), HttpStatus.OK);
    }

    @GetMapping("/sort/by-status")
    public ResponseEntity<List<ApplicationDto>> sortByStatus(@RequestParam(value = "start", required = false) String date1,
                                                          @RequestParam(value = "end", required = false) String date2,
                                                             @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.sortByStatus(LocalDateTime.parse(date1), LocalDateTime.parse(date2), status), HttpStatus.OK);
    }

    @GetMapping("/sort/by-employee")
    public ResponseEntity<List<ApplicationDto>> sortByEmployee(@RequestParam(value = "start", required = false) String date1,
                                                            @RequestParam(value = "end", required = false) String date2,
                                                               @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.sortByEmployee(LocalDateTime.parse(date1), LocalDateTime.parse(date2), status), HttpStatus.OK);
    }

    @GetMapping("/sort/find-by-company")
    public ResponseEntity<List<ApplicationDto>> sortAndFindByCompany(@RequestParam(value = "start", required = false) String date1,
                                                                  @RequestParam(value = "end", required = false) String date2,
                                                                  @RequestParam(value = "text", required = false) String text,
                                                                     @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(applicationService.findAllByCreatedAtBetweenAndCompanyStartingWith(LocalDateTime.parse(date1), LocalDateTime.parse(date2), text, status), HttpStatus.OK);
    }
}
