package com.leadpilot.crm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.leadpilot.crm.dto.LeadCategoryRequest;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.CategoryStatus;
import com.leadpilot.crm.repository.LeadCategoryRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.LeadCategoryService;

/**
 * ==========================================================
 * Service Implementation : LeadCategoryServiceImpl
 *
 * Description :
 * Implements business operations related to Lead Categories.
 *
 * Supported operations:
 *
 * - Add Lead Category
 * - View Lead Category
 * - View All Lead Categories
 * - Update Lead Category
 * - Activate Lead Category
 * - Deactivate Lead Category
 * - Search Lead Categories
 * - Filter Lead Categories By Status
 * - Get Ordered Lead Categories
 *
 * ==========================================================
 */

@Service
public class LeadCategoryServiceImpl implements LeadCategoryService {

    // ==========================================================
    // Repositories
    // ==========================================================

    @Autowired
    private LeadCategoryRepository leadCategoryRepository;

    @Autowired
    private UserRepository userRepository;


    // ==========================================================
    // Create
    // ==========================================================

    @Override
    public LeadCategory addCategory(LeadCategoryRequest request) {

        // ------------------------------------------------------
        // Check whether category already exists
        // ------------------------------------------------------

        List<LeadCategory> existingCategories =
                leadCategoryRepository
                        .findByCategoryNameContainingIgnoreCase(
                                request.getCategoryName()
                        );

        for (LeadCategory category : existingCategories) {

            if (category.getCategoryName()
                    .equalsIgnoreCase(request.getCategoryName())) {

                throw new RuntimeException(
                        "Lead Category already exists with name: "
                                + request.getCategoryName()
                );
            }
        }

        // ------------------------------------------------------
        // Create Entity
        // ------------------------------------------------------

        LeadCategory category = new LeadCategory();

        category.setCategoryName(
                request.getCategoryName()
        );

        category.setDescription(
                request.getDescription()
        );

        category.setDisplayOrder(
                request.getDisplayOrder()
        );

        category.setStatus(
                request.getStatus()
        );

        // ------------------------------------------------------
        // Audit Information
        // ------------------------------------------------------

        User currentUser = getCurrentUser();

        category.setCreatedBy(currentUser);
        category.setUpdatedBy(currentUser);

        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return leadCategoryRepository.save(category);
    }


    // ==========================================================
    // Read - Get By ID
    // ==========================================================

    @Override
    public LeadCategory getCategoryById(Long categoryId) {

        return leadCategoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead Category not found with ID: "
                                        + categoryId
                        )
                );
    }


    // ==========================================================
    // Read - Get All
    // ==========================================================

    @Override
    public List<LeadCategory> getAllCategories() {

        return leadCategoryRepository.findAll();
    }


    // ==========================================================
    // Update
    // ==========================================================

    @Override
    public LeadCategory updateCategory(
            Long categoryId,
            LeadCategoryRequest request) {

        // ------------------------------------------------------
        // Find Existing Category
        // ------------------------------------------------------

        LeadCategory existingCategory =
                leadCategoryRepository.findById(categoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead Category not found with ID: "
                                                + categoryId
                                )
                        );

        // ------------------------------------------------------
        // Update Category Information
        // ------------------------------------------------------

        existingCategory.setCategoryName(
                request.getCategoryName()
        );

        existingCategory.setDescription(
                request.getDescription()
        );

        existingCategory.setDisplayOrder(
                request.getDisplayOrder()
        );

        existingCategory.setStatus(
                request.getStatus()
        );

        // ------------------------------------------------------
        // Update Audit User
        // ------------------------------------------------------

        User currentUser = getCurrentUser();

        existingCategory.setUpdatedBy(currentUser);

        // ------------------------------------------------------
        // Save Updated Category
        // ------------------------------------------------------

        return leadCategoryRepository.save(existingCategory);
    }


    // ==========================================================
    // Status Management
    // ==========================================================

    @Override
    public LeadCategory updateCategoryStatus(
            Long categoryId,
            CategoryStatus status) {

        // ------------------------------------------------------
        // Find Existing Category
        // ------------------------------------------------------

        LeadCategory existingCategory =
                leadCategoryRepository.findById(categoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead Category not found with ID: "
                                                + categoryId
                                )
                        );

        // ------------------------------------------------------
        // Update Status
        // ------------------------------------------------------

        existingCategory.setStatus(status);

        // ------------------------------------------------------
        // Update Audit User
        // ------------------------------------------------------

        User currentUser = getCurrentUser();

        existingCategory.setUpdatedBy(currentUser);

        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return leadCategoryRepository.save(existingCategory);
    }


    // ==========================================================
    // Activate Category
    // ==========================================================

    @Override
    public LeadCategory activateCategory(Long categoryId) {

        return updateCategoryStatus(
                categoryId,
                CategoryStatus.ACTIVE
        );
    }


    // ==========================================================
    // Deactivate Category
    // ==========================================================

    @Override
    public LeadCategory deactivateCategory(Long categoryId) {

        return updateCategoryStatus(
                categoryId,
                CategoryStatus.INACTIVE
        );
    }


    // ==========================================================
    // Search
    // ==========================================================

    @Override
    public List<LeadCategory> searchCategories(
            String categoryName) {

        return leadCategoryRepository
                .findByCategoryNameContainingIgnoreCase(
                        categoryName
                );
    }


    // ==========================================================
    // Filter By Status
    // ==========================================================

    @Override
    public List<LeadCategory> getCategoriesByStatus(
            CategoryStatus status) {

        return leadCategoryRepository.findByStatus(status);
    }


    // ==========================================================
    // Ordered Categories
    // ==========================================================

    @Override
    public List<LeadCategory> getCategoriesOrdered() {

        return leadCategoryRepository
                .findByStatusOrderByCategoryNameAsc(
                        CategoryStatus.ACTIVE
                );
    }


    // ==========================================================
    // Get Currently Logged-in User
    // ==========================================================

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        // ------------------------------------------------------
        // Check Authentication
        // ------------------------------------------------------

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                        authentication.getPrincipal())) {

            throw new RuntimeException(
                    "No authenticated user found."
            );
        }

        // ------------------------------------------------------
        // Get Username From JWT Authentication
        // ------------------------------------------------------

        String username = authentication.getName();

        // ------------------------------------------------------
        // Find User
        // ------------------------------------------------------

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found."
                        )
                );
    }
}