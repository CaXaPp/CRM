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
    @Query("SELECT a.employee, a.company, a.status.name, a.price, t.createdAt, t.deadline, t.result " +
            "FROM Task t " +
            "INNER JOIN Application a ON a.id = t.application.id " +
            "WHERE t.deadline < :date")
    List<Object[]> findAllByDate(LocalDateTime date);

    @Query("SELECT a.employee, a.company, a.status.name, a.price, t.createdAt, t.deadline, t.result " +
            "FROM Task t " +
            "INNER JOIN Application a ON a.id = t.application.id " +
            "WHERE t.deadline < :date AND t.employee.id = :userId")
    List<Object[]> findAllByDateAndByUserId(LocalDateTime date, Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.price, t.createdAt, t.result " +
            "FROM Task t " +
            "INNER JOIN Application a ON a.id = t.application.id " +
            "WHERE t.deadline > :date")
    List<Object[]> findAllActiveTask(LocalDateTime date);

    @Query("SELECT a.employee, a.company, a.status.name, a.price, t.createdAt, t.result " +
            "FROM Task t " +
            "INNER JOIN Application a ON a.id = t.application.id " +
            "WHERE t.deadline > :date AND t.employee.id = :userId")
    List<Object[]> findAllActiveTaskByUserId(LocalDateTime date, Long userId);

}
