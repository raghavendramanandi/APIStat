package com.number26.APIStatistics.service;

import com.number26.APIStatistics.dao.DataStore;
import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.ii.LocaleNames_ii;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class BucketManager {
    @Autowired
    private ConfigurationHelper configurationHelper;
    private static final Logger logger = LoggerFactory.getLogger(BucketManager.class);
    int NUMBER_OF_BUCKETS = 60;
    int TIME_IN_SEC = 60;

    public void addToBucket(Transaction transaction){
        updateSummary(getSummarizedTransactionIndex(transaction.getTimeStamp()),transaction);
        DataStore.print();
    }

    private void updateSummary(int summarizedTransactionIndex, Transaction transaction) {
        LocalDateTime transactionTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimeStamp()), TimeZone
                        .getDefault().toZoneId());

        SummarizedTransaction summaryFormStore = DataStore.get(summarizedTransactionIndex);
        if(summaryFormStore == null){
            DataStore.add(summarizedTransactionIndex, new SummarizedTransaction(transactionTime,
                    1,
                    transaction.getAmount(),
                    transaction.getAmount(),
                    transaction.getAmount()));
        } else {
            if (summaryFormStore.getTime().getMinute() < transactionTime.getMinute()) {
                DataStore.add(summarizedTransactionIndex, new SummarizedTransaction(transactionTime,
                        1,
                        transaction.getAmount(),
                        transaction.getAmount(),
                        transaction.getAmount()));
            } else if (summaryFormStore.getTime().getMinute() == transactionTime.getMinute()) {
                DataStore.add(summarizedTransactionIndex, new SummarizedTransaction(transactionTime,
                        (1 + summaryFormStore.getCount()),
                        (transaction.getAmount() + summaryFormStore.getSum()),
                        Math.max(transaction.getAmount(), summaryFormStore.getMax()),
                        Math.min(transaction.getAmount(), summaryFormStore.getMin())));
            } else {
                //Invalid case
                logger.error("Invalid scenario");
                return;
            }
        }
    }

    private int getSummarizedTransactionIndex(long timeStamp) {
        LocalDateTime time =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), TimeZone
                        .getDefault().toZoneId());
        int bucketSize = TIME_IN_SEC/NUMBER_OF_BUCKETS;
        int index = time.getSecond()/bucketSize;
        return index;
    }

    public List<SummarizedTransaction> getAllStatisticsFor(LocalDateTime timeNow) {
        List<SummarizedTransaction> summarizedTransactions = new ArrayList<>();
        for (int i =0; i< DataStore.getReference().length(); i++){
            SummarizedTransaction summarizedTransaction = (SummarizedTransaction) DataStore.getReference().get(i);
            if(summarizedTransaction != null &&
                    Duration.between(summarizedTransaction.getTime(), timeNow).getSeconds() < configurationHelper.getTimeInterval()){
                summarizedTransactions.add(summarizedTransaction);
            }
        }
        return summarizedTransactions;
    }
}
