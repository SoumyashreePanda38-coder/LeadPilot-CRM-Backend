package com.leadpilot.crm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadpilot.crm.entity.CustomerLead;
import com.leadpilot.crm.entity.LeadCategory;
import com.leadpilot.crm.entity.LeadSource;
import com.leadpilot.crm.entity.LeadSubCategory;
import com.leadpilot.crm.entity.User;
import com.leadpilot.crm.enums.LeadPriority;
import com.leadpilot.crm.enums.LeadStatus;
import com.leadpilot.crm.repository.CustomerLeadRepository;
import com.leadpilot.crm.repository.UserRepository;
import com.leadpilot.crm.service.CustomerLeadService;

/**
 * ==========================================================
 * Service Implementation : CustomerLeadServiceImpl
 *
 * Description :
 * Implements business operations related to CustomerLead.
 *
 * Handles:
 *
 * - Create Lead
 * - View All Leads
 * - View Lead By ID
 * - Update Lead
 * - Delete Lead
 * - Lead Filtering
 * - Lead Assignment
 * - Lead Unassignment
 * - Category / Subcategory / Source filtering
 *
 * ==========================================================
 */

@Service
public class CustomerLeadServiceImpl implements CustomerLeadService {

    @Autowired
    private CustomerLeadRepository customerLeadRepository;

    @Autowired
    private UserRepository userRepository;


    // ==========================================================
    // Create Lead
    // ==========================================================

    @Override
    public CustomerLead createLead(CustomerLead customerLead) {

        return customerLeadRepository.save(customerLead);
    }


    // ==========================================================
    // Get All Leads
    // ==========================================================

    @Override
    public List<CustomerLead> getAllLeads() {

        return customerLeadRepository.findAll();
    }


    // ==========================================================
    // Get Lead By ID
    // ==========================================================

    @Override
    public CustomerLead getLeadById(Long leadId) {

        return customerLeadRepository.findById(leadId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead not found with ID: " + leadId
                        )
                );
    }


    // ==========================================================
    // Update Lead
    // ==========================================================

    @Override
    public CustomerLead updateLead(
            Long leadId,
            CustomerLead customerLead) {

        CustomerLead existingLead =
                customerLeadRepository.findById(leadId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead not found with ID: " + leadId
                        )
                );

        // ======================================================
        // Update Lead Information
        // ======================================================

        existingLead.setFullName(
                customerLead.getFullName()
        );

        existingLead.setAge(
                customerLead.getAge()
        );

        existingLead.setEmail(
                customerLead.getEmail()
        );

        existingLead.setPhoneNumber(
                customerLead.getPhoneNumber()
        );

        existingLead.setAddress(
                customerLead.getAddress()
        );

        existingLead.setCity(
                customerLead.getCity()
        );

        existingLead.setState(
                customerLead.getState()
        );

        existingLead.setPincode(
                customerLead.getPincode()
        );

        existingLead.setLeadCategory(
                customerLead.getLeadCategory()
        );

        existingLead.setLeadSubCategory(
                customerLead.getLeadSubCategory()
        );

        existingLead.setLeadSource(
                customerLead.getLeadSource()
        );

        existingLead.setLeadStatus(
                customerLead.getLeadStatus()
        );

        existingLead.setLeadPriority(
                customerLead.getLeadPriority()
        );

        existingLead.setAssignedUser(
                customerLead.getAssignedUser()
        );

        return customerLeadRepository.save(existingLead);
    }


    // ==========================================================
    // Delete Lead
    // ==========================================================

    @Override
    public void deleteLead(Long leadId) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead not found with ID: " + leadId
                        )
                );

        customerLeadRepository.delete(customerLead);
    }


    // ==========================================================
    // Get Leads By Category ID
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByCategoryId(
            Long categoryId) {

        return customerLeadRepository
                .findByLeadCategory_CategoryId(categoryId);
    }


    // ==========================================================
    // Get Leads By Subcategory ID
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsBySubCategoryId(
            Long subCategoryId) {

        return customerLeadRepository
                .findByLeadSubCategory_SubCategoryId(
                        subCategoryId
                );
    }


    // ==========================================================
    // Get Leads By Source ID
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsBySourceId(
            Long sourceId) {

        return customerLeadRepository
                .findByLeadSource_LeadSourceId(
                        sourceId
                );
    }


    // ==========================================================
    // Assigned Executive / User
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByAssignedUser(
            User assignedUser) {

        return customerLeadRepository.findByAssignedUser(
                assignedUser
        );
    }


    // ==========================================================
    // Lead Status
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByStatus(
            LeadStatus leadStatus) {

        return customerLeadRepository.findByLeadStatus(
                leadStatus
        );
    }


    // ==========================================================
    // Lead Priority
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByPriority(
            LeadPriority leadPriority) {

        return customerLeadRepository.findByLeadPriority(
                leadPriority
        );
    }


    // ==========================================================
    // Lead Category
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByCategory(
            LeadCategory leadCategory) {

        return customerLeadRepository.findByLeadCategory(
                leadCategory
        );
    }


    // ==========================================================
    // Lead Subcategory
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsBySubCategory(
            LeadSubCategory leadSubCategory) {

        return customerLeadRepository.findByLeadSubCategory(
                leadSubCategory
        );
    }


    // ==========================================================
    // Lead Source
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsBySource(
            LeadSource leadSource) {

        return customerLeadRepository.findByLeadSource(
                leadSource
        );
    }


    // ==========================================================
    // Status + Priority
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByStatusAndPriority(
            LeadStatus leadStatus,
            LeadPriority leadPriority) {

        return customerLeadRepository
                .findByLeadStatusAndLeadPriority(
                        leadStatus,
                        leadPriority
                );
    }


    // ==========================================================
    // Category + Subcategory
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByCategoryAndSubCategory(
            LeadCategory leadCategory,
            LeadSubCategory leadSubCategory) {

        return customerLeadRepository
                .findByLeadCategoryAndLeadSubCategory(
                        leadCategory,
                        leadSubCategory
                );
    }


    // ==========================================================
    // Assigned User + Status
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByAssignedUserAndStatus(
            User assignedUser,
            LeadStatus leadStatus) {

        return customerLeadRepository
                .findByAssignedUserAndLeadStatus(
                        assignedUser,
                        leadStatus
                );
    }


    // ==========================================================
    // Assigned User + Priority
    // ==========================================================

    @Override
    public List<CustomerLead> getLeadsByAssignedUserAndPriority(
            User assignedUser,
            LeadPriority leadPriority) {

        return customerLeadRepository
                .findByAssignedUserAndLeadPriority(
                        assignedUser,
                        leadPriority
                );
    }


    // ==========================================================
    // Assign Lead
    // ==========================================================

    @Override
    public CustomerLead assignLead(
            Long leadId,
            Long assignedUserId) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead not found with ID: " + leadId
                        )
                );

        User assignedUser =
                userRepository.findById(assignedUserId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: "
                                + assignedUserId
                        )
                );

        customerLead.setAssignedUser(assignedUser);

        return customerLeadRepository.save(customerLead);
    }


    // ==========================================================
    // Unassign Lead
    // ==========================================================

    @Override
    public CustomerLead unassignLead(
            Long leadId) {

        CustomerLead customerLead =
                customerLeadRepository.findById(leadId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lead not found with ID: " + leadId
                        )
                );

        customerLead.setAssignedUser(null);

        return customerLeadRepository.save(customerLead);
    }
}