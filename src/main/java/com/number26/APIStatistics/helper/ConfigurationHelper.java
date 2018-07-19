package com.number26.APIStatistics.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationHelper {
    @Autowired
    private Environment environment;

    public int getTimeIntervalInSeconds(){
        return Integer.parseInt(environment.getProperty("timeInterval"));
    }
}
