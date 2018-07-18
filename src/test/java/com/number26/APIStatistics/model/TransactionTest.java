package com.number26.APIStatistics.model;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class TransactionTest {
   @Test
    public void shouldEvaluateToTrueIfTransactionIsOlderThanWiindow(){
        Transaction transaction = new Transaction(10.0, 1531829873000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 17, 17, 47, 53);

        assertTrue(transaction.isTransactionInWindowSeconds(60, localDateTime));
    }

    @Test
    public void shouldEvaluateToFalseIfTransactionIsOlderThanWiindow(){
        Transaction transaction = new Transaction(10.0, 1531829973000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 18, 18, 47, 53);

        assertFalse(transaction.isTransactionInWindowSeconds(60, localDateTime));
    }

    @Test
    public void shouldEvaluateBeforeTimeAsTrue(){
        Transaction transaction = new Transaction(10.0, 1531829973000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 18, 18, 47, 53);

        assertTrue(transaction.isTransactionInFuture(localDateTime));
    }
    @Test
    public void shouldEvaluateAfterTimeAsFalse(){
        Transaction transaction = new Transaction(10.0, 1532829973000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 18, 18, 47, 53);
        assertFalse(transaction.isTransactionInFuture(localDateTime));
    }
}
