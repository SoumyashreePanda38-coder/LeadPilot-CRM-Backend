package com.leadpilot.crm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadpilot.crm.dto.ReminderRequest;
import com.leadpilot.crm.dto.ReminderResponse;
import com.leadpilot.crm.entity.Reminder;
import com.leadpilot.crm.mapper.ReminderMapper;
import com.leadpilot.crm.service.ReminderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * ==========================================================
 * Controller : ReminderController
 * ==========================================================
 *
 * REST controller for managing CRM reminders.
 *
 * Base URL:
 * /api/reminders
 *
 * Responsibilities:
 *
 * - Create reminder
 * - Get reminder
 * - Get all reminders
 * - Update reminder
 * - Delete reminder
 * - Get reminders by user
 * - Get reminders by lead
 * - Get reminders by follow-up
 * - Get unread reminders
 * - Mark reminder as read
 * - Complete reminder
 * - Get pending reminders
 * - Dismiss reminder
 * - Restore reminder
 * - Get upcoming reminders
 * - Get due reminders
 * - Notification tracking
 * - Search reminders
 *
 * ==========================================================
 */
@RestController
@RequestMapping("/api/reminders")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Reminder Management",
        description = "APIs for creating, managing, searching and tracking CRM reminders"
)
public class ReminderController {

    // ==========================================================
    // SERVICE
    // ==========================================================

    private final ReminderService reminderService;

    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    // ==========================================================
    // CREATE REMINDER
    // ==========================================================

