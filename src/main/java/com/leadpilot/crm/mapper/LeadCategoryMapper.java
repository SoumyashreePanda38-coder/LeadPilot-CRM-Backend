package com.leadpilot.crm.mapper;

import com.leadpilot.crm.dto.LeadCategoryRequest;
import com.leadpilot.crm.dto.LeadCategoryResponse;
import com.leadpilot.crm.entity.LeadCategory;

/**
 * ==========================================================
 * Mapper : LeadCategoryMapper
 *
 * Description :
 * Converts LeadCategory entities to DTOs and DTOs to
 * LeadCategory entities.
 *
 * Responsibilities:
 *
 * 1. LeadCategoryRequest -> LeadCategory
 * 2. LeadCategory -> LeadCategoryResponse
 * 3. Update existing LeadCategory from LeadCategoryRequest
 *
 * Audit user information is mapped only from Entity to
 * Response because LeadCategoryRequest does not contain
 * createdBy / updatedBy fields.
 * ==========================================================
 */
public class LeadCategoryMapper {

    // ==========================================================
    // Private Constructor
    //
    // Mapper contains only static utility methods.
    // ==========================================================

    private LeadCategoryMapper() {
    }


    // ==========================================================
    // Convert Request DTO -> Entity
    //
    // Used when creating a new Lead Category.
    // ==========================================================

    public static LeadCategory toEntity(
            LeadCategoryRequest request) {

        if (request == null) {
            return null;
        }

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

        return category;
    }


    // ==========================================================
    // Update Existing Entity From Request DTO
    //
    // Used when editing an existing Lead Category.
    //
    // Audit fields are intentionally NOT changed here.
    // createdBy, updatedBy, createdAt and updatedAt should
    // be controlled by the service/entity audit logic.
    // ==========================================================

    public static void updateEntity(
            LeadCategory category,
            LeadCategoryRequest request) {

        if (category == null || request == null) {
            return;
        }

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
    }


    // ==========================================================
    // Convert Entity -> Response DTO
    //
    // Used when returning Lead Category information
    // to the frontend.
    // ==========================================================

    public static LeadCategoryResponse toResponse(
            LeadCategory category) {

        if (category == null) {
            return null;
        }

        LeadCategoryResponse response =
                new LeadCategoryResponse();

        // ======================================================
        // Primary Key
        // ======================================================

        response.setCategoryId(
                category.getCategoryId()
        );


        // ======================================================
        // Category Information
        // ======================================================

        response.setCategoryName(
                category.getCategoryName()
        );

        response.setDescription(
                category.getDescription()
        );

        response.setDisplayOrder(
                category.getDisplayOrder()
        );


        // ======================================================
        // Category Status
        // ======================================================

        response.setStatus(
                category.getStatus()
        );


        // ======================================================
        // Created By
        //
        // Null-safe because createdBy is not mandatory
        // in the entity.
        // ======================================================

        if (category.getCreatedBy() != null) {

            response.setCreatedById(
                    category.getCreatedBy().getId()
            );

            response.setCreatedByName(
                    category.getCreatedBy().getFullName()
            );
        }


        // ======================================================
        // Updated By
        // ======================================================

        if (category.getUpdatedBy() != null) {

            response.setUpdatedById(
                    category.getUpdatedBy().getId()
            );

            response.setUpdatedByName(
                    category.getUpdatedBy().getFullName()
            );
        }


        // ======================================================
        // Audit Dates
        // ======================================================

        response.setCreatedAt(
                category.getCreatedAt()
        );

        response.setUpdatedAt(
                category.getUpdatedAt()
        );

        return response;
    }
}