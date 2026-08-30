
package com.leadpilot.crm.service;

import java.time.LocalDateTime;
import java.util.List;

import com.leadpilot.crm.dto.ReminderRequest;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.Reminder;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Service : ReminderService
 *
 * Description :
 * Defines business operations for managing CRM reminders.
 *
 * IMPORTANT:
 *
 * Create and Update operations use ReminderRequest DTO
 * instead of accepting Reminder entities directly.
 *
 * The service implementation is responsible for:
 *
 * - Converting ReminderRequest -> Reminder entity
 * - Resolving CustomerLead
 * - Resolving FollowUp
 * - Resolving Assigned User
 * - Saving the Reminder entity
 *
 * Read operations continue to return Reminder entities
 * internally. The controller can convert them to
 * ReminderResponse using ReminderMapper.
 *
 * ==========================================================
 */
public interface ReminderService {

    // ==========================================================
    // CREATE
    // ==========================================================

    /**
     * Creates a new reminder.
     *
     * ReminderRequest contains the incoming reminder data.
     * The service implementation converts it into a Reminder
     * entity and resolves all required relationships.
     *
     * @param request reminder request DTO
     * @return saved reminder entity
     */
    Reminder createReminder(ReminderRequest request);


    // ==========================================================
    // READ
    // ==========================================================

    /**
     * Gets all reminders.
     *
     * @return list of reminders
     */
    List<Reminder> getAllReminders();


    /**
     * Gets a reminder by its ID.
     *
     * @param reminderId reminder ID
     * @return reminder
     */
    Reminder getReminderById(Long reminderId);


    // ==========================================================
    // UPDATE
    // ==========================================================

    /**
     * Updates an existing reminder.
     *
     * ReminderRequest contains the updated reminder data.
     *
     * The service implementation is responsible for:
     *
     * - Finding the existing Reminder
     * - Updating basic reminder information
     * - Resolving CustomerLead
     * - Resolving FollowUp
     * - Resolving Assigned User
     * - Updating audit information
     *
     * @param reminderId reminder ID
     * @param request updated reminder request DTO
     * @param updatedBy user performing the update
     * @return updated reminder entity
     */
    Reminder updateReminder(
            Long reminderId,
            ReminderRequest request,
            User updatedBy
    );


    // ==========================================================
    // DELETE
    // ==========================================================

    /**
     * Deletes a reminder permanently.
     *
     * @param reminderId reminder ID
     */
    void deleteReminder(Long reminderId);


    // ==========================================================
    // ASSIGNED USER
    // ==========================================================

    /**
     * Gets all reminders assigned to a particular user.
     *
     * @param assignedTo assigned user
     * @return reminders assigned to the user
     */
    List<Reminder> getRemindersByAssignedUser(
            User assignedTo
    );


    /**
     * Gets reminders assigned to a user ordered by
     * reminder date.
     *
     * @param assignedTo assigned user
     * @return ordered reminders
     */
    List<Reminder> getRemindersByAssignedUserOrdered(
            User assignedTo
    );


    /**
     * Gets reminders assigned to a user using user ID.
     *
     * @param userId assigned user ID
     * @return reminders
     */
    List<Reminder> getRemindersByAssignedUserId(
            Long userId
    );


    /**
     * Gets pending reminders assigned to a particular user.
     *
     * Pending means:
     *
     * - completed = false
     * - dismissed = false
     *
     * @param userId assigned user ID
     * @return pending reminders
     */
    List<Reminder> getPendingRemindersByAssignedUser(
            Long userId
    );


    // ==========================================================
    // CUSTOMER LEAD
    // ==========================================================

    /**
     * Gets all reminders associated with a customer lead.
     *
     * @param customerLead customer lead
     * @return reminders
     */
    List<Reminder> getRemindersByLead(
            CustomerLead customerLead
    );


    /**
     * Gets reminders associated with a lead using lead ID.
     *
     * @param leadId customer lead ID
     * @return reminders
     */
    List<Reminder> getRemindersByLeadId(
            Long leadId
    );


    /**
     * Gets reminders of a lead ordered by reminder date.
     *
     * @param leadId customer lead ID
     * @return ordered reminders
     */
    List<Reminder> getRemindersByLeadIdOrdered(
            Long leadId
    );


    // ==========================================================
    // FOLLOW-UP
    // ==========================================================

    /**
     * Gets all reminders associated with a follow-up.
     *
     * @param followUp follow-up entity
     * @return reminders
     */
    List<Reminder> getRemindersByFollowUp(
            FollowUp followUp
    );


    /**
     * Gets reminders associated with a follow-up using
     * follow-up ID.
     *
     * @param followUpId follow-up ID
     * @return reminders
     */
    List<Reminder> getRemindersByFollowUpId(
            Long followUpId
    );


    // ==========================================================
    // UNREAD REMINDERS
    // ==========================================================

    /**
     * Gets all unread reminders.
     *
     * @return unread reminders
     */
    List<Reminder> getUnreadReminders();


    /**
     * Gets unread reminders assigned to a particular user.
     *
     * @param userId assigned user ID
     * @return unread reminders
     */
    List<Reminder> getUnreadRemindersByUser(
            Long userId
    );


    /**
     * Gets unread and pending reminders.
     *
     * @return unread pending reminders
     */
    List<Reminder> getUnreadPendingReminders();


    /**
     * Gets unread and pending reminders assigned to a user.
     *
     * @param userId assigned user ID
     * @return unread pending reminders
     */
    List<Reminder> getUnreadPendingRemindersByUser(
            Long userId
    );


