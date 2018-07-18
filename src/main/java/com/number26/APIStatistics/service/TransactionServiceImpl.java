package com.number26.APIStatistics.service;

import com.number26.APIStatistics.enums.Status;
import com.number26.APIStatistics.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private BucketManager bucketManager;
    private static final Logger logger = LoggerFactory.getLogger(BucketManager.class);

    @Override
    public Status processTransaction(Transaction transaction) {
        try {
            if (!validateTransaction(transaction))
                return Status.INVALID;
            consumeTransaction(transaction);
            return Status.SUCCESS;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    private void consumeTransaction(Transaction transaction) {
        bucketManager.addToBucket(transaction);
    }

    private boolean validateTransaction(Transaction transaction) {
        LocalDateTime timeNow = LocalDateTime.now();
        return transaction.isTransactionInWindowSeconds(60, timeNow) &&
                transaction.isTransactionInFuture(timeNow);
    }
}
