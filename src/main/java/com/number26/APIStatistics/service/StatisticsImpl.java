package com.number26.APIStatistics.service;

import com.number26.APIStatistics.enums.Status;
import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.model.Transaction;
import com.number26.APIStatistics.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

@Service
public class StatisticsImpl implements Statistics{

    @Autowired
    private BucketManager bucketManager;

    @Override
    public Status processTransaction(Transaction transaction) {
        if(!validateTransaction(transaction))
            return Status.INVALID;
        consumeTransaction(transaction);
        return Status.SUCCESS;
    }

    private void consumeTransaction(Transaction transaction) {
        bucketManager.addToBucket(transaction);
    }

    private boolean validateTransaction(Transaction transaction) {
        LocalDateTime timeNow = LocalDateTime.now();
        return transaction.isTransactionInWindowSeconds(60, timeNow) &&
                validateFutureTransactions(transaction, timeNow);
    }

    public boolean validateFutureTransactions(Transaction transaction, LocalDateTime timeNow) {
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimeStamp()), TimeZone
                        .getDefault().toZoneId());
        return dateTime.isBefore(timeNow);
    }

    public Response getStatistics(){
        LocalDateTime timeNow = LocalDateTime.now();
        List<SummarizedTransaction> summarizedTransactions = bucketManager.getAllStatisticsFor(timeNow);
        int count = summarizedTransactions.stream().map(e -> e.getCount()).reduce(0, (x, y) -> x + y);
        double sum = summarizedTransactions.stream().map(e -> e.getSum()).reduce(0.0, (x, y) -> x + y);
        double avg = sum/count;
        double min = summarizedTransactions.stream().mapToDouble(a -> a.getMin()).min().getAsDouble();
        double max = summarizedTransactions.stream().mapToDouble(a -> a.getMax()).max().getAsDouble();
        return new Response(count,sum, avg, min, max);
    }
}
