package esdp.crm.attractor.school.repository;

import esdp.crm.attractor.school.entity.Role;
import esdp.crm.attractor.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByRole(Role role);

    List<User> findAllByRole_Value(String role);

    @Query("SELECT u FROM User u WHERE u.role.value NOT IN ('ROLE_ADMIN')")
    List<User> findAllUsersNotInAdmin();
}