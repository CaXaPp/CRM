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
    @Query("SELECT a FROM Application a WHERE a.status.funnel.id = :id AND a.status.name NOT IN (:status1, :status2) AND a.employee IS NOT NULL")
    List<Application> findAllActiveOperationsByFunnelId(Long id, String status1, String status2);
    @Query("SELECT a FROM Application a WHERE a.employee.id =:userId AND a.status.funnel.id = :funnelId AND a.status.name NOT IN (:status1, :status2) AND a.employee IS NOT NULL")
    List<Application> getAllActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId, String status1, String status2);


    @Query("SELECT a FROM Application a WHERE a.status.funnel.id = :id AND (a.status.name =:status1 OR a.status.name =:status2) AND a.employee IS NOT NULL")
    List<Application> findAllNotActiveOperationsByFunnelId(Long id, String status1, String status2);
    @Query("SELECT a FROM Application a WHERE a.employee.id =:userId AND a.status.funnel.id = :funnelId AND (a.status.name =:status1 OR a.status.name =:status2) AND a.employee IS NOT NULL")
    List<Application> getAllNotActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId, String status1, String status2);




    @Query("SELECT a FROM Application as a WHERE a.employee IS NULL")
    List<Application> findAllFree();

    @Query("SELECT COUNT(a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status, SUM (CASE WHEN a.status.name = 'Успешно' THEN a.price ELSE 0 END) AS success FROM Application a")
    List<Object[]> findSumAndCountOfApplication();
    @Query("SELECT COUNT(a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status, SUM (CASE WHEN a.status.name = 'Успешно' THEN a.price ELSE 0 END) AS success FROM Application a WHERE a.product.id = :productId")
    List<Object[]> findApplicationByProductId(Long productId);

    @Query("SELECT COUNT (a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status, SUM (CASE WHEN a.status.name = 'Успешно' THEN a.price ELSE 0 END) AS success FROM Application a WHERE a.source.id = :sourceId")
    List<Object[]> findApplicationBySourceId(Long sourceId);

    @Query("SELECT COUNT (a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status, SUM (CASE WHEN a.status.name = 'Успешно' THEN a.price ELSE 0 END) AS success FROM Application a WHERE a.employee.id = :employeeId")
    List<Object[]> findApplicationByEmployeeId(Long employeeId);
    List<Application> findApplicationByStatusId(Long id);
    @Query("SELECT a FROM Application a WHERE a.status IN :statuses AND a.employee IS NOT NULL")
    List<Application> findOperationsByFunnel(List<ApplicationStatus> statuses);

    @Query("SELECT COUNT(a), SUM(a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :statusId GROUP BY a.status")
    List<Object[]> findAllSumAndCountByApplication(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("statusId") Long statusId);

    @Query("SELECT COUNT(a), SUM(a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :statusId AND a.employee.id = :userId GROUP BY a.status")
    List<Object[]> findAllSumAndCountByApplicationByEmployeeId(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("statusId") Long statusId, @Param("userId") Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.status.name NOT IN (:status1, :status2) AND a.employee IS NOT NULL")
    List<Object[]> findAllActiveApplicationForToday(String status1, String status2);

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.status.name NOT IN (:status1, :status2) AND a.employee.id = :userId")
    List<Object[]> findAllActiveApplicationForTodayByUserId(String status1, String status2, Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.status.name =:status")
    List<Object[]> findAllCompletedDealsOnToday(String status);

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.status.name =:status AND a.employee.id = :userId")
    List<Object[]> findAllCompletedDealsOnTodayByUserId(String status, Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.price, a.source.name FROM Application a WHERE a.source IS NOT NULL AND a.employee IS NOT null ")
    List<Object[]> findAllSourceOfApplication();

    @Query("SELECT a.employee, a.company, a.status.name, a.price, a.source.name FROM Application a WHERE a.source IS NOT NULL AND a.employee IS NOT null AND a.employee.id = :userId")
    List<Object[]> findAllSourceOfApplicationByUserId(Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.employee IS NOT NULL")
    List<Object[]> findAllDealOfApplication();

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.employee IS NOT NULL AND a.employee.id = :userId")
    List<Object[]> findAllDealOfApplicationByUserId(Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.product.name, a.description FROM Application a WHERE a.status.name = 'Отказ'")
    List<Object[]> findAllFailureApplication();

    @Query("SELECT a.employee, a.company, a.status.name, a.product.name, a.description FROM Application a WHERE a.status.name = 'Отказ' AND a.employee.id = :userId")
    List<Object[]> findAllFailureApplicationByUserId(Long userId);

    @Query("SELECT a FROM Application a WHERE a.createdAt BETWEEN :start AND :end ORDER BY a.company")
    List<Application> sortByCompanyName(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Application a WHERE a.createdAt BETWEEN :start AND :end ORDER BY a.price ASC")
    List<Application> sortByPrice(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Application a WHERE a.createdAt BETWEEN :start AND :end ORDER BY a.product.name")
    List<Application> sortByProduct(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Application a WHERE a.createdAt BETWEEN :start AND :end ORDER BY a.status.name")
    List<Application> sortByStatus(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Application a WHERE a.createdAt BETWEEN :start AND :end ORDER BY a.employee.firstName")
    List<Application> sortByEmployee(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Application a WHERE a.createdAt BETWEEN :start AND :end")
    List<Application> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Application> findAllByCreatedAtBetweenAndCompanyStartingWithIgnoreCase(LocalDateTime start, LocalDateTime end, String text);
}
