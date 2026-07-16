package com.enagorik.factory.payment;

public interface PaymentProcessor
{
    boolean charge(double amount, String mobileNumber);
}