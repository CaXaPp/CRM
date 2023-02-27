package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {
    private final ApplicationService applicationService;

    @GetMapping
    public String mainApplications() {
        return "operations";
    }
    @GetMapping("/funnel/all/{id}")
    public ResponseEntity<List<ApplicationDto>> getAllByFunnel(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(applicationService.getAllOperationsByFunnelId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.getAllOperationsByEmployeeIdAndFunnelId(user.getId(), id), HttpStatus.OK);
        }
    }

    @GetMapping("/funnel/active/{id}")
    public ResponseEntity<List<ApplicationDto>> getAllActiveDealByFunnel(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(applicationService.getAllActiveOperationsByFunnelId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.getAllActiveOperationsByEmployeeIdAndFunnelId(user.getId(), id), HttpStatus.OK);
        }
    }

    @GetMapping("/funnel/not-active/{id}")
    public ResponseEntity<List<ApplicationDto>> getAllNotActiveDealByFunnel(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRole().getValue(), "ROLE_EMPLOYEE")) {
            return new ResponseEntity<>(applicationService.getAllNotActiveOperationsByFunnelId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(applicationService.getAllNotActiveOperationsByEmployeeIdAndFunnelId(user.getId(), id), HttpStatus.OK);
        }
    }
}
