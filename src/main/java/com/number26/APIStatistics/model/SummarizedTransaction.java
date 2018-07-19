package com.number26.APIStatistics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class SummarizedTransaction {
    private LocalDateTime time;
    private int count;
    private double sum;
    private double max;
    private double min;
}
