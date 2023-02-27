package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByApplication_Id(Long id);
    List<Task> findAllByDeadlineBefore(LocalDateTime date);
    List<Task> findAllByDeadlineBeforeAndEmployee_Id(LocalDateTime date, Long id);
    List<Task> findAllByDeadlineAfter(LocalDateTime date);
    List<Task> findAllByDeadlineAfterAndEmployee_Id(LocalDateTime date, Long id);

}
