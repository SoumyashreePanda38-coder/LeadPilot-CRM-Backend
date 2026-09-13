package com.leadpilot.crm.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.dto.ReminderRequest;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.Reminder;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.mapper.ReminderMapper;
import com.leadpilot.crm.repository.CustomerLeadRepository;
import com.leadpilot.crm.repository.FollowUpRepository;
import com.leadpilot.crm.repository.ReminderRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.ReminderService;

/**
 * ==========================================================
 * Service Implementation : ReminderServiceImpl
 *
 * Description :
 * Implements business operations for CRM reminders.
 *
 * Automatic Follow-Up Reminder:
 *
 * When a Follow-Up is created, this service can automatically
 * create one reminder for that Follow-Up.
 *
 * Reminder time:
 *
 *     Follow-Up scheduledAt - 24 hours
 *
 * Example:
 *
 * Follow-Up:
 * 20 September 2026 - 10:00 AM
 *
 * Reminder:
 * 19 September 2026 - 10:00 AM
 *
 * ==========================================================
 */
@Service
@Transactional
public class ReminderServiceImpl
        implements ReminderService {

    // ==========================================================
    // CONSTANTS
    // ==========================================================

    /**
     * Number of hours before the Follow-Up when the
     * automatic reminder should become due.
     */
    private static final long FOLLOW_UP_REMINDER_HOURS_BEFORE = 24;


    // ==========================================================
    // REPOSITORIES
    // ==========================================================

    private final ReminderRepository reminderRepository;

    private final UserRepository userRepository;

    private final FollowUpRepository followUpRepository;

    private final CustomerLeadRepository customerLeadRepository;


    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    public ReminderServiceImpl(
            ReminderRepository reminderRepository,
            UserRepository userRepository,
            FollowUpRepository followUpRepository,
            CustomerLeadRepository customerLeadRepository) {

        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
        this.followUpRepository = followUpRepository;
        this.customerLeadRepository = customerLeadRepository;
    }


    // ==========================================================
    // MANUAL / EXISTING CREATE
    // ==========================================================

    @Override
    public Reminder createReminder(
            ReminderRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Reminder request cannot be null"
            );
        }

        // ------------------------------------------------------
        // Validate request
        // ------------------------------------------------------

        validateRequest(request);


        // ------------------------------------------------------
        // Convert DTO -> Entity
        // ------------------------------------------------------

        Reminder reminder =
                ReminderMapper.toEntity(request);


        // ------------------------------------------------------
        // Resolve Customer Lead
        // ------------------------------------------------------

        if (request.getLeadId() != null) {

            CustomerLead customerLead =
                    customerLeadRepository
                            .findById(request.getLeadId())
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "Customer lead not found with ID: "
                                                    + request.getLeadId()
                                    )
                            );

            reminder.setCustomerLead(
                    customerLead
            );
        }


        // ------------------------------------------------------
        // Resolve Follow-Up
        // ------------------------------------------------------

        if (request.getFollowUpId() != null) {

            FollowUp followUp =
                    followUpRepository
                            .findById(request.getFollowUpId())
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "Follow-up not found with ID: "
                                                    + request.getFollowUpId()
                                    )
                            );

            reminder.setFollowUp(
                    followUp
            );
        }


        // ------------------------------------------------------
        // Resolve Assigned User
        //
        // Priority:
        //
        // 1. Explicit assignedToId
        // 2. FollowUp assigned user
        // 3. CustomerLead assigned user
        // ------------------------------------------------------

        User assignedUser = null;


        // ======================================================
        // OPTION 1 : Explicit assigned user
        // ======================================================

        if (request.getAssignedToId() != null) {

            assignedUser =
                    userRepository
                            .findById(
                                    request.getAssignedToId()
                            )
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "Assigned user not found with ID: "
                                                    + request.getAssignedToId()
                                    )
                            );
        }


        // ======================================================
        // OPTION 2 : Follow-Up assigned user
        // ======================================================

        if (assignedUser == null
                && reminder.getFollowUp() != null) {

            assignedUser =
                    reminder.getFollowUp()
                            .getAssignedUser();
        }


        // ======================================================
        // OPTION 3 : Lead assigned user
        // ======================================================

        if (assignedUser == null
                && reminder.getCustomerLead() != null) {

            assignedUser =
                    reminder.getCustomerLead()
                            .getAssignedUser();
        }


        // ======================================================
        // FINAL VALIDATION
        // ======================================================

        if (assignedUser == null) {

            throw new IllegalArgumentException(
                    "Reminder cannot be created because no assigned user was found."
            );
        }


        // ------------------------------------------------------
        // Set verified assigned user
        // ------------------------------------------------------

        reminder.setAssignedTo(
                assignedUser
        );


        // ======================================================
        // SAVE
        // ======================================================

        return reminderRepository.save(
                reminder
        );
    }


    // ==========================================================
    // AUTOMATIC FOLLOW-UP REMINDER
    // ==========================================================

    /**
     * Creates one automatic reminder for a Follow-Up.
     *
     * The reminder date/time is calculated automatically:
     *
     *     Follow-Up scheduledAt - 24 hours
     *
     * The reminder receives:
     *
     * - Same customer lead
     * - Same assigned user
     * - Same Follow-Up
     * - Follow-Up subject as title
     * - Follow-Up description as message
     * - Reminder time 24 hours before Follow-Up
     *
     * If the Follow-Up already has a reminder, the existing
     * reminder is returned to prevent duplicates.
     */
    @Override
    public Reminder createReminderForFollowUp(
            FollowUp followUp) {

        // ======================================================
        // VALIDATE FOLLOW-UP
        // ======================================================

        if (followUp == null) {

            throw new IllegalArgumentException(
                    "Follow-up cannot be null"
            );
        }


        if (followUp.getFollowUpId() == null) {

            throw new IllegalArgumentException(
                    "Follow-up ID cannot be null when creating a reminder"
            );
        }


        if (followUp.getScheduledAt() == null) {

            throw new IllegalArgumentException(
                    "Follow-up scheduled date and time cannot be null"
            );
        }


        if (followUp.getAssignedUser() == null) {

            throw new IllegalArgumentException(
                    "Follow-up must have an assigned user"
            );
        }


        if (followUp.getCustomerLead() == null) {

            throw new IllegalArgumentException(
                    "Follow-up must be associated with a customer lead"
            );
        }


        // ======================================================
        // PREVENT DUPLICATE REMINDER
        // ======================================================

        Reminder existingReminder =
                reminderRepository
                        .findFirstByFollowUp_FollowUpId(
                                followUp.getFollowUpId()
                        )
                        .orElse(null);


        if (existingReminder != null) {

            return existingReminder;
        }


        // ======================================================
        // CALCULATE REMINDER DATE/TIME
        // ======================================================

        LocalDateTime reminderAt =
                followUp.getScheduledAt()
                        .minusHours(
                                FOLLOW_UP_REMINDER_HOURS_BEFORE
                        );


        // ======================================================
        // CREATE REMINDER
        // ======================================================

        Reminder reminder =
                new Reminder();


        // ------------------------------------------------------
        // Link Follow-Up
        // ------------------------------------------------------

        reminder.setFollowUp(
                followUp
        );


        // ------------------------------------------------------
        // Link Customer Lead
        // ------------------------------------------------------

        reminder.setCustomerLead(
                followUp.getCustomerLead()
        );


        // ------------------------------------------------------
        // Assign to same user as Follow-Up
        // ------------------------------------------------------

        reminder.setAssignedTo(
                followUp.getAssignedUser()
        );


        // ======================================================
        // REMINDER CONTENT
        // ======================================================

        String subject =
                followUp.getSubject();


        if (subject == null
                || subject.isBlank()) {

            subject = "Upcoming Follow-Up";
        }


        reminder.setTitle(
                "Follow-Up Reminder: " + subject
        );


        // ------------------------------------------------------
        // Message
        // ------------------------------------------------------

        String description =
                followUp.getDescription();


        if (description == null
                || description.isBlank()) {

            description =
                    "You have a follow-up scheduled for "
                            + followUp.getScheduledAt();
        }


        reminder.setMessage(
                description
        );


        // ======================================================
        // REMINDER TIME
        // ======================================================

        reminder.setReminderAt(
                reminderAt
        );


        // ======================================================
        // INITIAL STATE
        // ======================================================

        reminder.setRead(false);

        reminder.setCompleted(false);

        reminder.setDismissed(false);

        reminder.setNotificationSent(false);

        reminder.setCompletedAt(null);

        reminder.setNotificationSentAt(null);


        // ======================================================
        // SAVE
        // ======================================================

        return reminderRepository.save(
                reminder
        );
    }


    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getAllReminders() {

        return reminderRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Reminder getReminderById(
            Long reminderId) {

        validateReminderId(
                reminderId
        );

        return reminderRepository
                .findByReminderId(reminderId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Reminder not found with ID: "
                                        + reminderId
                        )
                );
    }


    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public Reminder updateReminder(
            Long reminderId,
            ReminderRequest request,
            User updatedBy) {

        validateReminderId(
                reminderId
        );

        validateRequest(
                request
        );


        // ------------------------------------------------------
        // Find existing reminder
        // ------------------------------------------------------

        Reminder existingReminder =
                reminderRepository
                        .findByReminderId(
                                reminderId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Reminder not found with ID: "
                                                + reminderId
                                )
                        );


        // ======================================================
        // Update basic fields
        // ======================================================

        ReminderMapper.updateEntity(
                existingReminder,
                request
        );


        // ======================================================
        // UPDATE CUSTOMER LEAD
        // ======================================================

        if (request.getLeadId() != null) {

            CustomerLead customerLead =
                    customerLeadRepository
                            .findById(
                                    request.getLeadId()
                            )
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "Customer lead not found with ID: "
                                                    + request.getLeadId()
                                    )
                            );

            existingReminder.setCustomerLead(
                    customerLead
            );
        }


        // ======================================================
        // UPDATE FOLLOW-UP
        // ======================================================

        if (request.getFollowUpId() != null) {

            FollowUp followUp =
                    followUpRepository
                            .findById(
                                    request.getFollowUpId()
                            )
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "Follow-up not found with ID: "
                                                    + request.getFollowUpId()
                                    )
                            );

            existingReminder.setFollowUp(
                    followUp
            );
        }


        // ======================================================
        // UPDATE ASSIGNED USER
        // ======================================================

        if (request.getAssignedToId() != null) {

            User assignedUser =
                    userRepository
                            .findById(
                                    request.getAssignedToId()
                            )
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "Assigned user not found with ID: "
                                                    + request.getAssignedToId()
                                    )
                            );

            existingReminder.setAssignedTo(
                    assignedUser
            );
        }


        // ======================================================
        // NEVER ALLOW ASSIGNED USER TO BE NULL
        // ======================================================

        if (existingReminder.getAssignedTo() == null) {

            throw new IllegalArgumentException(
                    "Reminder must have an assigned user"
            );
        }


        // ======================================================
        // UPDATED BY
        // ======================================================

        if (updatedBy != null) {

            if (updatedBy.getId() == null) {

                throw new IllegalArgumentException(
                        "Updated by user ID cannot be null"
                );
            }

            User existingUpdatedBy =
                    userRepository
                            .findById(
                                    updatedBy.getId()
                            )
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "Updated by user not found with ID: "
                                                    + updatedBy.getId()
                                    )
                            );

            existingReminder.setUpdatedBy(
                    existingUpdatedBy
            );
        }


        // ======================================================
        // SAVE
        // ======================================================

        return reminderRepository.save(
                existingReminder
        );
    }


    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteReminder(
            Long reminderId) {

        validateReminderId(
                reminderId
        );

        Reminder reminder =
                getReminderById(
                        reminderId
                );

        reminderRepository.delete(
                reminder
        );
    }


    // ==========================================================
    // ASSIGNED USER
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByAssignedUser(
            User assignedTo) {

        validateUser(
                assignedTo
        );

        return reminderRepository
                .findByAssignedTo(
                        assignedTo
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByAssignedUserOrdered(
            User assignedTo) {

        validateUser(
                assignedTo
        );

        return reminderRepository
                .findByAssignedToOrderByReminderAtAsc(
                        assignedTo
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByAssignedUserId(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByAssignedTo_Id(
                        userId
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getPendingRemindersByAssignedUser(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByAssignedTo_IdAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        userId
                );
    }


    // ==========================================================
    // CUSTOMER LEAD
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByLead(
            CustomerLead customerLead) {

        if (customerLead == null) {

            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        return reminderRepository
                .findByCustomerLead(
                        customerLead
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByLeadId(
            Long leadId) {

        validateLeadId(
                leadId
        );

        return reminderRepository
                .findByCustomerLead_LeadId(
                        leadId
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByLeadIdOrdered(
            Long leadId) {

        validateLeadId(
                leadId
        );

        return reminderRepository
                .findByCustomerLead_LeadIdOrderByReminderAtAsc(
                        leadId
                );
    }


    // ==========================================================
    // FOLLOW-UP
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByFollowUp(
            FollowUp followUp) {

        if (followUp == null) {

            throw new IllegalArgumentException(
                    "Follow-up cannot be null"
            );
        }

        return reminderRepository
                .findByFollowUp(
                        followUp
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByFollowUpId(
            Long followUpId) {

        validateFollowUpId(
                followUpId
        );

        return reminderRepository
                .findByFollowUp_FollowUpId(
                        followUpId
                );
    }


    // ==========================================================
    // UNREAD
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUnreadReminders() {

        return reminderRepository
                .findByReadFalse();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUnreadRemindersByUser(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByAssignedTo_IdAndReadFalseOrderByReminderAtAsc(
                        userId
                );
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadReminderCountByUser(
            Long userId) {

        validateUserId(userId);

        return reminderRepository
                .countByAssignedTo_IdAndReadFalse(
                        userId
                );
    }
    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUnreadPendingReminders() {

        return reminderRepository
                .findByReadFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUnreadPendingRemindersByUser(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByAssignedTo_IdAndReadFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        userId
                );
    }


    @Override
    public Reminder markAsRead(
            Long reminderId) {

        Reminder reminder =
                getReminderById(
                        reminderId
                );

        reminder.setRead(
                true
        );

        return reminderRepository.save(
                reminder
        );
    }


    // ==========================================================
    // COMPLETED
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getCompletedReminders() {

        return reminderRepository
                .findByCompletedTrue();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getCompletedRemindersByUser(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByAssignedTo_IdAndCompletedTrueOrderByCompletedAtDesc(
                        userId
                );
    }


    @Override
    public Reminder completeReminder(
            Long reminderId) {

        Reminder reminder =
                getReminderById(
                        reminderId
                );

        reminder.setCompleted(
                true
        );

        reminder.setCompletedAt(
                LocalDateTime.now()
        );

        reminder.setRead(
                true
        );

        return reminderRepository.save(
                reminder
        );
    }


    // ==========================================================
    // PENDING
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getPendingReminders() {

        return reminderRepository
                .findByCompletedFalseAndDismissedFalse();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getPendingRemindersOrdered() {

        return reminderRepository
                .findByCompletedFalseAndDismissedFalseOrderByReminderAtAsc();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getPendingRemindersByUser(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByAssignedTo_IdAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        userId
                );
    }


    // ==========================================================
    // DISMISSED
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getDismissedReminders() {

        return reminderRepository
                .findByDismissedTrue();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getDismissedRemindersByUser(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByAssignedTo_IdAndDismissedTrueOrderByReminderAtDesc(
                        userId
                );
    }


    @Override
    public Reminder dismissReminder(
            Long reminderId) {

        Reminder reminder =
                getReminderById(
                        reminderId
                );

        reminder.setDismissed(
                true
        );

        return reminderRepository.save(
                reminder
        );
    }


    @Override
    public Reminder restoreReminder(
            Long reminderId) {

        Reminder reminder =
                getReminderById(
                        reminderId
                );

        reminder.setDismissed(
                false
        );

        return reminderRepository.save(
                reminder
        );
    }


    // ==========================================================
    // UPCOMING
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUpcomingReminders(
            LocalDateTime dateTime) {

        validateDateTime(
                dateTime
        );

        return reminderRepository
                .findByReminderAtAfterAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        dateTime
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUpcomingRemindersByUser(
            Long userId,
            LocalDateTime dateTime) {

        validateUserId(
                userId
        );

        validateDateTime(
                dateTime
        );

        return reminderRepository
                .findByAssignedTo_IdAndReminderAtAfterAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        userId,
                        dateTime
                );
    }


    // ==========================================================
    // DUE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getDueReminders(
            LocalDateTime dateTime) {

        validateDateTime(
                dateTime
        );

        return reminderRepository
                .findByReminderAtLessThanEqualAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        dateTime
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getDueRemindersByUser(
            Long userId,
            LocalDateTime dateTime) {

        validateUserId(
                userId
        );

        validateDateTime(
                dateTime
        );

        return reminderRepository
                .findByAssignedTo_IdAndReminderAtLessThanEqualAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        userId,
                        dateTime
                );
    }


    // ==========================================================
    // NOTIFICATION
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder>
    getRemindersWithNotificationPending() {

        return reminderRepository
                .findByNotificationSentFalse();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder>
    getDueRemindersWithNotificationPending(
            LocalDateTime dateTime) {

        validateDateTime(
                dateTime
        );

        return reminderRepository
                .findByReminderAtLessThanEqualAndNotificationSentFalseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        dateTime
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder>
    getRemindersWithNotificationSent() {

        return reminderRepository
                .findByNotificationSentTrue();
    }


    @Override
    public Reminder markNotificationAsSent(
            Long reminderId) {

        Reminder reminder =
                getReminderById(
                        reminderId
                );

        reminder.setNotificationSent(
                true
        );

        reminder.setNotificationSentAt(
                LocalDateTime.now()
        );

        return reminderRepository.save(
                reminder
        );
    }


    // ==========================================================
    // SEARCH
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> searchRemindersByTitle(
            String title) {

        if (title == null
                || title.isBlank()) {

            return reminderRepository.findAll();
        }

        return reminderRepository
                .findByTitleContainingIgnoreCaseOrderByReminderAtAsc(
                        title.trim()
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<Reminder> searchPendingRemindersByTitle(
            String title) {

        if (title == null
                || title.isBlank()) {

            return reminderRepository
                    .findByCompletedFalseAndDismissedFalseOrderByReminderAtAsc();
        }

        return reminderRepository
                .findByTitleContainingIgnoreCaseAndCompletedFalseAndDismissedFalseOrderByReminderAtAsc(
                        title.trim()
                );
    }


    // ==========================================================
    // CREATED BY
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByCreatedBy(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByCreatedBy_IdOrderByCreatedAtDesc(
                        userId
                );
    }


    // ==========================================================
    // UPDATED BY
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getRemindersByUpdatedBy(
            Long userId) {

        validateUserId(
                userId
        );

        return reminderRepository
                .findByUpdatedBy_IdOrderByUpdatedAtDesc(
                        userId
                );
    }


    // ==========================================================
    // EXISTENCE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLeadId(
            Long leadId) {

        validateLeadId(
                leadId
        );

        return reminderRepository
                .existsByCustomerLead_LeadId(
                        leadId
                );
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existsByFollowUpId(
            Long followUpId) {

        validateFollowUpId(
                followUpId
        );

        return reminderRepository
                .existsByFollowUp_FollowUpId(
                        followUpId
                );
    }


    // ==========================================================
    // VALIDATION
    // ==========================================================

    private void validateRequest(
            ReminderRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "Reminder request cannot be null"
            );
        }


        if (request.getTitle() == null
                || request.getTitle().isBlank()) {

            throw new IllegalArgumentException(
                    "Reminder title is required"
            );
        }


        if (request.getReminderAt() == null) {

            throw new IllegalArgumentException(
                    "Reminder date and time is required"
            );
        }
    }


    private void validateReminderId(
            Long reminderId) {

        if (reminderId == null) {

            throw new IllegalArgumentException(
                    "Reminder ID cannot be null"
            );
        }
    }


    private void validateUserId(
            Long userId) {

        if (userId == null) {

            throw new IllegalArgumentException(
                    "User ID cannot be null"
            );
        }
    }


    private void validateLeadId(
            Long leadId) {

        if (leadId == null) {

            throw new IllegalArgumentException(
                    "Lead ID cannot be null"
            );
        }
    }


    private void validateFollowUpId(
            Long followUpId) {

        if (followUpId == null) {

            throw new IllegalArgumentException(
                    "Follow-up ID cannot be null"
            );
        }
    }


    private void validateDateTime(
            LocalDateTime dateTime) {

        if (dateTime == null) {

            throw new IllegalArgumentException(
                    "Date and time cannot be null"
            );
        }
    }


    private void validateUser(
            User user) {

        if (user == null) {

            throw new IllegalArgumentException(
                    "Assigned user cannot be null"
            );
        }


        if (user.getId() == null) {

            throw new IllegalArgumentException(
                    "Assigned user ID cannot be null"
            );
        }
    }
}