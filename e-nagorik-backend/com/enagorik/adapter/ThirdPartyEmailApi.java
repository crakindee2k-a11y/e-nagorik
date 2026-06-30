package com.enagorik.adapter;

public class ThirdPartyEmailApi
{
    public void deliverMail(String to, String subject, String body)
    {
        System.out.println("[Email Gateway] -> " + to + " | " + subject + " | " + body);
    }
}