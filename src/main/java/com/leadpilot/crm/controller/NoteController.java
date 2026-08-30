package com.leadpilot.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadpilot.crm.dto.NoteRequest;
import com.leadpilot.crm.dto.NoteResponse;
import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.Note;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.mapper.NoteMapper;
import com.leadpilot.crm.repository.CustomerLeadRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.NoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


/**
 * ==========================================================
 * Controller : NoteController
 *
 * Description :
 * REST controller for managing CRM notes associated
 * with customer leads.
 *
 * Base URL :
 * /api/notes
 *
 * Supported Operations :
 *
 * - Create Note
 * - Get All Active Notes
 * - Get Note By ID
 * - Get Notes By Lead
 * - Get All Notes By Lead
 * - Update Note
 * - Soft Delete Note
 * - Restore Note
 * - Pin Note
 * - Unpin Note
 * - Mark Note Important
 * - Mark Note Not Important
 * - Get Pinned Notes By Lead
 * - Get Important Notes By Lead
 * - Get Notes Created By User
 *
 * ==========================================================
 */
@RestController
@RequestMapping("/api/notes")
@SecurityRequirement(name = "bearerAuth")
@Tag(
    name = "Notes",
    description = "APIs for creating, viewing, updating, deleting, restoring, pinning and managing CRM notes associated with customer leads"
)
public class NoteController {


    // ==========================================================
    // Dependencies
    // ==========================================================

    private final NoteService noteService;

    private final CustomerLeadRepository customerLeadRepository;

    private final UserRepository userRepository;


    // ==========================================================
    // Constructor Injection
    // ==========================================================

    public NoteController(
            NoteService noteService,
            CustomerLeadRepository customerLeadRepository,
            UserRepository userRepository) {

        this.noteService = noteService;
        this.customerLeadRepository = customerLeadRepository;
        this.userRepository = userRepository;
    }


    // ==========================================================
    // CREATE NOTE
    // ==========================================================

