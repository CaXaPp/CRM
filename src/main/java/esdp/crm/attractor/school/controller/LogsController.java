package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogsController {
    private final LogsService logsService;


}
