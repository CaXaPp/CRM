package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.*;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.exception.NotPermittedException;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import esdp.crm.attractor.school.repository.FunnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final FunnelRepository funnelRepository;
    private final ApplicationMapper applicationMapper;

    public Map<String, Object> getAll(User user, Long funnelId) throws NotFoundException, NotPermittedException {
        if (funnelId.equals(0L))
            return getDefaultFunnel(user);
        Funnel funnel = funnelRepository.findById(funnelId)
                .orElseThrow(() -> new NotFoundException("Funnel with id " + funnelId + " not found!"));
        var statuses = applicationStatusRepository.findAllByFunnel(funnel);
        var operations = getOperationsByUser(user, statuses);
        return Map.of("operations", operations,
                "operation_statuses", statuses);
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

    private Map<String, Object> getDefaultFunnel(User user) {
        Department department = user.getDepartment();
        Optional<Funnel> funnel = department.getFunnels().stream()
                .findAny();
        if (funnel.isEmpty()) {
            return Map.of("operations", new ArrayList<>(),
                    "operation_statuses", new ArrayList<>());
        }
        var statuses = applicationStatusRepository.findAllByFunnel(funnel.get());
        var operations = getOperationsByUser(user, statuses);
        return Map.of("operations", operations,
                "operation_statuses", statuses);
    }

    private List<ApplicationDto> getOperationsByUser(User user, List<ApplicationStatus> statuses) throws NotPermittedException {
        List<Application> applications;
        if ("Сотрудник".equals(user.getRole().getName())) {
            if (!user.getDepartment().getFunnels().contains(statuses.get(0).getFunnel()))
                throw new NotPermittedException("You can't see other departments' funnels!");
            applications = applicationRepository.findAllByEmployee(user, statuses);
        } else {
            applications = applicationRepository.findOperationsByFunnel(statuses);
        }
        return applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }
}
