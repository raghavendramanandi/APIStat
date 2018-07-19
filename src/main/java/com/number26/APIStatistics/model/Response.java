package com.number26.APIStatistics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Response {
    private int count;
    private double sum;
    private double avg;
    private double min;
    private double max;
}
