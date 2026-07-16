package com.enagorik.factory.payment;

public class NagadGatewayFactory implements PaymentGatewayFactory
{
    @Override
    public PaymentProcessor createProcessor()
    {
        return (amount, mobileNumber) -> mobileNumber != null && mobileNumber.startsWith("01");
    }

    @Override
    public ReceiptPrinter createReceiptPrinter()
    {
        return (amount, transactionRef) -> "Nagad Receipt | Tk " + amount + " | Ref: " + transactionRef;
    }
}