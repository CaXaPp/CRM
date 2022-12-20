package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByEmployee(User employee);

    @Query("SELECT a FROM Application as a WHERE a.employee is null")
    List<Application> findAllFree();

    @Query(nativeQuery = true, value = "UPDATE applications SET " +
            "company=:company, " +
            "price=:price, " +
            "product_id=:product, " +
            "name=:name, " +
            "phone=:phone, " +
            "email=:email, " +
            "address=:address, " +
            "employee_id=:employee, " +
            "status_id=:status " +
            "WHERE id = :id ")
    void updateApplicationById(Long id, String company, BigDecimal price, Long product, String name, String phone, String email, String address, Long employee, Long status);
}
