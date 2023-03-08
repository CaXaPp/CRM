package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.entity.Department;
import esdp.crm.attractor.school.entity.PlanSum;
import esdp.crm.attractor.school.mapper.DepartmentMapper;
import esdp.crm.attractor.school.repository.DepartmentRepository;
import esdp.crm.attractor.school.repository.PlanSumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final PlanSumRepository planSumRepository;

    public List<DepartmentDto> getAll(){
        var departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toDepartmentDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto save(DepartmentDto dto) throws EntityExistsException {
        if (departmentRepository.existsByName(dto.getName()))
            throw new EntityExistsException("Отдел с таким именем существует!");
        var department = departmentMapper.toDepartment(dto);
        var saved = departmentRepository.save(department);
        createPlanOfSum(saved);
        return departmentMapper.toDepartmentDto(saved);
    }

    private void createPlanOfSum(Department department) {
        PlanSum planSum = new PlanSum();
        planSum.setSum(BigDecimal.ZERO);
        planSum.setDepartment(department);
        planSumRepository.save(planSum);
    }
}
