package com.leadpilot.crm.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;
import com.leadpilot.crm.repository.FollowUpRepository;
import com.leadpilot.crm.service.FollowUpService;
import com.leadpilot.crm.service.ReminderService;

/**
 * ==========================================================
 * Service Implementation : FollowUpServiceImpl
 *
 * Description :
 * Implements business operations for managing Follow-Ups.
 *
 * IMPORTANT:
 *
 * Whenever a Follow-Up is created, an automatic Reminder is
 * created through ReminderService.
 *
 * Automatic reminder rule:
 *
 *     Reminder Time = Follow-Up Time - 24 hours
 *
 * Example:
 *
 * Follow-Up:
 * 20 September 2026 - 10:00 AM
 *
 * Reminder:
 * 19 September 2026 - 10:00 AM
 *
 * The Reminder is assigned to the same user as the Follow-Up.
 *
 * ==========================================================
 */
@Service
@Transactional
public class FollowUpServiceImpl implements FollowUpService {

    // ==========================================================
    // REPOSITORIES
    // ==========================================================

    private final FollowUpRepository followUpRepository;


    // ==========================================================
    // SERVICES
    // ==========================================================

    private final ReminderService reminderService;


    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    public FollowUpServiceImpl(
            FollowUpRepository followUpRepository,
            ReminderService reminderService) {

        this.followUpRepository = followUpRepository;
        this.reminderService = reminderService;
    }


    // ==========================================================
    // CREATE FOLLOW-UP
    // ==========================================================

    /**
     * Creates a Follow-Up and automatically creates its
     * corresponding Reminder.
     *
     * Flow:
     *
     * 1. Validate Follow-Up
     * 2. Save Follow-Up
     * 3. Create Reminder automatically
     * 4. Return saved Follow-Up
     *
     * The entire operation is transactional.
     */
    @Override
    public FollowUp createFollowUp(
            FollowUp followUp) {

        // ------------------------------------------------------
        // Basic validation
        // ------------------------------------------------------

        if (followUp == null) {

            throw new IllegalArgumentException(
                    "Follow-up cannot be null"
            );
        }


        if (followUp.getCustomerLead() == null) {

            throw new IllegalArgumentException(
                    "Customer lead is required for a follow-up"
            );
        }


        if (followUp.getAssignedUser() == null) {

            throw new IllegalArgumentException(
                    "Assigned user is required for a follow-up"
            );
        }


        if (followUp.getScheduledAt() == null) {

            throw new IllegalArgumentException(
                    "Follow-up scheduled date and time is required"
            );
        }


        if (followUp.getSubject() == null
                || followUp.getSubject().isBlank()) {

            throw new IllegalArgumentException(
                    "Follow-up subject is required"
            );
        }


        // ------------------------------------------------------
        // Save Follow-Up first
        // ------------------------------------------------------

        FollowUp savedFollowUp =
                followUpRepository.save(
                        followUp
                );


        // ------------------------------------------------------
        // IMPORTANT:
        //
        // Flush the Follow-Up so that it definitely has its
        // generated database ID before the Reminder is created.
        // ------------------------------------------------------

        followUpRepository.flush();


        // ------------------------------------------------------
        // Automatically create Reminder
        //
        // ReminderService calculates:
        //
        // scheduledAt - 24 hours
        // ------------------------------------------------------

        reminderService.createReminderForFollowUp(
                savedFollowUp
        );


        // ------------------------------------------------------
        // Return Follow-Up
        // ------------------------------------------------------

        return savedFollowUp;
    }


    // ==========================================================
    // READ
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getAllFollowUps() {

        return followUpRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public FollowUp getFollowUpById(
            Long followUpId) {

        return followUpRepository.findById(followUpId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Follow-up not found with ID: "
                                        + followUpId
                        )
                );
    }


    // ==========================================================
    // UPDATE
    // ==========================================================

    @Override
    public FollowUp updateFollowUp(
            Long followUpId,
            FollowUp followUp) {

        FollowUp existingFollowUp =
                followUpRepository.findById(followUpId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Follow-up not found with ID: "
                                                + followUpId
                                )
                        );


        // ------------------------------------------------------
        // Update Lead
        // ------------------------------------------------------

        existingFollowUp.setCustomerLead(
                followUp.getCustomerLead()
        );


        // ------------------------------------------------------
        // Update Assigned User
        // ------------------------------------------------------

        existingFollowUp.setAssignedUser(
                followUp.getAssignedUser()
        );


        // ------------------------------------------------------
        // Update Follow-Up Type
        // ------------------------------------------------------

        existingFollowUp.setFollowUpType(
                followUp.getFollowUpType()
        );


        // ------------------------------------------------------
        // Update Subject
        // ------------------------------------------------------

        existingFollowUp.setSubject(
                followUp.getSubject()
        );


