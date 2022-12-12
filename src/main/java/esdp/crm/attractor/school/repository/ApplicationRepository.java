package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    Optional<Application> findById(Long id);
    Page<Application> findAll(Pageable pageable);

}
