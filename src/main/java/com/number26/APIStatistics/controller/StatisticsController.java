package com.number26.APIStatistics.controller;

import com.number26.APIStatistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatisticsController {
    @Autowired
    public StatisticsService statistics;

    @GetMapping("/statistics")
    public Object getStatistics() {
        return statistics.getStatistics();
    }
}