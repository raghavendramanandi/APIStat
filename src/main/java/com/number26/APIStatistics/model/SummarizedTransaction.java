package com.number26.APIStatistics.model;

import java.time.LocalDateTime;

public class SummarizedTransaction {
    private LocalDateTime time;
    private double avg;
    private int count;
    private double sum;
    private double max;
    private double min;

    public SummarizedTransaction(LocalDateTime time, double avg, int count, double sum, double max, double min) {
        this.time = time;
        this.avg = avg;
        this.count = count;
        this.sum = sum;
        this.max = max;
        this.min = min;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getAvg() {
        return avg;
    }

    public int getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    @Override
    public String toString() {
        return "SummarizedTransaction{" +
                "time=" + time +
                ", avg=" + avg +
                ", count=" + count +
                ", sum=" + sum +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}