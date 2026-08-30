package com.leadpilot.crm.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.Reminder;
import com.leadpilot.crm.entity.User;

/**
 * ==========================================================
 * Repository : ReminderRepository
 *
 * Description :
 * Handles database operations related to Reminder entities.
 *
 * Reminder supports:
 *
 * - Creating reminders
 * - Finding reminders by ID
 * - Finding reminders by assigned user
 * - Finding reminders by customer lead
 * - Finding reminders by follow-up
 * - Finding unread reminders
 * - Finding completed reminders
 * - Finding pending reminders
 * - Finding dismissed reminders
 * - Finding upcoming reminders
 * - Finding due reminders
 * - Notification tracking
 * - Searching reminders by title
 *
 * Spring Data JPA automatically provides the implementation
 * for the methods declared in this interface.
 *
 * ==========================================================
 */
@Repository
public interface ReminderRepository
        extends JpaRepository<Reminder, Long> {

    // ==========================================================
    // Find Reminder By ID
    // ==========================================================

    /**
     * Finds a reminder by its primary key.
     *
     * @param reminderId reminder ID
     * @return optional reminder
     */
    Optional<Reminder> findByReminderId(Long reminderId);


    // ==========================================================
    // Assigned User
    // ==========================================================

    /**
     * Finds all reminders assigned to a particular user.
     *
     * @param assignedTo assigned user
     * @return list of reminders
     */
    List<Reminder> findByAssignedTo(
            User assignedTo
    );


    /**
     * Finds reminders assigned to a user ordered
     * by reminder date and time.
     *
     * @param assignedTo assigned user
     * @return ordered reminders
     */
    List<Reminder> findByAssignedToOrderByReminderAtAsc(
            User assignedTo
    );


    /**
     * Finds reminders using the assigned user's ID.
     *
     * @param userId assigned user ID
     * @return list of reminders
     */
    List<Reminder> findByAssignedTo_Id(
            Long userId
    );


    /**
     * Finds active pending reminders assigned to
     * a particular user.
     *
     * Active pending means:
     *
     * - completed = false
     * - dismissed = false
     *
     * @param userId assigned user ID
     * @return pending reminders
     */
  

    // ==========================================================
    // Customer Lead
    // ==========================================================

    /**
     * Finds all reminders associated with a customer lead.
     *
     * @param customerLead customer lead
     * @return list of reminders
     */
    List<Reminder> findByCustomerLead(
            CustomerLead customerLead
    );


    /**
     * Finds reminders using the customer lead ID.
     *
     * @param leadId customer lead ID
     * @return list of reminders
     */
    List<Reminder> findByCustomerLead_LeadId(
            Long leadId
    );


    /**
     * Finds reminders for a lead ordered by reminder date.
     *
     * @param leadId customer lead ID
     * @return ordered reminders
     */
    List<Reminder>
    findByCustomerLead_LeadIdOrderByReminderAtAsc(
            Long leadId
    );


    // ==========================================================
    // Follow-Up
    // ==========================================================

    /**
     * Finds all reminders associated with a follow-up.
     *
     * @param followUp follow-up entity
     * @return list of reminders
     */
    List<Reminder> findByFollowUp(
            FollowUp followUp
    );


    /**
     * Finds reminders using the follow-up ID.
     *
     * @param followUpId follow-up ID
     * @return list of reminders
     */
    List<Reminder> findByFollowUp_FollowUpId(
            Long followUpId
    );


    // ==========================================================
    // Unread Reminders
    // ==========================================================

    /**
     * Finds all unread reminders.
     */
    List<Reminder> findByReadFalse();


    /**
     * Finds unread reminders assigned to a particular user.
     *
     * @param userId assigned user ID
     * @return unread reminders
     */
    List<Reminder>
    findByAssignedTo_IdAndReadFalseOrderByReminderAtAsc(
            Long userId
    );


    // ==========================================================
    // Completed Reminders
    // ==========================================================

    /**
     * Finds all completed reminders.
     */
    List<Reminder> findByCompletedTrue();


    /**
     * Finds completed reminders assigned to a user.
     *
     * @param userId assigned user ID
     * @return completed reminders
     */
    List<Reminder>
    findByAssignedTo_IdAndCompletedTrueOrderByCompletedAtDesc(
            Long userId
    );


    // ==========================================================
    // Pending Reminders
    // ==========================================================

    /**
     * Finds all reminders that are not completed
     * and not dismissed.
     */
    List<Reminder>
    findByCompletedFalseAndDismissedFalse();


    /**
     * Finds pending reminders ordered by reminder date.
     */
    List<Reminder>
    findByCompletedFalseAndDismissedFalseOrderByReminderAtAsc();


    /**
     * Finds pending reminders assigned to a particular user.
     *
     * IMPORTANT:
     * This method appears ONLY ONCE in this repository.
     */
    List<Reminder>
    findByAssignedTo_IdAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId
    );


    // ==========================================================
    // Dismissed Reminders
    // ==========================================================

    /**
     * Finds all dismissed reminders.
     */
    List<Reminder> findByDismissedTrue();


    /**
     * Finds dismissed reminders assigned to a user.
     *
     * @param userId assigned user ID
     * @return dismissed reminders
     */
    List<Reminder>
    findByAssignedTo_IdAndDismissedTrueOrderByReminderAtDesc(
            Long userId
    );


    // ==========================================================
    // Upcoming Reminders
    // ==========================================================

    /**
     * Finds pending reminders scheduled after
     * the supplied date and time.
     *
     * @param dateTime starting date/time
     * @return upcoming reminders
     */
    List<Reminder>
    findByReminderAtAfterAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            LocalDateTime dateTime
    );


    /**
     * Finds upcoming pending reminders for a particular user.
     *
     * @param userId assigned user ID
     * @param dateTime starting date/time
     * @return upcoming reminders
     */
    List<Reminder>
    findByAssignedTo_IdAndReminderAtAfterAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // Due Reminders
    // ==========================================================

    /**
     * Finds reminders that are due at or before
     * the supplied date/time and are still pending.
     *
     * @param dateTime current date/time
     * @return due reminders
     */
    List<Reminder>
    findByReminderAtLessThanEqualAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            LocalDateTime dateTime
    );


    /**
     * Finds due reminders for a particular user.
     *
     * @param userId assigned user ID
     * @param dateTime current date/time
     * @return due reminders
     */
    List<Reminder>
    findByAssignedTo_IdAndReminderAtLessThanEqualAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // Notification Tracking
    // ==========================================================

    /**
     * Finds reminders whose notification has not been sent.
     */
    List<Reminder> findByNotificationSentFalse();


    /**
     * Finds due reminders whose notification has not
     * yet been sent.
     *
     * Useful for scheduled notification processing.
     *
     * @param dateTime current date/time
     * @return reminders requiring notification
     */
    List<Reminder>
    findByReminderAtLessThanEqualAndNotificationSentFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            LocalDateTime dateTime
    );


    /**
     * Finds reminders whose notification has already been sent.
     */
    List<Reminder> findByNotificationSentTrue();


    // ==========================================================
    // Read + Pending
    // ==========================================================

    /**
     * Finds unread and pending reminders.
     */
    List<Reminder>
    findByReadFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc();


    /**
     * Finds unread and pending reminders assigned
     * to a particular user.
     *
     * @param userId assigned user ID
     * @return unread pending reminders
     */
    List<Reminder>
    findByAssignedTo_IdAndReadFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId
    );


    // ==========================================================
    // Search By Title
    // ==========================================================

    /**
     * Searches reminders by title.
     *
     * Case-insensitive and matches titles containing
     * the supplied keyword.
     *
     * @param title search keyword
     * @return matching reminders
     */
    List<Reminder>
    findByTitleContainingIgnoreCaseOrderByReminderAtAsc(
            String title
    );


    /**
     * Searches pending reminders by title.
     *
     * @param title search keyword
     * @return matching pending reminders
     */
    List<Reminder>
    findByTitleContainingIgnoreCaseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            String title
    );


    // ==========================================================
    // Created By
    // ==========================================================

    /**
     * Finds reminders created by a particular user.
     *
     * @param userId creator user ID
     * @return reminders created by the user
     */
    List<Reminder>
    findByCreatedBy_IdOrderByCreatedAtDesc(
            Long userId
    );


    // ==========================================================
    // Updated By
    // ==========================================================

    /**
     * Finds reminders last updated by a particular user.
     *
     * @param userId updater user ID
     * @return reminders updated by the user
     */
    List<Reminder>
    findByUpdatedBy_IdOrderByUpdatedAtDesc(
            Long userId
    );


    // ==========================================================
    // Existence Checks
    // ==========================================================

    /**
     * Checks whether a reminder exists for a particular lead.
     *
     * @param leadId customer lead ID
     * @return true if at least one reminder exists
     */
    boolean existsByCustomerLead_LeadId(
            Long leadId
    );


    /**
     * Checks whether a reminder exists for a particular
     * follow-up.
     *
     * @param followUpId follow-up ID
     * @return true if at least one reminder exists
     */
    boolean existsByFollowUp_FollowUpId(
            Long followUpId
    );
}