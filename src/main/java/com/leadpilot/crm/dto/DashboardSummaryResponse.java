package com.leadpilot.crm.dto;

/**
 * ==========================================================
 * DTO : DashboardSummaryResponse
 *
 * Description :
 * Contains the summary statistics displayed on the dashboard.
 *
 * This DTO is shared by:
 *
 *  - Admin Dashboard
 *  - Executive Dashboard
 *
 * For ADMIN:
 *  The values represent organization-wide statistics.
 *
 * For EXECUTIVE:
 *  The values represent only the logged-in executive's
 *  assigned leads and activities.
 *
 * This is a DTO only.
 * It is NOT a database entity.
 * ==========================================================
 */
public class DashboardSummaryResponse {

    // ==========================================================
    // Lead Statistics
    // ==========================================================

    /**
     * Total number of leads.
     */
    private long totalLeads;

    /**
     * Number of active leads.
     */
    private long activeLeads;

    /**
     * Number of new leads created today.
     */
    private long newLeadsToday;

    /**
     * Number of pending leads.
     */
    private long pendingLeads;

    /**
     * Number of hot-priority leads.
     */
    private long hotLeads;

    /**
     * Number of successfully closed/won leads.
     */
    private long closedDeals;

    /**
     * Number of lost leads.
     */
    private long lostLeads;

    /**
     * Number of active executives.
     *
     * Primarily useful for the Admin Dashboard.
     */
    private long activeExecutives;

    // ==========================================================
    // Follow-Up Statistics
    // ==========================================================

    /**
     * Total number of follow-ups.
     */
    private long totalFollowUps;

    /**
     * Number of follow-ups scheduled for today.
     */
    private long todayFollowUps;

    /**
     * Number of overdue follow-ups.
     */
    private long overdueFollowUps;

    /**
     * Number of completed follow-ups.
     */
    private long completedFollowUps;

    // ==========================================================
    // Constructors
    // ==========================================================

    /**
     * Default constructor.
     */
    public DashboardSummaryResponse() {
    }

    /**
     * Parameterized constructor.
     *
     * @param totalLeads total leads
     * @param activeLeads active leads
     * @param newLeadsToday new leads created today
     * @param pendingLeads pending leads
     * @param hotLeads hot leads
     * @param closedDeals closed/won deals
     * @param lostLeads lost leads
     * @param activeExecutives active executives
     * @param totalFollowUps total follow-ups
     * @param todayFollowUps today's follow-ups
     * @param overdueFollowUps overdue follow-ups
     * @param completedFollowUps completed follow-ups
     */
    public DashboardSummaryResponse(
            long totalLeads,
            long activeLeads,
            long newLeadsToday,
            long pendingLeads,
            long hotLeads,
            long closedDeals,
            long lostLeads,
            long activeExecutives,
            long totalFollowUps,
            long todayFollowUps,
            long overdueFollowUps,
            long completedFollowUps
    ) {
        this.totalLeads = totalLeads;
        this.activeLeads = activeLeads;
        this.newLeadsToday = newLeadsToday;
        this.pendingLeads = pendingLeads;
        this.hotLeads = hotLeads;
        this.closedDeals = closedDeals;
        this.lostLeads = lostLeads;
        this.activeExecutives = activeExecutives;
        this.totalFollowUps = totalFollowUps;
        this.todayFollowUps = todayFollowUps;
        this.overdueFollowUps = overdueFollowUps;
        this.completedFollowUps = completedFollowUps;
    }

    // ==========================================================
    // Getters and Setters
    // ==========================================================

    public long getTotalLeads() {
        return totalLeads;
    }

    public void setTotalLeads(long totalLeads) {
        this.totalLeads = totalLeads;
    }

    public long getActiveLeads() {
        return activeLeads;
    }

    public void setActiveLeads(long activeLeads) {
        this.activeLeads = activeLeads;
    }

    public long getNewLeadsToday() {
        return newLeadsToday;
    }

    public void setNewLeadsToday(long newLeadsToday) {
        this.newLeadsToday = newLeadsToday;
    }

    public long getPendingLeads() {
        return pendingLeads;
    }

    public void setPendingLeads(long pendingLeads) {
        this.pendingLeads = pendingLeads;
    }

    public long getHotLeads() {
        return hotLeads;
    }

    public void setHotLeads(long hotLeads) {
        this.hotLeads = hotLeads;
    }

    public long getClosedDeals() {
        return closedDeals;
    }

    public void setClosedDeals(long closedDeals) {
        this.closedDeals = closedDeals;
    }

    public long getLostLeads() {
        return lostLeads;
    }

    public void setLostLeads(long lostLeads) {
        this.lostLeads = lostLeads;
    }

    public long getActiveExecutives() {
        return activeExecutives;
    }

    public void setActiveExecutives(long activeExecutives) {
        this.activeExecutives = activeExecutives;
    }

    public long getTotalFollowUps() {
        return totalFollowUps;
    }

    public void setTotalFollowUps(long totalFollowUps) {
        this.totalFollowUps = totalFollowUps;
    }

    public long getTodayFollowUps() {
        return todayFollowUps;
    }

    public void setTodayFollowUps(long todayFollowUps) {
        this.todayFollowUps = todayFollowUps;
    }

    public long getOverdueFollowUps() {
        return overdueFollowUps;
    }

    public void setOverdueFollowUps(long overdueFollowUps) {
        this.overdueFollowUps = overdueFollowUps;
    }

    public long getCompletedFollowUps() {
        return completedFollowUps;
    }

    public void setCompletedFollowUps(long completedFollowUps) {
        this.completedFollowUps = completedFollowUps;
    }
}