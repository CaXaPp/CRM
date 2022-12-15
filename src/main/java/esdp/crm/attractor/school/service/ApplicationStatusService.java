package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationStatusService {
    private final ApplicationStatusRepository applicationStatusRepository;

    public List<ApplicationStatus> getAll() {
        return applicationStatusRepository.findAll();
    }
}
