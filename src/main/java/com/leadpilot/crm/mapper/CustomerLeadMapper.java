package com.leadpilot.crm.mapper;

import org.springframework.stereotype.Component;

import com.leadpilot.crm.dto.CustomerLeadResponse;
import com.leadpilot.crm.entity.CustomerLead;

/**
 * ==========================================================
 * Mapper : CustomerLeadMapper
 *
 * Description :
 * Converts CustomerLead Entity into CustomerLeadResponse DTO.
 * ==========================================================
 */

@Component
public class CustomerLeadMapper {

    /**
     * ==========================================================
     * Convert CustomerLead Entity to CustomerLeadResponse
     * ==========================================================
     */
    public CustomerLeadResponse toResponse(CustomerLead lead) {

        if (lead == null) {
            return null;
        }

        CustomerLeadResponse response = new CustomerLeadResponse();

        // ======================================================
        // Primary Key
        // ======================================================

        response.setLeadId(lead.getLeadId());

        // ======================================================
        // Personal Information
        // ======================================================

        response.setFullName(lead.getFullName());
        response.setAge(lead.getAge());

        // ======================================================
        // Contact Information
        // ======================================================

        response.setEmail(lead.getEmail());
        response.setPhoneNumber(lead.getPhoneNumber());

        // ======================================================
        // Address Information
        // ======================================================

        response.setAddress(lead.getAddress());
        response.setCity(lead.getCity());
        response.setState(lead.getState());
        response.setPincode(lead.getPincode());

        // ======================================================
        // Lead Category
        // ======================================================

        if (lead.getLeadCategory() != null) {

            response.setCategoryId(
                    lead.getLeadCategory().getCategoryId()
            );

            response.setCategoryName(
                    lead.getLeadCategory().getCategoryName()
            );
        }

        // ======================================================
        // Lead Subcategory
        // ======================================================

        if (lead.getLeadSubCategory() != null) {

            response.setSubCategoryId(
                    lead.getLeadSubCategory().getSubCategoryId()
            );

            response.setSubCategoryName(
                    lead.getLeadSubCategory().getSubCategoryName()
            );
        }

        // ======================================================
        // Lead Source
        // ======================================================

        if (lead.getLeadSource() != null) {

            response.setLeadSourceId(
                    lead.getLeadSource().getLeadSourceId()
            );

            response.setSourceName(
                    lead.getLeadSource().getSourceName()
            );
        }

        // ======================================================
        // Lead Status
        // ======================================================

        response.setLeadStatus(
                lead.getLeadStatus()
        );

        // ======================================================
        // Lead Priority
        // ======================================================

        response.setLeadPriority(
                lead.getLeadPriority()
        );

        // ======================================================
        // Assigned Executive
        // ======================================================

        if (lead.getAssignedUser() != null) {

            response.setAssignedUserId(
                    lead.getAssignedUser().getId()
            );

            response.setAssignedUserName(
                    lead.getAssignedUser().getFullName()
            );
        }

        // ======================================================
        // Created By
        // ======================================================

        if (lead.getCreatedBy() != null) {

            response.setCreatedById(
                    lead.getCreatedBy().getId()
            );

            response.setCreatedByName(
                    lead.getCreatedBy().getFullName()
            );
        }

        // ======================================================
        // Updated By
        // ======================================================

        if (lead.getUpdatedBy() != null) {

            response.setUpdatedById(
                    lead.getUpdatedBy().getId()
            );

            response.setUpdatedByName(
                    lead.getUpdatedBy().getFullName()
            );
        }

        // ======================================================
        // Audit Timestamps
        // ======================================================

        response.setCreatedAt(
                lead.getCreatedAt()
        );

        response.setUpdatedAt(
                lead.getUpdatedAt()
        );

        return response;
    }
}