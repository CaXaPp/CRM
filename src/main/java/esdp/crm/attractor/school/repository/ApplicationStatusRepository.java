package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
    List<ApplicationStatus> getByName(String name);
    List<ApplicationStatus> findAllByFunnel(Funnel funnel);

    List<ApplicationStatus> findAllByName(String name);

    List<ApplicationStatus> findAllByFunnel_Id(Long id);
}
