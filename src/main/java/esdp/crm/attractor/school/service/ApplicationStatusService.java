package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.Status;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationStatusService {
    private final ApplicationStatusRepository applicationStatusRepository;

    public List<ApplicationStatus> getAll() {
        return applicationStatusRepository.findAll();
    }

    public Optional<ApplicationStatus> getStatusById(Long id) {
        return applicationStatusRepository.findById(id);
    }
}
