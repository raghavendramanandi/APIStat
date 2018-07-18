package com.number26.APIStatistics.service;

import com.number26.APIStatistics.enums.Status;
import com.number26.APIStatistics.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Service
public class TransactionServiceImpl implements TransactionService{
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
}
