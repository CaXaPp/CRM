package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ProductService productService;
    private final UserService userService;
    private final ApplicationStatusService statusService;

    public ApplicationDto save(ApplicationFormDto form) {
        var application = applicationRepository.save(applicationMapper.toEntity(form));
        return applicationMapper.toDto(application);
    }

    public List<Application> getAll() {
        return this.applicationRepository.findAll();
    }

    public ApplicationDto getApplicationById(Long id) {
        var application = applicationRepository.findById(id);
        if (application.isEmpty())
            throw new NotFoundException("Application with id: " + id + " not found!");
        return applicationMapper.toDto(application.get());
    }

    public List<ApplicationDto> getAllFreeApplications() {
        var applications = applicationRepository.findAllFree();
        return applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Application updateApplication(ApplicationFormDto application) {
        if (application.getId() != null) application.setCreatedAt
                (applicationRepository.getApplicationById(application.getId()).getCreatedAt()); // TODO Временное решение
        return applicationRepository.save(applicationMapper.toEntity(application));
    }

    public List<Application> getApplicationByProduct(Long id) {
        return this.applicationRepository.findApplicationByProductId(id);
    }

    public List<Application> getApplicationBySource(Long id) {
        return this.applicationRepository.findApplicationBySourceId(id);
    }

    public List<Application> getApplicationByEmployee(Long id) {
        return this.applicationRepository.findApplicationByEmployeeId(id);
    }
    public Application findById(Long id) {
        return applicationRepository.getApplicationById(id);
    }

    public List<Application> getApplicationByStatus(Long id) {
        return this.applicationRepository.findApplicationByStatusId(id);
    }

    public Float getApplicationByPriceAndStatus(LocalDateTime start, LocalDateTime end, Long status) {
        return this.applicationRepository.getPriceByDateAndStatus(start, end, status);
    }

    public Float getApplicationCount(LocalDateTime start, LocalDateTime end, Long status) {
        return this.applicationRepository.getCountByDateAndStatus(start, end, status);
    }
}
