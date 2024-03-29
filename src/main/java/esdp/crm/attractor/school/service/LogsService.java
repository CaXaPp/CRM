package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ChangesDto;
import esdp.crm.attractor.school.dto.LogsDto;
import esdp.crm.attractor.school.dto.UserDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.mapper.UserMapper;
import esdp.crm.attractor.school.repository.*;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.ChangesByCommit;
import org.javers.core.Javers;
import org.javers.core.commit.CommitMetadata;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LogsService {
    private final UserRepository userRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final ProductRepository productRepository;
    private final ClientSourceRepository clientSourceRepository;
    private final Javers javers;
    private final UserMapper userMapper;
    private final LogsRepository logsRepository;

    public List<LogsDto> getApplicationChanges(Long applicationId) {
        Changes changes = javers.findChanges(QueryBuilder.byInstanceId(applicationId, Application.class).build());
        List<LogsDto> logs = new ArrayList<>();
        changes.groupByCommit().forEach(changesByCommit -> {
            logs.add(logsFromCommit(changesByCommit, applicationId));
        });
        logs.forEach(logsDto -> logsDto.setApplicationId(applicationId));
        return logs;
    }

    public LogsDto logsFromCommit(ChangesByCommit changesByCommit, Long applicationId) {
        LogsDto logsDto = new LogsDto();
        CommitMetadata commit = changesByCommit.getCommit();
        try {
            logsDto.setAuthor(userMapper.toUserDto(userRepository.findByEmail(commit.getAuthor())));
        } catch (Exception e) {
            logsDto.setAuthor(UserDto.builder().firstName("Unknown author").build());
        }
        logsDto.setDate(commit.getCommitDate());
        Changes changes = javers.findChanges(QueryBuilder.byInstanceId(applicationId, Application.class)
                .withCommitId(commit.getId()).build());
        List<ChangesDto> changesList = new ArrayList<>();
        changes.getChangesByType(PropertyChange.class).forEach(propertyChange -> {
            changesList.add(getPropertyDescription(propertyChange));
        });
        logsDto.setChanges(changesList);
        return logsDto;
    }

    public ChangesDto getPropertyDescription(PropertyChange propertyChange) {
        final String UNDEFINED = "Не объявлено";
        switch (propertyChange.getPropertyName()) {
            case "Продукт":
                return ChangesDto.builder()
                        .property(propertyChange.getPropertyName())
                        .oldRecord(propertyChange.getLeft() != null ? productRepository.getById(getIdFromPropertyStr(
                                propertyChange.getLeft().toString())).getName() : UNDEFINED)
                        .newRecord(propertyChange.getRight() != null ? productRepository.getById(getIdFromPropertyStr(
                                propertyChange.getRight().toString())).getName() : UNDEFINED)
                        .build();
            case "Статус":
                return ChangesDto.builder()
                        .property(propertyChange.getPropertyName())
                        .oldRecord(propertyChange.getLeft() != null ? applicationStatusRepository.getById(getIdFromPropertyStr(
                                propertyChange.getLeft().toString())).getName() : UNDEFINED)
                        .newRecord(propertyChange.getRight() != null ? applicationStatusRepository.getById(getIdFromPropertyStr(
                                propertyChange.getRight().toString())).getName() : UNDEFINED)
                        .build();
            case "Источник":
                return ChangesDto.builder()
                        .property(propertyChange.getPropertyName())
                        .oldRecord(propertyChange.getLeft() != null ? clientSourceRepository.getById(getIdFromPropertyStr(
                                propertyChange.getLeft().toString())).getName() : UNDEFINED)
                        .newRecord(propertyChange.getRight() != null ? clientSourceRepository.getById(getIdFromPropertyStr(
                                propertyChange.getRight().toString())).getName() : UNDEFINED)
                        .build();
            case "Сотрудник":
                return ChangesDto.builder()
                        .property(propertyChange.getPropertyName())
                        .oldRecord(propertyChange.getLeft() != null ? getUserFullName(userRepository.getById(getIdFromPropertyStr(propertyChange.getLeft().toString()))) : UNDEFINED)
                        .newRecord(propertyChange.getRight() != null ? getUserFullName(userRepository.getById(getIdFromPropertyStr(propertyChange.getRight().toString()))) : UNDEFINED)
                        .build();
            default:
                return ChangesDto.builder()
                        .property(propertyChange.getPropertyName())
                        .oldRecord(propertyChange.getLeft() != null ? propertyChange.getLeft().toString() : UNDEFINED)
                        .newRecord(propertyChange.getRight() != null ? propertyChange.getRight().toString() : UNDEFINED)
                        .build();
        }
    }

    public Long getIdFromPropertyStr(String propertyStr) {
        return Long.parseLong(propertyStr.substring(propertyStr.lastIndexOf('/') + 1));
    }

    private String getUserFullName(User user) {
        return user.getFirstName() + " " + user.getSurname();
    }

    public void deleteApplicationHistory(Long applicationId) {
        JqlQuery query = QueryBuilder.byInstanceId(applicationId, Application.class).build();
        javers.findShadows(query).forEach(objectShadow -> {
            LocalDateTime dateTime = objectShadow.getCommitMetadata().getCommitDate();
            String author = objectShadow.getCommitMetadata().getAuthor();
            logsRepository.deleteCommitHistory(author, dateTime);
        });

    }
}
