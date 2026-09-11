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
    // Organization
    // ==========================================================

    /**
     * Returns all users belonging to a particular organization.
     */
    List<User> findByOrganizationId(Long organizationId);

    /**
     * Returns the number of users belonging to a particular organization.
     */
    long countByOrganizationId(Long organizationId);


    // ==========================================================
    // Organization + Role
    // ==========================================================

    /**
     * Returns all users with a specific role
     * inside a particular organization.
     *
     * Example:
     * Find all EXECUTIVE users of organization 1.
     */
    List<User> findByOrganizationIdAndRole(
            Long organizationId,
            Role role
    );


    // ==========================================================
    // Organization + Status
    // ==========================================================

    /**
     * Returns all users with a specific status
     * inside a particular organization.
     */
    List<User> findByOrganizationIdAndStatus(
            Long organizationId,
            UserStatus status
    );


    // ==========================================================
    // Organization + Role + Status
    // ==========================================================

    /**
     * Returns users matching organization, role and status.
     *
     * Example:
     * Find all ACTIVE EXECUTIVEs of organization 1.
     */
    List<User> findByOrganizationIdAndRoleAndStatus(
            Long organizationId,
            Role role,
            UserStatus status
    );


    // ==========================================================
    // Dashboard Statistics
    // ==========================================================

    long countByRole(Role role);

    long countByStatus(UserStatus status);


    // ==========================================================
    // Organization Dashboard Statistics
    // ==========================================================

    /**
     * Counts users of a particular role
     * inside a particular organization.
     */
    long countByOrganizationIdAndRole(
            Long organizationId,
            Role role
    );

    /**
     * Counts users of a particular status
     * inside a particular organization.
     */
    long countByOrganizationIdAndStatus(
            Long organizationId,
            UserStatus status
    );

    /**
     * Counts users with a particular role and status
     * inside a particular organization.
     */
    long countByOrganizationIdAndRoleAndStatus(
            Long organizationId,
            Role role,
            UserStatus status
    );
}