package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDetailsAndStatusDto;
import esdp.crm.attractor.school.dto.ApplicationStatusDto;
import esdp.crm.attractor.school.dto.FunnelDto;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.Department;
import esdp.crm.attractor.school.entity.Funnel;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.ApplicationStatusMapper;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import esdp.crm.attractor.school.repository.FunnelRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationStatusService {
    private final ApplicationStatusRepository applicationStatusRepository;
    private final UserRepository userRepository;
    private final FunnelRepository funnelRepository;
    private final ApplicationStatusMapper statusMapper;

    public List<ApplicationStatus> getAll() {
        return applicationStatusRepository.findAll();
    }

    public ApplicationStatus getStatusById(Long id) {
        return applicationStatusRepository.findById(id).orElseThrow(() -> new NotFoundException("Status with " + id + " not found"));
    }

    public List<ApplicationStatus> findByUserId(Long id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        return findByUser(user);
    }

    public List<ApplicationStatus> findByUser(User user) throws NotFoundException {
        Funnel funnel = findFunnelByUser(user);
        var data =  applicationStatusRepository.findAllByFunnel(funnel);
        return data;
    }

    private Funnel findFunnelByUser(User user) throws NotFoundException {
        Department department = user.getDepartment();
        return department.getFunnels().stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Department has no funnels!"));
    }

    public List<ApplicationStatusDto> findAllByFunnel_Id(Long id){
        return applicationStatusRepository.findAllByFunnel_Id(id).stream().map(statusMapper::toDto).collect(Collectors.toList());
    }

    public Long findStatusIdByFunnel(String status, Long funnel) {
        return applicationStatusRepository.getStatusIdByFunnel(status, funnel);
    }
}
