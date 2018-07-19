package com.number26.APIStatistics.helper;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Service
public class Util {
    public LocalDateTime now(){
        return LocalDateTime.now();
    }

    public LocalDateTime getDateTime(long epochTime){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTime), TimeZone.getDefault().toZoneId());
    }
}
