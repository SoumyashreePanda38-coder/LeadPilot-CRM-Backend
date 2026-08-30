package com.leadpilot.crm.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadpilot.crm.dto.AdminDashboardResponse;
import com.leadpilot.crm.dto.DashboardSummaryResponse;
import com.leadpilot.crm.dto.ExecutiveDashboardResponse;
import com.leadpilot.crm.dto.LeadPriorityChartResponse;
import com.leadpilot.crm.dto.LeadSourceChartResponse;
import com.leadpilot.crm.dto.LeadStatusChartResponse;
import com.leadpilot.crm.dto.MonthlyLeadChartResponse;
import com.leadpilot.crm.dto.OverdueFollowUpResponse;
import com.leadpilot.crm.dto.RecentLeadResponse;
import com.leadpilot.crm.dto.RecentNoteResponse;
import com.leadpilot.crm.dto.TodayFollowUpResponse;
import com.leadpilot.crm.dto.UpcomingVisitResponse;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.FollowUp;
import com.leadpilot.crm.entity.User;

import com.leadpilot.crm.enums.FollowUpStatus;
import com.leadpilot.crm.enums.FollowUpType;
import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;
import com.leadpilot.crm.enums.Role;

import com.leadpilot.crm.repository.CustomerLeadRepository;
import com.leadpilot.crm.repository.FollowUpRepository;
import com.leadpilot.crm.repository.LeadActivityRepository;
import com.leadpilot.crm.repository.LeadCategoryRepository;
import com.leadpilot.crm.repository.UserRepository;

import com.leadpilot.crm.service.DashboardService;


/**
 * ==========================================================
 * Service Implementation : DashboardServiceImpl
 *
 * Description :
 * Provides aggregated dashboard information for:
 *
 *  - ADMIN
 *  - EXECUTIVE
 *
 * The dashboard does not have its own database entity.
 * Data is collected from existing CRM entities.
 *
 * ==========================================================
 */
