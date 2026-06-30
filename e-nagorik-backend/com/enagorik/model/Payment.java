package com.enagorik.model;

import java.io.Serializable;

public class Payment implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int paymentId;
    private final double amount;
    private final String method;
    private String status;

    public Payment(int paymentId, double amount, String method)
    {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.status = "PENDING";
    }

    public void confirmPayment()
    {
        this.status = "CONFIRMED";
    }

    public double getAmount()
    {
        return amount;
    }

    public String getMethod()
    {
        return method;
    }

    public String getStatus()
    {
        return status;
    }
}