package com.enagorik.command;

import com.enagorik.exception.ENagorikException;
import com.enagorik.model.Application;
import com.enagorik.model.ApplicationStatus;
import com.enagorik.model.Certificate;
import com.enagorik.model.StaffMember;
import com.enagorik.service.AuditService;
import com.enagorik.service.OtpService;
import com.enagorik.singleton.IdGenerator;

/** Mayor-only, OTP-gated approval. Only a VERIFIED application can reach here. */
public class ApproveCommand implements ApplicationCommand
{
    private final Application application;
    private final StaffMember mayor;
    private final String submittedOtp;
    private final OtpService otpService;
    private final AuditService auditService;
    private ApplicationStatus previousStatus;

    public ApproveCommand(Application application, StaffMember mayor, String submittedOtp, OtpService otpService, AuditService auditService)
    {
        this.application = application;
        this.mayor = mayor;
        this.submittedOtp = submittedOtp;
        this.otpService = otpService;
        this.auditService = auditService;
    }

    @Override
    public void execute()
    {
        guardAgainstUnverifiedApplication();
        guardAgainstInvalidOtp();

        previousStatus = application.getStatus();
        application.updateStatus(ApplicationStatus.APPROVED, "Approved by Mayor");

        String certNo = IdGenerator.getInstance().nextCertificateNo();
        application.attachCertificate(new Certificate(certNo, application.getApplicationNo()));

        auditService.log(mayor.getName(), "APPROVE", application.getApplicationNo());
    }

    private void guardAgainstUnverifiedApplication()
    {
        if (application.getStatus() != ApplicationStatus.VERIFIED)
        {
            throw new ENagorikException("Only officer-verified applications can be approved.");
        }
    }

    private void guardAgainstInvalidOtp()
    {
        if (!otpService.validateOtp(application.getApplicationNo(), submittedOtp))
        {
            throw new ENagorikException("Invalid or expired OTP.");
        }
    }

    @Override
    public void undo()
    {
        application.updateStatus(previousStatus, "Approval undone");
    }
}