    @Operation(
            summary = "Create a reminder",
            description = "Creates a new CRM reminder."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Reminder created successfully",
                    content = @Content(
                            schema = @Schema(implementation = ReminderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid reminder data"
            )
    })
    @PostMapping
    public ResponseEntity<ReminderResponse> createReminder(
            @Valid @RequestBody ReminderRequest request) {

        Reminder savedReminder =
                reminderService.createReminder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReminderMapper.toResponse(savedReminder));
    }

    // ==========================================================
    // GET ALL REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get all reminders",
            description = "Returns all reminders available in the CRM system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reminders retrieved successfully",
                    content = @Content(
                            schema = @Schema(
                                    type = "array",
                                    implementation = ReminderResponse.class
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ReminderResponse>> getAllReminders() {

        List<ReminderResponse> responses =
                reminderService.getAllReminders()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // GET REMINDER BY ID
    // ==========================================================

    @Operation(
            summary = "Get reminder by ID",
            description = "Retrieves a specific reminder using its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reminder found",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ReminderResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reminder not found"
            )
    })
    @GetMapping("/{reminderId}")
    public ResponseEntity<ReminderResponse> getReminderById(

            @Parameter(
                    name = "reminderId",
                    description = "Unique ID of the reminder",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.getReminderById(reminderId);

        return ResponseEntity.ok(
                ReminderMapper.toResponse(reminder)
        );
    }

    // ==========================================================
    // UPDATE REMINDER
    // ==========================================================

    @Operation(
            summary = "Update a reminder",
            description = "Updates an existing CRM reminder."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reminder updated successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ReminderResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid reminder data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reminder not found"
            )
    })
    @PutMapping("/{reminderId}")
    public ResponseEntity<ReminderResponse> updateReminder(

            @Parameter(
                    name = "reminderId",
                    description = "Unique ID of the reminder to update",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId,

            @Valid @RequestBody ReminderRequest request) {

        Reminder updatedReminder =
                reminderService.updateReminder(
                        reminderId,
                        request,
                        null
                );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(updatedReminder)
        );
    }
    // ==========================================================
    // DELETE REMINDER
    // ==========================================================

    @Operation(
            summary = "Delete a reminder",
            description = "Permanently deletes a reminder from the CRM system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Reminder deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reminder not found"
            )
    })
    @DeleteMapping("/{reminderId}")
    public ResponseEntity<Void> deleteReminder(

            @Parameter(
                    name = "reminderId",
                    description = "Unique ID of the reminder",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        reminderService.deleteReminder(reminderId);

        return ResponseEntity.noContent().build();
    }

    // ==========================================================
    // REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get reminders by user",
            description = "Returns all reminders assigned to a specific user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reminders retrieved successfully"
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByAssignedUserId(

            @Parameter(
                    name = "userId",
                    description = "ID of the assigned user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByAssignedUserId(userId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // PENDING REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get pending reminders by user",
            description = "Returns pending reminders assigned to a specific user."
    )
    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<ReminderResponse>>
    getPendingRemindersByUser(

            @Parameter(
                    name = "userId",
                    description = "ID of the user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getPendingRemindersByUser(userId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // UNREAD REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get unread reminders by user",
            description = "Returns unread reminders assigned to a specific user."
    )
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<ReminderResponse>>
    getUnreadRemindersByUser(

            @Parameter(
                    name = "userId",
                    description = "ID of the user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getUnreadRemindersByUser(userId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // COMPLETED REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get completed reminders by user",
            description = "Returns completed reminders assigned to a specific user."
    )
    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<ReminderResponse>>
    getCompletedRemindersByUser(

            @Parameter(
                    name = "userId",
                    description = "ID of the user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getCompletedRemindersByUser(userId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // DISMISSED REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get dismissed reminders by user",
            description = "Returns dismissed reminders assigned to a specific user."
    )
    @GetMapping("/user/{userId}/dismissed")
    public ResponseEntity<List<ReminderResponse>>
    getDismissedRemindersByUser(

            @Parameter(
                    name = "userId",
                    description = "ID of the user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getDismissedRemindersByUser(userId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // REMINDERS BY LEAD
    // ==========================================================

    @Operation(
            summary = "Get reminders by lead",
            description = "Returns all reminders associated with a customer lead."
    )
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByLeadId(

            @Parameter(
                    name = "leadId",
                    description = "ID of the customer lead",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByLeadId(leadId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // ORDERED REMINDERS BY LEAD
    // ==========================================================

    @Operation(
            summary = "Get ordered reminders by lead",
            description = "Returns reminders of a lead ordered by reminder time."
    )
    @GetMapping("/lead/{leadId}/ordered")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByLeadIdOrdered(

            @Parameter(
                    name = "leadId",
                    description = "ID of the customer lead",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByLeadIdOrdered(leadId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // REMINDERS BY FOLLOW-UP
    // ==========================================================

    @Operation(
            summary = "Get reminders by follow-up",
            description = "Returns reminders associated with a specific follow-up."
    )
    @GetMapping("/follow-up/{followUpId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByFollowUpId(

            @Parameter(
                    name = "followUpId",
                    description = "ID of the follow-up",
                    required = true,
                    example = "1"
            )
            @PathVariable Long followUpId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByFollowUpId(followUpId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // UNREAD REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get all unread reminders",
            description = "Returns all reminders that have not been read."
    )
    @GetMapping("/unread")
    public ResponseEntity<List<ReminderResponse>>
    getUnreadReminders() {

        List<ReminderResponse> responses =
                reminderService
                        .getUnreadReminders()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // UNREAD PENDING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get unread pending reminders",
            description = "Returns reminders that are both unread and pending."
    )
    @GetMapping("/unread/pending")
    public ResponseEntity<List<ReminderResponse>>
    getUnreadPendingReminders() {

        List<ReminderResponse> responses =
                reminderService
                        .getUnreadPendingReminders()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // MARK AS READ
    // ==========================================================

    @Operation(
            summary = "Mark reminder as read",
            description = "Marks a specific reminder as read."
    )
    @PatchMapping("/{reminderId}/read")
    public ResponseEntity<ReminderResponse>
    markAsRead(

            @Parameter(
                    name = "reminderId",
                    description = "ID of the reminder",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.markAsRead(reminderId);

        return ResponseEntity.ok(
                ReminderMapper.toResponse(reminder)
        );
    }

    // ==========================================================
    // COMPLETED REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get completed reminders",
            description = "Returns all completed reminders."
    )
    @GetMapping("/completed")
    public ResponseEntity<List<ReminderResponse>>
    getCompletedReminders() {

        List<ReminderResponse> responses =
                reminderService
                        .getCompletedReminders()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // COMPLETE REMINDER
    // ==========================================================

    @Operation(
            summary = "Complete a reminder",
            description = "Marks a reminder as completed."
    )
    @PatchMapping("/{reminderId}/complete")
    public ResponseEntity<ReminderResponse>
    completeReminder(

            @Parameter(
                    name = "reminderId",
                    description = "ID of the reminder",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.completeReminder(reminderId);

        return ResponseEntity.ok(
                ReminderMapper.toResponse(reminder)
        );
    }

    // ==========================================================
    // PENDING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get pending reminders",
            description = "Returns all reminders that are currently pending."
    )
    @GetMapping("/pending")
    public ResponseEntity<List<ReminderResponse>>
    getPendingReminders() {

        List<ReminderResponse> responses =
                reminderService
                        .getPendingReminders()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // ORDERED PENDING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get ordered pending reminders",
            description = "Returns pending reminders ordered by reminder time."
    )
    @GetMapping("/pending/ordered")
    public ResponseEntity<List<ReminderResponse>>
    getPendingRemindersOrdered() {

        List<ReminderResponse> responses =
                reminderService
                        .getPendingRemindersOrdered()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // DISMISSED REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get dismissed reminders",
            description = "Returns all dismissed reminders."
    )
    @GetMapping("/dismissed")
    public ResponseEntity<List<ReminderResponse>>
    getDismissedReminders() {

        List<ReminderResponse> responses =
                reminderService
                        .getDismissedReminders()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // DISMISS REMINDER
    // ==========================================================

    @Operation(
            summary = "Dismiss a reminder",
            description = "Dismisses a reminder."
    )
    @PatchMapping("/{reminderId}/dismiss")
    public ResponseEntity<ReminderResponse>
    dismissReminder(

            @Parameter(
                    name = "reminderId",
                    description = "ID of the reminder",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.dismissReminder(reminderId);

        return ResponseEntity.ok(
                ReminderMapper.toResponse(reminder)
        );
    }

    // ==========================================================
    // RESTORE REMINDER
    // ==========================================================

    @Operation(
            summary = "Restore a reminder",
            description = "Restores a previously dismissed reminder."
    )
    @PatchMapping("/{reminderId}/restore")
    public ResponseEntity<ReminderResponse>
    restoreReminder(

            @Parameter(
                    name = "reminderId",
                    description = "ID of the reminder",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.restoreReminder(reminderId);

        return ResponseEntity.ok(
                ReminderMapper.toResponse(reminder)
        );
    }

    // ==========================================================
    // UPCOMING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get upcoming reminders",
            description = "Returns reminders scheduled after the specified date and time. If no dateTime is supplied, the current date and time is used."
    )
    @GetMapping("/upcoming")
    public ResponseEntity<List<ReminderResponse>>
    getUpcomingReminders(

            @Parameter(
                    name = "dateTime",
                    description = "Starting date and time for upcoming reminders. Format: yyyy-MM-ddTHH:mm:ss",
                    required = false,
                    example = "2026-08-11T20:00:00"
            )
            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getUpcomingReminders(dateTime)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // UPCOMING REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get upcoming reminders by user",
            description = "Returns upcoming reminders assigned to a specific user."
    )
    @GetMapping("/user/{userId}/upcoming")
    public ResponseEntity<List<ReminderResponse>>
    getUpcomingRemindersByUser(

            @Parameter(
                    name = "userId",
                    description = "ID of the user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId,

            @Parameter(
                    name = "dateTime",
                    description = "Starting date and time. Format: yyyy-MM-ddTHH:mm:ss",
                    required = false,
                    example = "2026-08-11T20:00:00"
            )
            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getUpcomingRemindersByUser(
                                userId,
                                dateTime
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // DUE REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get due reminders",
            description = "Returns reminders whose scheduled time is due. If dateTime is omitted, the current date and time is used."
    )
    @GetMapping("/due")
    public ResponseEntity<List<ReminderResponse>>
    getDueReminders(

            @Parameter(
                    name = "dateTime",
                    description = "Reference date and time. Format: yyyy-MM-ddTHH:mm:ss",
                    required = false,
                    example = "2026-08-11T20:00:00"
            )
            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getDueReminders(dateTime)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // DUE REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get due reminders by user",
            description = "Returns due reminders assigned to a specific user."
    )
    @GetMapping("/user/{userId}/due")
    public ResponseEntity<List<ReminderResponse>>
    getDueRemindersByUser(

            @Parameter(
                    name = "userId",
                    description = "ID of the user",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId,

            @Parameter(
                    name = "dateTime",
                    description = "Reference date and time. Format: yyyy-MM-ddTHH:mm:ss",
                    required = false,
                    example = "2026-08-11T20:00:00"
            )
            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getDueRemindersByUser(
                                userId,
                                dateTime
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // NOTIFICATION PENDING
    // ==========================================================

    @Operation(
            summary = "Get reminders with pending notifications",
            description = "Returns reminders for which notification has not been sent."
    )
    @GetMapping("/notifications/pending")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersWithNotificationPending() {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersWithNotificationPending()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // DUE NOTIFICATIONS PENDING
    // ==========================================================

    @Operation(
            summary = "Get due reminders with pending notifications",
            description = "Returns due reminders whose notification has not yet been sent."
    )
    @GetMapping("/notifications/due")
    public ResponseEntity<List<ReminderResponse>>
    getDueRemindersWithNotificationPending(

            @Parameter(
                    name = "dateTime",
                    description = "Reference date and time. Format: yyyy-MM-ddTHH:mm:ss",
                    required = false,
                    example = "2026-08-11T20:00:00"
            )
            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getDueRemindersWithNotificationPending(
                                dateTime
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // NOTIFICATION SENT
    // ==========================================================

    @Operation(
            summary = "Get reminders with sent notifications",
            description = "Returns reminders whose notifications have already been sent."
    )
    @GetMapping("/notifications/sent")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersWithNotificationSent() {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersWithNotificationSent()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // MARK NOTIFICATION AS SENT
    // ==========================================================

    @Operation(
            summary = "Mark notification as sent",
            description = "Marks the notification associated with a reminder as sent."
    )
    @PatchMapping("/{reminderId}/notification-sent")
    public ResponseEntity<ReminderResponse>
    markNotificationAsSent(

            @Parameter(
                    name = "reminderId",
                    description = "ID of the reminder",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.markNotificationAsSent(
                        reminderId
                );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(reminder)
        );
    }

    // ==========================================================
    // SEARCH REMINDERS
    // ==========================================================

    @Operation(
            summary = "Search reminders by title",
            description = "Searches reminders using their title."
    )
    @GetMapping("/search")
    public ResponseEntity<List<ReminderResponse>>
    searchRemindersByTitle(

            @Parameter(
                    name = "title",
                    description = "Title text to search for",
                    required = true,
                    example = "Call customer"
            )
            @RequestParam String title) {

        List<ReminderResponse> responses =
                reminderService
                        .searchRemindersByTitle(title)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // SEARCH PENDING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Search pending reminders by title",
            description = "Searches only pending reminders using their title."
    )
    @GetMapping("/search/pending")
    public ResponseEntity<List<ReminderResponse>>
    searchPendingRemindersByTitle(

            @Parameter(
                    name = "title",
                    description = "Title text to search for",
                    required = true,
                    example = "Call customer"
            )
            @RequestParam String title) {

        List<ReminderResponse> responses =
                reminderService
                        .searchPendingRemindersByTitle(title)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // CREATED BY USER
    // ==========================================================

    @Operation(
            summary = "Get reminders created by user",
            description = "Returns reminders created by a specific user."
    )
    @GetMapping("/created-by/{userId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByCreatedBy(

            @Parameter(
                    name = "userId",
                    description = "ID of the user who created the reminders",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByCreatedBy(userId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // UPDATED BY USER
    // ==========================================================

    @Operation(
            summary = "Get reminders updated by user",
            description = "Returns reminders last updated by a specific user."
    )
    @GetMapping("/updated-by/{userId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByUpdatedBy(

            @Parameter(
                    name = "userId",
                    description = "ID of the user who updated the reminders",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByUpdatedBy(userId)
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ==========================================================
    // EXISTS BY LEAD
    // ==========================================================

    @Operation(
            summary = "Check reminder existence by lead",
            description = "Checks whether at least one reminder exists for a specific lead."
    )
    @GetMapping("/exists/lead/{leadId}")
    public ResponseEntity<Boolean> existsByLeadId(

            @Parameter(
                    name = "leadId",
                    description = "ID of the customer lead",
                    required = true,
                    example = "1"
            )
            @PathVariable Long leadId) {

        return ResponseEntity.ok(
                reminderService.existsByLeadId(leadId)
        );
    }

    // ==========================================================
    // EXISTS BY FOLLOW-UP
    // ==========================================================

    @Operation(
            summary = "Check reminder existence by follow-up",
            description = "Checks whether at least one reminder exists for a specific follow-up."
    )
    @GetMapping("/exists/follow-up/{followUpId}")
    public ResponseEntity<Boolean> existsByFollowUpId(

            @Parameter(
                    name = "followUpId",
                    description = "ID of the follow-up",
                    required = true,
                    example = "1"
            )
            @PathVariable Long followUpId) {

        return ResponseEntity.ok(
                reminderService.existsByFollowUpId(followUpId)
        );
    }
}