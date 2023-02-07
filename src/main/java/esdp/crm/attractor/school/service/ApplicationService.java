package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.ChangesDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ProductService productService;
    private final UserService userService;
    private final ApplicationStatusService statusService;
    private final LogsService logsService;
    private final Javers javers;
    private final ApplicationStatusRepository applicationStatusRepository;

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

    public List<ApplicationDto> getAllSuccessfulAppsByToday() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Changes changes = javers.findChanges(QueryBuilder.byClass(Application.class)
                .withChangedProperty("Статус").to(tomorrow).from(today).build());

        List<ApplicationDto> dtos = new ArrayList<>();
        changes.getPropertyChanges("Статус").forEach(propertyChange -> {
            if (applicationStatusRepository.findById
                    (logsService.getIdFromPropertyStr
                            (propertyChange.getRight().toString()))
                    .get().getName().equals("Успешно")) {
                dtos.add(applicationMapper.toDto
                        (applicationRepository.getApplicationById
                                (Long.valueOf(propertyChange.getAffectedLocalId().toString()))));
            }
        });
        return dtos.stream().distinct().collect(Collectors.toList());
    }

    public List<ApplicationDto> getSuccessfulAppByEmployeeToday(User user) {
        return getAllSuccessfulAppsByToday().stream()
                .filter(app -> app.getEmployee().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
