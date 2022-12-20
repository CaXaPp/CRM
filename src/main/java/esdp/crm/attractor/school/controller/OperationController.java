package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping
    public String getOperations(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("operations", operationService.getAll(user));
        model.addAttribute("operation_statuses", applicationStatusService.getAll());
        return "operations";
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
        model.addAttribute("employees", userService.findAllByUser(user));
        model.addAttribute("products", productService.getAll());
        model.addAttribute("sources", clientSourceService.getAll());
        model.addAttribute("statuses", applicationStatusService.getAll());
        return "createNewOperation";
    }

    @PostMapping
    public String createOperation(@Valid @ModelAttribute ApplicationFormDto form) {
        operationService.createOrEditOperation(form);
        return "redirect:/operations";
    }
}
