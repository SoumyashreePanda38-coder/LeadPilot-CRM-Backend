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

import com.leadpilot.crm.dto.LeadSubCategoryRequest;
import com.leadpilot.crm.dto.LeadSubCategoryResponse;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.enums.CategoryStatus;
import com.leadpilot.crm.service.LeadSubCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ==========================================================
 * Controller : LeadSubCategoryController
 *
 * Description :
 * Handles REST API operations related to Lead SubCategories.
 *
 * Request:
 *     LeadSubCategoryRequest
 *
 * Response:
 *     LeadSubCategoryResponse
 *
 * ==========================================================
 */

@RestController
@RequestMapping("/api/lead-subcategories")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Lead SubCategory Management",
        description = "APIs for creating, viewing, updating, searching, deleting, and managing lead subcategories."
)
public class LeadSubCategoryController {

    // ==========================================================
    // Service Dependency
    // ==========================================================

    @Autowired
    private LeadSubCategoryService leadSubCategoryService;


    // ==========================================================
    // CREATE
    // ==========================================================

    @Operation(
            summary = "Create lead subcategory",
            description = "Creates a new lead subcategory under an existing lead category."
    )
    @PostMapping
    public ResponseEntity<LeadSubCategoryResponse> addSubCategory(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lead subcategory information. Audit fields such as createdBy, updatedBy, createdAt and updatedAt are generated automatically.",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LeadSubCategoryRequest.class
                            )
                    )
            )
            @RequestBody LeadSubCategoryRequest request) {

        LeadSubCategory savedSubCategory =
                leadSubCategoryService.addSubCategory(request);

        return new ResponseEntity<>(
                toResponse(savedSubCategory),
                HttpStatus.CREATED
        );
    }


    // ==========================================================
    // READ ALL
    // ==========================================================

    @Operation(
            summary = "Get all lead subcategories",
            description = "Retrieves all lead subcategories."
    )
    @GetMapping
    public ResponseEntity<List<LeadSubCategoryResponse>>
            getAllSubCategories() {

        List<LeadSubCategory> subCategories =
                leadSubCategoryService.getAllSubCategories();

        return ResponseEntity.ok(
                subCategories.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }


    // ==========================================================
    // READ BY ID
    // ==========================================================

    @Operation(
            summary = "Get lead subcategory by ID",
            description = "Retrieves a specific lead subcategory using its ID."
    )
    @GetMapping("/{subCategoryId}")
    public ResponseEntity<LeadSubCategoryResponse>
            getSubCategoryById(

                    @Parameter(
                            description = "Unique ID of the lead subcategory.",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long subCategoryId) {

        LeadSubCategory subCategory =
                leadSubCategoryService
                        .getSubCategoryById(subCategoryId);

        return ResponseEntity.ok(
                toResponse(subCategory)
        );
    }


    // ==========================================================
    // READ BY CATEGORY
    // ==========================================================

    @Operation(
            summary = "Get subcategories by category",
            description = "Retrieves all subcategories belonging to a specific lead category."
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<LeadSubCategoryResponse>>
            getSubCategoriesByCategory(

                    @Parameter(
                            description = "Unique ID of the parent lead category.",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long categoryId) {

        List<LeadSubCategory> subCategories =
                leadSubCategoryService
                        .getSubCategoriesByCategory(categoryId);

        return ResponseEntity.ok(
                subCategories.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }


    // ==========================================================
    // READ BY STATUS
    // ==========================================================

    @Operation(
            summary = "Get subcategories by status",
            description = "Retrieves all subcategories having the specified status."
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeadSubCategoryResponse>>
            getSubCategoriesByStatus(

                    @Parameter(
                            description = "Status used to filter subcategories.",
                            required = true,
                            example = "ACTIVE"
                    )
                    @PathVariable CategoryStatus status) {

        List<LeadSubCategory> subCategories =
                leadSubCategoryService
                        .getSubCategoriesByStatus(status);

        return ResponseEntity.ok(
                subCategories.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }


    // ==========================================================
    // UPDATE
    // ==========================================================

    @Operation(
            summary = "Update lead subcategory",
            description = "Updates an existing lead subcategory. Audit fields are generated automatically."
    )
    @PutMapping("/{subCategoryId}")
    public ResponseEntity<LeadSubCategoryResponse>
            updateSubCategory(

                    @Parameter(
                            description = "Unique ID of the lead subcategory to update.",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long subCategoryId,

                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Updated lead subcategory information.",
                            required = true,
                            content = @Content(
                                    schema = @Schema(
                                            implementation = LeadSubCategoryRequest.class
                                    )
                            )
                    )
                    @RequestBody LeadSubCategoryRequest request) {

        LeadSubCategory updatedSubCategory =
                leadSubCategoryService.updateSubCategory(
                        subCategoryId,
                        request
                );

        return ResponseEntity.ok(
                toResponse(updatedSubCategory)
        );
    }


    // ==========================================================
    // UPDATE STATUS
    // ==========================================================

    @Operation(
            summary = "Update subcategory status",
            description = "Changes the status of a lead subcategory."
    )
    @PutMapping("/{subCategoryId}/status/{status}")
    public ResponseEntity<LeadSubCategoryResponse>
            updateStatus(

                    @Parameter(
                            description = "Unique ID of the lead subcategory.",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long subCategoryId,

                    @Parameter(
                            description = "New status.",
                            required = true,
                            example = "ACTIVE"
                    )
                    @PathVariable CategoryStatus status) {

        LeadSubCategory updatedSubCategory =
                leadSubCategoryService.updateStatus(
                        subCategoryId,
                        status
                );

        return ResponseEntity.ok(
                toResponse(updatedSubCategory)
        );
    }


    // ==========================================================
    // ACTIVATE
    // ==========================================================

    @Operation(
            summary = "Activate lead subcategory",
            description = "Activates a lead subcategory."
    )
    @PutMapping("/{subCategoryId}/activate")
    public ResponseEntity<LeadSubCategoryResponse>
            activateSubCategory(

                    @Parameter(
                            description = "Unique ID of the lead subcategory.",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long subCategoryId) {

        LeadSubCategory updatedSubCategory =
                leadSubCategoryService.updateStatus(
                        subCategoryId,
                        CategoryStatus.ACTIVE
                );

        return ResponseEntity.ok(
                toResponse(updatedSubCategory)
        );
    }


    // ==========================================================
    // DEACTIVATE
    // ==========================================================

    @Operation(
            summary = "Deactivate lead subcategory",
            description = "Deactivates a lead subcategory."
    )
    @PutMapping("/{subCategoryId}/deactivate")
    public ResponseEntity<LeadSubCategoryResponse>
            deactivateSubCategory(

                    @Parameter(
                            description = "Unique ID of the lead subcategory.",
                            required = true,
                            example = "1"
                    )
                    @PathVariable Long subCategoryId) {

        LeadSubCategory updatedSubCategory =
                leadSubCategoryService.updateStatus(
                        subCategoryId,
                        CategoryStatus.INACTIVE
                );

        return ResponseEntity.ok(
                toResponse(updatedSubCategory)
        );
    }


    // ==========================================================
    // DELETE
    // ==========================================================

    @Operation(
            summary = "Delete lead subcategory",
            description = "Deletes an existing lead subcategory."
    )
    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<String> deleteSubCategory(

            @Parameter(
                    description = "Unique ID of the lead subcategory.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long subCategoryId) {

        leadSubCategoryService.deleteSubCategory(
                subCategoryId
        );

        return ResponseEntity.ok(
                "Lead subcategory deleted successfully"
        );
    }


    // ==========================================================
    // SEARCH
    // ==========================================================

    @Operation(
            summary = "Search lead subcategories",
            description = "Searches subcategories by name."
    )
    @GetMapping("/search")
    public ResponseEntity<List<LeadSubCategoryResponse>>
            searchSubCategories(

                    @Parameter(
                            description = "Keyword used to search subcategory names.",
                            required = true,
                            example = "apartment"
                    )
                    @RequestParam String keyword) {

        List<LeadSubCategory> subCategories =
                leadSubCategoryService.searchSubCategories(keyword);

        return ResponseEntity.ok(
                subCategories.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }


    // ==========================================================
    // ENTITY → RESPONSE DTO
    // ==========================================================

    private LeadSubCategoryResponse toResponse(
            LeadSubCategory subCategory) {

        if (subCategory == null) {
            return null;
        }

        LeadSubCategoryResponse response =
                new LeadSubCategoryResponse();


        // ------------------------------------------------------
        // Subcategory ID
        // ------------------------------------------------------

        response.setSubCategoryId(
                subCategory.getSubCategoryId()
        );


        // ------------------------------------------------------
        // Parent Category
        // ------------------------------------------------------

        if (subCategory.getLeadCategory() != null) {

            response.setCategoryId(
                    subCategory
                            .getLeadCategory()
                            .getCategoryId()
            );

            response.setCategoryName(
                    subCategory
                            .getLeadCategory()
                            .getCategoryName()
            );
        }


        // ------------------------------------------------------
        // Subcategory Information
        // ------------------------------------------------------

        response.setSubCategoryName(
                subCategory.getSubCategoryName()
        );

        response.setDescription(
                subCategory.getDescription()
        );

        response.setDisplayOrder(
                subCategory.getDisplayOrder()
        );


        // ------------------------------------------------------
        // Status
        // ------------------------------------------------------

        response.setStatus(
                subCategory.getStatus()
        );


        // ------------------------------------------------------
        // Created By
        // ------------------------------------------------------

        if (subCategory.getCreatedBy() != null) {

            response.setCreatedById(
                    subCategory
                            .getCreatedBy()
                            .getId()
            );

            response.setCreatedByName(
                    subCategory
                            .getCreatedBy()
                            .getFullName()
            );
        }


        // ------------------------------------------------------
        // Updated By
        // ------------------------------------------------------

        if (subCategory.getUpdatedBy() != null) {

            response.setUpdatedById(
                    subCategory
                            .getUpdatedBy()
                            .getId()
            );

            response.setUpdatedByName(
                    subCategory
                            .getUpdatedBy()
                            .getFullName()
            );
        }


        // ------------------------------------------------------
        // Audit Dates
        // ------------------------------------------------------

        response.setCreatedAt(
                subCategory.getCreatedAt()
        );

        response.setUpdatedAt(
                subCategory.getUpdatedAt()
        );

        return response;
    }
}