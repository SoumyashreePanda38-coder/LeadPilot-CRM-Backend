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
 * IMPORTANT:
 *
 * Automatic Follow-Up reminder creation is handled by
 * ReminderService.
 *
 * This mapper does NOT:
 *
 * - Calculate reminder dates
 * - Access repositories
 * - Resolve database relationships
 * - Create reminders
 *
 * Business logic remains in the service layer.
 *
 * ==========================================================
 */
public class ReminderMapper {

    // ==========================================================
    // REQUEST DTO -> ENTITY
    // ==========================================================

    /**
     * Converts ReminderRequest into Reminder entity.
     *
     * Relationships are intentionally not resolved here.
     *
     * The service layer resolves:
     *
     * - CustomerLead
     * - FollowUp
     * - Assigned User
     */
    public static Reminder toEntity(
            ReminderRequest request) {

        if (request == null) {
            return null;
        }

        Reminder reminder =
                new Reminder();

        // ------------------------------------------------------
        // Reminder information
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
        // Reminder state
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
        // Relationships are NOT resolved here.
        // ------------------------------------------------------

        return reminder;
    }


    // ==========================================================
    // ENTITY -> RESPONSE DTO
    // ==========================================================

    public static ReminderResponse toResponse(
            Reminder reminder) {

        if (reminder == null) {
            return null;
        }

        ReminderResponse response =
                new ReminderResponse();


        // ------------------------------------------------------
        // Reminder ID
        // ------------------------------------------------------

        response.setReminderId(
                reminder.getReminderId()
        );


        // ------------------------------------------------------
        // Customer Lead
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
        // Follow-Up
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
        // Assigned User
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
        // Reminder information
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
        // Reminder state
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
        // Notification tracking
        // ------------------------------------------------------

        response.setNotificationSent(
                reminder.isNotificationSent()
        );

        response.setNotificationSentAt(
                reminder.getNotificationSentAt()
        );


        // ------------------------------------------------------
        // Completion information
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
        // Audit information
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
     * Updates reminder fields from ReminderRequest.
     *
     * Relationships are intentionally excluded.
     */
    public static void updateEntity(
            Reminder reminder,
            ReminderRequest request) {

        if (reminder == null
                || request == null) {

            return;
        }


        reminder.setTitle(
                request.getTitle()
        );

        reminder.setMessage(
                request.getMessage()
        );

        reminder.setReminderAt(
                request.getReminderAt()
        );

        reminder.setRead(
                request.isRead()
        );

        reminder.setCompleted(
                request.isCompleted()
        );

        reminder.setDismissed(
                request.isDismissed()
        );
    }
}