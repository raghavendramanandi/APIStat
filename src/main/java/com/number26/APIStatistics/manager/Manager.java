package com.number26.APIStatistics.manager;

import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface Manager {
    void addToBucket(Transaction transaction);
    List<SummarizedTransaction> getAllStatisticsFor(LocalDateTime timeNow);
}