@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    // ==========================================================
    // Repository Dependencies
    // ==========================================================

    private final CustomerLeadRepository customerLeadRepository;

    private final FollowUpRepository followUpRepository;

    private final LeadActivityRepository leadActivityRepository;

    private final LeadCategoryRepository leadCategoryRepository;

    private final UserRepository userRepository;


    // ==========================================================
    // Date Formatter
    // ==========================================================

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    // ==========================================================
    // Constructor
    // ==========================================================

    public DashboardServiceImpl(
            CustomerLeadRepository customerLeadRepository,
            FollowUpRepository followUpRepository,
            LeadActivityRepository leadActivityRepository,
            LeadCategoryRepository leadCategoryRepository,
            UserRepository userRepository) {

        this.customerLeadRepository = customerLeadRepository;
        this.followUpRepository = followUpRepository;
        this.leadActivityRepository = leadActivityRepository;
        this.leadCategoryRepository = leadCategoryRepository;
        this.userRepository = userRepository;
    }


    // ==========================================================
    // ADMIN DASHBOARD
    // ==========================================================

    @Override
    public AdminDashboardResponse getAdminDashboard(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException(
                    "Admin user ID is required"
            );
        }

        User admin =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admin user not found with ID: "
                                                + userId
                                )
                        );

        List<CustomerLead> leads =
                customerLeadRepository.findAll();

        DashboardSummaryResponse summary =
                buildSummary(
                        leads,
                        null,
                        true
                );

        List<LeadStatusChartResponse> leadStatus =
                buildLeadStatusChart(leads);

        List<LeadPriorityChartResponse> leadPriority =
                buildLeadPriorityChart(leads);

        List<LeadSourceChartResponse> leadSource =
                buildLeadSourceChart(leads);

        List<MonthlyLeadChartResponse> monthlyLeads =
                buildMonthlyLeadChart(leads);

        List<RecentLeadResponse> recentLeads =
                buildRecentLeads(leads);

        List<TodayFollowUpResponse> todayFollowUps =
                buildTodayFollowUps(null);

        List<UpcomingVisitResponse> upcomingVisits =
                buildUpcomingVisits(null);

        /*
         * Recent notes will remain empty until the exact
         * Note entity/repository methods are connected.
         */

        return new AdminDashboardResponse(
                summary,
                leadStatus,
                leadPriority,
                leadSource,
                monthlyLeads,
                recentLeads,
                todayFollowUps,
                new ArrayList<>(),
                upcomingVisits
        );
    }


    // ==========================================================
    // EXECUTIVE DASHBOARD
    // ==========================================================

    @Override
    public ExecutiveDashboardResponse getExecutiveDashboard(
            Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException(
                    "Executive user ID is required"
            );
        }

        User executive =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Executive user not found with ID: "
                                                + userId
                                )
                        );

        List<CustomerLead> leads =
                customerLeadRepository
                        .findByAssignedUser(executive);

        DashboardSummaryResponse summary =
                buildSummary(
                        leads,
                        executive,
                        false
                );

        List<LeadStatusChartResponse> leadStatus =
                buildLeadStatusChart(leads);

        List<LeadPriorityChartResponse> leadPriority =
                buildLeadPriorityChart(leads);

        List<LeadSourceChartResponse> leadSource =
                buildLeadSourceChart(leads);

        List<MonthlyLeadChartResponse> monthlyLeads =
                buildMonthlyLeadChart(leads);

        List<RecentLeadResponse> recentLeads =
                buildRecentLeads(leads);

        List<TodayFollowUpResponse> todayFollowUps =
                buildTodayFollowUps(executive);

        List<RecentNoteResponse> recentNotes =
                new ArrayList<>();

        List<UpcomingVisitResponse> upcomingVisits =
                buildUpcomingVisits(executive);

        List<OverdueFollowUpResponse> overdueFollowUps =
                buildOverdueFollowUps(executive);

        return new ExecutiveDashboardResponse(
                summary,
                leadStatus,
                leadPriority,
                leadSource,
                monthlyLeads,
                recentLeads,
                todayFollowUps,
                recentNotes,
                upcomingVisits,
                overdueFollowUps
        );
    }

    // ==========================================================
    // DASHBOARD SUMMARY
    // ==========================================================

    private DashboardSummaryResponse buildSummary(
            List<CustomerLead> leads,
            User executive,
            boolean admin) {


        // ------------------------------------------------------
        // Lead Statistics
        // ------------------------------------------------------

        long totalLeads =
                leads.size();


        long activeLeads =
                leads.stream()
                        .filter(this::isActiveLead)
                        .count();


        LocalDate today =
                LocalDate.now();


        LocalDateTime startOfDay =
                today.atStartOfDay();


        LocalDateTime startOfNextDay =
                today.plusDays(1)
                        .atStartOfDay();


        long newLeadsToday =
                leads.stream()
                        .filter(lead ->
                                lead.getCreatedAt() != null
                                        &&
                                !lead.getCreatedAt()
                                        .isBefore(startOfDay)
                                        &&
                                lead.getCreatedAt()
                                        .isBefore(startOfNextDay)
                        )
                        .count();


        long pendingLeads =
                leads.stream()
                        .filter(lead ->
                                lead.getLeadStatus() != LeadStatus.WON
                                        &&
                                lead.getLeadStatus() != LeadStatus.LOST
                                        &&
                                lead.getLeadStatus() != LeadStatus.CLOSED
                        )
                        .count();


        long hotLeads =
                leads.stream()
                        .filter(lead ->
                                lead.getLeadPriority()
                                        == LeadPriority.HOT
                        )
                        .count();


        long closedDeals =
                leads.stream()
                        .filter(lead ->
                                lead.getLeadStatus()
                                        == LeadStatus.WON
                        )
                        .count();


        long lostLeads =
                leads.stream()
                        .filter(lead ->
                                lead.getLeadStatus()
                                        == LeadStatus.LOST
                        )
                        .count();


        // ------------------------------------------------------
        // Active Executives
        // ------------------------------------------------------

        long activeExecutives =
                admin
                        ? userRepository.countByRole(
                                Role.EXECUTIVE
                        )
                        : 0;


        // ------------------------------------------------------
        // Follow-Up Statistics
        // ------------------------------------------------------

        long totalFollowUps;

        long todayFollowUps;

        long overdueFollowUps;

        long completedFollowUps;


        if (admin) {

            totalFollowUps =
                    followUpRepository.count();


            completedFollowUps =
                    followUpRepository.countByStatus(
                            FollowUpStatus.COMPLETED
                    );


            todayFollowUps =
                    getTodayFollowUps(null).size();


            overdueFollowUps =
                    getOverdueFollowUps(null).size();

        } else {

            totalFollowUps =
                    followUpRepository
                            .countByAssignedUser(
                                    executive
                            );


            completedFollowUps =
                    followUpRepository
                            .countByAssignedUserAndStatus(
                                    executive,
                                    FollowUpStatus.COMPLETED
                            );


            todayFollowUps =
                    getTodayFollowUps(executive).size();


            overdueFollowUps =
                    getOverdueFollowUps(executive).size();
        }


        // ------------------------------------------------------
        // Create Summary DTO
        // ------------------------------------------------------

        return new DashboardSummaryResponse(
                totalLeads,
                activeLeads,
                newLeadsToday,
                pendingLeads,
                hotLeads,
                closedDeals,
                lostLeads,
                activeExecutives,
                totalFollowUps,
                todayFollowUps,
                overdueFollowUps,
                completedFollowUps
        );
    }


    // ==========================================================
    // ACTIVE LEAD CHECK
    // ==========================================================

    private boolean isActiveLead(
            CustomerLead lead) {

        if (lead == null ||
                lead.getLeadStatus() == null) {

            return false;
        }


        LeadStatus status =
                lead.getLeadStatus();


        return status != LeadStatus.WON
                && status != LeadStatus.LOST
                && status != LeadStatus.CLOSED;
    }


    // ==========================================================
    // LEAD STATUS CHART
    // ==========================================================

    private List<LeadStatusChartResponse>
            buildLeadStatusChart(
                    List<CustomerLead> leads) {

        Map<String, Long> counts =
                new LinkedHashMap<>();


        for (CustomerLead lead : leads) {

            if (lead.getLeadStatus() == null) {
                continue;
            }


            String status =
                    lead.getLeadStatus().name();


            counts.put(
                    status,
                    counts.getOrDefault(status, 0L) + 1
            );
        }


        return counts.entrySet()
                .stream()
                .map(entry ->
                        new LeadStatusChartResponse(
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .toList();
    }


    // ==========================================================
    // LEAD PRIORITY CHART
    // ==========================================================

    private List<LeadPriorityChartResponse>
            buildLeadPriorityChart(
                    List<CustomerLead> leads) {

        Map<String, Long> counts =
                new LinkedHashMap<>();


        for (CustomerLead lead : leads) {

            if (lead.getLeadPriority() == null) {
                continue;
            }


            String priority =
                    lead.getLeadPriority().name();


            counts.put(
                    priority,
                    counts.getOrDefault(priority, 0L) + 1
            );
        }


        return counts.entrySet()
                .stream()
                .map(entry ->
                        new LeadPriorityChartResponse(
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .toList();
    }


    // ==========================================================
    // LEAD SOURCE CHART
    // ==========================================================

    private List<LeadSourceChartResponse>
            buildLeadSourceChart(
                    List<CustomerLead> leads) {

        Map<String, Long> counts =
                new LinkedHashMap<>();


        /*
         * IMPORTANT:
         *
         * The exact type of CustomerLead's source field is not
         * confirmed in the supplied material.
         *
         * If CustomerLead contains:
         *
         *     private LeadSource leadSource;
         *
         * then use:
         *
         * String source =
         *     lead.getLeadSource().getSourceName();
         *
         * If it is an enum, use:
         *
         * String source =
         *     lead.getLeadSource().name();
         *
         * Do not guess this field until your exact
         * CustomerLead.java is confirmed.
         */


        return counts.entrySet()
                .stream()
                .map(entry ->
                        new LeadSourceChartResponse(
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .toList();
    }


    // ==========================================================
    // MONTHLY LEAD CHART
    // ==========================================================

    private List<MonthlyLeadChartResponse>
            buildMonthlyLeadChart(
                    List<CustomerLead> leads) {

        Map<String, Long> counts =
                new LinkedHashMap<>();


        for (CustomerLead lead : leads) {

            if (lead.getCreatedAt() == null) {
                continue;
            }


            String month =
                    lead.getCreatedAt().getYear()
                            + "-"
                            + String.format(
                                    "%02d",
                                    lead.getCreatedAt()
                                            .getMonthValue()
                            );


            counts.put(
                    month,
                    counts.getOrDefault(month, 0L) + 1
            );
        }


        return counts.entrySet()
                .stream()
                .map(entry ->
                        new MonthlyLeadChartResponse(
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .toList();
    }


    // ==========================================================
    // RECENT LEADS
    // ==========================================================

    private List<RecentLeadResponse>
            buildRecentLeads(
                    List<CustomerLead> leads) {

        return leads.stream()
                .filter(lead ->
                        lead.getCreatedAt() != null
                )
                .sorted(
                        Comparator.comparing(
                                CustomerLead::getCreatedAt
                        ).reversed()
                )
                .limit(10)
                .map(this::toRecentLeadResponse)
                .toList();
    }


    // ==========================================================
    // RECENT LEAD MAPPER
    // ==========================================================

    private RecentLeadResponse
            toRecentLeadResponse(
                    CustomerLead lead) {

        String assignedExecutive = null;


        if (lead.getAssignedUser() != null) {

            assignedExecutive =
                    lead.getAssignedUser()
                            .getFullName();
        }


        return new RecentLeadResponse(
                lead.getLeadId(),
                getLeadName(lead),
                lead.getLeadStatus() != null
                        ? lead.getLeadStatus().name()
                        : null,
                lead.getLeadPriority() != null
                        ? lead.getLeadPriority().name()
                        : null,
                assignedExecutive,
                formatDateTime(
                        lead.getCreatedAt()
                )
        );
    }


    // ==========================================================
    // TODAY'S FOLLOW-UPS
    // ==========================================================

    private List<TodayFollowUpResponse>
            buildTodayFollowUps(
                    User executive) {

        return getTodayFollowUps(executive)
                .stream()
                .limit(10)
                .map(this::toTodayFollowUpResponse)
                .toList();
    }


    // ==========================================================
    // GET TODAY FOLLOW-UPS
    // ==========================================================

    private List<FollowUp>
            getTodayFollowUps(
                    User executive) {

        LocalDate today =
                LocalDate.now();


        LocalDateTime start =
                today.atStartOfDay();


        LocalDateTime end =
                today.plusDays(1)
                        .atStartOfDay();


        if (executive == null) {

            return followUpRepository
                    .findByScheduledAtGreaterThanEqualAndScheduledAtLessThanOrderByScheduledAtAsc(
                            start,
                            end
                    );
        }


        return followUpRepository
                .findByAssignedUserAndScheduledAtGreaterThanEqualAndScheduledAtLessThanOrderByScheduledAtAsc(
                        executive,
                        start,
                        end
                );
    }


    // ==========================================================
    // TODAY FOLLOW-UP MAPPER
    // ==========================================================

    private TodayFollowUpResponse
            toTodayFollowUpResponse(
                    FollowUp followUp) {

        String assignedExecutive = null;


        if (followUp.getAssignedUser() != null) {

            assignedExecutive =
                    followUp.getAssignedUser()
                            .getFullName();
        }


        return new TodayFollowUpResponse(
                followUp.getFollowUpId(),
                getLeadId(followUp),
                getLeadName(
                        followUp.getCustomerLead()
                ),
                followUp.getFollowUpType() != null
                        ? followUp.getFollowUpType().name()
                        : null,
                formatDateTime(
                        followUp.getScheduledAt()
                ),
                followUp.getStatus() != null
                        ? followUp.getStatus().name()
                        : null,
                assignedExecutive
        );
    }


    // ==========================================================
    // UPCOMING VISITS
    // ==========================================================

    private List<UpcomingVisitResponse>
            buildUpcomingVisits(
                    User executive) {

        List<FollowUp> followUps;


        LocalDateTime now =
                LocalDateTime.now();


        if (executive == null) {

            followUps =
                    followUpRepository
                            .findByScheduledAtGreaterThanEqualAndStatusNotOrderByScheduledAtAsc(
                                    now,
                                    FollowUpStatus.CANCELLED
                            );

        } else {

            followUps =
                    followUpRepository
                            .findByAssignedUserAndScheduledAtGreaterThanEqualAndStatusNotOrderByScheduledAtAsc(
                                    executive,
                                    now,
                                    FollowUpStatus.CANCELLED
                            );
        }


        return followUps.stream()
                .filter(this::isVisit)
                .limit(10)
                .map(this::toUpcomingVisitResponse)
                .toList();
    }


    // ==========================================================
    // CHECK VISIT
    // ==========================================================

    private boolean isVisit(
            FollowUp followUp) {

        return followUp != null
                && followUp.getFollowUpType()
                        == FollowUpType.VISIT;
    }


    // ==========================================================
    // UPCOMING VISIT MAPPER
    // ==========================================================

    private UpcomingVisitResponse
            toUpcomingVisitResponse(
                    FollowUp followUp) {

        String assignedExecutive = null;


        if (followUp.getAssignedUser() != null) {

            assignedExecutive =
                    followUp.getAssignedUser()
                            .getFullName();
        }


        return new UpcomingVisitResponse(
                followUp.getFollowUpId(),
                getLeadId(followUp),
                getLeadName(
                        followUp.getCustomerLead()
                ),
                formatDateTime(
                        followUp.getScheduledAt()
                ),
                followUp.getLocation(),
                assignedExecutive,
                followUp.getStatus() != null
                        ? followUp.getStatus().name()
                        : null
        );
    }


    // ==========================================================
    // OVERDUE FOLLOW-UPS
    // ==========================================================

    private List<OverdueFollowUpResponse>
            buildOverdueFollowUps(
                    User executive) {

        return getOverdueFollowUps(executive)
                .stream()
                .limit(10)
                .map(this::toOverdueFollowUpResponse)
                .toList();
    }


    // ==========================================================
    // GET OVERDUE FOLLOW-UPS
    // ==========================================================

    private List<FollowUp>
            getOverdueFollowUps(
                    User executive) {

        LocalDateTime now =
                LocalDateTime.now();


        if (executive == null) {

            return followUpRepository
                    .findByScheduledAtLessThanAndStatusOrderByScheduledAtAsc(
                            now,
                            FollowUpStatus.SCHEDULED
                    );
        }


        return followUpRepository
                .findByAssignedUserAndScheduledAtLessThanAndStatusOrderByScheduledAtAsc(
                        executive,
                        now,
                        FollowUpStatus.SCHEDULED
                );
    }


    // ==========================================================
    // OVERDUE FOLLOW-UP MAPPER
    // ==========================================================

    private OverdueFollowUpResponse
            toOverdueFollowUpResponse(
                    FollowUp followUp) {

        String assignedExecutive = null;


        if (followUp.getAssignedUser() != null) {

            assignedExecutive =
                    followUp.getAssignedUser()
                            .getFullName();
        }


        return new OverdueFollowUpResponse(
                followUp.getFollowUpId(),
                getLeadId(followUp),
                getLeadName(
                        followUp.getCustomerLead()
                ),
                followUp.getFollowUpType() != null
                        ? followUp.getFollowUpType().name()
                        : null,
                formatDateTime(
                        followUp.getScheduledAt()
                ),
                followUp.getStatus() != null
                        ? followUp.getStatus().name()
                        : null,
                assignedExecutive
        );
    }


    // ==========================================================
    // LEAD ID HELPER
    // ==========================================================

    private Long getLeadId(
            FollowUp followUp) {

        if (followUp == null
                || followUp.getCustomerLead() == null) {

            return null;
        }


        return followUp
                .getCustomerLead()
                .getLeadId();
    }


    // ==========================================================
    // LEAD NAME HELPER
    // ==========================================================

    private String getLeadName(
            CustomerLead lead) {

        if (lead == null) {
            return null;
        }


        /*
         * Use the actual name field from CustomerLead.
         *
         * This is kept isolated in one helper so if your
         * CustomerLead entity uses a different customer-name
         * property, only this method needs to change.
         */


        return lead.getFullName();
    }


    // ==========================================================
    // DATE FORMATTER HELPER
    // ==========================================================

    private String formatDateTime(
            LocalDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }


        return dateTime.format(
                DATE_TIME_FORMATTER
        );
    }
}