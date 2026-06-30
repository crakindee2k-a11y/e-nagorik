package com.enagorik.service;

import com.enagorik.adapter.NotificationSender;
import com.enagorik.model.Citizen;

import java.util.Map;

/**
 * DIP: depends only on the NotificationSender abstraction, so SMS and
 * Email gateways (or any future channel) plug in without this class
 * changing.
 */
public class NotificationService
{
    private final Map<String, NotificationSender> senders;

    public NotificationService(Map<String, NotificationSender> senders)
    {
        this.senders = senders;
    }

    public void notifyCitizen(Citizen citizen, String message)
    {
        NotificationSender sms = senders.get("SMS");
        NotificationSender email = senders.get("EMAIL");

        if (sms != null)
        {
            sms.send(citizen.getPhone(), message);
        }

        if (email != null)
        {
            email.send(citizen.getEmail(), message);
        }
    }
}