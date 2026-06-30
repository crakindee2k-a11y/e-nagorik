package com.enagorik.model;

import java.io.Serializable;

public class Service implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int serviceId;
    private final String name;
    private final String category;
    private final double baseFee;
    private final int slaDays;
    private final String[] checklist;

    public Service(int serviceId, String name, String category, double baseFee, int slaDays, String[] checklist)
    {
        this.serviceId = serviceId;
        this.name = name;
        this.category = category;
        this.baseFee = baseFee;
        this.slaDays = slaDays;
        this.checklist = checklist;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public String getName()
    {
        return name;
    }

    public double getBaseFee()
    {
        return baseFee;
    }

    public int getSlaDays()
    {
        return slaDays;
    }

    public String[] getChecklist()
    {
        return checklist;
    }
}