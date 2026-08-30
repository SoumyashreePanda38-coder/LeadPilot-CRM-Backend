package com.leadpilot.crm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.dto.LeadActivityRequest;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadActivity;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadActivityType;
import com.leadpilot.crm.repository.CustomerLeadRepository;
import com.leadpilot.crm.repository.LeadActivityRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.LeadActivityService;

/**
 * ==========================================================
 * Service Implementation : LeadActivityServiceImpl
 *
 * Description :
 * Handles business operations for lead activities.
 * ==========================================================
 */

@Service
@Transactional
public class LeadActivityServiceImpl implements LeadActivityService {

    // ==========================================================
    // REPOSITORIES
    // ==========================================================

    private final LeadActivityRepository leadActivityRepository;
    private final CustomerLeadRepository customerLeadRepository;
    private final UserRepository userRepository;

    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    @Autowired
    public LeadActivityServiceImpl(
            LeadActivityRepository leadActivityRepository,
            CustomerLeadRepository customerLeadRepository,
            UserRepository userRepository) {

        this.leadActivityRepository = leadActivityRepository;
        this.customerLeadRepository = customerLeadRepository;
        this.userRepository = userRepository;
    }

    // ==========================================================
    // CREATE ACTIVITY
    // ==========================================================

    @Override
    public LeadActivity createActivity(
            LeadActivityRequest request) {

        // ------------------------------------------------------
        // Validate request
        // ------------------------------------------------------

        if (request == null) {
            throw new IllegalArgumentException(
                    "Lead activity request cannot be null"
            );
        }

        // ------------------------------------------------------
        // Validate Lead ID
        // ------------------------------------------------------

        if (request.getLeadId() == null) {
            throw new IllegalArgumentException(
                    "Lead ID is required"
            );
        }

        // ------------------------------------------------------
        // Validate Activity Type
        // ------------------------------------------------------

        if (request.getActivityType() == null) {
            throw new IllegalArgumentException(
                    "Activity type is required"
            );
        }

        // ------------------------------------------------------
        // Validate Description
        // ------------------------------------------------------

        if (request.getDescription() != null
                && request.getDescription().length() > 1000) {

            throw new IllegalArgumentException(
                    "Description cannot exceed 1000 characters"
            );
        }

        // ------------------------------------------------------
        // Find Lead
        // ------------------------------------------------------

        CustomerLead customerLead =
                customerLeadRepository.findById(
                        request.getLeadId()
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "Customer lead not found with ID: "
                                        + request.getLeadId()
                        )
                );

        // ------------------------------------------------------
        // Create Activity Entity
        // ------------------------------------------------------

        LeadActivity leadActivity =
                new LeadActivity();

        // ------------------------------------------------------
        // Set Lead
        // ------------------------------------------------------

        leadActivity.setCustomerLead(
                customerLead
        );

        // ------------------------------------------------------
        // Set Activity Type
        // ------------------------------------------------------

        leadActivity.setActivityType(
                request.getActivityType()
        );

        // ------------------------------------------------------
        // Set Description
        // ------------------------------------------------------

        leadActivity.setDescription(
                request.getDescription()
        );

        // ------------------------------------------------------
        // Set Performed By
        // ------------------------------------------------------

        if (request.getPerformedById() != null) {

            User performedBy =
                    userRepository.findById(
                            request.getPerformedById()
                    ).orElseThrow(
                            () -> new IllegalArgumentException(
                                    "User not found with ID: "
                                            + request.getPerformedById()
                            )
                    );

            leadActivity.setPerformedBy(
                    performedBy
            );
        }

        // ------------------------------------------------------
        // Save Activity
        // ------------------------------------------------------

        return leadActivityRepository.save(
                leadActivity
        );
    }

    // ==========================================================
    // GET ACTIVITY BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public LeadActivity getActivityById(
            Long activityId) {

        validateActivityId(activityId);

        return leadActivityRepository.findById(
                activityId
        ).orElseThrow(
                () -> new RuntimeException(
                        "Lead Activity not found with ID: "
                                + activityId
                )
        );
    }

    // ==========================================================
    // GET ALL ACTIVITIES
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> getAllActivities() {

        return leadActivityRepository.findAll();
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD
    // LATEST FIRST
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> getActivitiesByLead(
            CustomerLead customerLead) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        return leadActivityRepository
                .findByCustomerLeadOrderByCreatedAtDesc(
                        customerLead
                );
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD
    // OLDEST FIRST
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> getActivitiesByLeadOldestFirst(
            CustomerLead customerLead) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        return leadActivityRepository
                .findByCustomerLeadOrderByCreatedAtAsc(
                        customerLead
                );
    }

    // ==========================================================
    // GET ACTIVITIES BY TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> getActivitiesByType(
            LeadActivityType activityType) {

        if (activityType == null) {
            throw new IllegalArgumentException(
                    "Activity type cannot be null"
            );
        }

        return leadActivityRepository
                .findByActivityType(
                        activityType
                );
    }

    // ==========================================================
    // GET ACTIVITIES BY USER
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> getActivitiesByUser(
            User performedBy) {

        if (performedBy == null) {
            throw new IllegalArgumentException(
                    "Performed-by user cannot be null"
            );
        }

        return leadActivityRepository
                .findByPerformedBy(
                        performedBy
                );
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD + TYPE
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> getActivitiesByLeadAndType(
            CustomerLead customerLead,
            LeadActivityType activityType) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        if (activityType == null) {
            throw new IllegalArgumentException(
                    "Activity type cannot be null"
            );
        }

        return leadActivityRepository
                .findByCustomerLeadAndActivityType(
                        customerLead,
                        activityType
                );
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD + USER
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> getActivitiesByLeadAndUser(
            CustomerLead customerLead,
            User performedBy) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        if (performedBy == null) {
            throw new IllegalArgumentException(
                    "Performed-by user cannot be null"
            );
        }

        return leadActivityRepository
                .findByCustomerLeadAndPerformedBy(
                        customerLead,
                        performedBy
                );
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD + TYPE
    // LATEST FIRST
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity>
    getActivitiesByLeadAndTypeLatestFirst(
            CustomerLead customerLead,
            LeadActivityType activityType) {

        if (customerLead == null) {
            throw new IllegalArgumentException(
                    "Customer lead cannot be null"
            );
        }

        if (activityType == null) {
            throw new IllegalArgumentException(
                    "Activity type cannot be null"
            );
        }

        return leadActivityRepository
                .findByCustomerLeadAndActivityTypeOrderByCreatedAtDesc(
                        customerLead,
                        activityType
                );
    }

    // ==========================================================
    // GET USER ACTIVITIES
    // LATEST FIRST
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity>
    getActivitiesByUserLatestFirst(
            User performedBy) {

        if (performedBy == null) {
            throw new IllegalArgumentException(
                    "Performed-by user cannot be null"
            );
        }

        return leadActivityRepository
                .findByPerformedByOrderByCreatedAtDesc(
                        performedBy
                );
    }

    // ==========================================================
    // VALIDATE ACTIVITY ID
    // ==========================================================

    private void validateActivityId(
            Long activityId) {

        if (activityId == null) {
            throw new IllegalArgumentException(
                    "Activity ID cannot be null"
            );
        }

        if (activityId <= 0) {
            throw new IllegalArgumentException(
                    "Activity ID must be greater than zero"
            );
        }
    }
}