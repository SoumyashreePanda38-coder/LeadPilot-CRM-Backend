package com.leadpilot.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadpilot.crm.dto.LeadSourceRequest;
import com.leadpilot.crm.dto.LeadSourceResponse;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.enums.CategoryStatus;
import com.leadpilot.crm.service.LeadSourceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/lead-sources")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Lead Source Management",
        description = "APIs for managing lead sources"
)
public class LeadSourceController {

    // ==========================================================
    // SERVICE
    // ==========================================================

    @Autowired
    private LeadSourceService leadSourceService;


    // ==========================================================
    // CREATE
    // ==========================================================

    @Operation(
            summary = "Create lead source",
            description = "Creates a new lead source using only the required source information."
    )
    @PostMapping
    public ResponseEntity<LeadSourceResponse> addLeadSource(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lead source information required to create a source.",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LeadSourceRequest.class
                            )
                    )
            )
            @RequestBody LeadSourceRequest request) {

        LeadSource savedLeadSource =
                leadSourceService.addLeadSource(request);

        return new ResponseEntity<>(
                toResponse(savedLeadSource),
                HttpStatus.CREATED
        );
    }


    // ==========================================================
    // GET ALL
    // ==========================================================

    @Operation(
            summary = "Get all lead sources",
            description = "Retrieves all lead sources ordered by display order."
    )
    @GetMapping
    public ResponseEntity<List<LeadSourceResponse>> getAllLeadSources() {

        List<LeadSource> leadSources =
                leadSourceService.getAllLeadSources();

        return ResponseEntity.ok(
                leadSources.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }


    // ==========================================================
    // GET BY ID
    // ==========================================================

    @Operation(
            summary = "Get lead source by ID",
            description = "Retrieves a lead source using its unique ID."
    )
    @GetMapping("/{sourceId}")
    public ResponseEntity<LeadSourceResponse> getLeadSourceById(

            @Parameter(
                    description = "Unique ID of the lead source.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long sourceId) {

        LeadSource leadSource =
                leadSourceService.getLeadSourceById(sourceId);

        return ResponseEntity.ok(
                toResponse(leadSource)
        );
    }


    // ==========================================================
    // GET BY STATUS
    // ==========================================================

    @Operation(
            summary = "Get lead sources by status",
            description = "Retrieves lead sources having the specified status."
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeadSourceResponse>> getLeadSourcesByStatus(

            @Parameter(
                    description = "Status of the lead source.",
                    required = true,
                    example = "ACTIVE"
            )
            @PathVariable CategoryStatus status) {

        List<LeadSource> leadSources =
                leadSourceService.getLeadSourcesByStatus(status);

        return ResponseEntity.ok(
                leadSources.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }


    // ==========================================================
    // UPDATE
    // ==========================================================

    @Operation(
            summary = "Update lead source",
            description = "Updates an existing lead source."
    )
    @PutMapping("/{sourceId}")
    public ResponseEntity<LeadSourceResponse> updateLeadSource(

            @Parameter(
                    description = "Unique ID of the lead source.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long sourceId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated lead source information.",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LeadSourceRequest.class
                            )
                    )
            )
            @RequestBody LeadSourceRequest request) {

        LeadSource updatedLeadSource =
                leadSourceService.updateLeadSource(
                        sourceId,
                        request
                );

        return ResponseEntity.ok(
                toResponse(updatedLeadSource)
        );
    }


    // ==========================================================
    // UPDATE STATUS
    // ==========================================================

    @Operation(
            summary = "Update lead source status",
            description = "Changes the status of a lead source."
    )
    @PutMapping("/{sourceId}/status/{status}")
    public ResponseEntity<LeadSourceResponse> updateStatus(

            @Parameter(
                    description = "Unique ID of the lead source.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long sourceId,

            @Parameter(
                    description = "New status.",
                    required = true,
                    example = "ACTIVE"
            )
            @PathVariable CategoryStatus status) {

        LeadSource updatedLeadSource =
                leadSourceService.updateStatus(
                        sourceId,
                        status
                );

        return ResponseEntity.ok(
                toResponse(updatedLeadSource)
        );
    }


    // ==========================================================
    // ACTIVATE
    // ==========================================================

    @Operation(
            summary = "Activate lead source",
            description = "Activates a lead source."
    )
    @PutMapping("/{sourceId}/activate")
    public ResponseEntity<LeadSourceResponse> activateLeadSource(

            @PathVariable Long sourceId) {

        LeadSource updatedLeadSource =
                leadSourceService.updateStatus(
                        sourceId,
                        CategoryStatus.ACTIVE
                );

        return ResponseEntity.ok(
                toResponse(updatedLeadSource)
        );
    }


    // ==========================================================
    // DEACTIVATE
    // ==========================================================

    @Operation(
            summary = "Deactivate lead source",
            description = "Deactivates a lead source."
    )
    @PutMapping("/{sourceId}/deactivate")
    public ResponseEntity<LeadSourceResponse> deactivateLeadSource(

            @PathVariable Long sourceId) {

        LeadSource updatedLeadSource =
                leadSourceService.updateStatus(
                        sourceId,
                        CategoryStatus.INACTIVE
                );

        return ResponseEntity.ok(
                toResponse(updatedLeadSource)
        );
    }


    // ==========================================================
    // DELETE
    // ==========================================================

    @Operation(
            summary = "Delete lead source",
            description = "Deletes a lead source using its unique ID."
    )
    @DeleteMapping("/{sourceId}")
    public ResponseEntity<String> deleteLeadSource(

            @PathVariable Long sourceId) {

        leadSourceService.deleteLeadSource(sourceId);

        return ResponseEntity.ok(
                "Lead source deleted successfully"
        );
    }


    // ==========================================================
    // SEARCH
    // ==========================================================

    @Operation(
            summary = "Search lead sources",
            description = "Searches lead sources by source name."
    )
    @GetMapping("/search")
    public ResponseEntity<List<LeadSourceResponse>> searchLeadSources(

            @Parameter(
                    description = "Keyword to search for.",
                    required = true,
                    example = "Website"
            )
            @RequestParam String keyword) {

        List<LeadSource> leadSources =
                leadSourceService.searchLeadSources(keyword);

        return ResponseEntity.ok(
                leadSources.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }


    // ==========================================================
    // ENTITY → RESPONSE DTO
    // ==========================================================

    private LeadSourceResponse toResponse(
            LeadSource leadSource) {

        if (leadSource == null) {
            return null;
        }

        LeadSourceResponse response =
                new LeadSourceResponse();

        // ------------------------------------------------------
        // ID
        // ------------------------------------------------------

        response.setLeadSourceId(
                leadSource.getLeadSourceId()
        );

        // ------------------------------------------------------
        // SOURCE INFORMATION
        // ------------------------------------------------------

        response.setSourceName(
                leadSource.getSourceName()
        );

        response.setDescription(
                leadSource.getDescription()
        );

        response.setDisplayOrder(
                leadSource.getDisplayOrder()
        );

        // ------------------------------------------------------
        // STATUS
        // ------------------------------------------------------

        response.setStatus(
                leadSource.getStatus()
        );

        // ------------------------------------------------------
        // CREATED BY
        // ------------------------------------------------------

        if (leadSource.getCreatedBy() != null) {

            response.setCreatedById(
                    leadSource.getCreatedBy().getId()
            );

            response.setCreatedByName(
                    leadSource.getCreatedBy().getFullName()
            );
        }

        // ------------------------------------------------------
        // UPDATED BY
        // ------------------------------------------------------

        if (leadSource.getUpdatedBy() != null) {

            response.setUpdatedById(
                    leadSource.getUpdatedBy().getId()
            );

            response.setUpdatedByName(
                    leadSource.getUpdatedBy().getFullName()
            );
        }

        // ------------------------------------------------------
        // AUDIT DATES
        // ------------------------------------------------------

        response.setCreatedAt(
                leadSource.getCreatedAt()
        );

        response.setUpdatedAt(
                leadSource.getUpdatedAt()
        );

        return response;
    }
}