package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public List<ApplicationDto> getAll(User user) {
        List<Application> applications;
        if ("Сотрудник".equals(user.getRole().getName())) {
            applications = applicationRepository.findAllByEmployee(user);
        } else {
            applications = applicationRepository.findAll();
        }
        return applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApplicationDto createOrEditOperation(ApplicationFormDto form) {
        if (form.getId() != null) {
            var optionalApplication = applicationRepository.findById(form.getId());
            if (optionalApplication.isEmpty())
                throw new NotFoundException("Operation with id " + form.getId() + " not found!");
            form.setCreatedAt(optionalApplication.get().getCreatedAt());
        } else
            form.setCreatedAt(LocalDateTime.now());
        var application = applicationMapper.toOperation(form);
        var saved = applicationRepository.save(application);
        return applicationMapper.toDto(saved);
    }
}
