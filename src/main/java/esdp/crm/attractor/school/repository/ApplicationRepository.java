package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.User;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@JaversSpringDataAuditable
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT a FROM Application a WHERE a.status IN :statuses AND a.employee = :employee")
    List<Application> findAllByEmployee(@Param("employee") User employee, @Param("statuses") List<ApplicationStatus> statuses);
    Application getApplicationById(Long id);
    List<Application> findAllByEmployeeNotNullAndStatus_Funnel_Id(Long id);
    List<Application> findAllByEmployeeIdAndEmployeeNotNullAndStatus_Funnel_Id(Long id, Long funnelId);
    @Query("SELECT a FROM Application a WHERE a.status.funnel.id = :id AND a.status.name NOT IN (:status1, :status2) AND a.employee IS NOT NULL ORDER BY a.createdAt ASC")
    List<Application> findAllActiveOperationsByFunnelId(Long id, String status1, String status2);
    @Query("SELECT a FROM Application a WHERE a.employee.id =:userId AND a.status.funnel.id = :funnelId AND a.status.name NOT IN (:status1, :status2) AND a.employee IS NOT NULL ORDER BY a.createdAt ASC")
    List<Application> getAllActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId, String status1, String status2);
    @Query("SELECT a FROM Application a WHERE a.status.funnel.id = :id AND (a.status.name =:status1 OR a.status.name =:status2) AND a.employee IS NOT NULL")
    List<Application> findAllNotActiveOperationsByFunnelId(Long id, String status1, String status2);
    @Query("SELECT a FROM Application a WHERE a.employee.id =:userId AND a.status.funnel.id = :funnelId AND (a.status.name =:status1 OR a.status.name =:status2) AND a.employee IS NOT NULL")
    List<Application> getAllNotActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId, String status1, String status2);
    @Query("SELECT a FROM Application as a WHERE a.employee IS NULL")
    List<Application> findAllFree();
    List<Application> findAllByProduct_Id(Long id);
    List<Application> findAllBySource_Id(Long id);
    List<Application> findAllByEmployee_Id(Long id);
    List<Application> findAllByStatus_Id(Long id);
    @Query("SELECT a FROM Application a WHERE a.status IN :statuses AND a.employee IS NOT NULL")
    List<Application> findOperationsByFunnel(List<ApplicationStatus> statuses);
    @Query("SELECT a FROM Application a WHERE a.status.name NOT IN (:status1, :status2) AND a.employee IS NOT NULL ORDER BY a.createdAt ASC")
    List<Application> findAllActiveApplicationForToday(String status1, String status2);
    @Query("SELECT a FROM Application a WHERE a.status.name NOT IN (:status1, :status2) AND a.employee.id = :userId ORDER BY a.createdAt ASC")
    List<Application> findAllActiveApplicationForTodayByUserId(String status1, String status2, Long userId);
    List<Application> findAllByStatus_NameAndStatusIsNotNullOrderByEmployee(String status);
    List<Application> findAllByStatus_NameAndStatusIsNotNullAndEmployee_IdAndEmployeeIsNotNullOrderByEmployee(String status, Long userId);
    List<Application> findAllBySourceIsNotNullAndEmployeeIsNotNullOrderBySource();
    List<Application> findAllByEmployee_IdAndSourceIsNotNullAndEmployeeIsNotNullOrderBySource(Long id);
    List<Application> findAllByEmployeeIsNotNullOrderByEmployee();
    List<Application> findAllByEmployee_IdOrderByEmployee(Long id);
    @Query("SELECT a FROM Application a WHERE a.status.name = 'Отказ' ORDER BY a.product.name")
    List<Application> findAllFailureApplication();
    @Query("SELECT a FROM Application a WHERE a.status.name = 'Отказ' AND a.employee.id = :userId ORDER BY a.product.name")
    List<Application> findAllFailureApplicationByUserId(Long userId);
    List<Application> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Application> findAllByCreatedAtBetweenAndEmployee_Department_Id(LocalDateTime start, LocalDateTime end, Long depId);
    List<Application> findAllByCreatedAtBetweenAndEmployee_id(LocalDateTime start, LocalDateTime end, Long emplId);
    List<Application> findAllByCreatedAtBetweenAndCompanyStartingWithIgnoreCase(LocalDateTime start, LocalDateTime end, String text);
}
