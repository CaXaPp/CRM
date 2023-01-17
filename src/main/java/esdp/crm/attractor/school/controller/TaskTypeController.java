package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.service.TaskTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/task-type")
@RequiredArgsConstructor
public class TaskTypeController {

    private final TaskTypeService typeService;

    @GetMapping("/all")
    public ResponseEntity<List<TaskTypeDto>> types() {
        return new ResponseEntity<>(typeService.findAll(), HttpStatus.OK);
    }
}
