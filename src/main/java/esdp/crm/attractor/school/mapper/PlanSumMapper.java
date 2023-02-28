package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.PlanSumDto;
import esdp.crm.attractor.school.dto.request.PlanSumRequest;
import esdp.crm.attractor.school.entity.PlanSum;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class PlanSumMapper {
    @Autowired
    protected DepartmentMapper departmentMapper;

    @Mapping(target = "department", expression = "java(departmentMapper.toDepartmentDto(planSum.getDepartment()))")
    public abstract PlanSumDto toDto(PlanSum planSum);

    @Mapping(target = "id", expression = "java(oldSum.getId())")
    @Mapping(target = "department", expression = "java(oldSum.getDepartment())")
    @Mapping(target = "sum", expression = "java(form.getSum())")
    public abstract PlanSum toEntity(PlanSumRequest form, PlanSum oldSum);
}
