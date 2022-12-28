package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.LogsDto;
import esdp.crm.attractor.school.entity.Logs;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LogsMapper {
    @Mapping(target="user", source="employee")
    LogsDto toLogsDto(Logs logs);
}