        // ------------------------------------------------------
        // Update Scheduled Date & Time
        // ------------------------------------------------------

        existingFollowUp.setScheduledAt(
                followUp.getScheduledAt()
        );


        // ------------------------------------------------------
        // Update Location
        // ------------------------------------------------------

        existingFollowUp.setLocation(
                followUp.getLocation()
        );


        // ------------------------------------------------------
        // Update Description
        // ------------------------------------------------------

        existingFollowUp.setDescription(
                followUp.getDescription()
        );


        // ------------------------------------------------------
        // Update Status
        // ------------------------------------------------------

        if (followUp.getStatus() != null) {

            existingFollowUp.setStatus(
                    followUp.getStatus()
            );
        }


        // ------------------------------------------------------
        // Update Completion Information
        // ------------------------------------------------------

        existingFollowUp.setCompletedAt(
                followUp.getCompletedAt()
        );

        existingFollowUp.setOutcome(
                followUp.getOutcome()
        );


        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        return followUpRepository.save(
                existingFollowUp
        );
    }


    // ==========================================================
    // DELETE
    // ==========================================================

    @Override
    public void deleteFollowUp(
            Long followUpId) {

        FollowUp existingFollowUp =
                followUpRepository.findById(followUpId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Follow-up not found with ID: "
                                                + followUpId
                                )
                        );

        followUpRepository.delete(
                existingFollowUp
        );
    }


    // ==========================================================
    // LEAD-BASED OPERATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByLead(
            CustomerLead customerLead) {

        return followUpRepository
                .findByCustomerLeadOrderByScheduledAtDesc(
                        customerLead
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByLeadAndDateRange(
            CustomerLead customerLead,
            LocalDateTime start,
            LocalDateTime end) {

        return followUpRepository
                .findByCustomerLeadAndScheduledAtBetweenOrderByScheduledAtAsc(
                        customerLead,
                        start,
                        end
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByLeadAndStatus(
            CustomerLead customerLead,
            FollowUpStatus status) {

        return followUpRepository
                .findByCustomerLeadAndStatus(
                        customerLead,
                        status
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByLeadAndType(
            CustomerLead customerLead,
            FollowUpType followUpType) {

        return followUpRepository
                .findByCustomerLeadAndFollowUpType(
                        customerLead,
                        followUpType
                );
    }


    // ==========================================================
    // EXECUTIVE / USER-BASED OPERATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByUser(
            User assignedUser) {

        return followUpRepository
                .findByAssignedUserOrderByScheduledAtDesc(
                        assignedUser
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByUserAndDateRange(
            User assignedUser,
            LocalDateTime start,
            LocalDateTime end) {

        return followUpRepository
                .findByAssignedUserAndScheduledAtBetweenOrderByScheduledAtAsc(
                        assignedUser,
                        start,
                        end
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByUserAndStatus(
            User assignedUser,
            FollowUpStatus status) {

        return followUpRepository
                .findByAssignedUserAndStatus(
                        assignedUser,
                        status
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByUserAndType(
            User assignedUser,
            FollowUpType followUpType) {

        return followUpRepository
                .findByAssignedUserAndFollowUpType(
                        assignedUser,
                        followUpType
                );
    }


    // ==========================================================
    // STATUS OPERATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByStatus(
            FollowUpStatus status) {

        return followUpRepository
                .findByStatus(status);
    }


    @Override
    public FollowUp updateFollowUpStatus(
            Long followUpId,
            FollowUpStatus status) {

        FollowUp existingFollowUp =
                getFollowUpById(followUpId);

        existingFollowUp.setStatus(status);

        return followUpRepository.save(
                existingFollowUp
        );
    }


    @Override
    public FollowUp completeFollowUp(
            Long followUpId,
            String outcome) {

        FollowUp existingFollowUp =
                getFollowUpById(followUpId);

        existingFollowUp.setStatus(
                FollowUpStatus.COMPLETED
        );

        existingFollowUp.setCompletedAt(
                LocalDateTime.now()
        );

        existingFollowUp.setOutcome(
                outcome
        );

        return followUpRepository.save(
                existingFollowUp
        );
    }


    @Override
    public FollowUp cancelFollowUp(
            Long followUpId) {

        FollowUp existingFollowUp =
                getFollowUpById(followUpId);

        existingFollowUp.setStatus(
                FollowUpStatus.CANCELLED
        );

        return followUpRepository.save(
                existingFollowUp
        );
    }


    // ==========================================================
    // TYPE OPERATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByType(
            FollowUpType followUpType) {

        return followUpRepository
                .findByFollowUpType(
                        followUpType
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByTypeAndStatus(
            FollowUpType followUpType,
            FollowUpStatus status) {

        return followUpRepository
                .findByFollowUpTypeAndStatus(
                        followUpType,
                        status
                );
    }


    // ==========================================================
    // DATE / SCHEDULE OPERATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return followUpRepository
                .findByScheduledAtBetweenOrderByScheduledAtAsc(
                        start,
                        end
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getTodaysFollowUps() {

        LocalDate today =
                LocalDate.now();

        LocalDateTime start =
                today.atStartOfDay();

        LocalDateTime end =
                today.plusDays(1).atStartOfDay();

        return followUpRepository
                .findByScheduledAtGreaterThanEqualAndScheduledAtLessThanOrderByScheduledAtAsc(
                        start,
                        end
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getTodaysFollowUpsByUser(
            User assignedUser) {

        LocalDate today =
                LocalDate.now();

        LocalDateTime start =
                today.atStartOfDay();

        LocalDateTime end =
                today.plusDays(1).atStartOfDay();

        return followUpRepository
                .findByAssignedUserAndScheduledAtGreaterThanEqualAndScheduledAtLessThanOrderByScheduledAtAsc(
                        assignedUser,
                        start,
                        end
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getUpcomingFollowUps(
            LocalDateTime currentTime) {

        return followUpRepository
                .findByScheduledAtGreaterThanEqualAndStatusNotOrderByScheduledAtAsc(
                        currentTime,
                        FollowUpStatus.CANCELLED
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getUpcomingFollowUpsByUser(
            User assignedUser,
            LocalDateTime currentTime) {

        return followUpRepository
                .findByAssignedUserAndScheduledAtGreaterThanEqualAndStatusNotOrderByScheduledAtAsc(
                        assignedUser,
                        currentTime,
                        FollowUpStatus.CANCELLED
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getOverdueFollowUps(
            LocalDateTime currentTime) {

        return followUpRepository
                .findByScheduledAtLessThanAndStatusOrderByScheduledAtAsc(
                        currentTime,
                        FollowUpStatus.SCHEDULED
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getOverdueFollowUpsByUser(
            User assignedUser,
            LocalDateTime currentTime) {

        return followUpRepository
                .findByAssignedUserAndScheduledAtLessThanAndStatusOrderByScheduledAtAsc(
                        assignedUser,
                        currentTime,
                        FollowUpStatus.SCHEDULED
                );
    }


    // ==========================================================
    // DASHBOARD OPERATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public long countFollowUpsByStatus(
            FollowUpStatus status) {

        return followUpRepository
                .countByStatus(status);
    }


    @Override
    @Transactional(readOnly = true)
    public long countFollowUpsByUserAndStatus(
            User assignedUser,
            FollowUpStatus status) {

        return followUpRepository
                .countByAssignedUserAndStatus(
                        assignedUser,
                        status
                );
    }


    @Override
    @Transactional(readOnly = true)
    public long countFollowUpsByLead(
            CustomerLead customerLead) {

        return followUpRepository
                .countByCustomerLead(
                        customerLead
                );
    }


    @Override
    @Transactional(readOnly = true)
    public long countFollowUpsByUser(
            User assignedUser) {

        return followUpRepository
                .countByAssignedUser(
                        assignedUser
                );
    }


    // ==========================================================
    // SORTED / TIMELINE OPERATIONS
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByScheduledDate() {

        return followUpRepository
                .findAllByOrderByScheduledAtAsc();
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getRecentlyCreatedFollowUps() {

        return followUpRepository
                .findAllByOrderByCreatedAtDesc();
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getCompletedFollowUpsByLead(
            CustomerLead customerLead) {

        return followUpRepository
                .findByCustomerLeadAndStatusOrderByCompletedAtDesc(
                        customerLead,
                        FollowUpStatus.COMPLETED
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getCompletedFollowUpsByUser(
            User assignedUser) {

        return followUpRepository
                .findByAssignedUserAndStatusOrderByCompletedAtDesc(
                        assignedUser,
                        FollowUpStatus.COMPLETED
                );
    }


    // ==========================================================
    // SEARCH / CALENDAR SUPPORT
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByStatusAndDateRange(
            FollowUpStatus status,
            LocalDateTime start,
            LocalDateTime end) {

        return followUpRepository
                .findByStatusAndScheduledAtBetweenOrderByScheduledAtAsc(
                        status,
                        start,
                        end
                );
    }


    @Override
    @Transactional(readOnly = true)
    public List<FollowUp> getFollowUpsByUserStatusAndDateRange(
            User assignedUser,
            FollowUpStatus status,
            LocalDateTime start,
            LocalDateTime end) {

        return followUpRepository
                .findByAssignedUserAndStatusAndScheduledAtBetweenOrderByScheduledAtAsc(
                        assignedUser,
                        status,
                        start,
                        end
                );
    }
}