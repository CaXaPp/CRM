package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.DepartmentDto;
import esdp.crm.attractor.school.dto.RoleDto;
import esdp.crm.attractor.school.entity.Department;
import esdp.crm.attractor.school.entity.Role;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto dto);
}
