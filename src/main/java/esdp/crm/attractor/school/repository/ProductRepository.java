package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT p.id, p.name FROM Product p")
    List<Object[]> getProductNameAndId();
    @Query("SELECT p FROM Product p WHERE p.department.id = :id")
    List<Product> findAllByDepartmentId(Long id);
}
