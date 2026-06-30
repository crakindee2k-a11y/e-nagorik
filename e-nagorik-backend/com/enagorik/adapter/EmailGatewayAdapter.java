package com.enagorik.adapter;

public class EmailGatewayAdapter implements NotificationSender
{
    private static final String DEFAULT_SUBJECT = "E-Nagorik Update";
    private final ThirdPartyEmailApi emailApi;

    public EmailGatewayAdapter(ThirdPartyEmailApi emailApi)
    {
        this.emailApi = emailApi;
    }

    @Override
    public void send(String recipient, String message)
    {
        emailApi.deliverMail(recipient, DEFAULT_SUBJECT, message);
    }
}