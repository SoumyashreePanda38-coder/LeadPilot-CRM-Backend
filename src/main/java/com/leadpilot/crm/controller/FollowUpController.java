package com.leadpilot.crm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leadpilot.crm.dto.FollowUpRequest;
import com.leadpilot.crm.dto.FollowUpResponse;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;
import com.leadpilot.crm.mapper.FollowUpMapper;
import com.leadpilot.crm.repository.CustomerLeadRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.FollowUpService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


/**
 * ==========================================================
 * Controller : FollowUpController
 *
 * Description :
 * REST controller for managing follow-ups associated with
 * customer leads.
 *
 * Follow-ups may include:
 *
 *  - Phone calls
 *  - Emails
 *  - WhatsApp
 *  - Meetings
 *  - Site / property visits
 *  - Other customer interactions
 *
 * Supported operations:
 *
 *  - Create Follow-Up
 *  - Get All Follow-Ups
 *  - Get Follow-Up By ID
 *  - Update Follow-Up
 *  - Delete Follow-Up
 *  - Filter By Lead
 *  - Filter By User
 *  - Filter By Status
 *  - Filter By Type
 *  - Filter By Date Range
 *  - Get Today's Follow-Ups
 *  - Get Upcoming Follow-Ups
 *  - Get Overdue Follow-Ups
 *  - Complete Follow-Up
 *  - Cancel Follow-Up
 *  - Count Follow-Ups
 *
 * Base URL:
 *
 * /api/follow-ups
 *
 * ==========================================================
 */
@RestController
@RequestMapping("/api/follow-ups")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Follow-Up Management",
        description = "APIs for creating, managing, filtering, completing and monitoring customer lead follow-ups."
)
public class FollowUpController {

    // ==========================================================
    // Service
    // ==========================================================

    @Autowired
    private FollowUpService followUpService;

    // ==========================================================
    // Repositories
    // Used to resolve IDs received from frontend.
    // ==========================================================

    @Autowired
    private CustomerLeadRepository customerLeadRepository;

    @Autowired
    private UserRepository userRepository;


    // ==========================================================
    // CREATE FOLLOW-UP
    // ==========================================================

