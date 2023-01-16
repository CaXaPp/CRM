package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunnelRepository extends JpaRepository<Funnel, Long> {
}