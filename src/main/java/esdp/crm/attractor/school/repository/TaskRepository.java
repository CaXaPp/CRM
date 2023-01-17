package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(nativeQuery = true, value = "select * from tasks t WHERE t.application_id = :id")
    List<Task> findAllById(Long id);
}