    /**
     * Create a new follow-up for a customer lead.
     *
     * The request contains the lead ID, assigned user ID,
     * follow-up type, scheduled date/time, status and other
     * follow-up information.
     *
     * @param request follow-up creation request
     * @return created follow-up
     */
    @Operation(
            summary = "Create a new follow-up",
            description = "Creates a new follow-up and associates it with a customer lead and assigned user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Follow-up created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FollowUpResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid or empty request"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer lead or assigned user not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PostMapping
    public ResponseEntity<FollowUpResponse> createFollowUp(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Follow-up details including lead ID, assigned user ID, type, schedule and status.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = FollowUpRequest.class)
                    )
            )
            @RequestBody FollowUpRequest request) {

        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        // ------------------------------------------------------
        // Resolve Lead
        // ------------------------------------------------------

        CustomerLead customerLead =
                getCustomerLead(request.getLeadId());

        // ------------------------------------------------------
        // Resolve Assigned User
        // ------------------------------------------------------

        User assignedUser =
                getUser(request.getAssignedUserId());

        // ------------------------------------------------------
        // Convert Request -> Entity
        // ------------------------------------------------------

        FollowUp followUp =
                FollowUpMapper.toEntity(request);

        followUp.setCustomerLead(customerLead);
        followUp.setAssignedUser(assignedUser);

        // ------------------------------------------------------
        // Status
        // ------------------------------------------------------

        if (request.getStatus() != null) {
            followUp.setStatus(request.getStatus());
        }

        // ------------------------------------------------------
        // Completion Information
        // ------------------------------------------------------

        followUp.setCompletedAt(
                request.getCompletedAt()
        );

        followUp.setOutcome(
                request.getOutcome()
        );

        // ------------------------------------------------------
        // Save
        // ------------------------------------------------------

        FollowUp savedFollowUp =
                followUpService.createFollowUp(followUp);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(FollowUpMapper.toResponse(savedFollowUp));
    }


    // ==========================================================
    // GET ALL FOLLOW-UPS
    // ==========================================================

    @Operation(
            summary = "Get all follow-ups",
            description = "Retrieves all follow-ups available in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = FollowUpResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @GetMapping
    public ResponseEntity<List<FollowUpResponse>>
            getAllFollowUps() {

        List<FollowUp> followUps =
                followUpService.getAllFollowUps();

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UP BY ID
    // ==========================================================

    @Operation(
            summary = "Get follow-up by ID",
            description = "Retrieves detailed information about a specific follow-up."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FollowUpResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Follow-up not found"
            )
    })
    @GetMapping("/{followUpId}")
    public ResponseEntity<FollowUpResponse>
            getFollowUpById(

                    @Parameter(
                            name = "followUpId",
                            description = "Unique ID of the follow-up",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long followUpId) {

        FollowUp followUp =
                followUpService.getFollowUpById(
                        followUpId
                );

        return ResponseEntity.ok(
                FollowUpMapper.toResponse(followUp)
        );
    }


    // ==========================================================
    // UPDATE FOLLOW-UP
    // ==========================================================

    @Operation(
            summary = "Update a follow-up",
            description = "Updates the details of an existing follow-up including lead, assigned user, status, schedule and outcome."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FollowUpResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Follow-up, lead or user not found"
            )
    })
    @PutMapping("/{followUpId}")
    public ResponseEntity<FollowUpResponse>
            updateFollowUp(

                    @Parameter(
                            name = "followUpId",
                            description = "Unique ID of the follow-up to update",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long followUpId,

                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Updated follow-up information.",
                            required = true,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = FollowUpRequest.class
                                    )
                            )
                    )
                    @RequestBody FollowUpRequest request) {

        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        FollowUp existingFollowUp =
                followUpService.getFollowUpById(
                        followUpId
                );

        FollowUpMapper.updateEntity(
                existingFollowUp,
                request
        );

        if (request.getLeadId() != null) {

            CustomerLead customerLead =
                    getCustomerLead(
                            request.getLeadId()
                    );

            existingFollowUp.setCustomerLead(
                    customerLead
            );
        }

        if (request.getAssignedUserId() != null) {

            User assignedUser =
                    getUser(
                            request.getAssignedUserId()
                    );

            existingFollowUp.setAssignedUser(
                    assignedUser
            );
        }

        if (request.getStatus() != null) {

            existingFollowUp.setStatus(
                    request.getStatus()
            );
        }

        existingFollowUp.setCompletedAt(
                request.getCompletedAt()
        );

        existingFollowUp.setOutcome(
                request.getOutcome()
        );

        FollowUp updatedFollowUp =
                followUpService.updateFollowUp(
                        followUpId,
                        existingFollowUp
                );

        return ResponseEntity.ok(
                FollowUpMapper.toResponse(
                        updatedFollowUp
                )
        );
    }


    // ==========================================================
    // DELETE FOLLOW-UP
    // ==========================================================

    @Operation(
            summary = "Delete a follow-up",
            description = "Permanently deletes a follow-up using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Follow-up deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Follow-up not found"
            )
    })
    @DeleteMapping("/{followUpId}")
    public ResponseEntity<Void> deleteFollowUp(

            @Parameter(
                    name = "followUpId",
                    description = "Unique ID of the follow-up to delete",
                    required = true,
                    example = "1"
            )
            @PathVariable Long followUpId) {

        followUpService.deleteFollowUp(
                followUpId
        );

        return ResponseEntity.noContent().build();
    }


    // ==========================================================
    // GET FOLLOW-UPS BY LEAD
    // ==========================================================

    @Operation(
            summary = "Get follow-ups by lead",
            description = "Retrieves all follow-ups associated with a specific customer lead."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer lead not found"
            )
    })
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByLead(

                    @Parameter(
                            name = "leadId",
                            description = "Unique ID of the customer lead",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long leadId) {

        CustomerLead customerLead =
                getCustomerLead(leadId);

        List<FollowUp> followUps =
                followUpService.getFollowUpsByLead(
                        customerLead
                );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BY LEAD + DATE RANGE
    // ==========================================================

    @Operation(
            summary = "Get lead follow-ups by date range",
            description = "Retrieves follow-ups belonging to a specific lead within the supplied start and end date-time."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer lead not found"
            )
    })
    @GetMapping("/lead/{leadId}/date-range")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByLeadAndDateRange(

                    @Parameter(
                            name = "leadId",
                            description = "Unique ID of the customer lead",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long leadId,

                    @Parameter(
                            name = "start",
                            description = "Start date-time",
                            required = true,
                            example = "2026-08-01T00:00:00"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime start,

                    @Parameter(
                            name = "end",
                            description = "End date-time",
                            required = true,
                            example = "2026-08-31T23:59:59"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime end) {

        CustomerLead customerLead =
                getCustomerLead(leadId);

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByLeadAndDateRange(
                                customerLead,
                                start,
                                end
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BY LEAD + STATUS
    // ==========================================================

    @Operation(
            summary = "Get lead follow-ups by status",
            description = "Retrieves follow-ups for a specific lead filtered by follow-up status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer lead not found"
            )
    })
    @GetMapping("/lead/{leadId}/status/{status}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByLeadAndStatus(

                    @Parameter(
                            name = "leadId",
                            description = "Unique ID of the customer lead",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long leadId,

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status) {

        CustomerLead customerLead =
                getCustomerLead(leadId);

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByLeadAndStatus(
                                customerLead,
                                status
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BY LEAD + TYPE
    // ==========================================================

    @Operation(
            summary = "Get lead follow-ups by type",
            description = "Retrieves follow-ups for a specific lead filtered by follow-up type."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer lead not found"
            )
    })
    @GetMapping("/lead/{leadId}/type/{followUpType}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByLeadAndType(

                    @Parameter(
                            name = "leadId",
                            description = "Unique ID of the customer lead",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long leadId,

                    @Parameter(
                            name = "followUpType",
                            description = "Type of follow-up",
                            required = true,
                            example = "CALL"
                    )
                    @PathVariable FollowUpType followUpType) {

        CustomerLead customerLead =
                getCustomerLead(leadId);

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByLeadAndType(
                                customerLead,
                                followUpType
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BY USER
    // ==========================================================

    @Operation(
            summary = "Get follow-ups by assigned user",
            description = "Retrieves all follow-ups assigned to a particular executive or user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByUser(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService.getFollowUpsByUser(
                        assignedUser
                );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET USER FOLLOW-UPS BY DATE RANGE
    // ==========================================================

    @Operation(
            summary = "Get user follow-ups by date range",
            description = "Retrieves follow-ups assigned to a specific user within a given date-time range."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByUserAndDateRange(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId,

                    @Parameter(
                            name = "start",
                            description = "Start date-time",
                            required = true,
                            example = "2026-08-01T00:00:00"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime start,

                    @Parameter(
                            name = "end",
                            description = "End date-time",
                            required = true,
                            example = "2026-08-31T23:59:59"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime end) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByUserAndDateRange(
                                assignedUser,
                                start,
                                end
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET USER FOLLOW-UPS BY STATUS
    // ==========================================================

    @Operation(
            summary = "Get user follow-ups by status",
            description = "Retrieves follow-ups assigned to a user filtered by status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByUserAndStatus(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId,

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByUserAndStatus(
                                assignedUser,
                                status
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET USER FOLLOW-UPS BY TYPE
    // ==========================================================

    @Operation(
            summary = "Get user follow-ups by type",
            description = "Retrieves follow-ups assigned to a user filtered by follow-up type."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/user/{userId}/type/{followUpType}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByUserAndType(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId,

                    @Parameter(
                            name = "followUpType",
                            description = "Type of follow-up",
                            required = true,
                            example = "CALL"
                    )
                    @PathVariable FollowUpType followUpType) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByUserAndType(
                                assignedUser,
                                followUpType
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BY STATUS
    // ==========================================================

    @Operation(
            summary = "Get follow-ups by status",
            description = "Retrieves all follow-ups having the specified status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            )
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByStatus(

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status) {

        List<FollowUp> followUps =
                followUpService.getFollowUpsByStatus(
                        status
                );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // UPDATE STATUS
    // ==========================================================

    @Operation(
            summary = "Update follow-up status",
            description = "Changes the status of an existing follow-up."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Follow-up not found"
            )
    })
    @PutMapping("/{followUpId}/status/{status}")
    public ResponseEntity<FollowUpResponse>
            updateFollowUpStatus(

                    @Parameter(
                            name = "followUpId",
                            description = "Unique ID of the follow-up",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long followUpId,

                    @Parameter(
                            name = "status",
                            description = "New follow-up status",
                            required = true,
                            example = "COMPLETED"
                    )
                    @PathVariable FollowUpStatus status) {

        FollowUp updatedFollowUp =
                followUpService.updateFollowUpStatus(
                        followUpId,
                        status
                );

        return ResponseEntity.ok(
                FollowUpMapper.toResponse(
                        updatedFollowUp
                )
        );
    }


    // ==========================================================
    // COMPLETE FOLLOW-UP
    // ==========================================================

    @Operation(
            summary = "Complete a follow-up",
            description = "Marks a follow-up as completed and optionally stores the outcome of the interaction."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up completed successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Follow-up not found"
            )
    })
    @PutMapping("/{followUpId}/complete")
    public ResponseEntity<FollowUpResponse>
            completeFollowUp(

                    @Parameter(
                            name = "followUpId",
                            description = "Unique ID of the follow-up",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long followUpId,

                    @Parameter(
                            name = "outcome",
                            description = "Outcome or notes from the completed follow-up",
                            required = false,
                            example = "Customer agreed to visit the property."
                    )
                    @RequestParam(required = false)
                    String outcome) {

        FollowUp completedFollowUp =
                followUpService.completeFollowUp(
                        followUpId,
                        outcome
                );

        return ResponseEntity.ok(
                FollowUpMapper.toResponse(
                        completedFollowUp
                )
        );
    }


    // ==========================================================
    // CANCEL FOLLOW-UP
    // ==========================================================

    @Operation(
            summary = "Cancel a follow-up",
            description = "Cancels an existing follow-up."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up cancelled successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Follow-up not found"
            )
    })
    @PutMapping("/{followUpId}/cancel")
    public ResponseEntity<FollowUpResponse>
            cancelFollowUp(

                    @Parameter(
                            name = "followUpId",
                            description = "Unique ID of the follow-up",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long followUpId) {

        FollowUp cancelledFollowUp =
                followUpService.cancelFollowUp(
                        followUpId
                );

        return ResponseEntity.ok(
                FollowUpMapper.toResponse(
                        cancelledFollowUp
                )
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BY TYPE
    // ==========================================================

    @Operation(
            summary = "Get follow-ups by type",
            description = "Retrieves all follow-ups matching the specified follow-up type."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            )
    })
    @GetMapping("/type/{followUpType}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByType(

                    @Parameter(
                            name = "followUpType",
                            description = "Type of follow-up",
                            required = true,
                            example = "CALL"
                    )
                    @PathVariable FollowUpType followUpType) {

        List<FollowUp> followUps =
                followUpService.getFollowUpsByType(
                        followUpType
                );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BY TYPE + STATUS
    // ==========================================================

    @Operation(
            summary = "Get follow-ups by type and status",
            description = "Retrieves follow-ups filtered by both follow-up type and status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            )
    })
    @GetMapping("/type/{followUpType}/status/{status}")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByTypeAndStatus(

                    @Parameter(
                            name = "followUpType",
                            description = "Type of follow-up",
                            required = true,
                            example = "CALL"
                    )
                    @PathVariable FollowUpType followUpType,

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status) {

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByTypeAndStatus(
                                followUpType,
                                status
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // GET FOLLOW-UPS BETWEEN DATES
    // ==========================================================

    @Operation(
            summary = "Get follow-ups by date range",
            description = "Retrieves all follow-ups scheduled between the specified start and end date-time."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            )
    })
    @GetMapping("/date-range")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsBetween(

                    @Parameter(
                            name = "start",
                            description = "Start date-time",
                            required = true,
                            example = "2026-08-01T00:00:00"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime start,

                    @Parameter(
                            name = "end",
                            description = "End date-time",
                            required = true,
                            example = "2026-08-31T23:59:59"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime end) {

        List<FollowUp> followUps =
                followUpService.getFollowUpsBetween(
                        start,
                        end
                );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // TODAY'S FOLLOW-UPS
    // ==========================================================

    @Operation(
            summary = "Get today's follow-ups",
            description = "Retrieves all follow-ups scheduled for the current day."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Today's follow-ups retrieved successfully"
            )
    })
    @GetMapping("/today")
    public ResponseEntity<List<FollowUpResponse>>
            getTodaysFollowUps() {

        List<FollowUp> followUps =
                followUpService.getTodaysFollowUps();

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // TODAY'S FOLLOW-UPS BY USER
    // ==========================================================

    @Operation(
            summary = "Get today's follow-ups for a user",
            description = "Retrieves today's follow-ups assigned to a specific user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Today's user follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/today/user/{userId}")
    public ResponseEntity<List<FollowUpResponse>>
            getTodaysFollowUpsByUser(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getTodaysFollowUpsByUser(
                                assignedUser
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // UPCOMING FOLLOW-UPS
    // ==========================================================

    @Operation(
            summary = "Get upcoming follow-ups",
            description = "Retrieves follow-ups scheduled for future dates and times."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Upcoming follow-ups retrieved successfully"
            )
    })
    @GetMapping("/upcoming")
    public ResponseEntity<List<FollowUpResponse>>
            getUpcomingFollowUps() {

        List<FollowUp> followUps =
                followUpService.getUpcomingFollowUps(
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // UPCOMING FOLLOW-UPS BY USER
    // ==========================================================

    @Operation(
            summary = "Get upcoming follow-ups for a user",
            description = "Retrieves future follow-ups assigned to a specific user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Upcoming user follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/upcoming/user/{userId}")
    public ResponseEntity<List<FollowUpResponse>>
            getUpcomingFollowUpsByUser(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getUpcomingFollowUpsByUser(
                                assignedUser,
                                LocalDateTime.now()
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // OVERDUE FOLLOW-UPS
    // ==========================================================

    @Operation(
            summary = "Get overdue follow-ups",
            description = "Retrieves follow-ups whose scheduled date and time have already passed."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Overdue follow-ups retrieved successfully"
            )
    })
    @GetMapping("/overdue")
    public ResponseEntity<List<FollowUpResponse>>
            getOverdueFollowUps() {

        List<FollowUp> followUps =
                followUpService.getOverdueFollowUps(
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // OVERDUE FOLLOW-UPS BY USER
    // ==========================================================

    @Operation(
            summary = "Get overdue follow-ups for a user",
            description = "Retrieves overdue follow-ups assigned to a specific user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Overdue user follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/overdue/user/{userId}")
    public ResponseEntity<List<FollowUpResponse>>
            getOverdueFollowUpsByUser(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getOverdueFollowUpsByUser(
                                assignedUser,
                                LocalDateTime.now()
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // COUNT BY STATUS
    // ==========================================================

    @Operation(
            summary = "Count follow-ups by status",
            description = "Returns the total number of follow-ups having the specified status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up count retrieved successfully"
            )
    })
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long>
            countFollowUpsByStatus(

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status) {

        return ResponseEntity.ok(
                followUpService.countFollowUpsByStatus(
                        status
                )
        );
    }


    // ==========================================================
    // COUNT BY USER + STATUS
    // ==========================================================

    @Operation(
            summary = "Count user follow-ups by status",
            description = "Returns the number of follow-ups assigned to a specific user with the specified status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up count retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/count/user/{userId}/status/{status}")
    public ResponseEntity<Long>
            countFollowUpsByUserAndStatus(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId,

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status) {

        User assignedUser =
                getUser(userId);

        return ResponseEntity.ok(
                followUpService
                        .countFollowUpsByUserAndStatus(
                                assignedUser,
                                status
                        )
        );
    }


    // ==========================================================
    // COUNT BY LEAD
    // ==========================================================

    @Operation(
            summary = "Count follow-ups by lead",
            description = "Returns the total number of follow-ups associated with a customer lead."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up count retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer lead not found"
            )
    })
    @GetMapping("/count/lead/{leadId}")
    public ResponseEntity<Long>
            countFollowUpsByLead(

                    @Parameter(
                            name = "leadId",
                            description = "Unique ID of the customer lead",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long leadId) {

        CustomerLead customerLead =
                getCustomerLead(leadId);

        return ResponseEntity.ok(
                followUpService.countFollowUpsByLead(
                        customerLead
                )
        );
    }


    // ==========================================================
    // COUNT BY USER
    // ==========================================================

    @Operation(
            summary = "Count follow-ups by user",
            description = "Returns the total number of follow-ups assigned to a specific user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-up count retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Long>
            countFollowUpsByUser(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId) {

        User assignedUser =
                getUser(userId);

        return ResponseEntity.ok(
                followUpService.countFollowUpsByUser(
                        assignedUser
                )
        );
    }


    // ==========================================================
    // SORTED BY SCHEDULED DATE
    // ==========================================================

    @Operation(
            summary = "Get follow-ups ordered by scheduled date",
            description = "Retrieves follow-ups sorted according to their scheduled date and time."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            )
    })
    @GetMapping("/scheduled")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByScheduledDate() {

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByScheduledDate();

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // RECENTLY CREATED FOLLOW-UPS
    // ==========================================================

    @Operation(
            summary = "Get recently created follow-ups",
            description = "Retrieves recently created follow-ups from the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recent follow-ups retrieved successfully"
            )
    })
    @GetMapping("/recent")
    public ResponseEntity<List<FollowUpResponse>>
            getRecentlyCreatedFollowUps() {

        List<FollowUp> followUps =
                followUpService
                        .getRecentlyCreatedFollowUps();

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // COMPLETED FOLLOW-UPS BY LEAD
    // ==========================================================

    @Operation(
            summary = "Get completed follow-ups by lead",
            description = "Retrieves all completed follow-ups associated with a specific customer lead."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Completed follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer lead not found"
            )
    })
    @GetMapping("/lead/{leadId}/completed")
    public ResponseEntity<List<FollowUpResponse>>
            getCompletedFollowUpsByLead(

                    @Parameter(
                            name = "leadId",
                            description = "Unique ID of the customer lead",
                            required = true,
                            example = "10"
                    )
                    @PathVariable Long leadId) {

        CustomerLead customerLead =
                getCustomerLead(leadId);

        List<FollowUp> followUps =
                followUpService
                        .getCompletedFollowUpsByLead(
                                customerLead
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // COMPLETED FOLLOW-UPS BY USER
    // ==========================================================

    @Operation(
            summary = "Get completed follow-ups by user",
            description = "Retrieves all completed follow-ups assigned to a specific user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Completed follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<FollowUpResponse>>
            getCompletedFollowUpsByUser(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getCompletedFollowUpsByUser(
                                assignedUser
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // STATUS + DATE RANGE
    // ==========================================================

    @Operation(
            summary = "Get follow-ups by status and date range",
            description = "Retrieves follow-ups having a specific status within a given date-time range."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            )
    })
    @GetMapping("/status/{status}/date-range")
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByStatusAndDateRange(

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status,

                    @Parameter(
                            name = "start",
                            description = "Start date-time",
                            required = true,
                            example = "2026-08-01T00:00:00"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime start,

                    @Parameter(
                            name = "end",
                            description = "End date-time",
                            required = true,
                            example = "2026-08-31T23:59:59"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime end) {

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByStatusAndDateRange(
                                status,
                                start,
                                end
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // USER + STATUS + DATE RANGE
    // ==========================================================

    @Operation(
            summary = "Get user follow-ups by status and date range",
            description = "Retrieves follow-ups assigned to a specific user, filtered by status and date range."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Follow-ups retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping(
            "/user/{userId}/status/{status}/date-range"
    )
    public ResponseEntity<List<FollowUpResponse>>
            getFollowUpsByUserStatusAndDateRange(

                    @Parameter(
                            name = "userId",
                            description = "Unique ID of the assigned user",
                            required = true,
                            example = "5"
                    )
                    @PathVariable Long userId,

                    @Parameter(
                            name = "status",
                            description = "Follow-up status",
                            required = true,
                            example = "SCHEDULED"
                    )
                    @PathVariable FollowUpStatus status,

                    @Parameter(
                            name = "start",
                            description = "Start date-time",
                            required = true,
                            example = "2026-08-01T00:00:00"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime start,

                    @Parameter(
                            name = "end",
                            description = "End date-time",
                            required = true,
                            example = "2026-08-31T23:59:59"
                    )
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE_TIME
                    )
                    LocalDateTime end) {

        User assignedUser =
                getUser(userId);

        List<FollowUp> followUps =
                followUpService
                        .getFollowUpsByUserStatusAndDateRange(
                                assignedUser,
                                status,
                                start,
                                end
                        );

        return ResponseEntity.ok(
                toResponseList(followUps)
        );
    }


    // ==========================================================
    // HELPER : FIND CUSTOMER LEAD
    // ==========================================================

    /**
     * Finds a customer lead by its ID.
     *
     * @param leadId customer lead ID
     * @return customer lead entity
     */
    private CustomerLead getCustomerLead(Long leadId) {

        if (leadId == null) {
            throw new IllegalArgumentException(
                    "Lead ID is required"
            );
        }

        return customerLeadRepository
                .findById(leadId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Customer lead not found with ID: "
                                        + leadId
                        )
                );
    }


    // ==========================================================
    // HELPER : FIND USER
    // ==========================================================

    /**
     * Finds a user by their ID.
     *
     * @param userId user ID
     * @return user entity
     */
    private User getUser(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException(
                    "User ID is required"
            );
        }

        return userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found with ID: "
                                        + userId
                        )
                );
    }


    // ==========================================================
    // HELPER : ENTITY LIST -> RESPONSE LIST
    // ==========================================================

    /**
     * Converts a list of FollowUp entities into a list
     * of FollowUpResponse DTOs.
     *
     * @param followUps list of follow-up entities
     * @return list of follow-up response DTOs
     */
    private List<FollowUpResponse> toResponseList(
            List<FollowUp> followUps) {

        if (followUps == null) {
            return List.of();
        }

        return followUps.stream()
                .map(FollowUpMapper::toResponse)
                .collect(Collectors.toList());
    }
} 