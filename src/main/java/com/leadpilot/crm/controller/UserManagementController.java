package com.leadpilot.crm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadpilot.crm.dto.UserManagementRequest;
import com.leadpilot.crm.dto.UserResponse;
import com.leadpilot.crm.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * ==========================================================
 * Controller : UserManagementController
 *
 * Description :
 * Provides ADMIN APIs for managing EXECUTIVE users.
 *
 * ADMIN can:
 * - Create Executive
 * - View all Executives
 * - View Executive by ID
 * - Update Executive
 * - Activate Executive
 * - Deactivate Executive
 *
 * Base URL:
 * /api/admin/users
 *
 * ==========================================================
 */

@RestController
@RequestMapping("/api/admin/users")
@Tag(
        name = "User Management",
        description = "ADMIN APIs for creating, viewing, updating, activating and deactivating EXECUTIVE users"
)
@SecurityRequirement(name = "bearerAuth")
public class UserManagementController {

    // ==========================================================
    // DEPENDENCY
    // ==========================================================

    private final UserManagementService userManagementService;

    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    public UserManagementController(
            UserManagementService userManagementService) {

        this.userManagementService = userManagementService;
    }

    // ==========================================================
    // CREATE EXECUTIVE
    // ==========================================================

    /**
     * Creates a new Executive user.
     *
     * POST /api/admin/users?adminId=1
     */

    @Operation(
            summary = "Create Executive",
            description = "Creates a new EXECUTIVE user. "
                    + "The adminId identifies the ADMIN performing the operation."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "201",
                    description = "Executive created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only ADMIN can create executives.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "409",
                    description = "Executive already exists",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<UserResponse> createExecutive(

            @Parameter(
                    description = "ID of the ADMIN performing the operation",
                    required = true,
                    example = "1"
            )
            @RequestParam Long adminId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Executive details required for creation",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserManagementRequest.class
                            )
                    )
            )
            @Valid
            @RequestBody
            UserManagementRequest request) {

        UserResponse response =
                userManagementService.createExecutive(
                        request,
                        adminId
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================================================
    // GET ALL EXECUTIVES
    // ==========================================================

    /**
     * Returns all Executive users.
     *
     * GET /api/admin/users
     */

    @Operation(
            summary = "Get all Executives",
            description = "Returns all users whose role is EXECUTIVE."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Executives retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = UserResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only ADMIN can view executives.",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllExecutives() {

        List<UserResponse> responses =
                userManagementService.getAllExecutives();

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // GET EXECUTIVE BY ID
    // ==========================================================

    /**
     * Returns one Executive by ID.
     *
     * GET /api/admin/users/{id}
     */

    @Operation(
            summary = "Get Executive by ID",
            description = "Returns the details of a specific EXECUTIVE user using the user ID."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Executive retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only ADMIN can view executive details.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Executive not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getExecutiveById(

            @Parameter(
                    description = "ID of the EXECUTIVE user",
                    required = true,
                    example = "5"
            )
            @PathVariable Long id) {

        UserResponse response =
                userManagementService.getExecutiveById(id);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // UPDATE EXECUTIVE
    // ==========================================================

    /**
     * Updates an existing Executive.
     *
     * PUT /api/admin/users/{id}?adminId=1
     */

    @Operation(
            summary = "Update Executive",
            description = "Updates an existing EXECUTIVE user. "
                    + "The adminId identifies the ADMIN performing the operation."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Executive updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only ADMIN can update executives.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Executive not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateExecutive(

            @Parameter(
                    description = "ID of the EXECUTIVE user to update",
                    required = true,
                    example = "5"
            )
            @PathVariable Long id,

            @Parameter(
                    description = "ID of the ADMIN performing the operation",
                    required = true,
                    example = "1"
            )
            @RequestParam Long adminId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated Executive details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserManagementRequest.class
                            )
                    )
            )
            @Valid
            @RequestBody
            UserManagementRequest request) {

        UserResponse response =
                userManagementService.updateExecutive(
                        id,
                        request,
                        adminId
                );

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // ACTIVATE EXECUTIVE
    // ==========================================================

    /**
     * Activates an Executive account.
     *
     * PUT /api/admin/users/{id}/activate?adminId=1
     */

    @Operation(
            summary = "Activate Executive",
            description = "Activates an EXECUTIVE user's account. "
                    + "The adminId identifies the ADMIN performing the operation."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Executive activated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only ADMIN can activate executives.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Executive not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateExecutive(

            @Parameter(
                    description = "ID of the EXECUTIVE user to activate",
                    required = true,
                    example = "5"
            )
            @PathVariable Long id,

            @Parameter(
                    description = "ID of the ADMIN performing the operation",
                    required = true,
                    example = "1"
            )
            @RequestParam Long adminId) {

        UserResponse response =
                userManagementService.activateExecutive(
                        id,
                        adminId
                );

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // DEACTIVATE EXECUTIVE
    // ==========================================================

    /**
     * Deactivates an Executive account.
     *
     * PUT /api/admin/users/{id}/deactivate?adminId=1
     */

    @Operation(
            summary = "Deactivate Executive",
            description = "Deactivates an EXECUTIVE user's account. "
                    + "The adminId identifies the ADMIN performing the operation."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Executive deactivated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only ADMIN can deactivate executives.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Executive not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserResponse> deactivateExecutive(

            @Parameter(
                    description = "ID of the EXECUTIVE user to deactivate",
                    required = true,
                    example = "5"
            )
            @PathVariable Long id,

            @Parameter(
                    description = "ID of the ADMIN performing the operation",
                    required = true,
                    example = "1"
            )
            @RequestParam Long adminId) {

        UserResponse response =
                userManagementService.deactivateExecutive(
                        id,
                        adminId
                );

        return ResponseEntity.ok(response);
    }
}