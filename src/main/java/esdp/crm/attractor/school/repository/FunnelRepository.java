package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunnelRepository extends JpaRepository<Funnel, Long> {
    boolean existsByName(String name);

    @Query(nativeQuery = true, value = "SELECT d.funnels_id FROM departments_funnels d WHERE d.department_id = :id")
    List<Long> findAllFunnelsIdByDepartmentId(@Param("id") Long id);

    @Query("SELECT f FROM Funnel f WHERE f.id = :id")
    Funnel findAllByFunnelsById(@Param("id") Long id);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO departments_funnels (department_id, funnels_id) VALUES (:departmentId, :funnelId)")
    void insertIntoDepartmentFunnels(@Param("departmentId") Long departmentId, @Param("funnelId") Long funnelId);
}
