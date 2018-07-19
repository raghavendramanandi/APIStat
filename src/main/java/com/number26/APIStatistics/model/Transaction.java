package com.number26.APIStatistics.model;

import com.number26.APIStatistics.helper.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Transaction {
    private double amount;
    private long timeStamp;

    public boolean isTransactionInWindowSeconds(int windowInSeconds, LocalDateTime localDateTime){
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(this.timeStamp), TimeZone
                        .getDefault().toZoneId());
        return !(Duration.between(dateTime, localDateTime).getSeconds() > windowInSeconds);
    }

    public boolean isTransactionInFuture(LocalDateTime timeNow) {
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(this.timeStamp), TimeZone
                        .getDefault().toZoneId());
        return dateTime.isBefore(timeNow);
    }
}
