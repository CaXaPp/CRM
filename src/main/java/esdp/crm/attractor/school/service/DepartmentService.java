package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.mapper.DepartmentMapper;
import esdp.crm.attractor.school.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
