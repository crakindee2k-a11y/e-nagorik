package com.enagorik.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OTPApproval implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int otpId;
    private final String otpCode;
    private final LocalDateTime expiresAt;
    private LocalDateTime verifiedAt;

    public OTPApproval(int otpId, String otpCode, int validMinutes)
    {
        this.otpId = otpId;
        this.otpCode = otpCode;
        this.expiresAt = LocalDateTime.now().plusMinutes(validMinutes);
    }

    /** Guard clauses instead of nested ifs (Topic 3 fix applied here). */
    public boolean verifyOtp(String inputCode)
    {
        boolean isWrongCode = !otpCode.equals(inputCode);
        boolean isExpired = LocalDateTime.now().isAfter(expiresAt);

        if (isWrongCode || isExpired)
        {
            return false;
        }

        verifiedAt = LocalDateTime.now();
        return true;
    }
}