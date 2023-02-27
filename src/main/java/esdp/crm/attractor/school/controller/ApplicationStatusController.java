package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ApplicationStatusDto;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.service.ApplicationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/statuses")
@RequiredArgsConstructor
public class ApplicationStatusController {
    private final ApplicationStatusService applicationStatusService;

    @GetMapping
    public ResponseEntity<List<ApplicationStatus>> findByUserId(@RequestParam(name = "employee", defaultValue = "0") Long userId) {
        return ResponseEntity.ok(applicationStatusService.findByUserId(userId));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<List<ApplicationStatusDto>> getAll(@PathVariable Long id) {
        return new ResponseEntity<>(applicationStatusService.findAllByFunnel_Id(id), HttpStatus.OK);
    }

    @GetMapping("/funnels-status")
    public ResponseEntity<Long> getStatusIdByFunnel(@RequestParam(value = "status", required = false) String statusName,
                                                                          @RequestParam(value = "funnel", required = false) Long funnelId) {
        return new ResponseEntity<>(applicationStatusService.findStatusIdByFunnel(statusName, funnelId), HttpStatus.OK);
    }


}
