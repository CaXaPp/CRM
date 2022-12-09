package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentDto toDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }
}
