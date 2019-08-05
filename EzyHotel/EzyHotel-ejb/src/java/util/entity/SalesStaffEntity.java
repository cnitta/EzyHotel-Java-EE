/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author vincentyeo
 */
@Entity
@DiscriminatorValue("SalesStaffEntity")
public class SalesStaffEntity extends StaffEntity implements Serializable {
   
    @OneToMany(mappedBy = "salesStaff")
    private List<ConfirmationEmailEntity> confirmationEmails;
    
    @OneToMany(mappedBy = "salesStaff")
    private List<PublicRelationProgramEntity> publicRelationPrograms;
    
    @OneToMany(mappedBy = "salesStaff")
    private List<SalesCallResultEntity> salesCallResults;
    
    @OneToMany(mappedBy = "salesStaff")
    private List<SalesCallGuidelineEntity> salesCallGuidelines;
    
    @OneToMany(mappedBy = "salesStaff")
    private List<CallReportEntity> callReports;
    
    @OneToMany(mappedBy = "salesStaff")
    private List<CustomerMailingEntity> customerMailings;
    
    @OneToMany(mappedBy = "salesStaff")
    private List<ConventionInstructionMemoEntity> conventionInstructionMemos;

    public SalesStaffEntity(List<ConfirmationEmailEntity> confirmationEmails, List<PublicRelationProgramEntity> publicRelationPrograms, List<SalesCallResultEntity> salesCallResults, List<SalesCallGuidelineEntity> salesCallGuidelines, List<CallReportEntity> callReports, List<CustomerMailingEntity> customerMailings, List<ConventionInstructionMemoEntity> conventionInstructionMemos) {
        this.confirmationEmails = confirmationEmails;
        this.publicRelationPrograms = publicRelationPrograms;
        this.salesCallResults = salesCallResults;
        this.salesCallGuidelines = salesCallGuidelines;
        this.callReports = callReports;
        this.customerMailings = customerMailings;
        this.conventionInstructionMemos = conventionInstructionMemos;
    }

    public SalesStaffEntity() {
        confirmationEmails = new ArrayList<>();
        publicRelationPrograms = new ArrayList<>();
        salesCallResults = new ArrayList<>();
        salesCallGuidelines = new ArrayList<>();
        callReports = new ArrayList<>();
        customerMailings = new ArrayList<>();
        conventionInstructionMemos = new ArrayList<>();
    }

    public List<ConfirmationEmailEntity> getConfirmationEmails() {
        return confirmationEmails;
    }

    public void setConfirmationEmails(List<ConfirmationEmailEntity> confirmationEmails) {
        this.confirmationEmails = confirmationEmails;
    }

    public List<PublicRelationProgramEntity> getPublicRelationPrograms() {
        return publicRelationPrograms;
    }

    public void setPublicRelationPrograms(List<PublicRelationProgramEntity> publicRelationPrograms) {
        this.publicRelationPrograms = publicRelationPrograms;
    }

    public List<SalesCallResultEntity> getSalesCallResults() {
        return salesCallResults;
    }

    public void setSalesCallResults(List<SalesCallResultEntity> salesCallResults) {
        this.salesCallResults = salesCallResults;
    }

    public List<SalesCallGuidelineEntity> getSalesCallGuidelines() {
        return salesCallGuidelines;
    }

    public void setSalesCallGuidelines(List<SalesCallGuidelineEntity> salesCallGuidelines) {
        this.salesCallGuidelines = salesCallGuidelines;
    }

    public List<CallReportEntity> getCallReports() {
        return callReports;
    }

    public void setCallReports(List<CallReportEntity> callReports) {
        this.callReports = callReports;
    }

    public List<CustomerMailingEntity> getCustomerMailings() {
        return customerMailings;
    }

    public void setCustomerMailings(List<CustomerMailingEntity> customerMailings) {
        this.customerMailings = customerMailings;
    }

    public List<ConventionInstructionMemoEntity> getConventionInstructionMemos() {
        return conventionInstructionMemos;
    }

    public void setConventionInstructionMemos(List<ConventionInstructionMemoEntity> conventionInstructionMemos) {
        this.conventionInstructionMemos = conventionInstructionMemos;
    }
    
    
    
    
    
}
