package com.leadpilot.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * Repository : LeadCategoryRepository
 *
 * Description :
 * Handles all database operations related to LeadCategory.
 *
 * The Admin can use this repository through the service
 * layer to:
 *
 * - Add lead categories
 * - View lead categories
 * - Edit lead categories
 * - Deactivate lead categories
 * - Search categories
 * - Filter categories by status
 *
 * Spring Data JPA automatically provides the implementation.
 * ==========================================================
 */

@Repository
public interface LeadCategoryRepository
        extends JpaRepository<LeadCategory, Long> {

    // ==========================================================
    // Find Category By Name
    // ==========================================================

    Optional<LeadCategory> findByCategoryName(String categoryName);

    // ==========================================================
    // Check Duplicate Category Name
    // ==========================================================

    boolean existsByCategoryName(String categoryName);

    // ==========================================================
    // Find Categories By Status
    // ==========================================================

    List<LeadCategory> findByStatus(CategoryStatus status);

    // ==========================================================
    // Find Active Categories
    // ==========================================================

    List<LeadCategory> findByStatusOrderByCategoryNameAsc(
            CategoryStatus status
    );

    // ==========================================================
    // Search Categories By Name
    // ==========================================================

    List<LeadCategory> findByCategoryNameContainingIgnoreCase(
            String categoryName
    );
}