package com.leadpilot.crm.dto;

import java.util.List;

/**
 * ==========================================================
 * DTO : AdminDashboardResponse
 *
 * Description :
 * Represents the complete dashboard response for an ADMIN.
 *
 * The Admin Dashboard contains organization-wide CRM
 * information including:
 *
 * - Dashboard summary
 * - Lead status chart
 * - Lead priority chart
 * - Lead source chart
 * - Monthly lead statistics
 * - Recent leads
 * - Today's follow-ups
 * - Recent notes
 * - Upcoming visits
 *
 * This DTO does not represent a database entity.
 * It is only used to transfer aggregated dashboard data
 * from the backend to the frontend.
 *
 * ==========================================================
 */
public class AdminDashboardResponse {

    // ==========================================================
    // DASHBOARD SUMMARY
    // ==========================================================

    private DashboardSummaryResponse summary;


    // ==========================================================
    // LEAD STATUS CHART
    // ==========================================================

    private List<LeadStatusChartResponse> leadStatus;


    // ==========================================================
    // LEAD PRIORITY CHART
    // ==========================================================

    private List<LeadPriorityChartResponse> leadPriority;


    // ==========================================================
    // LEAD SOURCE CHART
    // ==========================================================

    private List<LeadSourceChartResponse> leadSource;


    // ==========================================================
    // MONTHLY LEAD CHART
    // ==========================================================

    private List<MonthlyLeadChartResponse> monthlyLeads;


    // ==========================================================
    // RECENT LEADS
    // ==========================================================

    private List<RecentLeadResponse> recentLeads;


    // ==========================================================
    // TODAY'S FOLLOW-UPS
    // ==========================================================

    private List<TodayFollowUpResponse> todayFollowUps;


    // ==========================================================
    // RECENT NOTES
    // ==========================================================

    private List<RecentNoteResponse> recentNotes;


    // ==========================================================
    // UPCOMING VISITS
    // ==========================================================

    private List<UpcomingVisitResponse> upcomingVisits;


    // ==========================================================
    // DEFAULT CONSTRUCTOR
    // ==========================================================

    public AdminDashboardResponse() {
    }


    // ==========================================================
    // PARAMETERIZED CONSTRUCTOR
    // ==========================================================

    public AdminDashboardResponse(
            DashboardSummaryResponse summary,
            List<LeadStatusChartResponse> leadStatus,
            List<LeadPriorityChartResponse> leadPriority,
            List<LeadSourceChartResponse> leadSource,
            List<MonthlyLeadChartResponse> monthlyLeads,
            List<RecentLeadResponse> recentLeads,
            List<TodayFollowUpResponse> todayFollowUps,
            List<RecentNoteResponse> recentNotes,
            List<UpcomingVisitResponse> upcomingVisits
    ) {
        this.summary = summary;
        this.leadStatus = leadStatus;
        this.leadPriority = leadPriority;
        this.leadSource = leadSource;
        this.monthlyLeads = monthlyLeads;
        this.recentLeads = recentLeads;
        this.todayFollowUps = todayFollowUps;
        this.recentNotes = recentNotes;
        this.upcomingVisits = upcomingVisits;
    }


    // ==========================================================
    // GETTERS AND SETTERS
    // ==========================================================

    public DashboardSummaryResponse getSummary() {
        return summary;
    }

    public void setSummary(DashboardSummaryResponse summary) {
        this.summary = summary;
    }


    public List<LeadStatusChartResponse> getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(
            List<LeadStatusChartResponse> leadStatus
    ) {
        this.leadStatus = leadStatus;
    }


    public List<LeadPriorityChartResponse> getLeadPriority() {
        return leadPriority;
    }

    public void setLeadPriority(
            List<LeadPriorityChartResponse> leadPriority
    ) {
        this.leadPriority = leadPriority;
    }


    public List<LeadSourceChartResponse> getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(
            List<LeadSourceChartResponse> leadSource
    ) {
        this.leadSource = leadSource;
    }


    public List<MonthlyLeadChartResponse> getMonthlyLeads() {
        return monthlyLeads;
    }

    public void setMonthlyLeads(
            List<MonthlyLeadChartResponse> monthlyLeads
    ) {
        this.monthlyLeads = monthlyLeads;
    }


    public List<RecentLeadResponse> getRecentLeads() {
        return recentLeads;
    }

    public void setRecentLeads(
            List<RecentLeadResponse> recentLeads
    ) {
        this.recentLeads = recentLeads;
    }


    public List<TodayFollowUpResponse> getTodayFollowUps() {
        return todayFollowUps;
    }

    public void setTodayFollowUps(
            List<TodayFollowUpResponse> todayFollowUps
    ) {
        this.todayFollowUps = todayFollowUps;
    }


    public List<RecentNoteResponse> getRecentNotes() {
        return recentNotes;
    }

    public void setRecentNotes(
            List<RecentNoteResponse> recentNotes
    ) {
        this.recentNotes = recentNotes;
    }


    public List<UpcomingVisitResponse> getUpcomingVisits() {
        return upcomingVisits;
    }

    public void setUpcomingVisits(
            List<UpcomingVisitResponse> upcomingVisits
    ) {
        this.upcomingVisits = upcomingVisits;
    }
}