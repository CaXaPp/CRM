package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<Logs,Long> {
}
