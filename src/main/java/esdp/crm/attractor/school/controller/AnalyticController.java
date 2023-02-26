package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticController {
    private final ApplicationService applicationService;

    @GetMapping
    public String mainApplications() {
        return "analytics";
    }

    @GetMapping("/application/all")
    public ResponseEntity<List<Object[]>> getAllApplication() {
        return new ResponseEntity<>(applicationService.getSumAndCountOfApplication(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<List<Object[]>> getApplicationByProduct(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.getApplicationByProduct(id), HttpStatus.OK);
    }
    @GetMapping("/source/{id}")
    public ResponseEntity<List<Object[]>> getApplicationBySource(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.getApplicationBySource(id), HttpStatus.OK);
    }
    @GetMapping("/employee/{id}")
    public ResponseEntity<List<Object[]>> getApplicationByEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.getApplicationByEmployee(id), HttpStatus.OK);
    }
    @GetMapping("/status/{id}")
    public ResponseEntity<List<Application>> getApplicationByStatus(@PathVariable Long id) {
        return new ResponseEntity<>(applicationService.getApplicationByStatus(id), HttpStatus.OK);
    }

}
