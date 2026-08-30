package com.leadpilot.crm.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;
import com.leadpilot.crm.repository.FollowUpRepository;
import com.leadpilot.crm.service.FollowUpService;

/**
 * ==========================================================
 * Service Implementation : FollowUpServiceImpl
 *
 * Description :
 * Implements business operations for managing FollowUp.
 *
 * FollowUp represents a planned action for a customer lead,
 * such as:
 *
 *  - Phone call
 *  - Email
 *  - WhatsApp
 *  - Meeting
 *  - Site / property visit
 *  - Other customer interactions
 *
 * This service supports both:
 *
 *  - Admin Dashboard
 *  - Executive Dashboard
 *
 * ==========================================================
 */
@Service
public class FollowUpServiceImpl implements FollowUpService {

    // ==========================================================
    // Repository Dependency
    // ==========================================================

    @Autowired
    private FollowUpRepository followUpRepository;


    // ==========================================================
    // CREATE
    // ==========================================================

    @Override
    public FollowUp createFollowUp(
            FollowUp followUp) {

        return followUpRepository.save(followUp);
    }


    // ==========================================================
    // READ
    // ==========================================================

    @Override
    public List<FollowUp> getAllFollowUps() {

        return followUpRepository.findAll();
    }


    @Override
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
    public List<FollowUp> getFollowUpsByLead(
            CustomerLead customerLead) {

        return followUpRepository
                .findByCustomerLeadOrderByScheduledAtDesc(
                        customerLead
                );
    }


    @Override
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
    public List<FollowUp> getFollowUpsByUser(
            User assignedUser) {

        return followUpRepository
                .findByAssignedUserOrderByScheduledAtDesc(
                        assignedUser
                );
    }


    @Override
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
    public List<FollowUp> getFollowUpsByType(
            FollowUpType followUpType) {

        return followUpRepository
                .findByFollowUpType(
                        followUpType
                );
    }


    @Override
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
    public List<FollowUp> getTodaysFollowUps() {

        LocalDate today = LocalDate.now();

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
    public List<FollowUp> getTodaysFollowUpsByUser(
            User assignedUser) {

        LocalDate today = LocalDate.now();

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
    public List<FollowUp> getUpcomingFollowUps(
            LocalDateTime currentTime) {

        return followUpRepository
                .findByScheduledAtGreaterThanEqualAndStatusNotOrderByScheduledAtAsc(
                        currentTime,
                        FollowUpStatus.CANCELLED
                );
    }


    @Override
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
    public List<FollowUp> getOverdueFollowUps(
            LocalDateTime currentTime) {

        return followUpRepository
                .findByScheduledAtLessThanAndStatusOrderByScheduledAtAsc(
                        currentTime,
                        FollowUpStatus.SCHEDULED
                );
    }


    @Override
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
    public long countFollowUpsByStatus(
            FollowUpStatus status) {

        return followUpRepository
                .countByStatus(status);
    }


    @Override
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
    public long countFollowUpsByLead(
            CustomerLead customerLead) {

        return followUpRepository
                .countByCustomerLead(
                        customerLead
                );
    }


    @Override
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
    public List<FollowUp> getFollowUpsByScheduledDate() {

        return followUpRepository
                .findAllByOrderByScheduledAtAsc();
    }


    @Override
    public List<FollowUp> getRecentlyCreatedFollowUps() {

        return followUpRepository
                .findAllByOrderByCreatedAtDesc();
    }


    @Override
    public List<FollowUp> getCompletedFollowUpsByLead(
            CustomerLead customerLead) {

        return followUpRepository
                .findByCustomerLeadAndStatusOrderByCompletedAtDesc(
                        customerLead,
                        FollowUpStatus.COMPLETED
                );
    }


    @Override
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