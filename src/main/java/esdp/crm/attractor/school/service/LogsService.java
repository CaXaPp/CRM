package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.LogsDto;
import esdp.crm.attractor.school.dto.TaskTypeDto;
import esdp.crm.attractor.school.mapper.LogsMapper;
import esdp.crm.attractor.school.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogsService {
    private final LogsRepository logsRepository;
    private final LogsMapper logsMapper;

    public List<LogsDto> findAll() {
        var logs = logsRepository.findAll();
        return logs.stream()
                .map(logsMapper::toLogsDto)
                .collect(Collectors.toList());
    }
}
