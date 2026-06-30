package com.enagorik.adapter;

/**
 * Adapter: wraps dispatchSms(msisdn, body, priority) so the rest of the
 * app only ever depends on the simple send(recipient, message) contract.
 */
public class SmsGatewayAdapter implements NotificationSender
{
    private static final int DEFAULT_PRIORITY = 1;
    private final ThirdPartySmsApi smsApi;

    public SmsGatewayAdapter(ThirdPartySmsApi smsApi)
    {
        this.smsApi = smsApi;
    }

    @Override
    public void send(String recipient, String message)
    {
        smsApi.dispatchSms(recipient, message, DEFAULT_PRIORITY);
    }
}