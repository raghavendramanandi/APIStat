package com.number26.APIStatistics.serviceImpl;

import com.number26.APIStatistics.enums.Status;
import com.number26.APIStatistics.helper.Util;
import com.number26.APIStatistics.manager.Manager;
import com.number26.APIStatistics.model.Transaction;
import com.number26.APIStatistics.manager.BucketManager;
import com.number26.APIStatistics.helper.ConfigurationHelper;
import com.number26.APIStatistics.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private Manager bucketManager;
    @Autowired
    private ConfigurationHelper configurationHelper;
    @Autowired
    private Util util;
    private static final Logger logger = LoggerFactory.getLogger(BucketManager.class);

    @Override
    public Status processTransaction(Transaction transaction) {
        LocalDateTime timeNow = util.now();
        try {
            if (!validateTransaction(transaction, timeNow))
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

    public boolean validateTransaction(Transaction transaction, LocalDateTime timeNow) {
        return transaction.isTransactionInWindowSeconds(configurationHelper.getTimeIntervalInSeconds(), timeNow) &&
                transaction.isTransactionInFuture(timeNow);
    }
}
