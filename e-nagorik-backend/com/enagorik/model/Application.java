package com.enagorik.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Composition over isolation: every child collection (documents, history)
 * is owned per-instance, so two applications never share data by accident.
 * Builder pattern keeps the constructor free of a long parameter list.
 */
public class Application implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final String applicationNo;
    private final Citizen citizen;
    private final Service service;
    private final LocalDateTime submittedAt;
    private ApplicationStatus status;
    private String currentDesk;

    private final List<Document> documents = new ArrayList<>();
    private final List<StatusHistory> history = new ArrayList<>();
    private Payment payment;
    private Certificate certificate;
    private StaffMember assignedStaff;

    private Application(Builder builder)
    {
        this.applicationNo = builder.applicationNo;
        this.citizen = builder.citizen;
        this.service = builder.service;
        this.submittedAt = LocalDateTime.now();
        this.status = ApplicationStatus.SUBMITTED;
        this.currentDesk = "Helpdesk";
        this.documents.addAll(builder.documents);
    }

    public void updateStatus(ApplicationStatus newStatus, String comment)
    {
        history.add(new StatusHistory(history.size() + 1, status, newStatus, comment));
        this.status = newStatus;
    }

    public void assignStaff(StaffMember staff)
    {
        this.assignedStaff = staff;
        this.currentDesk = staff.getDesignation();
    }

    public void attachPayment(Payment payment)
    {
        this.payment = payment;
    }

    public void attachCertificate(Certificate certificate)
    {
        this.certificate = certificate;
    }

    public String getApplicationNo()
    {
        return applicationNo;
    }

    public Citizen getCitizen()
    {
        return citizen;
    }

    public Service getService()
    {
        return service;
    }

    public LocalDateTime getSubmittedAt()
    {
        return submittedAt;
    }

    public ApplicationStatus getStatus()
    {
        return status;
    }

    public List<StatusHistory> getHistory()
    {
        return history;
    }

    public Payment getPayment()
    {
        return payment;
    }

    public Certificate getCertificate()
    {
        return certificate;
    }

    public static class Builder
    {
        private final String applicationNo;
        private final Citizen citizen;
        private final Service service;
        private final List<Document> documents = new ArrayList<>();

        public Builder(String applicationNo, Citizen citizen, Service service)
        {
            this.applicationNo = applicationNo;
            this.citizen = citizen;
            this.service = service;
        }

        public Builder addDocument(Document document)
        {
            documents.add(document);
            return this;
        }

        public Application build()
        {
            return new Application(this);
        }
    }
}