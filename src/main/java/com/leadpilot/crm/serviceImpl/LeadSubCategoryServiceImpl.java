package com.leadpilot.crm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.dto.LeadSubCategoryRequest;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.enums.CategoryStatus;
import com.leadpilot.crm.repository.LeadCategoryRepository;
import com.leadpilot.crm.repository.LeadSubCategoryRepository;
import com.leadpilot.crm.service.LeadSubCategoryService;

/**
 * ==========================================================
 * Service Implementation : LeadSubCategoryServiceImpl
 *
 * Description :
 * Implements business operations related to LeadSubCategory.
 *
 * Operations:
 *
 * - Add Subcategory
 * - View All Subcategories
 * - View Subcategory By ID
 * - View Subcategories By Category
 * - View Subcategories By Status
 * - Update Subcategory
 * - Update Status
 * - Delete Subcategory
 * - Search Subcategories
 * ==========================================================
 */

@Service
@Transactional
public class LeadSubCategoryServiceImpl
        implements LeadSubCategoryService {

    // ==========================================================
    // Repository Dependencies
    // ==========================================================

    @Autowired
    private LeadSubCategoryRepository leadSubCategoryRepository;

    @Autowired
    private LeadCategoryRepository leadCategoryRepository;


    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public LeadSubCategory addSubCategory(
            LeadSubCategoryRequest request) {

        // ------------------------------------------------------
        // Find Parent Category
        // ------------------------------------------------------

        LeadCategory leadCategory =
                leadCategoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead category not found with ID: "
                                                + request.getCategoryId()
                                )
                        );


        // ------------------------------------------------------
        // Check Duplicate Subcategory
        // ------------------------------------------------------

        if (leadSubCategoryRepository
                .existsByLeadCategoryAndSubCategoryName(
                        leadCategory,
                        request.getSubCategoryName())) {

            throw new RuntimeException(
                    "Subcategory already exists under this category"
            );
        }


        // ------------------------------------------------------
        // Create Entity
        // ------------------------------------------------------

        LeadSubCategory subCategory =
                new LeadSubCategory();


        // ------------------------------------------------------
        // Set Parent Category
        // ------------------------------------------------------

        subCategory.setLeadCategory(
                leadCategory
        );


        // ------------------------------------------------------
        // Set Request Fields
        // ------------------------------------------------------

        subCategory.setSubCategoryName(
                request.getSubCategoryName()
        );

        subCategory.setDescription(
                request.getDescription()
        );

        subCategory.setDisplayOrder(
                request.getDisplayOrder()
        );

        subCategory.setStatus(
                request.getStatus()
        );


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return leadSubCategoryRepository.save(
                subCategory
        );
    }


    // ==========================================================
    // READ ALL
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadSubCategory> getAllSubCategories() {

        return leadSubCategoryRepository
                .findAllByOrderByDisplayOrderAsc();
    }


    // ==========================================================
    // READ BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public LeadSubCategory getSubCategoryById(
            Long subCategoryId) {

        return leadSubCategoryRepository
                .findById(subCategoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead subcategory not found with ID: "
                                        + subCategoryId
                        )
                );
    }


    // ==========================================================
    // READ BY CATEGORY
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadSubCategory> getSubCategoriesByCategory(
            Long categoryId) {

        // ------------------------------------------------------
        // Verify Category Exists
        // ------------------------------------------------------

        LeadCategory leadCategory =
                leadCategoryRepository
                        .findById(categoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead category not found with ID: "
                                                + categoryId
                                )
                        );


        // ------------------------------------------------------
        // Get Subcategories
        // ------------------------------------------------------

        return leadSubCategoryRepository
                .findByLeadCategory(leadCategory);
    }


    // ==========================================================
    // READ BY STATUS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadSubCategory> getSubCategoriesByStatus(
            CategoryStatus status) {

        return leadSubCategoryRepository
                .findByStatus(status);
    }


    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public LeadSubCategory updateSubCategory(
            Long subCategoryId,
            LeadSubCategoryRequest request) {

        // ------------------------------------------------------
        // Find Existing Subcategory
        // ------------------------------------------------------

        LeadSubCategory existingSubCategory =
                leadSubCategoryRepository
                        .findById(subCategoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead subcategory not found with ID: "
                                                + subCategoryId
                                )
                        );


        // ------------------------------------------------------
        // Find New Parent Category
        // ------------------------------------------------------

        LeadCategory leadCategory =
                leadCategoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead category not found with ID: "
                                                + request.getCategoryId()
                                )
                        );


        // ------------------------------------------------------
        // Check Duplicate Name
        // ------------------------------------------------------

        boolean nameChanged =
                !existingSubCategory
                        .getSubCategoryName()
                        .equalsIgnoreCase(
                                request.getSubCategoryName()
                        );


        boolean categoryChanged =
                existingSubCategory
                        .getLeadCategory()
                        .getCategoryId()
                        .longValue()
                        != request.getCategoryId().longValue();


        if ((nameChanged || categoryChanged)
                && leadSubCategoryRepository
                    .existsByLeadCategoryAndSubCategoryName(
                            leadCategory,
                            request.getSubCategoryName())) {

            throw new RuntimeException(
                    "Subcategory already exists under this category"
            );
        }


        // ------------------------------------------------------
        // Update Parent Category
        // ------------------------------------------------------

        existingSubCategory.setLeadCategory(
                leadCategory
        );


        // ------------------------------------------------------
        // Update Subcategory Details
        // ------------------------------------------------------

        existingSubCategory.setSubCategoryName(
                request.getSubCategoryName()
        );

        existingSubCategory.setDescription(
                request.getDescription()
        );

        existingSubCategory.setDisplayOrder(
                request.getDisplayOrder()
        );

        existingSubCategory.setStatus(
                request.getStatus()
        );


        // ------------------------------------------------------
        // Save Updated Entity
        // ------------------------------------------------------

        return leadSubCategoryRepository.save(
                existingSubCategory
        );
    }


    // ==========================================================
    // UPDATE STATUS
    // ==========================================================

    @Override
    public LeadSubCategory updateStatus(
            Long subCategoryId,
            CategoryStatus status) {

        // ------------------------------------------------------
        // Find Existing Subcategory
        // ------------------------------------------------------

        LeadSubCategory existingSubCategory =
                leadSubCategoryRepository
                        .findById(subCategoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead subcategory not found with ID: "
                                                + subCategoryId
                                )
                        );


        // ------------------------------------------------------
        // Update Status
        // ------------------------------------------------------

        existingSubCategory.setStatus(
                status
        );


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return leadSubCategoryRepository.save(
                existingSubCategory
        );
    }


    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteSubCategory(
            Long subCategoryId) {

        // ------------------------------------------------------
        // Find Existing Subcategory
        // ------------------------------------------------------

        LeadSubCategory existingSubCategory =
                leadSubCategoryRepository
                        .findById(subCategoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead subcategory not found with ID: "
                                                + subCategoryId
                                )
                        );


        // ------------------------------------------------------
        // Delete
        // ------------------------------------------------------

        leadSubCategoryRepository.delete(
                existingSubCategory
        );
    }


    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadSubCategory> searchSubCategories(
            String keyword) {

        return leadSubCategoryRepository
                .findBySubCategoryNameContainingIgnoreCase(
                        keyword
                );
    }
}