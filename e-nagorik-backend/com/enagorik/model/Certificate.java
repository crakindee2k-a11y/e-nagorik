package com.enagorik.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.time.LocalDateTime;

public class Certificate implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final String certificateNo;
    private final LocalDateTime issueDate;
    private final String qrCodeValue;

    public Certificate(String certificateNo, String sourceForHash)
    {
        this.certificateNo = certificateNo;
        this.issueDate = LocalDateTime.now();
        this.qrCodeValue = generateHash(sourceForHash);
    }

    private String generateHash(String source)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(source.getBytes());
            StringBuilder hex = new StringBuilder();

            for (byte b : hashBytes)
            {
                hex.append(String.format("%02x", b));
            }

            return hex.substring(0, 16);
        }
        catch (Exception e)
        {
            return "QR-UNAVAILABLE";
        }
    }

    public String getCertificateNo()
    {
        return certificateNo;
    }

    public String getQrCodeValue()
    {
        return qrCodeValue;
    }
}