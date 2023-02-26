package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ProductService productService;
    private final ClientSourceService clientSourceService;
    private final UserService userService;
    private final ApplicationStatusService applicationStatusService;

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
    public ResponseEntity<List<Application>> applications() {
        return new ResponseEntity<>(applicationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ApplicationDto> getApplicationForEdit(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.getApplicationById(id), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateApplication(@Valid ApplicationFormDto application) {
        applicationService.updateApplication(application);
//        return HttpStatus.OK;
    }

    @GetMapping("/set-status")
    public ResponseEntity<HttpStatus> setStatus(@RequestParam(value = "application", required = false) String applicationId,
                                @RequestParam(value = "status", required = false) String status) {
        Optional<ApplicationStatus> applicationStatus = applicationStatusService.getStatusById(Long.parseLong(status));
        Application application = applicationService.findById(Long.parseLong(applicationId));
        applicationService.updateStatus(applicationStatus.get(), application);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all/date")
    public ResponseEntity<Map<String, List<Float>>> applicationsCountToday(
            @RequestParam(value = "startDate", required = false) String date1,
            @RequestParam(value = "endDate", required = false) String date2,
            @RequestParam(value = "parameter", required = false) String parameter,
            Principal principal) {

        String[] statuses = {"Новое", "Переговоры", "Принятие решения", "На обслуживании", "Успешно"};

        List<Long> usersId = (parameter.equals("all")) ? null :
                (parameter.equals("my")) ? Arrays.asList(userService.getUserIdByEmail(principal.getName())) :
                        userService.getAllUserId(Long.parseLong(parameter));

        Map<String, List<Float>> data = new HashMap<>();

        for (String status : statuses) {
            List<ApplicationStatus> statusIds = applicationStatusService.getStatusIdByName(status);
            Float fl1 = 0F;
            Float fl2 = 0F;
            for (ApplicationStatus applicationStatus : statusIds) {
                List<Object[]> resultList;
                if (usersId == null) {
                    resultList = applicationService.findAllSumAndCountByApplication(LocalDateTime.parse(date1), LocalDateTime.parse(date2), applicationStatus.getId());
                    for (Object[] obj : resultList) {
                        fl1 += (obj[0] != null) ? Float.parseFloat(obj[0].toString()) : 0F;
                        fl2 += (obj[1] != null) ? Float.parseFloat(obj[1].toString()) : 0F;
                    }
                    if (!resultList.isEmpty()) data.put(status, List.of(fl1, fl2));
                } else {
                    for (Long id : usersId) {
                        resultList = applicationService.findAllSumAndCountByApplicationByEmployeeId(LocalDateTime.parse(date1), LocalDateTime.parse(date2), applicationStatus.getId(), id);
                        for (Object[] obj : resultList) {
                            fl1 += (obj[0] != null) ? Float.parseFloat(obj[0].toString()) : 0F;
                            fl2 += (obj[1] != null) ? Float.parseFloat(obj[1].toString()) : 0F;
                        }
                        if (!resultList.isEmpty()) data.put(status, List.of(fl1, fl2));
                    }
                }
            }
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/activeApplication")
    public ResponseEntity<List<Object[]>> findAllActiveApplicationForToday(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(applicationService.findAllActiveApplicationForToday(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllActiveApplicationForTodayByUserId(user.get().getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/completedDeal")
    public ResponseEntity<List<Object[]>> findAllCompletedDealForToday(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(applicationService.findAllComplatedDealsOnToday(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllComplatedDealsOnTodayByUserId(user.get().getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/sourceApplication")
    public ResponseEntity<List<Object[]>> findAllSourceOfApplication(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(applicationService.findAllSourceOfApplication(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllSourceOfApplicationByUserId(user.get().getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/deal-by-employee")
    public ResponseEntity<List<Object[]>> findAllDealOfApplication(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(applicationService.findAllDealOfApplication(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllDealOfApplicationByUserId(user.get().getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/failureApplication")
    public ResponseEntity<List<Object[]>> findAllFailureApplication(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(applicationService.findAllFailureApplication(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.findAllFailureApplicationByUserId(user.get().getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/sort/by-date")
    public ResponseEntity<List<Application>> getAllApplicationByDateSort(@RequestParam(value = "startDate", required = false) String date1,
                                                                         @RequestParam(value = "endDate", required = false) String date2) {
        return new ResponseEntity<>(applicationService.getAllApplicationByDate(LocalDateTime.parse(date1), LocalDateTime.parse(date2)), HttpStatus.OK);
    }

    @GetMapping("/sort/by-company")
    public ResponseEntity<List<Application>> sortByCompanyName(@RequestParam(value = "start", required = false) String date1,
                                                               @RequestParam(value = "end", required = false) String date2) {
        return new ResponseEntity<>(applicationService.sortByCompanyName(LocalDateTime.parse(date1), LocalDateTime.parse(date2)), HttpStatus.OK);
    }

    @GetMapping("/sort/by-price")
    public ResponseEntity<List<Application>> sortByPrice(@RequestParam(value = "start", required = false) String date1,
                                                         @RequestParam(value = "end", required = false) String date2) {
        return new ResponseEntity<>(applicationService.sortByPrice(LocalDateTime.parse(date1), LocalDateTime.parse(date2)), HttpStatus.OK);
    }

    @GetMapping("/sort/by-product")
    public ResponseEntity<List<Application>> sortByProduct(@RequestParam(value = "start", required = false) String date1,
                                                           @RequestParam(value = "end", required = false) String date2) {
        return new ResponseEntity<>(applicationService.sortByProduct(LocalDateTime.parse(date1), LocalDateTime.parse(date2)), HttpStatus.OK);
    }

    @GetMapping("/sort/by-status")
    public ResponseEntity<List<Application>> sortByStatus(@RequestParam(value = "start", required = false) String date1,
                                                          @RequestParam(value = "end", required = false) String date2) {
        return new ResponseEntity<>(applicationService.sortByStatus(LocalDateTime.parse(date1), LocalDateTime.parse(date2)), HttpStatus.OK);
    }

    @GetMapping("/sort/by-employee")
    public ResponseEntity<List<Application>> sortByEmployee(@RequestParam(value = "start", required = false) String date1,
                                                            @RequestParam(value = "end", required = false) String date2) {
        return new ResponseEntity<>(applicationService.sortByEmployee(LocalDateTime.parse(date1), LocalDateTime.parse(date2)), HttpStatus.OK);
    }

    @GetMapping("/sort/find-by-company")
    public ResponseEntity<List<Application>> sortAndFindByCompany(@RequestParam(value = "start", required = false) String date1,
                                                                  @RequestParam(value = "end", required = false) String date2,
                                                                  @RequestParam(value = "text", required = false) String text) {
        return new ResponseEntity<>(applicationService.findAllByCreatedAtBetweenAndCompanyStartingWith(LocalDateTime.parse(date1), LocalDateTime.parse(date2), text), HttpStatus.OK);
    }
}
