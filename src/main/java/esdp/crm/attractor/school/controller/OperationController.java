package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.ApplicationService;
import esdp.crm.attractor.school.service.ApplicationStatusService;
import esdp.crm.attractor.school.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operation")
@RequiredArgsConstructor
public class OperationController {
    private final ApplicationService applicationService;
    private final OperationService operationService;
    private final ApplicationStatusService applicationStatusService;

    @GetMapping
    public String getOperations(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("operations", operationService.getAll(user));
        model.addAttribute("operation_statuses", applicationStatusService.getAll());
        return "operations";
    }

    @GetMapping("/add")
    public String addOperationPage(Model model) {
        model.addAttribute("applications", applicationService.getAllFreeApplications());
        return "add_operation";
    }

    @GetMapping("/add/new")
    public String addNewOperationPage(@AuthenticationPrincipal User user, Model model) {
        return "add_new_operation";
    }

    @PostMapping
    public String createOperation() {
        return "redirect:/operation";
    }
}
