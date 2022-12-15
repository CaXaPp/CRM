package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.ClientSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSourceRepository extends JpaRepository<ClientSource, Long> {
}