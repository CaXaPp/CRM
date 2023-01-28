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
    public ResponseEntity<ApplicationDto> getApplicationForEdit(@PathVariable Long id, Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getName(), "Сотрудник")) {
            return new ResponseEntity<>(applicationService.getApplicationById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ResponseBody
    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpStatus updateApplication(@Valid ApplicationFormDto application) {
        applicationService.updateApplication(application);
        return HttpStatus.OK;
    }

    @GetMapping("/all/date")
    public ResponseEntity<ArrayList<ArrayList<String>>> applicationsCountToday(
            @RequestParam(value = "startDate", required = false) String date1,
            @RequestParam(value = "endDate", required = false) String date2,
            @RequestParam(value = "parameter", required = false) String parameter,
            Principal principal) {

        LocalDateTime start = LocalDateTime.parse(date1);
        LocalDateTime end = LocalDateTime.parse(date2);
        String[] statuses = {"Новое", "Переговоры", "Принятие решения", "На обслуживании", "Успешно"};
        Long user_id;
        Long departmentId;

        ArrayList<ArrayList<String>> sumOfPrice = new ArrayList<>();
        ArrayList<String> newStatus = new ArrayList<>();
        ArrayList<String> negotiations = new ArrayList<>();
        ArrayList<String> decisionMaking = new ArrayList<>();
        ArrayList<String> onMaintenance = new ArrayList<>();
        ArrayList<String> success = new ArrayList<>();
        newStatus.add("Новое");
        negotiations.add("Переговоры");
        decisionMaking.add("Принятие решения");
        onMaintenance.add("На обслуживании");
        success.add("Успешно");
        ArrayList<Map<String, List<String>>> maps = new ArrayList<>();
        Map<String, List<String>> newSt = new HashMap<>();

        for (String status : statuses) {
            List<ApplicationStatus> statusId = applicationStatusService.getStatusIdByName(status);
            for (ApplicationStatus applicationStatus : statusId) {

                if (Objects.equals(applicationStatus.getName(), status)) {
                    switch (status) {
                        case "Новое":
                            if (Objects.equals(parameter, "all")) {
//                                List<Object[]> resultList = applicationService.findObjectV2(start, end, applicationStatus.getId());
//                                for (Object[] obj : resultList) {
//                                    newSt.put("Новое", Arrays.asList(obj[0].toString(), obj[1].toString()));
//                                }
//                                System.out.println(newSt);
//                                maps.add(newSt);

                                newStatus.add(applicationService.getSumOfAllApplication(start, end, applicationStatus.getId()).toString());
                                newStatus.add(applicationService.getCountAllApplication(start, end, applicationStatus.getId()).toString());
                                break;
                            } else if (Objects.equals(parameter, "my")) {
                                user_id = userService.getUserIdByEmail(principal.getName());
                                newStatus.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                newStatus.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                break;
                            } else {
                                departmentId = Long.parseLong(parameter);
                                List<Long> users_id = userService.getAllUserId(departmentId);
                                for (Long aLong : users_id) {
                                    newStatus.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                    newStatus.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                }
                                break;
                            }
                        case "Переговоры":
                            if (Objects.equals(parameter, "all")) {
                                negotiations.add(applicationService.getSumOfAllApplication(start, end, applicationStatus.getId()).toString());
                                negotiations.add(applicationService.getCountAllApplication(start, end, applicationStatus.getId()).toString());
                                break;
                            } else if (Objects.equals(parameter, "my")) {
                                user_id = userService.getUserIdByEmail(principal.getName());
                                negotiations.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                negotiations.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                break;
                            } else {
                                departmentId = Long.parseLong(parameter);
                                List<Long> users_id = userService.getAllUserId(departmentId);
                                for (Long aLong : users_id) {
                                    negotiations.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                    negotiations.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                }
                                break;
                            }
                        case "Принятие решения":
                            if (Objects.equals(parameter, "all")) {
                                decisionMaking.add(applicationService.getSumOfAllApplication(start, end, applicationStatus.getId()).toString());
                                decisionMaking.add(applicationService.getCountAllApplication(start, end, applicationStatus.getId()).toString());
                                break;
                            } else if (Objects.equals(parameter, "my")) {
                                user_id = userService.getUserIdByEmail(principal.getName());
                                decisionMaking.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                decisionMaking.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                break;
                            } else {
                                departmentId = Long.parseLong(parameter);
                                List<Long> users_id = userService.getAllUserId(departmentId);
                                for (Long aLong : users_id) {
                                    decisionMaking.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                    decisionMaking.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                }
                                break;
                            }
                        case "На обслуживании":
                            if (Objects.equals(parameter, "all")) {
                                onMaintenance.add(applicationService.getSumOfAllApplication(start, end, applicationStatus.getId()).toString());
                                onMaintenance.add(applicationService.getCountAllApplication(start, end, applicationStatus.getId()).toString());
                                break;
                            } else if (Objects.equals(parameter, "my")) {
                                user_id = userService.getUserIdByEmail(principal.getName());
                                onMaintenance.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                onMaintenance.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                break;
                            } else {
                                departmentId = Long.parseLong(parameter);
                                List<Long> users_id = userService.getAllUserId(departmentId);
                                for (Long aLong : users_id) {
                                    onMaintenance.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                    onMaintenance.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                }
                                break;
                            }
                        case "Успешно":
                            if (Objects.equals(parameter, "all")) {
                                success.add(applicationService.getSumOfAllApplication(start, end, applicationStatus.getId()).toString());
                                success.add(applicationService.getCountAllApplication(start, end, applicationStatus.getId()).toString());
                                break;
                            } else if (Objects.equals(parameter, "my")) {
                                user_id = userService.getUserIdByEmail(principal.getName());
                                success.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                success.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), user_id).toString());
                                break;
                            } else {
                                departmentId = Long.parseLong(parameter);
                                List<Long> users_id = userService.getAllUserId(departmentId);
                                for (Long aLong : users_id) {
                                    success.add(applicationService.getApplicationByPriceAndStatusAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                    success.add(applicationService.getApplicationCountAndEmployeeId(start, end, applicationStatus.getId(), aLong).toString());
                                }
                                break;
                            }
                    }
                }
            }
        }

        sumOfPrice.add(newStatus);
        sumOfPrice.add(negotiations);
        sumOfPrice.add(decisionMaking);
        sumOfPrice.add(onMaintenance);
        sumOfPrice.add(success);
        System.out.println(sumOfPrice);

        return new ResponseEntity<>(sumOfPrice, HttpStatus.OK);
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
}
