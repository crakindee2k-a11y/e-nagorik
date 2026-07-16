package com.enagorik.factory.payment;

public interface ReceiptPrinter
{
    String printReceipt(double amount, String transactionRef);
}