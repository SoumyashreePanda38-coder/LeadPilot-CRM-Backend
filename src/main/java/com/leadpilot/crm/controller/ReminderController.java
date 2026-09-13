package com.leadpilot.crm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadpilot.crm.dto.ReminderResponse;
import com.leadpilot.crm.entity.Reminder;
import com.leadpilot.crm.mapper.ReminderMapper;
import com.leadpilot.crm.service.ReminderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ==========================================================
 * Controller : ReminderController
 *
 * Description :
 * REST controller for viewing and managing automatically
 * generated CRM reminders.
 *
 * IMPORTANT:
 *
 * Reminders associated with Follow-Ups are NOT manually
 * created from this controller.
 *
 * They are automatically generated when a Follow-Up is
 * created.
 *
 * Automatic flow:
 *
 * Follow-Up created
 *       ↓
 * ReminderService
 *       ↓
 * Reminder created 24 hours before Follow-Up
 *
 * This controller is responsible for:
 *
 * - Viewing reminders
 * - Reading reminders
 * - Completing reminders
 * - Dismissing reminders
 * - Restoring reminders
 * - Upcoming reminders
 * - Due reminders
 * - Notification tracking
 * - Searching reminders
 *
 * ==========================================================
 */
@RestController
@RequestMapping("/api/reminders")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Reminder Management",
        description = "APIs for viewing and managing CRM reminders"
)
public class ReminderController {

    // ==========================================================
    // SERVICE
    // ==========================================================

    private final ReminderService reminderService;


    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    public ReminderController(
            ReminderService reminderService) {

        this.reminderService =
                reminderService;
    }


