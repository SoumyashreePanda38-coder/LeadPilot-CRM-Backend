package com.leadpilot.crm.mapper;

import com.leadpilot.crm.dto.LeadSourceRequest;
import com.leadpilot.crm.dto.LeadSourceResponse;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Mapper : LeadSourceMapper
 *
 * Description :
 * Converts LeadSource entities to DTOs and DTOs to entities.
 *
 * Responsibilities:
 *
 * 1. LeadSource -> LeadSourceResponse
 * 2. LeadSourceRequest -> LeadSource
 * 3. Update existing LeadSource from LeadSourceRequest
 *
 * This class does NOT perform database operations.
 * Database operations belong to the service/repository layer.
 *
 * ==========================================================
 */
public class LeadSourceMapper {

    // ==========================================================
    // Private Constructor
    // ==========================================================

    private LeadSourceMapper() {
        // Utility mapper class
    }

    // ==========================================================
    // Entity -> Response DTO
    // ==========================================================

    /**
     * Converts LeadSource entity into LeadSourceResponse DTO.
     *
     * @param leadSource LeadSource entity
     * @return LeadSourceResponse DTO
     */
    public static LeadSourceResponse toResponse(LeadSource leadSource) {

        if (leadSource == null) {
            return null;
        }

        LeadSourceResponse response = new LeadSourceResponse();

        // ------------------------------------------------------
        // Basic Information
        // ------------------------------------------------------

        response.setLeadSourceId(
                leadSource.getLeadSourceId()
        );

        response.setSourceName(
                leadSource.getSourceName()
        );

        response.setDescription(
                leadSource.getDescription()
        );

        response.setDisplayOrder(
                leadSource.getDisplayOrder()
        );

        // ------------------------------------------------------
        // Status
        // ------------------------------------------------------

        response.setStatus(
                leadSource.getStatus()
        );

        // ------------------------------------------------------
        // Created By
        // ------------------------------------------------------

        User createdBy = leadSource.getCreatedBy();

        if (createdBy != null) {

            response.setCreatedById(
                    createdBy.getId()
            );

            response.setCreatedByName(
                    getUserDisplayName(createdBy)
            );
        }

        // ------------------------------------------------------
        // Updated By
        // ------------------------------------------------------

        User updatedBy = leadSource.getUpdatedBy();

        if (updatedBy != null) {

            response.setUpdatedById(
                    updatedBy.getId()
            );

            response.setUpdatedByName(
                    getUserDisplayName(updatedBy)
            );
        }

        // ------------------------------------------------------
        // Audit Dates
        // ------------------------------------------------------

        response.setCreatedAt(
                leadSource.getCreatedAt()
        );

        response.setUpdatedAt(
                leadSource.getUpdatedAt()
        );

        return response;
    }

    // ==========================================================
    // Request DTO -> Entity
    // ==========================================================

    /**
     * Converts LeadSourceRequest into a new LeadSource entity.
     *
     * Note:
     * createdBy and updatedBy are intentionally not assigned here.
     * They should be assigned by the service layer using the
     * authenticated user.
     *
     * @param request LeadSourceRequest DTO
     * @return LeadSource entity
     */
    public static LeadSource toEntity(LeadSourceRequest request) {

        if (request == null) {
            return null;
        }

        LeadSource leadSource = new LeadSource();

        // ------------------------------------------------------
        // Basic Information
        // ------------------------------------------------------

        leadSource.setSourceName(
                request.getSourceName()
        );

        leadSource.setDescription(
                request.getDescription()
        );

        leadSource.setDisplayOrder(
                request.getDisplayOrder()
        );

        // ------------------------------------------------------
        // Status
        // ------------------------------------------------------

        leadSource.setStatus(
                request.getStatus()
        );

        return leadSource;
    }

    // ==========================================================
    // Update Existing Entity
    // ==========================================================

    /**
     * Updates an existing LeadSource using request data.
     *
     * The primary key, audit users and audit timestamps are
     * intentionally not modified here.
     *
     * @param leadSource existing LeadSource entity
     * @param request LeadSourceRequest DTO
     */
    public static void updateEntity(
            LeadSource leadSource,
            LeadSourceRequest request) {

        if (leadSource == null || request == null) {
            return;
        }

        // ------------------------------------------------------
        // Basic Information
        // ------------------------------------------------------

        leadSource.setSourceName(
                request.getSourceName()
        );

        leadSource.setDescription(
                request.getDescription()
        );

        leadSource.setDisplayOrder(
                request.getDisplayOrder()
        );

        // ------------------------------------------------------
        // Status
        // ------------------------------------------------------

        leadSource.setStatus(
                request.getStatus()
        );
    }

    // ==========================================================
    // User Display Name
    // ==========================================================

    /**
     * Returns the best available display name for a user.
     *
     * Adjust this method if your User entity uses different
     * field names.
     */
    private static String getUserDisplayName(User user) {

        if (user == null) {
            return null;
        }

        // Prefer full name
        if (user.getFullName() != null
                && !user.getFullName().isBlank()) {

            return user.getFullName();
        }

        // Fall back to username
        if (user.getUsername() != null
                && !user.getUsername().isBlank()) {

            return user.getUsername();
        }

        return null;
    }
}