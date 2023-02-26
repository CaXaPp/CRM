package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.Department;
import esdp.crm.attractor.school.entity.Funnel;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationStatusService {
    private final ApplicationStatusRepository applicationStatusRepository;
    private final UserRepository userRepository;

    public List<ApplicationStatus> getAll() {
        return applicationStatusRepository.findAll();
    }

    public Optional<ApplicationStatus> getStatusById(Long id) {
        return applicationStatusRepository.findById(id);
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

    public List<ApplicationStatus> getStatusIdByName(String name) {
        return applicationStatusRepository.findAllByName(name);
    }

    public List<ApplicationStatus> findAllByFunnel_Id(Long id) {
        return applicationStatusRepository.findAllByFunnel_Id(id);
    }
}
