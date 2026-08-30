package com.leadpilot.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.Role;
import com.leadpilot.crm.enums.UserStatus;

/**
 * ==========================================================
 * Repository : UserRepository
 *
 * Description :
 * Handles all database operations related to User Entity.
 * Spring Data JPA automatically provides the implementation
 * for all the methods declared below.
 * ==========================================================
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ==========================================================
    // Authentication
    // ==========================================================

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // ==========================================================
    // Validation
    // ==========================================================

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByEmployeeId(String employeeId);

    // ==========================================================
    // Search
    // ==========================================================

    Optional<User> findByEmployeeId(String employeeId);

    // ==========================================================
    // Filter
    // ==========================================================

    List<User> findByRole(Role role);

    List<User> findByStatus(UserStatus status);

    List<User> findByRoleAndStatus(Role role, UserStatus status);

    // ==========================================================
    // Dashboard Statistics
    // ==========================================================

    long countByRole(Role role);

    long countByStatus(UserStatus status);

}