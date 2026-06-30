package com.enagorik.model;

import java.io.Serializable;

public class Notification implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int notificationId;
    private final String message;
    private final String channel;

    public Notification(int notificationId, String message, String channel)
    {
        this.notificationId = notificationId;
        this.message = message;
        this.channel = channel;
    }

    public String getMessage()
    {
        return message;
    }

    public String getChannel()
    {
        return channel;
    }
}