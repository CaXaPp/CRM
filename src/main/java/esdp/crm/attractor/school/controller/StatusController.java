package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.service.ApplicationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/statuses")
@RequiredArgsConstructor
public class StatusController {
    private final ApplicationStatusService applicationStatusService;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationStatus>> getAll() {
        return new ResponseEntity<>(applicationStatusService.getAll(), HttpStatus.OK);
    }
}
