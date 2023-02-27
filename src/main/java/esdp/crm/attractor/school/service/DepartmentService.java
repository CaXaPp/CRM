package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.entity.Department;
import esdp.crm.attractor.school.mapper.DepartmentMapper;
import esdp.crm.attractor.school.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentDto> getAll(){
        var departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toDepartmentDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto save(DepartmentDto dto) throws EntityExistsException {
        if (departmentRepository.existsByName(dto.getName())) throw new EntityExistsException("Отдел с таким именем существует!");
        return departmentMapper.toDepartmentDto(departmentRepository.save(departmentMapper.toDepartment(dto)));
    }
}
