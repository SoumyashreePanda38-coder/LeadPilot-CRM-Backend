package com.leadpilot.crm.mapper;

import com.leadpilot.crm.dto.FollowUpRequest;
import com.leadpilot.crm.dto.FollowUpResponse;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Mapper : FollowUpMapper
 *
 * Description :
 * Converts FollowUp entities to DTOs and DTOs to entities.
 *
 * Responsibilities :
 *
 * 1. FollowUpRequest -> FollowUp
 * 2. FollowUp -> FollowUpResponse
 * 3. Update existing FollowUp from FollowUpRequest
 *
 * Important :
 *
 * - This mapper does NOT access repositories.
 * - Lead and assigned User relationships are resolved
 *   by the service layer.
 * - Status/completion lifecycle should preferably be
 *   controlled by dedicated service methods.
 *
 * ==========================================================
 */
public class FollowUpMapper {

    // ==========================================================
    // Private Constructor
    // ==========================================================

    private FollowUpMapper() {
        // Utility mapper class
    }

    // ==========================================================
    // Request DTO -> Entity
    // ==========================================================

    /**
     * Converts FollowUpRequest into a new FollowUp entity.
     *
     * Note:
     * CustomerLead and assignedUser are not set here because
     * they are entity relationships and should be resolved
     * by the service layer using their IDs.
     *
     * @param request FollowUpRequest
     * @return FollowUp entity
     */
    public static FollowUp toEntity(FollowUpRequest request) {

        if (request == null) {
            return null;
        }

        FollowUp followUp = new FollowUp();

        // ------------------------------------------------------
        // Follow-Up Type
        // ------------------------------------------------------

        followUp.setFollowUpType(
                request.getFollowUpType()
        );

        // ------------------------------------------------------
        // Subject
        // ------------------------------------------------------

        followUp.setSubject(
                request.getSubject()
        );

        // ------------------------------------------------------
        // Scheduled Date & Time
        // ------------------------------------------------------

        followUp.setScheduledAt(
                request.getScheduledAt()
        );

        // ------------------------------------------------------
        // Location
        // ------------------------------------------------------

        followUp.setLocation(
                request.getLocation()
        );

        // ------------------------------------------------------
        // Description
        // ------------------------------------------------------

        followUp.setDescription(
                request.getDescription()
        );

        /*
         * Status is intentionally not copied here.
         *
         * FollowUp entity @PrePersist already sets:
         *
         * FollowUpStatus.SCHEDULED
         *
         * if status is null.
         *
         * The service layer should control status transitions.
         */

        /*
         * completedAt and outcome are also intentionally not
         * copied during normal creation.
         *
         * They should normally be assigned when a follow-up
         * is completed.
         */

        return followUp;
    }

    // ==========================================================
    // Entity -> Response DTO
    // ==========================================================

    /**
     * Converts FollowUp entity into FollowUpResponse DTO.
     *
     * @param followUp FollowUp entity
     * @return FollowUpResponse DTO
     */
    public static FollowUpResponse toResponse(FollowUp followUp) {

        if (followUp == null) {
            return null;
        }

        FollowUpResponse response = new FollowUpResponse();

        // ------------------------------------------------------
        // Primary Key
        // ------------------------------------------------------

        response.setFollowUpId(
                followUp.getFollowUpId()
        );

        // ------------------------------------------------------
        // Follow-Up Information
        // ------------------------------------------------------

        response.setFollowUpType(
                followUp.getFollowUpType()
        );

        response.setSubject(
                followUp.getSubject()
        );

        response.setScheduledAt(
                followUp.getScheduledAt()
        );

        response.setLocation(
                followUp.getLocation()
        );

        response.setDescription(
                followUp.getDescription()
        );

        // ------------------------------------------------------
        // Status
        // ------------------------------------------------------

        response.setStatus(
                followUp.getStatus()
        );

        // ------------------------------------------------------
        // Completion Information
        // ------------------------------------------------------

        response.setCompletedAt(
                followUp.getCompletedAt()
        );

        response.setOutcome(
                followUp.getOutcome()
        );

        // ------------------------------------------------------
        // Customer Lead Information
        // ------------------------------------------------------

        CustomerLead lead = followUp.getCustomerLead();

        if (lead != null) {

            response.setLeadId(
                    lead.getLeadId()
            );

            response.setLeadName(
                    lead.getFullName()
            );
        }

        // ------------------------------------------------------
        // Assigned User / Executive Information
        // ------------------------------------------------------

        User assignedUser = followUp.getAssignedUser();

        if (assignedUser != null) {

            response.setAssignedUserId(
                    getUserId(assignedUser)
            );

            response.setAssignedUserName(
                    getUserDisplayName(assignedUser)
            );
        }

        // ------------------------------------------------------
        // Audit Information
        // ------------------------------------------------------

        response.setCreatedAt(
                followUp.getCreatedAt()
        );

        response.setUpdatedAt(
                followUp.getUpdatedAt()
        );

        // ------------------------------------------------------
        // Optimistic Locking
        // ------------------------------------------------------

        response.setVersion(
                followUp.getVersion()
        );

        return response;
    }

    // ==========================================================
    // Update Existing Entity From Request
    // ==========================================================

    /**
     * Updates an existing FollowUp entity using request data.
     *
     * Relationships are intentionally not changed here.
     * CustomerLead and assignedUser should be handled by
     * the service layer.
     *
     * Status, completedAt and outcome are also not changed
     * automatically because they represent follow-up lifecycle
     * operations.
     *
     * @param followUp existing FollowUp entity
     * @param request FollowUpRequest
     */
    public static void updateEntity(
            FollowUp followUp,
            FollowUpRequest request) {

        if (followUp == null || request == null) {
            return;
        }

        // ------------------------------------------------------
        // Follow-Up Type
        // ------------------------------------------------------

        followUp.setFollowUpType(
                request.getFollowUpType()
        );

        // ------------------------------------------------------
        // Subject
        // ------------------------------------------------------

        followUp.setSubject(
                request.getSubject()
        );

        // ------------------------------------------------------
        // Scheduled Date & Time
        // ------------------------------------------------------

        followUp.setScheduledAt(
                request.getScheduledAt()
        );

        // ------------------------------------------------------
        // Location
        // ------------------------------------------------------

        followUp.setLocation(
                request.getLocation()
        );

        // ------------------------------------------------------
        // Description
        // ------------------------------------------------------

        followUp.setDescription(
                request.getDescription()
        );

        /*
         * Do NOT update these here:
         *
         * - status
         * - completedAt
         * - outcome
         *
         * These should be handled by service methods such as:
         *
         * completeFollowUp()
         * cancelFollowUp()
         * rescheduleFollowUp()
         */
    }

    // ==========================================================
    // Helper : Get User ID
    // ==========================================================

    /**
     * Returns the ID of the assigned user.
     */
    private static Long getUserId(User user) {

        if (user == null) {
            return null;
        }

        return user.getId();
    }

    // ==========================================================
    // Helper : Get User Display Name
    // ==========================================================

    /**
     * Returns the best available display name for the user.
     *
     * Priority:
     * 1. fullName
     * 2. username
     */
    private static String getUserDisplayName(User user) {

        if (user == null) {
            return null;
        }

        // ------------------------------------------------------
        // Prefer Full Name
        // ------------------------------------------------------

        if (user.getFullName() != null
                && !user.getFullName().isBlank()) {

            return user.getFullName();
        }

        // ------------------------------------------------------
        // Fall Back To Username
        // ------------------------------------------------------

        if (user.getUsername() != null
                && !user.getUsername().isBlank()) {

            return user.getUsername();
        }

        return null;
    }
}