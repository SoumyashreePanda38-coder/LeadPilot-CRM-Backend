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
 * ==========================================================
 */
@Repository
public interface ReminderRepository
        extends JpaRepository<Reminder, Long> {

    // ==========================================================
    // FIND REMINDER BY ID
    // ==========================================================

    Optional<Reminder> findByReminderId(
            Long reminderId
    );


    // ==========================================================
    // ASSIGNED USER
    // ==========================================================

    List<Reminder> findByAssignedTo(
            User assignedTo
    );

    List<Reminder> findByAssignedToOrderByReminderAtAsc(
            User assignedTo
    );

    List<Reminder> findByAssignedTo_Id(
            Long userId
    );

    List<Reminder>
    findByAssignedTo_IdAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId
    );


    // ==========================================================
    // CUSTOMER LEAD
    // ==========================================================

    List<Reminder> findByCustomerLead(
            CustomerLead customerLead
    );

    List<Reminder> findByCustomerLead_LeadId(
            Long leadId
    );

    List<Reminder>
    findByCustomerLead_LeadIdOrderByReminderAtAsc(
            Long leadId
    );


    // ==========================================================
    // FOLLOW-UP
    // ==========================================================

    /**
     * Finds all reminders associated with a follow-up.
     */
    List<Reminder> findByFollowUp(
            FollowUp followUp
    );

    /**
     * Finds all reminders associated with a follow-up ID.
     */
    List<Reminder> findByFollowUp_FollowUpId(
            Long followUpId
    );

    /**
     * Finds one reminder associated with a follow-up.
     *
     * LeadPilot uses one automatic reminder per follow-up.
     *
     * This method is mainly used by the automatic
     * Follow-Up -> Reminder integration.
     */
    Optional<Reminder> findFirstByFollowUp_FollowUpId(
            Long followUpId
    );


    // ==========================================================
    // UNREAD REMINDERS
    // ==========================================================

    List<Reminder> findByReadFalse();

    List<Reminder>
    findByAssignedTo_IdAndReadFalseOrderByReminderAtAsc(
            Long userId
    );
    long countByAssignedTo_IdAndReadFalse(Long userId);

    List<Reminder>
    findByReadFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc();

    List<Reminder>
    findByAssignedTo_IdAndReadFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId
    );


    // ==========================================================
    // COMPLETED REMINDERS
    // ==========================================================

    List<Reminder> findByCompletedTrue();

    List<Reminder>
    findByAssignedTo_IdAndCompletedTrueOrderByCompletedAtDesc(
            Long userId
    );


    // ==========================================================
    // PENDING REMINDERS
    // ==========================================================

    List<Reminder>
    findByCompletedFalseAndDismissedFalse();

    List<Reminder>
    findByCompletedFalseAndDismissedFalseOrderByReminderAtAsc();

   
   


    // ==========================================================
    // DISMISSED REMINDERS
    // ==========================================================

    List<Reminder> findByDismissedTrue();

    List<Reminder>
    findByAssignedTo_IdAndDismissedTrueOrderByReminderAtDesc(
            Long userId
    );


    // ==========================================================
    // UPCOMING REMINDERS
    // ==========================================================

    List<Reminder>
    findByReminderAtAfterAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            LocalDateTime dateTime
    );

    List<Reminder>
    findByAssignedTo_IdAndReminderAtAfterAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // DUE REMINDERS
    // ==========================================================

    List<Reminder>
    findByReminderAtLessThanEqualAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            LocalDateTime dateTime
    );

    List<Reminder>
    findByAssignedTo_IdAndReminderAtLessThanEqualAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            Long userId,
            LocalDateTime dateTime
    );


    // ==========================================================
    // NOTIFICATION TRACKING
    // ==========================================================

    List<Reminder> findByNotificationSentFalse();

    List<Reminder>
    findByReminderAtLessThanEqualAndNotificationSentFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            LocalDateTime dateTime
    );

    List<Reminder> findByNotificationSentTrue();


    // ==========================================================
    // SEARCH BY TITLE
    // ==========================================================

    List<Reminder>
    findByTitleContainingIgnoreCaseOrderByReminderAtAsc(
            String title
    );

    List<Reminder>
    findByTitleContainingIgnoreCaseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
            String title
    );


    // ==========================================================
    // CREATED BY USER
    // ==========================================================

    List<Reminder>
    findByCreatedBy_IdOrderByCreatedAtDesc(
            Long userId
    );


    // ==========================================================
    // UPDATED BY USER
    // ==========================================================

    List<Reminder>
    findByUpdatedBy_IdOrderByUpdatedAtDesc(
            Long userId
    );


    // ==========================================================
    // EXISTENCE CHECKS
    // ==========================================================

    boolean existsByCustomerLead_LeadId(
            Long leadId
    );

    boolean existsByFollowUp_FollowUpId(
            Long followUpId
    );
}