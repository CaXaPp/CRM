package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.entity.Department;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DepartmentMapper {
    DepartmentDto toDepartmentDto(Department department);
}
