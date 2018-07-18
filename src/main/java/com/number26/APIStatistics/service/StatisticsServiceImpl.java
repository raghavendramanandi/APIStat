package com.number26.APIStatistics.service;

import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private BucketManager bucketManager;

    public Response getStatistics(){
        LocalDateTime timeNow = LocalDateTime.now();
        List<SummarizedTransaction> summarizedTransactions = bucketManager.getAllStatisticsFor(timeNow);
        int count = summarizedTransactions.stream().map(e -> e.getCount()).reduce(0, (x, y) -> x + y);
        System.out.println("Count" + count);
        double sum = summarizedTransactions.stream().map(e -> e.getSum()).reduce(0.0, (x, y) -> x + y);
        double avg = sum/count;
        double min = summarizedTransactions.stream().mapToDouble(a -> a.getMin()).min().orElse(0.0);
        double max = summarizedTransactions.stream().mapToDouble(a -> a.getMax()).max().orElse(0.0);
        return new Response(count,sum, avg, min, max);
    }
}
