package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

}
