package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.LogsDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.repository.*;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.ChangesByCommit;
import org.javers.core.Javers;
import org.javers.core.commit.CommitMetadata;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogsService {
    private final UserRepository userRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final ProductRepository productRepository;
    private final ClientSourceRepository clientSourceRepository;
    private final Javers javers;
    private final StatusRepository statusRepository;

    public List<LogsDto> getApplicationChanges(Long applicationId) {
        Changes changes = javers.findChanges(QueryBuilder.byInstanceId(applicationId, Application.class).build());
        List<LogsDto> logs = new ArrayList<>();
        changes.groupByCommit().forEach(changesByCommit -> {
            logs.add(logsFromCommit(changesByCommit, applicationId));
        });
        return logs;
    }

    public LogsDto logsFromCommit(ChangesByCommit changesByCommit, Long applicationId) {
        LogsDto logsDto = new LogsDto();
        CommitMetadata commit = changesByCommit.getCommit();
        logsDto.setAuthor(commit.getAuthor());
        logsDto.setDate(commit.getCommitDate());
        StringBuilder str = new StringBuilder();
        Changes changes = javers.findChanges(QueryBuilder.byInstanceId(applicationId, Application.class)
                .withCommitId(commit.getId()).build());
        changes.getChangesByType(PropertyChange.class).forEach(propertyChange -> {
            str.append(getPropertyDescription(propertyChange)).append("\n");
        });
        logsDto.setDescription(str.toString());
        return logsDto;
    }

    public String getPropertyDescription(PropertyChange propertyChange) {
        StringBuilder desc = new StringBuilder().append(propertyChange.getPropertyName()).append(": ");
        switch (propertyChange.getPropertyName()) {
            case "Продукт":
                desc.append(propertyChange.getLeft() != null ? productRepository.getById(getIdFromPropertyStr(
                        propertyChange.getLeft().toString())).getName() : "Не объявлено")
                        .append(" ---> ")
                        .append(propertyChange.getLeft() != null ? productRepository.getById(getIdFromPropertyStr(
                                propertyChange.getRight().toString())).getName() : "Не объявлено");
                break;
            case "Статус":
                desc.append(propertyChange.getLeft() != null ? applicationStatusRepository.getById(getIdFromPropertyStr(
                                propertyChange.getLeft().toString())).getName() : "Не объявлено")
                        .append(" ---> ")
                        .append(propertyChange.getLeft() != null ? applicationStatusRepository.getById(getIdFromPropertyStr(
                                propertyChange.getRight().toString())).getName() : "Не объявлено");
                break;
            case "Источник":
                desc.append(propertyChange.getLeft() != null ? clientSourceRepository.getById(getIdFromPropertyStr(
                                propertyChange.getLeft().toString())).getName() : "Не объявлено")
                        .append(" ---> ")
                        .append(propertyChange.getLeft() != null ? clientSourceRepository.getById(getIdFromPropertyStr(
                                propertyChange.getRight().toString())).getName() : "Не объявлено");
                break;
            case "Сотрудник":
                desc.append(propertyChange.getLeft() != null ? userRepository.getById(getIdFromPropertyStr(
                                propertyChange.getLeft().toString())).getEmail() : "Не объявлено")
                        .append(" ---> ")
                        .append(propertyChange.getLeft() != null ? userRepository.getById(getIdFromPropertyStr(
                                propertyChange.getRight().toString())).getEmail() : "Не объявлено");
                break;
            default:
                desc.append(propertyChange.getLeft())
                        .append(" ---> ")
                        .append(propertyChange.getRight());
                break;
        }
        return desc.toString();
    }

    public Long getIdFromPropertyStr(String propertyStr) {
        return Long.parseLong(propertyStr.substring(propertyStr.lastIndexOf('/') + 1));
    }
}
