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
 * Automatic Follow-Up Reminder:
 *
 * When a Follow-Up is created, LeadPilot automatically creates
 * one reminder for that Follow-Up.
 *
 * Reminder time:
 *
 *     Follow-Up scheduledAt - 24 hours
 *
 * The automatic reminder is assigned to the same user who
 * is assigned to the Follow-Up.
 *
 * ==========================================================
 */
public interface ReminderService {

    // ==========================================================
    // MANUAL / EXISTING CREATE
    // ==========================================================

    Reminder createReminder(
            ReminderRequest request
    );


    // ==========================================================
    // AUTOMATIC FOLLOW-UP REMINDER
    // ==========================================================

    /**
     * Creates the automatic reminder for a Follow-Up.
     *
     * Reminder time is calculated as:
     *
     * Follow-Up scheduledAt - 24 hours
     *
     * If a reminder already exists for the Follow-Up,
     * the existing reminder is returned instead of creating
     * a duplicate.
     *
     * @param followUp saved Follow-Up
     * @return created or existing reminder
     */
    Reminder createReminderForFollowUp(
            FollowUp followUp
    );


    // ==========================================================
    // READ
    // ==========================================================

    List<Reminder> getAllReminders();


    Reminder getReminderById(
            Long reminderId
    );


    // ==========================================================
    // UPDATE
    // ==========================================================

    Reminder updateReminder(
            Long reminderId,
            ReminderRequest request,
            User updatedBy
    );


    // ==========================================================
    // DELETE
    // ==========================================================

    void deleteReminder(
            Long reminderId
    );


    // ==========================================================
    // ASSIGNED USER
    // ==========================================================

    List<Reminder> getRemindersByAssignedUser(
            User assignedTo
    );


    List<Reminder> getRemindersByAssignedUserOrdered(
            User assignedTo
    );


    List<Reminder> getRemindersByAssignedUserId(
            Long userId
    );


    List<Reminder> getPendingRemindersByAssignedUser(
            Long userId
    );


    // ==========================================================
    // CUSTOMER LEAD
    // ==========================================================

    List<Reminder> getRemindersByLead(
            CustomerLead customerLead
    );


    List<Reminder> getRemindersByLeadId(
            Long leadId
    );


    List<Reminder> getRemindersByLeadIdOrdered(
            Long leadId
    );


    // ==========================================================
    // FOLLOW-UP
    // ==========================================================

    List<Reminder> getRemindersByFollowUp(
            FollowUp followUp
    );


    List<Reminder> getRemindersByFollowUpId(
            Long followUpId
    );


    // ==========================================================
    // UNREAD REMINDERS
    // ==========================================================

    List<Reminder> getUnreadReminders();


    List<Reminder> getUnreadRemindersByUser(
            Long userId
    );
    long getUnreadReminderCountByUser(Long userId);

    List<Reminder> getUnreadPendingReminders();


    List<Reminder> getUnreadPendingRemindersByUser(
            Long userId
    );


    Reminder markAsRead(
            Long reminderId
    );


    // ==========================================================
    // COMPLETED REMINDERS
    // ==========================================================

    List<Reminder> getCompletedReminders();


    List<Reminder> getCompletedRemindersByUser(
            Long userId
    );


    Reminder completeReminder(
            Long reminderId
    );


    // ==========================================================
    // PENDING REMINDERS
    // ==========================================================

    List<Reminder> getPendingReminders();


    List<Reminder> getPendingRemindersOrdered();


    List<Reminder> getPendingRemindersByUser(
            Long userId
    );


    // ==========================================================
    // DISMISSED REMINDERS
    // ==========================================================

    List<Reminder> getDismissedReminders();


    List<Reminder> getDismissedRemindersByUser(
            Long userId
    );


    Reminder dismissReminder(
            Long reminderId
    );


    Reminder restoreReminder(
            Long reminderId
    );


    // ==========================================================
    // UPCOMING REMINDERS
    // ==========================================================

    List<Reminder> getUpcomingReminders(
            LocalDateTime dateTime
    );


    List<Reminder> getUpcomingRemindersByUser(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // DUE REMINDERS
    // ==========================================================

    List<Reminder> getDueReminders(
            LocalDateTime dateTime
    );


    List<Reminder> getDueRemindersByUser(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // NOTIFICATION TRACKING
    // ==========================================================

    List<Reminder> getRemindersWithNotificationPending();


    List<Reminder> getDueRemindersWithNotificationPending(
            LocalDateTime dateTime
    );


    List<Reminder> getRemindersWithNotificationSent();


    Reminder markNotificationAsSent(
            Long reminderId
    );


    // ==========================================================
    // SEARCH
    // ==========================================================

    List<Reminder> searchRemindersByTitle(
            String title
    );


    List<Reminder> searchPendingRemindersByTitle(
            String title
    );


    // ==========================================================
    // CREATED BY USER
    // ==========================================================

    List<Reminder> getRemindersByCreatedBy(
            Long userId
    );


    // ==========================================================
    // UPDATED BY USER
    // ==========================================================

    List<Reminder> getRemindersByUpdatedBy(
            Long userId
    );


    // ==========================================================
    // EXISTENCE CHECKS
    // ==========================================================

    boolean existsByLeadId(
            Long leadId
    );


    boolean existsByFollowUpId(
            Long followUpId
    );
}