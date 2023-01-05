package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByEmployee(User employee);

    @Query("SELECT a FROM Application as a WHERE a.employee is null")
    List<Application> findAllFree();

    @Transactional
    @Modifying
    @Query("UPDATE Application a SET " +
            "a.company=:company, " +
            "a.price=:price, " +
            "a.product.id=:product, " +
            "a.name=:name, " +
            "a.phone=:phone, " +
            "a.email=:email, " +
            "a.address=:address, " +
            "a.employee.id=:employee, " +
            "a.status.id=:status " +
            "WHERE a.id = :id ")
    void updateApplicationById(Long id, String company, BigDecimal price, Long product, String name, String phone, String email, String address, Long employee, Long status);

    List<Application> findApplicationByProductId(Long id);
    List<Application> findApplicationBySourceId(Long id);
    List<Application> findApplicationByEmployeeId(Long id);
    List<Application> findApplicationByStatusId(Long id);
    @Query("select sum (a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :status")
    Float getPriceByDateAndStatus (LocalDateTime startDate, LocalDateTime endDate, Long status);

    @Query("select count (a.price) FROM Application a WHERE a.createdAt BETWEEN :startDate AND :endDate AND a.status.id = :status")
    Float getCountByDateAndStatus (LocalDateTime startDate, LocalDateTime endDate, Long status);
}
