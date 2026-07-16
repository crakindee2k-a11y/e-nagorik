package com.enagorik.command;

import com.enagorik.model.Application;
import com.enagorik.model.ApplicationStatus;
import com.enagorik.model.StaffMember;
import com.enagorik.service.AuditService;

public class RejectCommand implements ApplicationCommand
{
    private final Application application;
    private final StaffMember officer;
    private final String reason;
    private final AuditService auditService;
    private ApplicationStatus previousStatus;

    public RejectCommand(Application application, StaffMember officer, String reason, AuditService auditService)
    {
        this.application = application;
        this.officer = officer;
        this.reason = reason;
        this.auditService = auditService;
    }

    @Override
    public void execute()
    {
        previousStatus = application.getStatus();
        application.updateStatus(ApplicationStatus.REJECTED, reason);
        auditService.log(officer.getName(), "REJECT", application.getApplicationNo());
    }

    @Override
    public void undo()
    {
        application.updateStatus(previousStatus, "Rejection undone");
    }
}