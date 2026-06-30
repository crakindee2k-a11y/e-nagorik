package com.enagorik.service;

import com.enagorik.model.OTPApproval;
import com.enagorik.singleton.IdGenerator;
import com.enagorik.util.AppConstants;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class OtpService
{
    private static final int OTP_LENGTH = 6;

    private final Map<String, OTPApproval> activeOtps = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public String generateOtp(String applicationNo)
    {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++)
        {
            code.append(random.nextInt(10));
        }

        OTPApproval otp = new OTPApproval(IdGenerator.getInstance().nextOtpId(), code.toString(), AppConstants.OTP_VALID_MINUTES);
        activeOtps.put(applicationNo, otp);
        return code.toString();
    }

    public boolean validateOtp(String applicationNo, String submittedCode)
    {
        OTPApproval otp = activeOtps.get(applicationNo);
        return otp != null && otp.verifyOtp(submittedCode);
    }
}