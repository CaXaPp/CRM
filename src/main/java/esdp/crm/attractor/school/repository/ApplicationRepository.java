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

    @Query("SELECT a FROM Application as a WHERE a.employee IS NULL")
    List<Application> findAllFree();

    @Query("SELECT COUNT(a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status FROM Application a")
    List<Object[]> findSumAndCountOfApplication();
    @Query("SELECT COUNT(a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status FROM Application a WHERE a.product.id = :productId")
    List<Object[]> findApplicationByProductId(Long productId);

    @Query("SELECT COUNT (a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status FROM Application a WHERE a.source.id = :sourceId")
    List<Object[]> findApplicationBySourceId(Long sourceId);

    @Query("SELECT COUNT (a) AS count, SUM(a.price) AS price, SUM(CASE WHEN a.status.name = 'Отказ' THEN a.price ELSE 0 END) AS status FROM Application a WHERE a.employee.id = :employeeId")
    List<Object[]> findApplicationByEmployeeId(Long employeeId);
    List<Application> findApplicationByStatusId(Long id);
    @Query("SELECT a FROM Application a WHERE a.status IN :statuses AND a.employee IS NOT NULL")
    List<Application> findOperationsByFunnel(List<ApplicationStatus> statuses);




    @Query("select sum (a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :status")
    Float getPriceByDateAndStatus (LocalDateTime startDate, LocalDateTime endDate, Long status);

    @Query("select count (a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :status")
    Integer getCountByDateAndStatus (LocalDateTime startDate, LocalDateTime endDate, Long status);

    @Query("select sum (a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :status AND a.employee.id = :id")
    Float getPriceByDateAndStatusAndEmployeeId (LocalDateTime startDate, LocalDateTime endDate, Long status, Long id);

    @Query("select count (a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :status AND a.employee.id = :id")
    Integer getCountByDateAndStatusAndEmployeeId (LocalDateTime startDate, LocalDateTime endDate, Long status, Long id);



    @Query("SELECT COUNT(a), SUM(a.price) FROM Application a " +
            "WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :statusId " +
            "GROUP BY a.status")
    List<Object[]> findObjectV2(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("statusId") Long statusId);


    @Query("SELECT a.employee, a.company, a.status, a.price " +
            "FROM Application a " +
            "WHERE cast(a.createdAt as date) = cast(:date as date) AND a.status.name NOT IN (:status1, :status2)")
    List<Object[]> findAllActiveApplicationForToday(LocalDateTime date, String status1, String status2);

    @Query("SELECT a.employee, a.company, a.status, a.price " +
            "FROM Application a " +
            "WHERE cast(a.createdAt as date) = cast(:date as date) AND a.status.name NOT IN (:status1, :status2) AND a.employee.id = :userId")
    List<Object[]> findAllActiveApplicationForTodayByUserId(LocalDateTime date, String status1, String status2, Long userId);


    @Query("SELECT a.employee, a.company, a.status.name, a.price, a.source.name " +
            "FROM Application a " +
            "WHERE a.source IS NOT NULL AND a.employee IS NOT null ")
    List<Object[]> findAllSourceOfApplication();

    @Query("SELECT a.employee, a.company, a.status.name, a.price, a.source.name " +
            "FROM Application a " +
            "WHERE a.source IS NOT NULL AND a.employee IS NOT null AND a.employee.id = :userId")
    List<Object[]> findAllSourceOfApplicationByUserId(Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.employee IS NOT NULL")
    List<Object[]> findAllDealOfApplication();

    @Query("SELECT a.employee, a.company, a.status.name, a.price FROM Application a WHERE a.employee IS NOT NULL AND a.employee.id = :userId")
    List<Object[]> findAllDealOfApplicationByUserId(Long userId);

    @Query("SELECT a.employee, a.company, a.status.name, a.product.name, a.description FROM Application a WHERE a.status.name = 'Отказ'")
    List<Object[]> findAllFailureApplication();

    @Query("SELECT a.employee, a.company, a.status.name, a.product.name, a.description FROM Application a WHERE a.status.name = 'Отказ' AND a.employee.id = :userId")
    List<Object[]> findAllFailureApplicationByUserId(Long userId);
}
