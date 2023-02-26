package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.FunnelDto;
import esdp.crm.attractor.school.dto.request.FunnelFormDto;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.Department;
import esdp.crm.attractor.school.entity.Funnel;
import esdp.crm.attractor.school.mapper.FunnelMapper;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import esdp.crm.attractor.school.repository.DepartmentRepository;
import esdp.crm.attractor.school.repository.FunnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunnelService {
    private final FunnelRepository funnelRepository;
    private final FunnelMapper funnelMapper;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final DepartmentRepository departmentRepository;

    public List<FunnelDto> findAll() {
        List<Funnel> funnels = funnelRepository.findAll();
        return funnels.stream()
                .map(funnelMapper::toDto)
                .collect(Collectors.toList());
    }

    public void save(FunnelFormDto form) throws EntityExistsException {
        if (funnelRepository.existsByName(form.getName())) throw new EntityExistsException("Воронка продаж с такми именем существует!");
        Funnel funnel = funnelRepository.save(Funnel.builder().name(form.getName()).build());
        form.getStatuses().forEach(s -> {
            applicationStatusRepository.save(ApplicationStatus.builder().funnel(funnel).name(s).build());
        });
        form.getDepartments().forEach(d -> {
            Department department = departmentRepository.getById(d);
            department.getFunnels().add(funnel);
            departmentRepository.save(department);
        });
    }

    public List<Funnel> findById(Long id) {
        List<Long> funnels_id = funnelRepository.findAllFunnelsIdByDepartmentId(id);
        List<Funnel> funnels = new ArrayList<>();
        for (Long aLong : funnels_id) {
            funnels.add(funnelRepository.findAllByFunnelsById(aLong));
        }
        return funnels;
    }
}
