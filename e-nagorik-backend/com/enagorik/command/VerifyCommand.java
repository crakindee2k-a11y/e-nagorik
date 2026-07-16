package com.enagorik.command;

import com.enagorik.model.Application;
import com.enagorik.model.ApplicationStatus;
import com.enagorik.model.StaffMember;
import com.enagorik.service.AuditService;

public class VerifyCommand implements ApplicationCommand
{
    private final Application application;
    private final StaffMember officer;
    private final AuditService auditService;
    private ApplicationStatus previousStatus;

    public VerifyCommand(Application application, StaffMember officer, AuditService auditService)
    {
        this.application = application;
        this.officer = officer;
        this.auditService = auditService;
    }

    @Override
    public void execute()
    {
        previousStatus = application.getStatus();
        application.updateStatus(ApplicationStatus.VERIFIED, "Verified by " + officer.getName());
        auditService.log(officer.getName(), "VERIFY", application.getApplicationNo());
    }

    @Override
    public void undo()
    {
        application.updateStatus(previousStatus, "Verification undone");
    }
}