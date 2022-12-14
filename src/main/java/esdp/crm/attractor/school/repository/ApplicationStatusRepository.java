package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
    ApplicationStatus getByName(String name);
}
