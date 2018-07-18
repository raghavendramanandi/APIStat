package com.number26.APIStatistics.service;

import com.number26.APIStatistics.model.Transaction;
import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class StatisticsImplTest {

    @Test
    public void shouldEvaluateBeforeTimeAsTrue(){
        TransactionServiceImpl transactionService = new TransactionServiceImpl();

        Transaction transaction = new Transaction(10.0, 1531829973000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 18, 18, 47, 53);

        assertTrue(transactionService.validateFutureTransactions(transaction, localDateTime));
    }
    @Test
    public void shouldEvaluateAfterTimeAsFalse(){
        TransactionServiceImpl transactionService = new TransactionServiceImpl();

        Transaction transaction = new Transaction(10.0, 1532829973000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 18, 18, 47, 53);
        assertFalse(transactionService.validateFutureTransactions(transaction, localDateTime));
    }
}