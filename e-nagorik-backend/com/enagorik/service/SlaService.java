package com.enagorik.service;

import com.enagorik.model.Application;
import com.enagorik.model.ApplicationStatus;

import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SlaService
{
    public long daysRemaining(Application app)
    {
        long elapsedDays = ChronoUnit.DAYS.between(app.getSubmittedAt(), LocalDateTime.now());
        return app.getService().getSlaDays() - elapsedDays;
    }

    public List<Application> findBreached(List<Application> apps)
    {
        return apps.stream()
            .filter(this::isStillOpen)
            .filter(a -> daysRemaining(a) <= 0)
            .collect(Collectors.toList());
    }

    private boolean isStillOpen(Application app)
    {
        return app.getStatus() != ApplicationStatus.APPROVED && app.getStatus() != ApplicationStatus.REJECTED;
    }
}