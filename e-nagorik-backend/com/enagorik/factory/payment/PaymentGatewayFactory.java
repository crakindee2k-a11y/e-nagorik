package com.enagorik.factory.payment;

/**
 * Abstract Factory: produces a matched family (processor + receipt
 * printer) for one provider, so a bKash charge can never end up paired
 * with a Nagad receipt.
 */
public interface PaymentGatewayFactory
{
    PaymentProcessor createProcessor();
    ReceiptPrinter createReceiptPrinter();
}