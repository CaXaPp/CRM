package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Logs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogsRepository {
    private final JdbcTemplate jdbcTemplate;

    public void deleteCommitHistory(String author, LocalDateTime dateTime) {
        String query =  "select * " +
                        "from jv_commit " +
                        "where author = ? and commit_date = ?;";
        Long id = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Long.class), author, dateTime)
                .stream().findAny().orElseThrow();
        query = "delete from jv_commit_property where commit_fk = ?;" +
                "delete from jv_snapshot where commit_fk = ?;" +
                "delete from jv_commit where commit_pk = ?;";
        jdbcTemplate.update(query, id, id, id);
    }
}
