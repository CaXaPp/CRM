package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.LogsDto;
import esdp.crm.attractor.school.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogsController {
    private final LogsService logsService;

    @CrossOrigin(origins = "*")
    @GetMapping("/{applicationId}")
    public ResponseEntity<List<LogsDto>> getChangesByApplication(@PathVariable Long applicationId) {
        return new ResponseEntity<>(logsService.getApplicationChanges(applicationId), HttpStatus.OK);
    }
}
