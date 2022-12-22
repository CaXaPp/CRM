package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.LogsDto;
import esdp.crm.attractor.school.entity.Logs;
import org.springframework.stereotype.Component;

@Component
public class LogsMapper {
    public LogsDto toLogsDto(Logs logs) {
        return LogsDto.builder()
                .id(logs.getId())
                .date(logs.getDate())
                .description(logs.getDescription())
                .user(logs.getEmployee())
                .applicationStatus(logs.getStatus())
                .build();
    }
}
