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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadpilot.crm.dto.LeadCategoryRequest;
import com.leadpilot.crm.dto.LeadCategoryResponse;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.enums.CategoryStatus;
import com.leadpilot.crm.mapper.LeadCategoryMapper;
import com.leadpilot.crm.service.LeadCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * ==========================================================
 * Controller : LeadCategoryController
 *
 * Description :
 * REST controller for managing Lead Categories.
 *
 * Base URL :
 * /api/lead-categories
 *
 * Important :
 * This controller returns LeadCategoryResponse DTOs instead
 * of directly returning LeadCategory entities.
 *
 * This prevents Hibernate LAZY proxy serialization problems
 * such as ByteBuddyInterceptor errors.
 *
 * Supported Operations :
 *
 * 1. Create Lead Category
 * 2. Get All Lead Categories
 * 3. Get Lead Category By ID
 * 4. Update Lead Category
 * 5. Update Category Status
 * 6. Activate Category
 * 7. Deactivate Category
 * 8. Search Categories
 * 9. Filter Categories By Status
 * 10. Get Active Categories Ordered By Name
 *
 * ==========================================================
 */

@RestController
@RequestMapping("/api/lead-categories")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Lead Category Management",
        description = "APIs for creating, viewing, updating, searching, and managing lead categories and their statuses."
)
public class LeadCategoryController {

    // ==========================================================
    // Dependency
    // ==========================================================

    @Autowired
    private LeadCategoryService leadCategoryService;

    // ==========================================================
    // CREATE LEAD CATEGORY
    // ==========================================================

    /**
     * Creates a new lead category.
     *
     * POST /api/lead-categories
     *
     * Example Request:
     *
     * {
     *     "categoryName": "Healthcare",
     *     "description": "Healthcare and medical service leads",
     *     "displayOrder": 1,
     *     "status": "ACTIVE"
     * }
     *
     * @param request lead category details
     * @return created lead category
     */

