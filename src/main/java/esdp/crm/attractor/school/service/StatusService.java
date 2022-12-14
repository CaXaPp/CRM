package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.Status;
import esdp.crm.attractor.school.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<Status> getAll() {
        return statusRepository.findAll();
    }
}
