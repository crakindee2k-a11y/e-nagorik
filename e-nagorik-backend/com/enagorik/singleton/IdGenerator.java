package com.enagorik.singleton;

import java.time.Year;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton: one counter shared by the whole app — two independent
 * counters could issue the same application ID, which is exactly the
 * kind of bug the anti-corruption design rules out.
 */
public final class IdGenerator
{
    private static final IdGenerator INSTANCE = new IdGenerator();

    private final AtomicInteger applicationCounter = new AtomicInteger(0);
    private final AtomicInteger certificateCounter = new AtomicInteger(0);
    private final AtomicInteger otpCounter = new AtomicInteger(0);
    private final AtomicInteger paymentCounter = new AtomicInteger(0);

    private IdGenerator()
    {
    }

    public static IdGenerator getInstance()
    {
        return INSTANCE;
    }

    public synchronized String nextApplicationNo()
    {
        int seq = applicationCounter.incrementAndGet();
        return String.format("EN-%d-%05d", Year.now().getValue(), seq);
    }

    public synchronized String nextCertificateNo()
    {
        int seq = certificateCounter.incrementAndGet();
        return String.format("CERT-%d-%05d", Year.now().getValue(), seq);
    }

    public synchronized int nextOtpId()
    {
        return otpCounter.incrementAndGet();
    }

    public synchronized int nextPaymentId()
    {
        return paymentCounter.incrementAndGet();
    }
}