    /**
     * Marks a reminder as read.
     *
     * @param reminderId reminder ID
     * @return updated reminder
     */
    Reminder markAsRead(Long reminderId);


    // ==========================================================
    // COMPLETED REMINDERS
    // ==========================================================

    /**
     * Gets all completed reminders.
     *
     * @return completed reminders
     */
    List<Reminder> getCompletedReminders();


    /**
     * Gets completed reminders assigned to a user.
     *
     * @param userId assigned user ID
     * @return completed reminders
     */
    List<Reminder> getCompletedRemindersByUser(
            Long userId
    );


    /**
     * Marks a reminder as completed.
     *
     * This also records the completion date and time.
     *
     * @param reminderId reminder ID
     * @return completed reminder
     */
    Reminder completeReminder(Long reminderId);


    // ==========================================================
    // PENDING REMINDERS
    // ==========================================================

    /**
     * Gets all pending reminders.
     *
     * Pending means:
     *
     * - completed = false
     * - dismissed = false
     *
     * @return pending reminders
     */
    List<Reminder> getPendingReminders();


    /**
     * Gets pending reminders ordered by reminder date.
     *
     * @return ordered pending reminders
     */
    List<Reminder> getPendingRemindersOrdered();


    /**
     * Gets pending reminders assigned to a user.
     *
     * @param userId assigned user ID
     * @return pending reminders
     */
    List<Reminder> getPendingRemindersByUser(
            Long userId
    );


    // ==========================================================
    // DISMISSED REMINDERS
    // ==========================================================

    /**
     * Gets all dismissed reminders.
     *
     * @return dismissed reminders
     */
    List<Reminder> getDismissedReminders();


    /**
     * Gets dismissed reminders assigned to a user.
     *
     * @param userId assigned user ID
     * @return dismissed reminders
     */
    List<Reminder> getDismissedRemindersByUser(
            Long userId
    );


    /**
     * Dismisses a reminder.
     *
     * @param reminderId reminder ID
     * @return dismissed reminder
     */
    Reminder dismissReminder(Long reminderId);


    /**
     * Restores a dismissed reminder.
     *
     * @param reminderId reminder ID
     * @return restored reminder
     */
    Reminder restoreReminder(Long reminderId);


    // ==========================================================
    // UPCOMING REMINDERS
    // ==========================================================

    /**
     * Gets upcoming pending reminders after the supplied
     * date and time.
     *
     * @param dateTime starting date and time
     * @return upcoming reminders
     */
    List<Reminder> getUpcomingReminders(
            LocalDateTime dateTime
    );


    /**
     * Gets upcoming pending reminders for a particular user.
     *
     * @param userId assigned user ID
     * @param dateTime starting date and time
     * @return upcoming reminders
     */
    List<Reminder> getUpcomingRemindersByUser(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // DUE REMINDERS
    // ==========================================================

    /**
     * Gets reminders that are due at or before the supplied
     * date/time and are still pending.
     *
     * @param dateTime current date and time
     * @return due reminders
     */
    List<Reminder> getDueReminders(
            LocalDateTime dateTime
    );


    /**
     * Gets due reminders for a particular user.
     *
     * @param userId assigned user ID
     * @param dateTime current date and time
     * @return due reminders
     */
    List<Reminder> getDueRemindersByUser(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // NOTIFICATION TRACKING
    // ==========================================================

    /**
     * Gets reminders whose notification has not been sent.
     *
     * @return reminders requiring notification
     */
    List<Reminder> getRemindersWithNotificationPending();


    /**
     * Gets due reminders whose notification has not been sent.
     *
     * @param dateTime current date and time
     * @return reminders requiring notification
     */
    List<Reminder> getDueRemindersWithNotificationPending(
            LocalDateTime dateTime
    );


    /**
     * Gets reminders whose notification has already been sent.
     *
     * @return notified reminders
     */
    List<Reminder> getRemindersWithNotificationSent();


    /**
     * Marks a reminder notification as sent.
     *
     * This also records the notification sent date/time.
     *
     * @param reminderId reminder ID
     * @return updated reminder
     */
    Reminder markNotificationAsSent(
            Long reminderId
    );


    // ==========================================================
    // SEARCH
    // ==========================================================

    /**
     * Searches reminders by title.
     *
     * Search is case-insensitive.
     *
     * @param title search keyword
     * @return matching reminders
     */
    List<Reminder> searchRemindersByTitle(
            String title
    );


    /**
     * Searches pending reminders by title.
     *
     * @param title search keyword
     * @return matching pending reminders
     */
    List<Reminder> searchPendingRemindersByTitle(
            String title
    );


    // ==========================================================
    // CREATED BY USER
    // ==========================================================

    /**
     * Gets reminders created by a particular user.
     *
     * @param userId creator user ID
     * @return reminders created by the user
     */
    List<Reminder> getRemindersByCreatedBy(
            Long userId
    );


    // ==========================================================
    // UPDATED BY USER
    // ==========================================================

    /**
     * Gets reminders last updated by a particular user.
     *
     * @param userId updater user ID
     * @return reminders updated by the user
     */
    List<Reminder> getRemindersByUpdatedBy(
            Long userId
    );


    // ==========================================================
    // EXISTENCE CHECKS
    // ==========================================================

    /**
     * Checks whether a reminder exists for a customer lead.
     *
     * @param leadId customer lead ID
     * @return true if a reminder exists
     */
    boolean existsByLeadId(Long leadId);


    /**
     * Checks whether a reminder exists for a follow-up.
     *
     * @param followUpId follow-up ID
     * @return true if a reminder exists
     */
    boolean existsByFollowUpId(Long followUpId);

}

