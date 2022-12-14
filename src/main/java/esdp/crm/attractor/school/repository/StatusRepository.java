package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status getByName(String name);
}
