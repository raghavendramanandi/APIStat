package com.number26.APIStatistics.serviceImpl;

import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.model.Response;
import com.number26.APIStatistics.manager.BucketManager;
import com.number26.APIStatistics.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private BucketManager bucketManager;
    private static final Logger logger = LoggerFactory.getLogger(BucketManager.class);

    public Response getStatistics(){
        LocalDateTime timeNow = LocalDateTime.now();

        try {
            List<SummarizedTransaction> summarizedTransactions = bucketManager.getAllStatisticsFor(timeNow);
            int count = summarizedTransactions.stream().map(e -> e.getCount()).reduce(0, (x, y) -> x + y);
            double sum = summarizedTransactions.stream().map(e -> e.getSum()).reduce(0.0, (x, y) -> x + y);
            double avg = sum / count;
            double min = summarizedTransactions.stream().mapToDouble(a -> a.getMin()).min().orElse(0.0);
            double max = summarizedTransactions.stream().mapToDouble(a -> a.getMax()).max().orElse(0.0);
            return new Response(count, sum, avg, min, max);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }
}