    /**
     * Creates a new CRM note for a customer lead.
     */
    @Operation(
        summary = "Create note",
        description = "Creates a new note associated with a customer lead. "
                    + "The createdById parameter identifies the user who creates the note."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "201",
            description = "Note created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = NoteResponse.class)
            )
        ),

        @ApiResponse(
            responseCode = "400",
            description = "Invalid note data or validation failure",
            content = @Content
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Customer lead or user was not found",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Note information including lead ID, title, content and flags",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = NoteRequest.class
                    )
                )
            )
            @Valid @RequestBody NoteRequest request,

            @Parameter(
                description = "ID of the user creating the note",
                required = true,
                example = "1"
            )
            @RequestParam Long createdById) {


        // ------------------------------------------------------
        // Find Customer Lead
        // ------------------------------------------------------

        CustomerLead customerLead =
                customerLeadRepository.findById(request.getLeadId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer lead not found with ID: "
                                                + request.getLeadId()
                                )
                        );


        // ------------------------------------------------------
        // Find User Who Created The Note
        // ------------------------------------------------------

        User createdBy =
                userRepository.findById(createdById)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: "
                                                + createdById
                                )
                        );


        // ------------------------------------------------------
        // Convert Request DTO -> Entity
        // ------------------------------------------------------

        Note note =
                NoteMapper.toEntity(request);


        // ------------------------------------------------------
        // Set Entity Relationships
        // ------------------------------------------------------

        note.setCustomerLead(customerLead);

        note.setCreatedBy(createdBy);


        // ------------------------------------------------------
        // Save Note
        // ------------------------------------------------------

        Note savedNote =
                noteService.createNote(note);


        // ------------------------------------------------------
        // Convert Entity -> Response DTO
        // ------------------------------------------------------

        NoteResponse response =
                NoteMapper.toResponse(savedNote);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // ==========================================================
    // GET ALL ACTIVE NOTES
    // ==========================================================

    /**
     * Gets all active, non-deleted notes.
     */
    @Operation(
        summary = "Get all active notes",
        description = "Returns all active CRM notes that have not been soft-deleted."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Active notes retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {

        List<Note> notes =
                noteService.getAllNotes();

        List<NoteResponse> responses =
                notes.stream()
                        .map(NoteMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    // ==========================================================
    // GET NOTE BY ID
    // ==========================================================

    /**
     * Gets an active note using its ID.
     */
    @Operation(
        summary = "Get note by ID",
        description = "Returns an active CRM note using its unique note ID."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Note retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note not found",
            content = @Content
        )
    })
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponse> getNoteById(

            @Parameter(
                description = "Unique ID of the note",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId) {

        Note note =
                noteService.getNoteById(noteId);

        NoteResponse response =
                NoteMapper.toResponse(note);

        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // GET ACTIVE NOTES BY LEAD
    // ==========================================================

    /**
     * Gets all active notes belonging to a customer lead.
     */
    @Operation(
        summary = "Get active notes by lead",
        description = "Returns all active notes associated with the specified customer lead."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Notes retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Customer lead not found",
            content = @Content
        )
    })
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<NoteResponse>> getNotesByLead(

            @Parameter(
                description = "Unique ID of the customer lead",
                required = true,
                example = "1"
            )
            @PathVariable Long leadId) {


        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer lead not found with ID: "
                                                + leadId
                                )
                        );


        List<Note> notes =
                noteService.getNotesByLead(customerLead);


        List<NoteResponse> responses =
                notes.stream()
                        .map(NoteMapper::toResponse)
                        .collect(Collectors.toList());


        return ResponseEntity.ok(responses);
    }


    // ==========================================================
    // GET ALL NOTES BY LEAD
    // ==========================================================

    /**
     * Gets all notes belonging to a lead including soft-deleted notes.
     */
    @Operation(
        summary = "Get all notes by lead",
        description = "Returns all notes associated with a customer lead, including notes that have been soft-deleted."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "All lead notes retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Customer lead not found",
            content = @Content
        )
    })
    @GetMapping("/lead/{leadId}/all")
    public ResponseEntity<List<NoteResponse>> getAllNotesByLead(

            @Parameter(
                description = "Unique ID of the customer lead",
                required = true,
                example = "1"
            )
            @PathVariable Long leadId) {


        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer lead not found with ID: "
                                                + leadId
                                )
                        );


        List<Note> notes =
                noteService.getAllNotesByLead(customerLead);


        List<NoteResponse> responses =
                notes.stream()
                        .map(NoteMapper::toResponse)
                        .collect(Collectors.toList());


        return ResponseEntity.ok(responses);
    }


    // ==========================================================
    // UPDATE NOTE
    // ==========================================================

    /**
     * Updates an existing note.
     */
    @Operation(
        summary = "Update note",
        description = "Updates an existing CRM note. "
                    + "The updatedById parameter identifies the user performing the update."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Note updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "400",
            description = "Invalid note data",
            content = @Content
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note or user not found",
            content = @Content
        )
    })
    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponse> updateNote(

            @Parameter(
                description = "Unique ID of the note to update",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId,

            @Parameter(
                description = "ID of the user performing the update",
                required = true,
                example = "1"
            )
            @RequestParam Long updatedById,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated note information",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = NoteRequest.class
                    )
                )
            )
            @Valid @RequestBody NoteRequest request) {


        // ------------------------------------------------------
        // Find User Performing Update
        // ------------------------------------------------------

        User updatedBy =
                userRepository.findById(updatedById)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: "
                                                + updatedById
                                )
                        );


        // ------------------------------------------------------
        // Convert Request -> Entity
        // ------------------------------------------------------

        Note note =
                NoteMapper.toEntity(request);


        // ------------------------------------------------------
        // Update Note
        // ------------------------------------------------------

        Note updatedNote =
                noteService.updateNote(
                        noteId,
                        note,
                        updatedBy
                );


        // ------------------------------------------------------
        // Convert Entity -> Response
        // ------------------------------------------------------

        NoteResponse response =
                NoteMapper.toResponse(updatedNote);


        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // SOFT DELETE NOTE
    // ==========================================================

    /**
     * Soft-deletes a note.
     */
    @Operation(
        summary = "Delete note",
        description = "Soft-deletes a CRM note. The note is retained in the database and can be restored later."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "204",
            description = "Note deleted successfully"
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note or user not found",
            content = @Content
        )
    })
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(

            @Parameter(
                description = "Unique ID of the note to delete",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId,

            @Parameter(
                description = "ID of the user performing the deletion",
                required = true,
                example = "1"
            )
            @RequestParam Long deletedById) {


        // ------------------------------------------------------
        // Find User Performing Delete
        // ------------------------------------------------------

        User deletedBy =
                userRepository.findById(deletedById)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: "
                                                + deletedById
                                )
                        );


        // ------------------------------------------------------
        // Soft Delete
        // ------------------------------------------------------

        noteService.deleteNote(
                noteId,
                deletedBy
        );


        return ResponseEntity
                .noContent()
                .build();
    }


    // ==========================================================
    // RESTORE NOTE
    // ==========================================================

    /**
     * Restores a previously soft-deleted note.
     */
    @Operation(
        summary = "Restore note",
        description = "Restores a previously soft-deleted CRM note and makes it active again."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Note restored successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note not found",
            content = @Content
        )
    })
    @PutMapping("/{noteId}/restore")
    public ResponseEntity<NoteResponse> restoreNote(

            @Parameter(
                description = "Unique ID of the note to restore",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId) {


        Note restoredNote =
                noteService.restoreNote(noteId);


        NoteResponse response =
                NoteMapper.toResponse(restoredNote);


        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // PIN NOTE
    // ==========================================================

    /**
     * Pins a note.
     */
    @Operation(
        summary = "Pin note",
        description = "Marks a CRM note as pinned for quick access."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Note pinned successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note not found",
            content = @Content
        )
    })
    @PutMapping("/{noteId}/pin")
    public ResponseEntity<NoteResponse> pinNote(

            @Parameter(
                description = "Unique ID of the note",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId) {


        Note note =
                noteService.pinNote(noteId);


        NoteResponse response =
                NoteMapper.toResponse(note);


        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // UNPIN NOTE
    // ==========================================================

    /**
     * Removes the pinned status from a note.
     */
    @Operation(
        summary = "Unpin note",
        description = "Removes the pinned status from a CRM note."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Note unpinned successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note not found",
            content = @Content
        )
    })
    @PutMapping("/{noteId}/unpin")
    public ResponseEntity<NoteResponse> unpinNote(

            @Parameter(
                description = "Unique ID of the note",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId) {


        Note note =
                noteService.unpinNote(noteId);


        NoteResponse response =
                NoteMapper.toResponse(note);


        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // MARK IMPORTANT
    // ==========================================================

    /**
     * Marks a note as important.
     */
    @Operation(
        summary = "Mark note as important",
        description = "Marks a CRM note as important so that it can be identified as a high-priority note."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Note marked as important",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note not found",
            content = @Content
        )
    })
    @PutMapping("/{noteId}/important")
    public ResponseEntity<NoteResponse> markImportant(

            @Parameter(
                description = "Unique ID of the note",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId) {


        Note note =
                noteService.markImportant(noteId);


        NoteResponse response =
                NoteMapper.toResponse(note);


        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // MARK NOT IMPORTANT
    // ==========================================================

    /**
     * Removes the important status from a note.
     */
    @Operation(
        summary = "Mark note as not important",
        description = "Removes the important flag from a CRM note."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Note marked as not important",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Note not found",
            content = @Content
        )
    })
    @PutMapping("/{noteId}/not-important")
    public ResponseEntity<NoteResponse> markNotImportant(

            @Parameter(
                description = "Unique ID of the note",
                required = true,
                example = "1"
            )
            @PathVariable Long noteId) {


        Note note =
                noteService.markNotImportant(noteId);


        NoteResponse response =
                NoteMapper.toResponse(note);


        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // GET PINNED NOTES BY LEAD
    // ==========================================================

    /**
     * Gets all pinned notes belonging to a lead.
     */
    @Operation(
        summary = "Get pinned notes by lead",
        description = "Returns all pinned notes associated with the specified customer lead."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Pinned notes retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Customer lead not found",
            content = @Content
        )
    })
    @GetMapping("/lead/{leadId}/pinned")
    public ResponseEntity<List<NoteResponse>> getPinnedNotesByLead(

            @Parameter(
                description = "Unique ID of the customer lead",
                required = true,
                example = "1"
            )
            @PathVariable Long leadId) {


        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer lead not found with ID: "
                                                + leadId
                                )
                        );


        List<Note> notes =
                noteService.getPinnedNotesByLead(customerLead);


        List<NoteResponse> responses =
                notes.stream()
                        .map(NoteMapper::toResponse)
                        .collect(Collectors.toList());


        return ResponseEntity.ok(responses);
    }


    // ==========================================================
    // GET IMPORTANT NOTES BY LEAD
    // ==========================================================

    /**
     * Gets all important notes belonging to a lead.
     */
    @Operation(
        summary = "Get important notes by lead",
        description = "Returns all important notes associated with the specified customer lead."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "Important notes retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "Customer lead not found",
            content = @Content
        )
    })
    @GetMapping("/lead/{leadId}/important")
    public ResponseEntity<List<NoteResponse>> getImportantNotesByLead(

            @Parameter(
                description = "Unique ID of the customer lead",
                required = true,
                example = "1"
            )
            @PathVariable Long leadId) {


        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer lead not found with ID: "
                                                + leadId
                                )
                        );


        List<Note> notes =
                noteService.getImportantNotesByLead(customerLead);


        List<NoteResponse> responses =
                notes.stream()
                        .map(NoteMapper::toResponse)
                        .collect(Collectors.toList());


        return ResponseEntity.ok(responses);
    }


    // ==========================================================
    // GET NOTES CREATED BY USER
    // ==========================================================

    /**
     * Gets all active notes created by a particular user.
     */
    @Operation(
        summary = "Get notes created by user",
        description = "Returns all active CRM notes created by the specified user."
    )
    @ApiResponses({

        @ApiResponse(
            responseCode = "200",
            description = "User-created notes retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = NoteResponse.class
                )
            )
        ),

        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content
        )
    })
    @GetMapping("/created-by/{userId}")
    public ResponseEntity<List<NoteResponse>> getNotesByCreatedBy(

            @Parameter(
                description = "Unique ID of the user who created the notes",
                required = true,
                example = "1"
            )
            @PathVariable Long userId) {


        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with ID: "
                                                + userId
                                )
                        );


        List<Note> notes =
                noteService.getNotesByCreatedBy(user);


        List<NoteResponse> responses =
                notes.stream()
                        .map(NoteMapper::toResponse)
                        .collect(Collectors.toList());


        return ResponseEntity.ok(responses);
    }
}