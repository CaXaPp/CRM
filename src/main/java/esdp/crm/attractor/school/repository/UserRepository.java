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
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findAllByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.role.value='ROLE_EMPLOYEE'")
    List<User> findAllEmployees();

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long getIdByEmail(String email);

    @Query("SELECT u.department.id FROM User u WHERE u.email = :email")
    Long getDepartmentIdByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.department.id = :id")
    List<Long> getAllIdByDepartmentId(Long id);

    @Query("SELECT u.id, u.firstName, u.surname FROM User u WHERE u.role.value = 'ROLE_EMPLOYEE'")
    List<Object[]> findAllEmployeeByRole();

    @Query("SELECT u FROM User u WHERE u.role.value NOT IN ('ROLE_ADMIN')")
    List<User> findAllUsersNotInAdmin();
}