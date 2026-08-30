package com.leadpilot.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * Repository : LeadSubCategoryRepository
 *
 * Description :
 * Handles all database operations related to LeadSubCategory.
 *
 * Admin can use this repository through the service layer to:
 *
 * - Add subcategories
 * - View subcategories
 * - Edit subcategories
 * - Activate / Deactivate subcategories
 * - Search subcategories
 * - Filter subcategories
 * - View subcategories under a particular category
 *
 * Spring Data JPA automatically provides the implementation.
 * ==========================================================
 */

@Repository
public interface LeadSubCategoryRepository
        extends JpaRepository<LeadSubCategory, Long> {

    // ==========================================================
    // Find Subcategory By Name
    // ==========================================================

    Optional<LeadSubCategory> findBySubCategoryName(
            String subCategoryName
    );

    // ==========================================================
    // Check Duplicate Subcategory Name
    // ==========================================================

    boolean existsBySubCategoryName(
            String subCategoryName
    );

    // ==========================================================
    // Find Subcategories By Parent Category
    // ==========================================================

    List<LeadSubCategory> findByLeadCategory(
            LeadCategory leadCategory
    );

    // ==========================================================
    // Find Subcategories By Category ID
    // ==========================================================

    List<LeadSubCategory> findByLeadCategory_CategoryId(
            Long categoryId
    );

    // ==========================================================
    // Find Subcategory By Category And Name
    // ==========================================================

    Optional<LeadSubCategory> findByLeadCategoryAndSubCategoryName(
            LeadCategory leadCategory,
            String subCategoryName
    );

    // ==========================================================
    // Check Duplicate Name Inside Same Category
    // ==========================================================

    boolean existsByLeadCategoryAndSubCategoryName(
            LeadCategory leadCategory,
            String subCategoryName
    );

    // ==========================================================
    // Find By Status
    // ==========================================================

    List<LeadSubCategory> findByStatus(
            CategoryStatus status
    );

    // ==========================================================
    // Find By Category And Status
    // ==========================================================

    List<LeadSubCategory> findByLeadCategoryAndStatus(
            LeadCategory leadCategory,
            CategoryStatus status
    );

    // ==========================================================
    // Find Active Subcategories Of A Category
    // Ordered By Display Order
    // ==========================================================

    List<LeadSubCategory> findByLeadCategoryAndStatusOrderByDisplayOrderAsc(
            LeadCategory leadCategory,
            CategoryStatus status
    );

    // ==========================================================
    // Search Subcategories By Name
    // ==========================================================

    List<LeadSubCategory> findBySubCategoryNameContainingIgnoreCase(
            String subCategoryName
    );

    // ==========================================================
    // Search Within A Particular Category
    // ==========================================================

    List<LeadSubCategory>
    findByLeadCategoryAndSubCategoryNameContainingIgnoreCase(
            LeadCategory leadCategory,
            String subCategoryName
    );

    // ==========================================================
    // Find All Subcategories Ordered By Display Order
    // ==========================================================

    List<LeadSubCategory> findAllByOrderByDisplayOrderAsc();

}