package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}