    // ==========================================================
    // GET ALL REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get all reminders",
            description = "Returns all automatically generated CRM reminders."
    )
    @GetMapping
    public ResponseEntity<List<ReminderResponse>>
    getAllReminders() {

        List<ReminderResponse> responses =
                reminderService
                        .getAllReminders()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // GET REMINDER BY ID
    // ==========================================================

    @Operation(
            summary = "Get reminder by ID",
            description = "Retrieves a reminder using its ID."
    )
    @GetMapping("/{reminderId}")
    public ResponseEntity<ReminderResponse>
    getReminderById(

            @Parameter(
                    description = "Reminder ID",
                    required = true,
                    example = "1"
            )
            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService
                        .getReminderById(
                                reminderId
                        );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(
                        reminder
                )
        );
    }


    // ==========================================================
    // REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get reminders by user",
            description = "Returns reminders assigned to a specific user."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByUser(

            @Parameter(
                    description = "User ID",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByAssignedUserId(
                                userId
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // PENDING REMINDERS BY USER
    // ==========================================================

    @Operation(
            summary = "Get pending reminders by user",
            description = "Returns pending reminders assigned to a user."
    )
    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<ReminderResponse>>
    getPendingRemindersByUser(

            @PathVariable Long userId) {

        List<ReminderResponse> responses =
                reminderService
                        .getPendingRemindersByUser(
                                userId
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // UNREAD REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get unread reminders",
            description = "Returns all unread reminders."
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

        return ResponseEntity.ok(
                responses
        );
    }
 // ==========================================================
 // UNREAD REMINDER COUNT BY USER
 // ==========================================================

 @Operation(
         summary = "Get unread reminder count by user",
         description = "Returns the number of unread reminders assigned to a specific user."
 )
 @GetMapping("/user/{userId}/unread/count")
 public ResponseEntity<Long> getUnreadReminderCountByUser(
         @Parameter(
                 description = "User ID",
                 required = true,
                 example = "1"
         )
         @PathVariable Long userId) {

     long count =
             reminderService
                     .getUnreadReminderCountByUser(
                             userId
                     );

     return ResponseEntity.ok(count);
 }

    // ==========================================================
    // UNREAD PENDING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get unread pending reminders",
            description = "Returns reminders that are unread and still pending."
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

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // MARK AS READ
    // ==========================================================

    @Operation(
            summary = "Mark reminder as read",
            description = "Marks a reminder as read."
    )
    @PatchMapping("/{reminderId}/read")
    public ResponseEntity<ReminderResponse>
    markAsRead(

            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.markAsRead(
                        reminderId
                );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(
                        reminder
                )
        );
    }


    // ==========================================================
    // COMPLETE REMINDER
    // ==========================================================

    @Operation(
            summary = "Complete reminder",
            description = "Marks a reminder as completed."
    )
    @PatchMapping("/{reminderId}/complete")
    public ResponseEntity<ReminderResponse>
    completeReminder(

            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.completeReminder(
                        reminderId
                );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(
                        reminder
                )
        );
    }


    // ==========================================================
    // PENDING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get pending reminders",
            description = "Returns all pending reminders."
    )
    @GetMapping("/pending")
    public ResponseEntity<List<ReminderResponse>>
    getPendingReminders() {

        List<ReminderResponse> responses =
                reminderService
                        .getPendingRemindersOrdered()
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // DISMISSED REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get dismissed reminders",
            description = "Returns dismissed reminders."
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

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // DISMISS REMINDER
    // ==========================================================

    @Operation(
            summary = "Dismiss reminder",
            description = "Dismisses a reminder."
    )
    @PatchMapping("/{reminderId}/dismiss")
    public ResponseEntity<ReminderResponse>
    dismissReminder(

            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.dismissReminder(
                        reminderId
                );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(
                        reminder
                )
        );
    }


    // ==========================================================
    // RESTORE REMINDER
    // ==========================================================

    @Operation(
            summary = "Restore reminder",
            description = "Restores a dismissed reminder."
    )
    @PatchMapping("/{reminderId}/restore")
    public ResponseEntity<ReminderResponse>
    restoreReminder(

            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService.restoreReminder(
                        reminderId
                );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(
                        reminder
                )
        );
    }


    // ==========================================================
    // REMINDERS BY LEAD
    // ==========================================================

    @Operation(
            summary = "Get reminders by lead",
            description = "Returns reminders associated with a customer lead."
    )
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByLead(

            @PathVariable Long leadId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByLeadId(
                                leadId
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // REMINDERS BY FOLLOW-UP
    // ==========================================================

    @Operation(
            summary = "Get reminders by follow-up",
            description = "Returns the automatic reminder associated with a Follow-Up."
    )
    @GetMapping("/follow-up/{followUpId}")
    public ResponseEntity<List<ReminderResponse>>
    getRemindersByFollowUp(

            @PathVariable Long followUpId) {

        List<ReminderResponse> responses =
                reminderService
                        .getRemindersByFollowUpId(
                                followUpId
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // UPCOMING REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get upcoming reminders",
            description = "Returns pending reminders scheduled after the supplied date/time."
    )
    @GetMapping("/upcoming")
    public ResponseEntity<List<ReminderResponse>>
    getUpcomingReminders(

            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime =
                    LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getUpcomingReminders(
                                dateTime
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // DUE REMINDERS
    // ==========================================================

    @Operation(
            summary = "Get due reminders",
            description = "Returns reminders whose reminder time has arrived."
    )
    @GetMapping("/due")
    public ResponseEntity<List<ReminderResponse>>
    getDueReminders(

            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime =
                    LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getDueReminders(
                                dateTime
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
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

            @PathVariable Long userId,

            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime =
                    LocalDateTime.now();
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

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // NOTIFICATION PENDING
    // ==========================================================

    @Operation(
            summary = "Get reminders with pending notifications",
            description = "Returns reminders whose notifications have not been sent."
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

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // DUE NOTIFICATIONS PENDING
    // ==========================================================

    @Operation(
            summary = "Get due reminders with pending notifications",
            description = "Returns due reminders whose notifications have not been sent."
    )
    @GetMapping("/notifications/due")
    public ResponseEntity<List<ReminderResponse>>
    getDueRemindersWithNotificationPending(

            @RequestParam(required = false)
            LocalDateTime dateTime) {

        if (dateTime == null) {
            dateTime =
                    LocalDateTime.now();
        }

        List<ReminderResponse> responses =
                reminderService
                        .getDueRemindersWithNotificationPending(
                                dateTime
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // NOTIFICATION SENT
    // ==========================================================

    @Operation(
            summary = "Get reminders with sent notifications",
            description = "Returns reminders whose notifications have been sent."
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

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // MARK NOTIFICATION AS SENT
    // ==========================================================

    @Operation(
            summary = "Mark notification as sent",
            description = "Marks the notification for a reminder as sent."
    )
    @PatchMapping("/{reminderId}/notification-sent")
    public ResponseEntity<ReminderResponse>
    markNotificationAsSent(

            @PathVariable Long reminderId) {

        Reminder reminder =
                reminderService
                        .markNotificationAsSent(
                                reminderId
                        );

        return ResponseEntity.ok(
                ReminderMapper.toResponse(
                        reminder
                )
        );
    }


    // ==========================================================
    // SEARCH
    // ==========================================================

    @Operation(
            summary = "Search reminders",
            description = "Searches reminders by title."
    )
    @GetMapping("/search")
    public ResponseEntity<List<ReminderResponse>>
    searchReminders(

            @RequestParam String title) {

        List<ReminderResponse> responses =
                reminderService
                        .searchRemindersByTitle(
                                title
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }


    // ==========================================================
    // SEARCH PENDING
    // ==========================================================

    @Operation(
            summary = "Search pending reminders",
            description = "Searches only pending reminders by title."
    )
    @GetMapping("/search/pending")
    public ResponseEntity<List<ReminderResponse>>
    searchPendingReminders(

            @RequestParam String title) {

        List<ReminderResponse> responses =
                reminderService
                        .searchPendingRemindersByTitle(
                                title
                        )
                        .stream()
                        .map(ReminderMapper::toResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                responses
        );
    }
}