package esdp.crm.attractor.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("/getApplication")
    public String getApplication() {
        return "landing";
    }
}
