package com.isw.assessment.client.util;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class RequestRateLimiter {

    private final long timeIntervalInMilis;
    private final AtomicReference<LocalTime> previousRequestTime = new AtomicReference<>(LocalTime.now());

    public RequestRateLimiter(long timeIntervalInMilliSeconds) {
        this.timeIntervalInMilis = timeIntervalInMilliSeconds;
    }

    public void waitBetweenRequests() {

        try {
            LocalTime oldRequestTime = previousRequestTime.getAndUpdate(this::getNewRequestTime);
            long millis = Duration.between(LocalTime.now(), oldRequestTime).toMillis();

            Thread.sleep(millis > 0 ? millis : 0);
        } catch (InterruptedException ignored) {}

    }

    private LocalTime getNewRequestTime(LocalTime localTime) {
        return localTime.isBefore(LocalTime.now()) ?
                LocalTime.now().plus(timeIntervalInMilis, ChronoUnit.MILLIS) :
                localTime.plus(timeIntervalInMilis,ChronoUnit.MILLIS);
    }

}
