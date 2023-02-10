package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.FunnelDto;
import esdp.crm.attractor.school.entity.Funnel;
import esdp.crm.attractor.school.service.FunnelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/funnels")
@RequiredArgsConstructor
public class FunnelController {
    private final FunnelService funnelService;

    @GetMapping("/all")
    public ResponseEntity<List<FunnelDto>> getAllFunnels() {
        return new ResponseEntity<>(funnelService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/funnel/{id}")
    public ResponseEntity<List<Funnel>> getAll(@PathVariable Long id) {
        return new ResponseEntity<>(funnelService.findById(id), HttpStatus.OK);
    }
}