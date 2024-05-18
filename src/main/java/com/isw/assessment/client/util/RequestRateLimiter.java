package com.isw.assessment.client.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestRateLimiter {

    private final long timeIntervalInNanos;
    private volatile AtomicLong lastRequestTime = new AtomicLong(0);
    private volatile Lock lock = new ReentrantLock(true);

    public RequestRateLimiter(long timeIntervalInMilliSeconds) {
        this.timeIntervalInNanos = TimeUnit.MILLISECONDS.toNanos(timeIntervalInMilliSeconds);
    }

    public void waitForSpecificTimeBetweenRequests(){

        try {
            lock.lock();
            long duration = System.nanoTime() - lastRequestTime.get();
            long remainingTimeInNanoSeconds = duration > timeIntervalInNanos ? 0 : timeIntervalInNanos - duration;
            long remainingTimeInSeconds = (long) (remainingTimeInNanoSeconds/1_000_000.0);

            Thread.sleep(remainingTimeInSeconds);
        } catch (InterruptedException ignored) {

        }
        finally {
            lastRequestTime.set(System.nanoTime());
            lock.unlock();
         };
    }
}
