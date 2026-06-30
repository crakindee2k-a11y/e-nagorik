package com.enagorik.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StatusHistory implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int historyId;
    private final ApplicationStatus fromStatus;
    private final ApplicationStatus toStatus;
    private final String comment;
    private final LocalDateTime timestamp;

    public StatusHistory(int historyId, ApplicationStatus fromStatus, ApplicationStatus toStatus, String comment)
    {
        this.historyId = historyId;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString()
    {
        return timestamp + " : " + fromStatus + " -> " + toStatus + " (" + comment + ")";
    }
}