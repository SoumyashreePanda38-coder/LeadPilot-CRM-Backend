package com.leadpilot.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leadpilot.crm.dto.LeadActivityRequest;
import com.leadpilot.crm.dto.LeadActivityResponse;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadActivity;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadActivityType;
import com.leadpilot.crm.repository.CustomerLeadRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.LeadActivityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ==========================================================
 * Controller : LeadActivityController
 *
 * Description :
 * REST controller for managing lead activities and
 * lead timeline/history.
 *
 * IMPORTANT:
 * This controller does NOT expose LeadActivity entities
 * directly to the frontend.
 *
 * All response data is converted to LeadActivityResponse DTO.
 *
 * Base URL:
 * /api/lead-activities
 *
 * ==========================================================
 */

@RestController
@RequestMapping("/api/lead-activities")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Lead Activities",
        description = "APIs for managing lead activities and viewing lead timeline history"
)
public class LeadActivityController {

    // ==========================================================
    // SERVICE
    // ==========================================================

    private final LeadActivityService leadActivityService;

    // ==========================================================
    // REPOSITORIES
    // ==========================================================

    private final CustomerLeadRepository customerLeadRepository;

    private final UserRepository userRepository;

    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    @Autowired
    public LeadActivityController(
            LeadActivityService leadActivityService,
            CustomerLeadRepository customerLeadRepository,
            UserRepository userRepository) {

        this.leadActivityService = leadActivityService;
        this.customerLeadRepository = customerLeadRepository;
        this.userRepository = userRepository;
    }

    // ==========================================================
    // CREATE ACTIVITY
    // ==========================================================

