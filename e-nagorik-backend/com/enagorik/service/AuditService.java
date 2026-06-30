package com.enagorik.service;

import com.enagorik.util.AppConstants;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Append-only audit trail — every state-changing action across the
 * system is logged here, satisfying FR-12 ("who did what, when").
 */
public class AuditService
{
    public void log(String actor, String action, String reference)
    {
        String line = LocalDateTime.now() + " | " + actor + " | " + action + " | " + reference;
        System.out.println("[AUDIT] " + line);

        try (FileWriter writer = new FileWriter(AppConstants.DATA_DIR + "/audit.log", true))
        {
            writer.write(line + System.lineSeparator());
        }
        catch (IOException e)
        {
            System.err.println("Audit write failed: " + e.getMessage());
        }
    }
}