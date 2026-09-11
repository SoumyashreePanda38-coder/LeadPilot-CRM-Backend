package com.leadpilot.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.Organization;

/**
 * ==========================================================
 * Repository : OrganizationRepository
 *
 * Description :
 * Handles database operations related to organizations/
 * workspaces in LeadPilot CRM.
 *
 * ==========================================================
 */

@Repository
public interface OrganizationRepository
        extends JpaRepository<Organization, Long> {

    // ==========================================================
    // FIND ORGANIZATION BY NAME
    // ==========================================================

    Optional<Organization> findByOrganizationName(
            String organizationName
    );

    // ==========================================================
    // CHECK ORGANIZATION NAME
    // ==========================================================

    boolean existsByOrganizationName(
            String organizationName
    );
}