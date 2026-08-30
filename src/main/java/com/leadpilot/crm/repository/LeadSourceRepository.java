package com.leadpilot.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * Repository : LeadSourceRepository
 *
 * Description :
 * Handles all database operations related to LeadSource.
 *
 * Admin can use this repository through the service layer to:
 *
 * - Add lead sources
 * - View lead sources
 * - Edit lead sources
 * - Activate / Deactivate lead sources
 * - Search lead sources
 * - Filter lead sources
 * - Display lead sources in a specific order
 *
 * Spring Data JPA automatically provides the implementation.
 * ==========================================================
 */

@Repository
public interface LeadSourceRepository
        extends JpaRepository<LeadSource, Long> {

    // ==========================================================
    // Find Lead Source By Name
    // ==========================================================

    Optional<LeadSource> findBySourceName(
            String sourceName
    );

    // ==========================================================
    // Check Whether Source Name Already Exists
    // ==========================================================

    boolean existsBySourceName(
            String sourceName
    );

    // ==========================================================
    // Find Sources By Status
    // ==========================================================

    List<LeadSource> findByStatus(
            CategoryStatus status
    );

    // ==========================================================
    // Search Sources By Name
    // ==========================================================

    List<LeadSource> findBySourceNameContainingIgnoreCase(
            String sourceName
    );

    // ==========================================================
    // Find All Sources Ordered By Display Order
    // ==========================================================

    List<LeadSource> findAllByOrderByDisplayOrderAsc();

    // ==========================================================
    // Find Sources By Status
    // Ordered By Display Order
    // ==========================================================

    List<LeadSource> findByStatusOrderByDisplayOrderAsc(
            CategoryStatus status
    );

}