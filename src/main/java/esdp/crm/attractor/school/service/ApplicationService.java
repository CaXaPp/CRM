package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public void save(ApplicationDto applicationDto) {
        applicationRepository.save(applicationMapper.toEntity(applicationDto));
    }

    public Page<Application> getAll(Pageable pageable) {
        return this.applicationRepository.findAll(pageable);
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }
}
