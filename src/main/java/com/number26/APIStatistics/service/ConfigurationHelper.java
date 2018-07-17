package com.number26.APIStatistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationHelper {
    @Autowired
    private Environment environment;

    public int getNumberOfBBuckets(){
//        System.out.println(environment.getProperty("numbberOfbuckets"));
        return Integer.parseInt(environment.getProperty("numbberOfbuckets"));
    }
}
