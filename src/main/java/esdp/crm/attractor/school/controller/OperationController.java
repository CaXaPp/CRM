package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {
    private final ApplicationService applicationService;
    private final OperationService operationService;
    private final ApplicationStatusService applicationStatusService;
    private final UserService userService;
    private final ProductService productService;
    private final ClientSourceService clientSourceService;
    private final FunnelService funnelService;

    @GetMapping
    public String mainApplications() {
        return "operations";
    }
    @GetMapping("/by-funnel-all/{id}")
    public ResponseEntity<List<Application>> getAllByFunnel(@PathVariable Long id, Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(applicationService.getAllOperationsByFunnelId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.getAllOperationsByEmployeeIdAndFunnelId(user.get().getId(), id), HttpStatus.OK);
        }
    }

    @GetMapping("/by-funnel-active/{id}")
    public ResponseEntity<List<Application>> getAllActiveDealByFunnel(@PathVariable Long id, Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(applicationService.getAllActiveOperationsByFunnelId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.getAllActiveOperationsByEmployeeIdAndFunnelId(user.get().getId(), id), HttpStatus.OK);
        }
    }

    @GetMapping("/by-funnel-not-active/{id}")
    public ResponseEntity<List<Application>> getAllNotActiveDealByFunnel(@PathVariable Long id, Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (!Objects.equals(user.get().getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(applicationService.getAllNotActiveOperationsByFunnelId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.getAllNotActiveOperationsByEmployeeIdAndFunnelId(user.get().getId(), id), HttpStatus.OK);
        }
    }

    @GetMapping("/add")
    public String addOperationPage(Model model) {
        model.addAttribute("applications", applicationService.getAllFreeApplications());
        return "createOperation";
    }

    @GetMapping("/add/new")
    public String addNewOperationPage(@AuthenticationPrincipal User user, Model model,
                                      @RequestParam(name = "id", defaultValue = "0") Long applicationId) {
        if (applicationId != 0) {
            var application = applicationService.getApplicationById(applicationId);
            model.addAttribute("application", application);
        }
        List<UserDto> users = userService.findAllByUser(user);
        model.addAttribute("employees", users);
        model.addAttribute("products", productService.getAll());
        model.addAttribute("sources", clientSourceService.getAll());
        model.addAttribute("statuses", applicationStatusService.findByUserId(users.get(0).getId()));
        return "createNewOperation";
    }

    @PostMapping
    public String saveOperation(@Valid @ModelAttribute ApplicationFormDto form) {
        operationService.createOrEditOperation(form);
        return "redirect:/operations";
    }
}
