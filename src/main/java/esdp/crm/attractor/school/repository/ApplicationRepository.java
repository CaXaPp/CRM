package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;


public interface ApplicationRepository extends JpaRepository<Application, Long> {

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
