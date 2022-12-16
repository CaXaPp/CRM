package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {
}
