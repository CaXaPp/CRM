package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.ApplicationStatusDto;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ApplicationStatusMapper {
    ApplicationStatusDto toDto(ApplicationStatus applicationStatus);
}