    @Operation(
            summary = "Create lead category",
            description = "Creates a new lead category and returns the newly created category."
    )
    @PostMapping
    public ResponseEntity<LeadCategoryResponse> addCategory(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lead category details required to create a new category.",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LeadCategoryRequest.class
                            )
                    )
            )
            @Valid @RequestBody LeadCategoryRequest request) {

        // ------------------------------------------------------
        // Create category
        // ------------------------------------------------------

        LeadCategory createdCategory =
                leadCategoryService.addCategory(request);

        // ------------------------------------------------------
        // Convert Entity -> Response DTO
        // ------------------------------------------------------

        LeadCategoryResponse response =
                LeadCategoryMapper.toResponse(createdCategory);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================================================
    // GET ALL LEAD CATEGORIES
    // ==========================================================

    /**
     * Gets all lead categories.
     *
     * GET /api/lead-categories
     *
     * @return list of lead category response DTOs
     */

    @Operation(
            summary = "Get all lead categories",
            description = "Retrieves all lead categories available in the system."
    )
    @GetMapping
    public ResponseEntity<List<LeadCategoryResponse>> getAllCategories() {

        // ------------------------------------------------------
        // Get entities from service
        // ------------------------------------------------------

        List<LeadCategory> categories =
                leadCategoryService.getAllCategories();

        // ------------------------------------------------------
        // Convert Entity List -> Response DTO List
        // ------------------------------------------------------

        List<LeadCategoryResponse> responses =
                categories.stream()
                        .map(LeadCategoryMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // GET LEAD CATEGORY BY ID
    // ==========================================================

    /**
     * Gets a lead category by ID.
     *
     * GET /api/lead-categories/{categoryId}
     *
     * @param categoryId category ID
     * @return lead category response
     */

    @Operation(
            summary = "Get lead category by ID",
            description = "Retrieves a specific lead category using its unique ID."
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<LeadCategoryResponse> getCategoryById(

            @Parameter(
                    description = "Unique ID of the lead category.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long categoryId) {

        // ------------------------------------------------------
        // Get category
        // ------------------------------------------------------

        LeadCategory category =
                leadCategoryService.getCategoryById(categoryId);

        // ------------------------------------------------------
        // Convert Entity -> Response DTO
        // ------------------------------------------------------

        LeadCategoryResponse response =
                LeadCategoryMapper.toResponse(category);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // UPDATE LEAD CATEGORY
    // ==========================================================

    /**
     * Updates an existing lead category.
     *
     * PUT /api/lead-categories/{categoryId}
     *
     * Example Request:
     *
     * {
     *     "categoryName": "Healthcare",
     *     "description": "Hospitals, clinics and medical services",
     *     "displayOrder": 1,
     *     "status": "ACTIVE"
     * }
     *
     * @param categoryId category ID
     * @param request updated category information
     * @return updated category
     */

    @Operation(
            summary = "Update lead category",
            description = "Updates the details of an existing lead category."
    )
    @PutMapping("/{categoryId}")
    public ResponseEntity<LeadCategoryResponse> updateCategory(

            @Parameter(
                    description = "Unique ID of the lead category to update.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long categoryId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated lead category details.",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LeadCategoryRequest.class
                            )
                    )
            )
            @Valid @RequestBody LeadCategoryRequest request) {

        // ------------------------------------------------------
        // Update category
        // ------------------------------------------------------

        LeadCategory updatedCategory =
                leadCategoryService.updateCategory(
                        categoryId,
                        request
                );

        // ------------------------------------------------------
        // Convert Entity -> Response DTO
        // ------------------------------------------------------

        LeadCategoryResponse response =
                LeadCategoryMapper.toResponse(updatedCategory);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // UPDATE CATEGORY STATUS
    // ==========================================================

    /**
     * Updates the status of a lead category.
     *
     * PUT /api/lead-categories/{categoryId}/status/{status}
     *
     * Example:
     *
     * PUT /api/lead-categories/1/status/ACTIVE
     */

    @Operation(
            summary = "Update category status",
            description = "Changes the status of a lead category to ACTIVE or INACTIVE."
    )
    @PutMapping("/{categoryId}/status/{status}")
    public ResponseEntity<LeadCategoryResponse> updateCategoryStatus(

            @Parameter(
                    description = "Unique ID of the lead category.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long categoryId,

            @Parameter(
                    description = "New status of the category.",
                    required = true,
                    example = "ACTIVE"
            )
            @PathVariable CategoryStatus status) {

        // ------------------------------------------------------
        // Update status
        // ------------------------------------------------------

        LeadCategory updatedCategory =
                leadCategoryService.updateCategoryStatus(
                        categoryId,
                        status
                );

        // ------------------------------------------------------
        // Convert Entity -> Response DTO
        // ------------------------------------------------------

        LeadCategoryResponse response =
                LeadCategoryMapper.toResponse(updatedCategory);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // ACTIVATE CATEGORY
    // ==========================================================

    /**
     * Activates a lead category.
     *
     * PUT /api/lead-categories/{categoryId}/activate
     */

    @Operation(
            summary = "Activate lead category",
            description = "Activates a specific lead category."
    )
    @PutMapping("/{categoryId}/activate")
    public ResponseEntity<LeadCategoryResponse> activateCategory(

            @Parameter(
                    description = "Unique ID of the category to activate.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long categoryId) {

        // ------------------------------------------------------
        // Activate category
        // ------------------------------------------------------

        LeadCategory activatedCategory =
                leadCategoryService.activateCategory(
                        categoryId
                );

        // ------------------------------------------------------
        // Convert Entity -> Response DTO
        // ------------------------------------------------------

        LeadCategoryResponse response =
                LeadCategoryMapper.toResponse(activatedCategory);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // DEACTIVATE CATEGORY
    // ==========================================================

    /**
     * Deactivates a lead category.
     *
     * PUT /api/lead-categories/{categoryId}/deactivate
     */

    @Operation(
            summary = "Deactivate lead category",
            description = "Deactivates a specific lead category."
    )
    @PutMapping("/{categoryId}/deactivate")
    public ResponseEntity<LeadCategoryResponse> deactivateCategory(

            @Parameter(
                    description = "Unique ID of the category to deactivate.",
                    required = true,
                    example = "1"
            )
            @PathVariable Long categoryId) {

        // ------------------------------------------------------
        // Deactivate category
        // ------------------------------------------------------

        LeadCategory deactivatedCategory =
                leadCategoryService.deactivateCategory(
                        categoryId
                );

        // ------------------------------------------------------
        // Convert Entity -> Response DTO
        // ------------------------------------------------------

        LeadCategoryResponse response =
                LeadCategoryMapper.toResponse(deactivatedCategory);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // SEARCH CATEGORIES
    // ==========================================================

    /**
     * Searches categories by name.
     *
     * GET /api/lead-categories/search?name=Real
     *
     * Example:
     *
     * /api/lead-categories/search?name=Real
     */

    @Operation(
            summary = "Search lead categories",
            description = "Searches lead categories by category name."
    )
    @GetMapping("/search")
    public ResponseEntity<List<LeadCategoryResponse>> searchCategories(

            @Parameter(
                    description = "Category name or keyword.",
                    required = true,
                    example = "Real"
            )
            @RequestParam String name) {

        // ------------------------------------------------------
        // Search categories
        // ------------------------------------------------------

        List<LeadCategory> categories =
                leadCategoryService.searchCategories(name);

        // ------------------------------------------------------
        // Convert Entity List -> Response DTO List
        // ------------------------------------------------------

        List<LeadCategoryResponse> responses =
                categories.stream()
                        .map(LeadCategoryMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // GET CATEGORIES BY STATUS
    // ==========================================================

    /**
     * Gets categories by status.
     *
     * GET /api/lead-categories/status/ACTIVE
     */

    @Operation(
            summary = "Get categories by status",
            description = "Retrieves categories having the specified status."
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeadCategoryResponse>> getCategoriesByStatus(

            @Parameter(
                    description = "Status used to filter categories.",
                    required = true,
                    example = "ACTIVE"
            )
            @PathVariable CategoryStatus status) {

        // ------------------------------------------------------
        // Get categories by status
        // ------------------------------------------------------

        List<LeadCategory> categories =
                leadCategoryService.getCategoriesByStatus(status);

        // ------------------------------------------------------
        // Convert Entity List -> Response DTO List
        // ------------------------------------------------------

        List<LeadCategoryResponse> responses =
                categories.stream()
                        .map(LeadCategoryMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // GET ACTIVE CATEGORIES ORDERED
    // ==========================================================

    /**
     * Gets active categories ordered alphabetically.
     *
     * GET /api/lead-categories/active
     */

    @Operation(
            summary = "Get active categories",
            description = "Retrieves all active lead categories ordered alphabetically by category name."
    )
    @GetMapping("/active")
    public ResponseEntity<List<LeadCategoryResponse>> getActiveCategories() {

        // ------------------------------------------------------
        // Get active categories
        // ------------------------------------------------------

        List<LeadCategory> categories =
                leadCategoryService.getCategoriesOrdered();

        // ------------------------------------------------------
        // Convert Entity List -> Response DTO List
        // ------------------------------------------------------

        List<LeadCategoryResponse> responses =
                categories.stream()
                        .map(LeadCategoryMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}