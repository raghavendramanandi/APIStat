package com.number26.APIStatistics.manager;

import com.number26.APIStatistics.dao.DataStore;
import com.number26.APIStatistics.helper.ConfigurationHelper;
import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class BucketManager implements Manager {
    @Autowired
    private ConfigurationHelper configurationHelper;
    private static final Logger logger = LoggerFactory.getLogger(BucketManager.class);

    public void addToBucket(Transaction transaction){
        updateSummary(getSummarizedTransactionIndex(transaction.getTimeStamp()),transaction);
        DataStore.print();
    }

    public List<SummarizedTransaction> getAllStatisticsFor(LocalDateTime timeNow) {
        List<SummarizedTransaction> summarizedTransactions = new ArrayList<>();
        for (int i =0; i< DataStore.getReference().length(); i++){
            SummarizedTransaction summarizedTransaction = (SummarizedTransaction) DataStore.getReference().get(i);
            if(summarizedTransaction != null &&
                    Duration.between(summarizedTransaction.getTime(), timeNow).getSeconds() < configurationHelper.getTimeIntervalInSeconds()){
                summarizedTransactions.add(summarizedTransaction);
            }
        }
        return summarizedTransactions;
    }

    private void updateSummary(int summarizedTransactionIndex, Transaction transaction) {
        LocalDateTime transactionTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimeStamp()), TimeZone
                        .getDefault().toZoneId());

        SummarizedTransaction summaryFormStore = DataStore.get(summarizedTransactionIndex);

        if (summaryFormStore == null || summaryFormStore.getTime().getMinute() < transactionTime.getMinute()) {
            AddSummarizedTransactionAtIndex(summarizedTransactionIndex, transaction, transactionTime);
        } else if (summaryFormStore.getTime().getMinute() == transactionTime.getMinute()) {
            AppendSummarizedTransactionAtIndex(summarizedTransactionIndex, transaction, transactionTime, summaryFormStore);
        } else {
            //Invalid case
            logger.error("Invalid scenario");return;
        }
    }

    private void AppendSummarizedTransactionAtIndex(int summarizedTransactionIndex, Transaction transaction, LocalDateTime transactionTime, SummarizedTransaction summaryFormStore) {
        DataStore.add(summarizedTransactionIndex, new SummarizedTransaction(transactionTime,
                (1 + summaryFormStore.getCount()),
                (transaction.getAmount() + summaryFormStore.getSum()),
                Math.max(transaction.getAmount(), summaryFormStore.getMax()),
                Math.min(transaction.getAmount(), summaryFormStore.getMin())));
    }

    private void AddSummarizedTransactionAtIndex(int summarizedTransactionIndex, Transaction transaction, LocalDateTime transactionTime) {
        DataStore.add(summarizedTransactionIndex, new SummarizedTransaction(transactionTime,
                1,
                transaction.getAmount(),
                transaction.getAmount(),
                transaction.getAmount()));
    }

    private int getSummarizedTransactionIndex(long timeStamp) {
        LocalDateTime time =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), TimeZone
                        .getDefault().toZoneId());
        int index = time.getSecond();
        return index;
    }
}
