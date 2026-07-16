package com.enagorik.command;

import com.enagorik.model.Application;
import com.enagorik.model.ApplicationStatus;
import com.enagorik.model.StaffMember;
import com.enagorik.service.AuditService;

public class RequestCorrectionCommand implements ApplicationCommand
{
    private final Application application;
    private final StaffMember officer;
    private final AuditService auditService;
    private ApplicationStatus previousStatus;

    public RequestCorrectionCommand(Application application, StaffMember officer, AuditService auditService)
    {
        this.application = application;
        this.officer = officer;
        this.auditService = auditService;
    }

    @Override
    public void execute()
    {
        previousStatus = application.getStatus();
        application.updateStatus(ApplicationStatus.CORRECTION_REQUESTED, "Correction requested by " + officer.getName());
        auditService.log(officer.getName(), "REQUEST_CORRECTION", application.getApplicationNo());
    }

    @Override
    public void undo()
    {
        application.updateStatus(previousStatus, "Correction request undone");
    }
}