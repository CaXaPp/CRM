package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.LogsDto;
import esdp.crm.attractor.school.entity.Logs;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class LogsMapper {

    @Autowired
    protected UserMapper userMapper;

    @Mapping(target="employee", expression = "java(userMapper.toUserDto(logs.getEmployee()))")
    @Mapping(target = "status", expression = "java(logs.getStatus().getName())")
    public abstract LogsDto toLogsDto(Logs logs);
}
