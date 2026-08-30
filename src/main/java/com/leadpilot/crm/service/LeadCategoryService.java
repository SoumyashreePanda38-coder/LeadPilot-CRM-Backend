package com.leadpilot.crm.service;

import java.util.List;

import com.leadpilot.crm.dto.LeadCategoryRequest;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * Service : LeadCategoryService
 *
 * Description :
 * Defines business operations related to Lead Categories.
 *
 * The implementation of this interface will be provided by
 * LeadCategoryServiceImpl.
 *
 * Admin operations supported:
 *
 * - Add Lead Category
 * - View Lead Category
 * - View All Lead Categories
 * - Update Lead Category
 * - Activate Lead Category
 * - Deactivate Lead Category
 * - Search Lead Categories
 * - Filter Lead Categories By Status
 * ==========================================================
 */

public interface LeadCategoryService {

    // ==========================================================
    // Create
    // ==========================================================

    /**
     * Add a new lead category.
     *
     * @param leadCategory category information
     * @return saved lead category
     */
    LeadCategory addCategory(LeadCategoryRequest request);

    // ==========================================================
    // Read
    // ==========================================================

    /**
     * Get a lead category by its ID.
     *
     * @param categoryId category ID
     * @return lead category
     */
    LeadCategory getCategoryById(Long categoryId);

    /**
     * Get all lead categories.
     *
     * @return list of lead categories
     */
    List<LeadCategory> getAllCategories();

    // ==========================================================
    // Update
    // ==========================================================

    /**
     * Update an existing lead category.
     *
     * @param categoryId category ID
     * @param leadCategory updated category information
     * @return updated lead category
     */
    LeadCategory updateCategory(
            Long categoryId,
            LeadCategoryRequest request
    );

    // ==========================================================
    // Status Management
    // ==========================================================

    /**
     * Change the status of a lead category.
     *
     * This is preferred over permanently deleting a category
     * because existing leads may already be associated with it.
     *
     * @param categoryId category ID
     * @param status new category status
     * @return updated lead category
     */
    LeadCategory updateCategoryStatus(
            Long categoryId,
            CategoryStatus status
    );

    /**
     * Activate a lead category.
     *
     * @param categoryId category ID
     * @return activated lead category
     */
    LeadCategory activateCategory(Long categoryId);

    /**
     * Deactivate a lead category.
     *
     * @param categoryId category ID
     * @return deactivated lead category
     */
    LeadCategory deactivateCategory(Long categoryId);

    // ==========================================================
    // Search
    // ==========================================================

    /**
     * Search lead categories by name.
     *
     * @param categoryName search text
     * @return matching categories
     */
    List<LeadCategory> searchCategories(
            String categoryName
    );

    // ==========================================================
    // Filter
    // ==========================================================

    /**
     * Get categories by their status.
     *
     * @param status category status
     * @return categories having the given status
     */
    List<LeadCategory> getCategoriesByStatus(
            CategoryStatus status
    );

    // ==========================================================
    // Ordered Categories
    // ==========================================================

    /**
     * Get categories ordered by display order.
     *
     * @return ordered list of categories
     */
    List<LeadCategory> getCategoriesOrdered();

}