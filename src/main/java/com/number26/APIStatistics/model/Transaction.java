package com.number26.APIStatistics.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class Transaction {
    private double amount;
    private long timeStamp;

    public Transaction() {
    }

    public Transaction(double amount, long timeStamp) {
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public boolean isTransactionInWindowSeconds(int windowInSeconds, LocalDateTime localDateTime){
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(this.timeStamp), TimeZone
                        .getDefault().toZoneId());
        return !(Duration.between(dateTime, localDateTime).getSeconds() > windowInSeconds);
    }

    public boolean isTransactionInFuture(LocalDateTime timeNow) {
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(this.getTimeStamp()), TimeZone
                        .getDefault().toZoneId());
        return dateTime.isBefore(timeNow);
    }
}
