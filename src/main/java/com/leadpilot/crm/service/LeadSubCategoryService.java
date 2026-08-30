package com.leadpilot.crm.service;

import java.util.List;

import com.leadpilot.crm.dto.LeadSubCategoryRequest;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.enums.CategoryStatus;

/**
 * ==========================================================
 * Service : LeadSubCategoryService
 *
 * Description :
 * Defines business operations for LeadSubCategory.
 *
 * The implementation will be handled by
 * LeadSubCategoryServiceImpl.
 * ==========================================================
 */

public interface LeadSubCategoryService {

    // ==========================================================
    // CREATE
    // ==========================================================

	LeadSubCategory addSubCategory(LeadSubCategoryRequest request);

    // ==========================================================
    // READ
    // ==========================================================

    List<LeadSubCategory> getAllSubCategories();

    LeadSubCategory getSubCategoryById(
            Long subCategoryId
    );

    // ==========================================================
    // READ BY CATEGORY
    // ==========================================================

    List<LeadSubCategory> getSubCategoriesByCategory(
            Long categoryId
    );

    // ==========================================================
    // READ BY STATUS
    // ==========================================================

    List<LeadSubCategory> getSubCategoriesByStatus(
            CategoryStatus status
    );

    // ==========================================================
    // UPDATE
    // ==========================================================

    LeadSubCategory updateSubCategory(
            Long subCategoryId,
            LeadSubCategoryRequest request
    );

    // ==========================================================
    // ACTIVATE / DEACTIVATE
    // ==========================================================

    LeadSubCategory updateStatus(
            Long subCategoryId,
            CategoryStatus status
    );

    // ==========================================================
    // DELETE
    // ==========================================================

    void deleteSubCategory(
            Long subCategoryId
    );

    // ==========================================================
    // SEARCH
    // ==========================================================

    List<LeadSubCategory> searchSubCategories(
            String keyword
    );
}