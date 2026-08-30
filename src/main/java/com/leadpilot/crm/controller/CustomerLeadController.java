package com.leadpilot.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leadpilot.crm.dto.CustomerLeadRequest;
import com.leadpilot.crm.dto.CustomerLeadResponse;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;
import com.leadpilot.crm.repository.LeadCategoryRepository;
import com.leadpilot.crm.repository.LeadSourceRepository;
import com.leadpilot.crm.repository.LeadSubCategoryRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.CustomerLeadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * ==========================================================
 * Controller : CustomerLeadController
 *
 * Description :
 * Handles HTTP requests related to Customer Leads.
 *
 * Operations:
 * - Create Lead
 * - View All Leads
 * - View Lead By ID
 * - Update Lead
 * - Delete Lead
 * - Filter Leads
 * - Assign Lead
 * - Unassign Lead
 *
 * ==========================================================
 */

@RestController
@RequestMapping("/api/customer-leads")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Customer Lead Management",
        description = "APIs for creating, retrieving, updating, deleting, filtering, assigning, and managing customer leads."
)
public class CustomerLeadController {

    // ==========================================================
    // Dependencies
    // ==========================================================

    @Autowired
    private CustomerLeadService customerLeadService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeadCategoryRepository leadCategoryRepository;

    @Autowired
    private LeadSubCategoryRepository leadSubCategoryRepository;

    @Autowired
    private LeadSourceRepository leadSourceRepository;

    // ==========================================================
    // Create Lead
    // ==========================================================

    @Operation(
            summary = "Create a new customer lead",
            description = "Creates and saves a new customer lead using the provided customer, category, subcategory, source, status, priority, and assignment details."
    )
    @PostMapping
    public ResponseEntity<CustomerLeadResponse> createLead(
            @RequestBody CustomerLeadRequest request) {

        CustomerLead customerLead = new CustomerLead();

        mapRequestToEntity(request, customerLead);

        CustomerLead savedLead =
                customerLeadService.createLead(customerLead);

        return new ResponseEntity<>(
                mapToResponse(savedLead),
                HttpStatus.CREATED
        );
    }

    // ==========================================================
    // Get All Leads
    // ==========================================================

