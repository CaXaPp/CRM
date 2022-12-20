package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.entity.ClientSource;
import esdp.crm.attractor.school.service.ClientSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/source")
@RequiredArgsConstructor
public class ClientSourceController {
    private final ClientSourceService clientSourceService;

    @GetMapping("/all")
    public ResponseEntity<List<ClientSource>> getAll() {
        return new ResponseEntity<>(clientSourceService.getAll(), HttpStatus.OK);
    }
}
