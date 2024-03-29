package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
    List<ApplicationStatus> getByName(String name);
    List<ApplicationStatus> findAllByFunnel(Funnel funnel);
    List<ApplicationStatus> findAllByName(String name);
    List<ApplicationStatus> findAllByFunnel_Id(Long id);

    ApplicationStatus findByFunnel_IdAndName(Long id, String status);

    @Query("SELECT a.id FROM ApplicationStatus a WHERE a.name = :status AND a.funnel.id = :funnel")
    Long getStatusIdByFunnel(@Param("status") String status, @Param("funnel") Long funnel);
}
