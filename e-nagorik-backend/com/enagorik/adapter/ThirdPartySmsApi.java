package com.enagorik.adapter;

/** Simulates a vendor SDK with its own incompatible method shape. */
public class ThirdPartySmsApi
{
    public boolean dispatchSms(String msisdn, String body, int priority)
    {
        System.out.println("[SMS Gateway] -> " + msisdn + " : " + body);
        return true;
    }
}