    @Operation(
            summary = "Create a lead activity",
            description = "Creates and saves a new activity for a customer lead."
    )
    @PostMapping
    public ResponseEntity<LeadActivityResponse> createActivity(
            @RequestBody LeadActivityRequest request) {

        LeadActivity savedActivity =
                leadActivityService.createActivity(request);

        LeadActivityResponse response =
                convertToResponse(savedActivity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================================================
    // GET ACTIVITY BY ID
    // ==========================================================

    @Operation(
            summary = "Get activity by ID",
            description = "Retrieves a specific lead activity using its activity ID."
    )
    @GetMapping("/{activityId}")
    public ResponseEntity<LeadActivityResponse> getActivityById(
            @PathVariable Long activityId) {

        LeadActivity activity =
                leadActivityService.getActivityById(activityId);

        return ResponseEntity.ok(
                convertToResponse(activity)
        );
    }

    // ==========================================================
    // GET ALL ACTIVITIES
    // ==========================================================

    @Operation(
            summary = "Get all lead activities",
            description = "Retrieves all lead activities."
    )
    @GetMapping
    public ResponseEntity<List<LeadActivityResponse>> getAllActivities() {

        List<LeadActivityResponse> response =
                leadActivityService.getAllActivities()
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD
    // LATEST FIRST
    // ==========================================================

    @Operation(
            summary = "Get lead activities",
            description = "Retrieves all activities of a specific lead from newest to oldest."
    )
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<LeadActivityResponse>> getActivitiesByLead(
            @PathVariable Long leadId) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead not found with ID: " + leadId
                                )
                        );

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByLead(customerLead)
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD
    // OLDEST FIRST
    // ==========================================================

    @Operation(
            summary = "Get lead activities oldest first",
            description = "Retrieves all activities of a lead from oldest to newest."
    )
    @GetMapping("/lead/{leadId}/oldest")
    public ResponseEntity<List<LeadActivityResponse>>
    getActivitiesByLeadOldestFirst(
            @PathVariable Long leadId) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead not found with ID: " + leadId
                                )
                        );

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByLeadOldestFirst(customerLead)
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET ACTIVITIES BY TYPE
    // ==========================================================

    @Operation(
            summary = "Get activities by type",
            description = "Retrieves activities matching the specified activity type."
    )
    @GetMapping("/type/{activityType}")
    public ResponseEntity<List<LeadActivityResponse>>
    getActivitiesByType(
            @PathVariable LeadActivityType activityType) {

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByType(activityType)
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET ACTIVITIES BY USER
    // ==========================================================

    @Operation(
            summary = "Get activities by user",
            description = "Retrieves all activities performed by a specific user."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeadActivityResponse>>
    getActivitiesByUser(
            @PathVariable Long userId) {

        User performedBy =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: " + userId
                                )
                        );

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByUser(performedBy)
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD + TYPE
    // ==========================================================

    @Operation(
            summary = "Get lead activities by type",
            description = "Retrieves activities of a lead filtered by activity type."
    )
    @GetMapping("/lead/{leadId}/type/{activityType}")
    public ResponseEntity<List<LeadActivityResponse>>
    getActivitiesByLeadAndType(
            @PathVariable Long leadId,
            @PathVariable LeadActivityType activityType) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead not found with ID: " + leadId
                                )
                        );

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByLeadAndType(
                                customerLead,
                                activityType
                        )
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET ACTIVITIES BY LEAD + USER
    // ==========================================================

    @Operation(
            summary = "Get lead activities by user",
            description = "Retrieves activities of a lead performed by a specific user."
    )
    @GetMapping("/lead/{leadId}/user/{userId}")
    public ResponseEntity<List<LeadActivityResponse>>
    getActivitiesByLeadAndUser(
            @PathVariable Long leadId,
            @PathVariable Long userId) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead not found with ID: " + leadId
                                )
                        );

        User performedBy =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: " + userId
                                )
                        );

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByLeadAndUser(
                                customerLead,
                                performedBy
                        )
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET LEAD ACTIVITIES BY TYPE
    // LATEST FIRST
    // ==========================================================

    @Operation(
            summary = "Get latest lead activities by type",
            description = "Retrieves activities of a lead and activity type from newest to oldest."
    )
    @GetMapping("/lead/{leadId}/type/{activityType}/latest")
    public ResponseEntity<List<LeadActivityResponse>>
    getActivitiesByLeadAndTypeLatestFirst(
            @PathVariable Long leadId,
            @PathVariable LeadActivityType activityType) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead not found with ID: " + leadId
                                )
                        );

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByLeadAndTypeLatestFirst(
                                customerLead,
                                activityType
                        )
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET USER ACTIVITIES
    // LATEST FIRST
    // ==========================================================

    @Operation(
            summary = "Get latest activities by user",
            description = "Retrieves activities performed by a user from newest to oldest."
    )
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<List<LeadActivityResponse>>
    getActivitiesByUserLatestFirst(
            @PathVariable Long userId) {

        User performedBy =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: " + userId
                                )
                        );

        List<LeadActivityResponse> response =
                leadActivityService
                        .getActivitiesByUserLatestFirst(performedBy)
                        .stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // ENTITY -> RESPONSE DTO
    // ==========================================================

    private LeadActivityResponse convertToResponse(
            LeadActivity activity) {

        LeadActivityResponse response =
                new LeadActivityResponse();

        // ------------------------------------------------------
        // Activity ID
        // ------------------------------------------------------

        response.setActivityId(
                activity.getActivityId()
        );

        // ------------------------------------------------------
        // Activity Type
        // ------------------------------------------------------

        response.setActivityType(
                activity.getActivityType()
        );

        // ------------------------------------------------------
        // Description
        // ------------------------------------------------------

        response.setDescription(
                activity.getDescription()
        );

        // ------------------------------------------------------
        // Created At
        // ------------------------------------------------------

        response.setCreatedAt(
                activity.getCreatedAt()
        );

        // ------------------------------------------------------
        // Lead Information
        // ------------------------------------------------------

        if (activity.getCustomerLead() != null) {

            CustomerLead lead =
                    activity.getCustomerLead();

            response.setLeadId(
                    lead.getLeadId()
            );

            /*
             * IMPORTANT:
             * Replace getLeadName() below if your
             * CustomerLead entity uses another field
             * such as getFullName(), getCustomerName(),
             * getName(), etc.
             */
            response.setLeadName(
                    lead.getFullName()
            );
        }

        // ------------------------------------------------------
        // User Information
        // ------------------------------------------------------

        if (activity.getPerformedBy() != null) {

            User user =
                    activity.getPerformedBy();

            /*
             * IMPORTANT:
             * Replace getUserId() / getFullName()
             * if your User entity uses different
             * getter names.
             */

            response.setPerformedById(
                    user.getId()
            );

            response.setPerformedByName(
                    user.getFullName()
            );
        }

        return response;
    }
}