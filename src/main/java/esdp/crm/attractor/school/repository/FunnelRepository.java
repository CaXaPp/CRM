package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunnelRepository extends JpaRepository<Funnel, Long> {

    @Query(nativeQuery = true, value = "select * from funnels f where f.id = :id")
    List<Funnel> findAllByFunnelsId(Long id); // Выборка производится путем сравнения id_department и id_funnels, нет сравнения в промежуточной таблице
}
