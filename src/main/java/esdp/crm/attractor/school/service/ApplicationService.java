package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    public Page<Application> getAll(Pageable pageable) {
        return this.applicationRepository.findAll(pageable);
    }

    public void save(Application application) {
        applicationRepository.save(application);
    }



}
