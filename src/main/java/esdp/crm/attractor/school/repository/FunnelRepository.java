package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunnelRepository extends JpaRepository<Funnel, Long> {
    @Query(nativeQuery = true, value = "SELECT d.funnels_id FROM departments_funnels d WHERE d.department_id = :id")
    List<Long> findAllFunnelsIdByDepartmentId(@Param("id") Long id);

    @Query("SELECT f FROM Funnel f WHERE f.id = :id")
    Funnel findAllByFunnelsById(@Param("id") Long id);

}