    @Operation(
            summary = "Get all customer leads",
            description = "Retrieves a list containing all customer leads available in the CRM system."
    )
    @GetMapping
    public ResponseEntity<List<CustomerLeadResponse>> getAllLeads() {

        List<CustomerLeadResponse> responses =
                customerLeadService.getAllLeads()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Get Lead By ID
    // ==========================================================

    @Operation(
            summary = "Get a customer lead by ID",
            description = "Retrieves the complete details of a specific customer lead using its unique lead ID."
    )
    @GetMapping("/{leadId}")
    public ResponseEntity<CustomerLeadResponse> getLeadById(

            @Parameter(
                    description = "Unique ID of the customer lead",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId) {

        CustomerLead customerLead =
                customerLeadService.getLeadById(leadId);

        return ResponseEntity.ok(
                mapToResponse(customerLead)
        );
    }

    // ==========================================================
    // Update Lead
    // ==========================================================

    @Operation(
            summary = "Update an existing customer lead",
            description = "Updates the details of an existing customer lead using the specified lead ID and the provided lead information."
    )
    @PutMapping("/{leadId}")
    public ResponseEntity<CustomerLeadResponse> updateLead(

            @Parameter(
                    description = "Unique ID of the customer lead to update",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId,

            @RequestBody CustomerLeadRequest request) {

        CustomerLead customerLead = new CustomerLead();

        mapRequestToEntity(request, customerLead);

        CustomerLead updatedLead =
                customerLeadService.updateLead(
                        leadId,
                        customerLead
                );

        return ResponseEntity.ok(
                mapToResponse(updatedLead)
        );
    }

    // ==========================================================
    // Delete Lead
    // ==========================================================

    @Operation(
            summary = "Delete a customer lead",
            description = "Deletes the customer lead associated with the specified lead ID from the CRM system."
    )
    @DeleteMapping("/{leadId}")
    public ResponseEntity<String> deleteLead(

            @Parameter(
                    description = "Unique ID of the customer lead to delete",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId) {

        customerLeadService.deleteLead(leadId);

        return ResponseEntity.ok(
                "Lead deleted successfully."
        );
    }

    // ==========================================================
    // Get Leads By Category ID
    // ==========================================================

    @Operation(
            summary = "Get leads by category",
            description = "Retrieves all customer leads that belong to the specified lead category."
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CustomerLeadResponse>> getLeadsByCategoryId(

            @Parameter(
                    description = "Unique ID of the lead category",
                    required = true,
                    example = "1"
            )
            @PathVariable Long categoryId) {

        LeadCategory category =
                leadCategoryRepository.findById(categoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found with ID: "
                                                + categoryId
                                )
                        );

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByCategory(category)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Get Leads By Subcategory ID
    // ==========================================================

    @Operation(
            summary = "Get leads by subcategory",
            description = "Retrieves all customer leads that belong to the specified lead subcategory."
    )
    @GetMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsBySubCategoryId(

            @Parameter(
                    description = "Unique ID of the lead subcategory",
                    required = true,
                    example = "1"
            )
            @PathVariable Long subCategoryId) {

        LeadSubCategory subCategory =
                leadSubCategoryRepository.findById(subCategoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subcategory not found with ID: "
                                                + subCategoryId
                                )
                        );

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsBySubCategory(subCategory)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Get Leads By Source ID
    // ==========================================================

    @Operation(
            summary = "Get leads by source",
            description = "Retrieves all customer leads that were created or acquired through the specified lead source."
    )
    @GetMapping("/source/{sourceId}")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsBySourceId(

            @Parameter(
                    description = "Unique ID of the lead source",
                    required = true,
                    example = "1"
            )
            @PathVariable Long sourceId) {

        LeadSource source =
                leadSourceRepository.findById(sourceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lead source not found with ID: "
                                                + sourceId
                                )
                        );

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsBySource(source)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Get Leads By Status
    // ==========================================================

    @Operation(
            summary = "Get leads by status",
            description = "Retrieves all customer leads that currently have the specified lead status."
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsByStatus(

            @Parameter(
                    description = "Lead status used to filter the results",
                    required = true,
                    example = "NEW"
            )
            @PathVariable LeadStatus status) {

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByStatus(status)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Get Leads By Priority
    // ==========================================================

    @Operation(
            summary = "Get leads by priority",
            description = "Retrieves all customer leads that have the specified lead priority."
    )
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsByPriority(

            @Parameter(
                    description = "Lead priority used to filter the results",
                    required = true,
                    example = "HOT"
            )
            @PathVariable LeadPriority priority) {

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByPriority(priority)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Get Leads By Assigned User ID
    // ==========================================================

    @Operation(
            summary = "Get leads assigned to a user",
            description = "Retrieves all customer leads currently assigned to the specified user."
    )
    @GetMapping("/assigned-user/{userId}")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsByAssignedUser(

            @Parameter(
                    description = "Unique ID of the assigned user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        User assignedUser =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: "
                                                + userId
                                )
                        );

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByAssignedUser(assignedUser)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Status + Priority
    // ==========================================================

    @Operation(
            summary = "Filter leads by status and priority",
            description = "Retrieves customer leads matching both the specified lead status and lead priority."
    )
    @GetMapping("/filter/status-priority")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsByStatusAndPriority(

            @Parameter(
                    description = "Lead status used for filtering",
                    required = true,
                    example = "NEW"
            )
            @RequestParam LeadStatus status,

            @Parameter(
                    description = "Lead priority used for filtering",
                    required = true,
                    example = "HOT"
            )
            @RequestParam LeadPriority priority) {

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByStatusAndPriority(
                                status,
                                priority
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Category + Subcategory
    // ==========================================================

    @Operation(
            summary = "Filter leads by category and subcategory",
            description = "Retrieves customer leads matching both the specified category and subcategory."
    )
    @GetMapping("/filter/category-subcategory")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsByCategoryAndSubCategory(

            @Parameter(
                    description = "Unique ID of the lead category",
                    required = true,
                    example = "1"
            )
            @RequestParam Long categoryId,

            @Parameter(
                    description = "Unique ID of the lead subcategory",
                    required = true,
                    example = "1"
            )
            @RequestParam Long subCategoryId) {

        LeadCategory category =
                leadCategoryRepository.findById(categoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found with ID: "
                                                + categoryId
                                )
                        );

        LeadSubCategory subCategory =
                leadSubCategoryRepository.findById(subCategoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subcategory not found with ID: "
                                                + subCategoryId
                                )
                        );

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByCategoryAndSubCategory(
                                category,
                                subCategory
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Assigned User + Status
    // ==========================================================

    @Operation(
            summary = "Filter leads by assigned user and status",
            description = "Retrieves customer leads assigned to a specific user and matching the specified lead status."
    )
    @GetMapping("/filter/assigned-user-status")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsByAssignedUserAndStatus(

            @Parameter(
                    description = "Unique ID of the assigned user",
                    required = true,
                    example = "1"
            )
            @RequestParam Long userId,

            @Parameter(
                    description = "Lead status used for filtering",
                    required = true,
                    example = "FOLLOW_UP"
            )
            @RequestParam LeadStatus status) {

        User assignedUser =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: "
                                                + userId
                                )
                        );

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByAssignedUserAndStatus(
                                assignedUser,
                                status
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Assigned User + Priority
    // ==========================================================

    @Operation(
            summary = "Filter leads by assigned user and priority",
            description = "Retrieves customer leads assigned to a specific user and matching the specified lead priority."
    )
    @GetMapping("/filter/assigned-user-priority")
    public ResponseEntity<List<CustomerLeadResponse>>
    getLeadsByAssignedUserAndPriority(

            @Parameter(
                    description = "Unique ID of the assigned user",
                    required = true,
                    example = "1"
            )
            @RequestParam Long userId,

            @Parameter(
                    description = "Lead priority used for filtering",
                    required = true,
                    example = "HOT"
            )
            @RequestParam LeadPriority priority) {

        User assignedUser =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: "
                                                + userId
                                )
                        );

        List<CustomerLeadResponse> responses =
                customerLeadService
                        .getLeadsByAssignedUserAndPriority(
                                assignedUser,
                                priority
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // Assign Lead
    // ==========================================================

    @Operation(
            summary = "Assign a lead to a user",
            description = "Assigns the specified customer lead to the selected user."
    )
    @PutMapping("/{leadId}/assign/{userId}")
    public ResponseEntity<CustomerLeadResponse> assignLead(

            @Parameter(
                    description = "Unique ID of the customer lead",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId,

            @Parameter(
                    description = "Unique ID of the user to assign the lead to",
                    required = true,
                    example = "2"
            )
            @PathVariable Long userId) {

        CustomerLead assignedLead =
                customerLeadService.assignLead(
                        leadId,
                        userId
                );

        return ResponseEntity.ok(
                mapToResponse(assignedLead)
        );
    }

    // ==========================================================
    // Unassign Lead
    // ==========================================================

    @Operation(
            summary = "Unassign a customer lead",
            description = "Removes the current user assignment from the specified customer lead."
    )
    @PutMapping("/{leadId}/unassign")
    public ResponseEntity<CustomerLeadResponse> unassignLead(

            @Parameter(
                    description = "Unique ID of the customer lead to unassign",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId) {

        CustomerLead unassignedLead =
                customerLeadService.unassignLead(leadId);

        return ResponseEntity.ok(
                mapToResponse(unassignedLead)
        );
    }

    // ==========================================================
    // Request -> Entity
    // ==========================================================

    private void mapRequestToEntity(
            CustomerLeadRequest request,
            CustomerLead customerLead) {

        customerLead.setFullName(
                request.getFullName()
        );

        customerLead.setAge(
                request.getAge()
        );

        customerLead.setEmail(
                request.getEmail()
        );

        customerLead.setPhoneNumber(
                request.getPhoneNumber()
        );

        customerLead.setAddress(
                request.getAddress()
        );

        customerLead.setCity(
                request.getCity()
        );

        customerLead.setState(
                request.getState()
        );

        customerLead.setPincode(
                request.getPincode()
        );

        customerLead.setLeadStatus(
                request.getLeadStatus()
        );

        customerLead.setLeadPriority(
                request.getLeadPriority()
        );

        // ======================================================
        // Category
        // ======================================================

        LeadCategory category =
                leadCategoryRepository.findById(
                        request.getCategoryId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with ID: "
                                        + request.getCategoryId()
                        )
                );

        customerLead.setLeadCategory(category);

        // ======================================================
        // Subcategory
        // ======================================================

        LeadSubCategory subCategory =
                leadSubCategoryRepository.findById(
                        request.getSubCategoryId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Subcategory not found with ID: "
                                        + request.getSubCategoryId()
                        )
                );

        customerLead.setLeadSubCategory(
                subCategory
        );

        // ======================================================
        // Lead Source
        // ======================================================

        LeadSource leadSource =
                leadSourceRepository.findById(
                        request.getLeadSourceId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead source not found with ID: "
                                        + request.getLeadSourceId()
                        )
                );

        customerLead.setLeadSource(
                leadSource
        );

        // ======================================================
        // Assigned User
        // ======================================================

        if (request.getAssignedUserId() != null) {

            User assignedUser =
                    userRepository.findById(
                            request.getAssignedUserId()
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User not found with ID: "
                                            + request.getAssignedUserId()
                            )
                    );

            customerLead.setAssignedUser(
                    assignedUser
            );

        } else {

            customerLead.setAssignedUser(null);
        }
    }

    // ==========================================================
    // Entity -> Response
    // ==========================================================

    private CustomerLeadResponse mapToResponse(
            CustomerLead customerLead) {

        CustomerLeadResponse response =
                new CustomerLeadResponse();

        response.setLeadId(
                customerLead.getLeadId()
        );

        response.setFullName(
                customerLead.getFullName()
        );

        response.setAge(
                customerLead.getAge()
        );

        response.setEmail(
                customerLead.getEmail()
        );

        response.setPhoneNumber(
                customerLead.getPhoneNumber()
        );

        response.setAddress(
                customerLead.getAddress()
        );

        response.setCity(
                customerLead.getCity()
        );

        response.setState(
                customerLead.getState()
        );

        response.setPincode(
                customerLead.getPincode()
        );

        // ======================================================
        // Category
        // ======================================================

        if (customerLead.getLeadCategory() != null) {

            response.setCategoryId(
                    customerLead
                            .getLeadCategory()
                            .getCategoryId()
            );

            response.setCategoryName(
                    customerLead
                            .getLeadCategory()
                            .getCategoryName()
            );
        }

        // ======================================================
        // Subcategory
        // ======================================================

        if (customerLead.getLeadSubCategory() != null) {

            response.setSubCategoryId(
                    customerLead
                            .getLeadSubCategory()
                            .getSubCategoryId()
            );

            response.setSubCategoryName(
                    customerLead
                            .getLeadSubCategory()
                            .getSubCategoryName()
            );
        }

        // ======================================================
        // Lead Source
        // ======================================================

        if (customerLead.getLeadSource() != null) {

            response.setLeadSourceId(
                    customerLead
                            .getLeadSource()
                            .getLeadSourceId()
            );

            response.setSourceName(
                    customerLead
                            .getLeadSource()
                            .getSourceName()
            );
        }

        // ======================================================
        // Status & Priority
        // ======================================================

        response.setLeadStatus(
                customerLead.getLeadStatus()
        );

        response.setLeadPriority(
                customerLead.getLeadPriority()
        );

        // ======================================================
        // Assigned User
        // ======================================================

        if (customerLead.getAssignedUser() != null) {

            response.setAssignedUserId(
                    customerLead
                            .getAssignedUser()
                            .getId()
            );

            response.setAssignedUserName(
                    customerLead
                            .getAssignedUser()
                            .getFullName()
            );
        }

        // ======================================================
        // Created By
        // ======================================================

        if (customerLead.getCreatedBy() != null) {

            response.setCreatedById(
                    customerLead
                            .getCreatedBy()
                            .getId()
            );

            response.setCreatedByName(
                    customerLead
                            .getCreatedBy()
                            .getFullName()
            );
        }

        // ======================================================
        // Updated By
        // ======================================================

        if (customerLead.getUpdatedBy() != null) {

            response.setUpdatedById(
                    customerLead
                            .getUpdatedBy()
                            .getId()
            );

            response.setUpdatedByName(
                    customerLead
                            .getUpdatedBy()
                            .getFullName()
            );
        }

        // ======================================================
        // Audit
        // ======================================================

        response.setCreatedAt(
                customerLead.getCreatedAt()
        );

        response.setUpdatedAt(
                customerLead.getUpdatedAt()
        );

        return response;
    }
}