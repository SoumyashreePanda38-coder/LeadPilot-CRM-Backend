package com.leadpilot.crm.mapper;

import com.leadpilot.crm.dto.ReminderRequest;
import com.leadpilot.crm.dto.ReminderResponse;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.Reminder;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Mapper : ReminderMapper
 *
 * Description :
 * Converts Reminder entities to DTOs and DTOs to entities.
 *
 * Responsibilities :
 *
 * - Convert ReminderRequest -> Reminder
 * - Convert Reminder -> ReminderResponse
 * - Update existing Reminder from ReminderRequest
 *
 * Important :
 *
 * - This mapper does NOT access repositories.
 * - CustomerLead, FollowUp and User relationships
 *   are resolved in the service layer.
 *
 * ==========================================================
 */
public class ReminderMapper {

    // ==========================================================
    // REQUEST DTO -> ENTITY
    // ==========================================================

    /**
     * Converts ReminderRequest into a Reminder entity.
     *
     * Relationships are intentionally not resolved here.
     * The service layer should fetch:
     *
     * - CustomerLead
     * - FollowUp
     * - User
     *
     * and then set them on the Reminder entity.
     *
     * @param request reminder request DTO
     * @return Reminder entity
     */
    public static Reminder toEntity(
            ReminderRequest request) {

        if (request == null) {
            return null;
        }

        Reminder reminder = new Reminder();

        // ------------------------------------------------------
        // Reminder Information
        // ------------------------------------------------------

        reminder.setTitle(
                request.getTitle()
        );

        reminder.setMessage(
                request.getMessage()
        );

        reminder.setReminderAt(
                request.getReminderAt()
        );

        // ------------------------------------------------------
        // Reminder State
        // ------------------------------------------------------

        reminder.setRead(
                request.isRead()
        );

        reminder.setCompleted(
                request.isCompleted()
        );

        reminder.setDismissed(
                request.isDismissed()
        );

        // ------------------------------------------------------
        // Relationships
        //
        // DO NOT resolve IDs here.
        //
        // Service layer should set:
        //
        // reminder.setCustomerLead(...)
        // reminder.setFollowUp(...)
        // reminder.setAssignedTo(...)
        // ------------------------------------------------------

        return reminder;
    }


    // ==========================================================
    // ENTITY -> RESPONSE DTO
    // ==========================================================

    /**
     * Converts Reminder entity into ReminderResponse DTO.
     *
     * @param reminder Reminder entity
     * @return ReminderResponse DTO
     */
    public static ReminderResponse toResponse(
            Reminder reminder) {

        if (reminder == null) {
            return null;
        }

        ReminderResponse response =
                new ReminderResponse();

        // ------------------------------------------------------
        // Primary Key
        // ------------------------------------------------------

        response.setReminderId(
                reminder.getReminderId()
        );


        // ------------------------------------------------------
        // Customer Lead Information
        // ------------------------------------------------------

        CustomerLead customerLead =
                reminder.getCustomerLead();

        if (customerLead != null) {

            response.setLeadId(
                    customerLead.getLeadId()
            );

            response.setLeadName(
                    customerLead.getFullName()
            );
        }


        // ------------------------------------------------------
        // Follow-Up Information
        // ------------------------------------------------------

        FollowUp followUp =
                reminder.getFollowUp();

        if (followUp != null) {

            response.setFollowUpId(
                    followUp.getFollowUpId()
            );

            response.setFollowUpSubject(
                    followUp.getSubject()
            );
        }


        // ------------------------------------------------------
        // Assigned User Information
        // ------------------------------------------------------

        User assignedTo =
                reminder.getAssignedTo();

        if (assignedTo != null) {

            response.setAssignedToId(
                    assignedTo.getId()
            );

            response.setAssignedToName(
                    assignedTo.getFullName()
            );
        }


        // ------------------------------------------------------
        // Reminder Information
        // ------------------------------------------------------

        response.setTitle(
                reminder.getTitle()
        );

        response.setMessage(
                reminder.getMessage()
        );

        response.setReminderAt(
                reminder.getReminderAt()
        );


        // ------------------------------------------------------
        // Reminder State
        // ------------------------------------------------------

        response.setRead(
                reminder.isRead()
        );

        response.setCompleted(
                reminder.isCompleted()
        );

        response.setDismissed(
                reminder.isDismissed()
        );


        // ------------------------------------------------------
        // Notification Tracking
        // ------------------------------------------------------

        response.setNotificationSent(
                reminder.isNotificationSent()
        );

        response.setNotificationSentAt(
                reminder.getNotificationSentAt()
        );


        // ------------------------------------------------------
        // Completion Information
        // ------------------------------------------------------

        response.setCompletedAt(
                reminder.getCompletedAt()
        );


        // ------------------------------------------------------
        // Created By
        // ------------------------------------------------------

        User createdBy =
                reminder.getCreatedBy();

        if (createdBy != null) {

            response.setCreatedById(
                    createdBy.getId()
            );

            response.setCreatedByName(
                    createdBy.getFullName()
            );
        }


        // ------------------------------------------------------
        // Updated By
        // ------------------------------------------------------

        User updatedBy =
                reminder.getUpdatedBy();

        if (updatedBy != null) {

            response.setUpdatedById(
                    updatedBy.getId()
            );

            response.setUpdatedByName(
                    updatedBy.getFullName()
            );
        }


        // ------------------------------------------------------
        // Audit Information
        // ------------------------------------------------------

        response.setCreatedAt(
                reminder.getCreatedAt()
        );

        response.setUpdatedAt(
                reminder.getUpdatedAt()
        );


        return response;
    }


    // ==========================================================
    // UPDATE ENTITY FROM REQUEST
    // ==========================================================

    /**
     * Updates an existing Reminder using ReminderRequest.
     *
     * Relationships are intentionally not changed here.
     *
     * The service layer should handle:
     *
     * - CustomerLead
     * - FollowUp
     * - Assigned User
     *
     * @param reminder existing Reminder entity
     * @param request updated reminder information
     */
    public static void updateEntity(
            Reminder reminder,
            ReminderRequest request) {

        if (reminder == null || request == null) {
            return;
        }

        // ------------------------------------------------------
        // Update Reminder Information
        // ------------------------------------------------------

        reminder.setTitle(
                request.getTitle()
        );

        reminder.setMessage(
                request.getMessage()
        );

        reminder.setReminderAt(
                request.getReminderAt()
        );


        // ------------------------------------------------------
        // Update State
        // ------------------------------------------------------

        reminder.setRead(
                request.isRead()
        );

        reminder.setCompleted(
                request.isCompleted()
        );

        reminder.setDismissed(
                request.isDismissed()
        );

        // ------------------------------------------------------
        // Relationships intentionally excluded.
        //
        // Service layer should handle:
        //
        // reminder.setCustomerLead(...)
        // reminder.setFollowUp(...)
        // reminder.setAssignedTo(...)
        // ------------------------------------------------------
    }
}