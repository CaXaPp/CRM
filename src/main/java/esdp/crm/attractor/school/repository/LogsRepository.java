package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Logs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LogsRepository {
    private final JdbcTemplate jdbcTemplate;

    public void deleteCommitHistory(Long commitId) {
        String sql = "delete from jv_commit_property where commit_fk = ?;" +
                     "delete from jv_snapshot where commit_fk = ?;" +
                     "delete from jv_commit where commit_pk = ?;";
        jdbcTemplate.update(sql, commitId, commitId, commitId);
    }
}
