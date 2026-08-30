package com.leadpilot.crm.mapper;

import com.leadpilot.crm.dto.LeadSubCategoryRequest;
import com.leadpilot.crm.dto.LeadSubCategoryResponse;
import com.leadpilot.crm.entity.LeadSubCategory;

/**
 * ==========================================================
 * Mapper : LeadSubCategoryMapper
 *
 * Description :
 * Converts LeadSubCategory entities to DTOs and DTOs to
 * LeadSubCategory entities.
 *
 * Responsibilities:
 *
 * 1. LeadSubCategoryRequest -> LeadSubCategory
 * 2. LeadSubCategory -> LeadSubCategoryResponse
 * 3. Update existing LeadSubCategory from Request DTO
 *
 * Parent category is resolved by the service layer using
 * categoryId from LeadSubCategoryRequest.
 *
 * Audit information is mapped from Entity -> Response.
 * ==========================================================
 */
public class LeadSubCategoryMapper {

    // ==========================================================
    // Private Constructor
    //
    // Utility mapper class.
    // ==========================================================

    private LeadSubCategoryMapper() {
    }


    // ==========================================================
    // Convert Request DTO -> Entity
    //
    // Used when creating a new Lead Subcategory.
    //
    // IMPORTANT:
    // categoryId is NOT directly converted to LeadCategory here.
    // The service layer should find the LeadCategory by ID and
    // call:
    //
    // leadSubCategory.setLeadCategory(category);
    //
    // This avoids putting repository/database logic inside
    // the mapper.
    // ==========================================================

    public static LeadSubCategory toEntity(
            LeadSubCategoryRequest request) {

        if (request == null) {
            return null;
        }

        LeadSubCategory subCategory =
                new LeadSubCategory();

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

        return subCategory;
    }


    // ==========================================================
    // Update Existing Entity From Request DTO
    //
    // Used while editing an existing Lead Subcategory.
    //
    // The parent LeadCategory is intentionally NOT changed here.
    // The service layer should resolve categoryId and decide
    // whether changing the parent category is allowed.
    //
    // Audit fields are also not modified here.
    // ==========================================================

    public static void updateEntity(
            LeadSubCategory subCategory,
            LeadSubCategoryRequest request) {

        if (subCategory == null || request == null) {
            return;
        }

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
    }


    // ==========================================================
    // Convert Entity -> Response DTO
    //
    // Used when sending Lead Subcategory information
    // to the frontend.
    // ==========================================================

    public static LeadSubCategoryResponse toResponse(
            LeadSubCategory subCategory) {

        if (subCategory == null) {
            return null;
        }

        LeadSubCategoryResponse response =
                new LeadSubCategoryResponse();


        // ======================================================
        // Primary Key
        // ======================================================

        response.setSubCategoryId(
                subCategory.getSubCategoryId()
        );


        // ======================================================
        // Parent Category
        //
        // Null-safe because the relationship is accessed
        // through a potentially lazy-loaded entity.
        // ======================================================

        if (subCategory.getLeadCategory() != null) {

            response.setCategoryId(
                    subCategory.getLeadCategory().getCategoryId()
            );

            response.setCategoryName(
                    subCategory.getLeadCategory().getCategoryName()
            );
        }


        // ======================================================
        // Subcategory Information
        // ======================================================

        response.setSubCategoryName(
                subCategory.getSubCategoryName()
        );

        response.setDescription(
                subCategory.getDescription()
        );

        response.setDisplayOrder(
                subCategory.getDisplayOrder()
        );


        // ======================================================
        // Status
        // ======================================================

        response.setStatus(
                subCategory.getStatus()
        );


        // ======================================================
        // Created By
        // ======================================================

        if (subCategory.getCreatedBy() != null) {

            response.setCreatedById(
                    subCategory.getCreatedBy().getId()
            );

            response.setCreatedByName(
                    subCategory.getCreatedBy().getFullName()
            );
        }


        // ======================================================
        // Updated By
        // ======================================================

        if (subCategory.getUpdatedBy() != null) {

            response.setUpdatedById(
                    subCategory.getUpdatedBy().getId()
            );

            response.setUpdatedByName(
                    subCategory.getUpdatedBy().getFullName()
            );
        }


        // ======================================================
        // Audit Dates
        // ======================================================

        response.setCreatedAt(
                subCategory.getCreatedAt()
        );

        response.setUpdatedAt(
                subCategory.getUpdatedAt()
        );

        return response;
    }
}