package com.number26.APIStatistics.service;

import com.number26.APIStatistics.enums.Status;
import com.number26.APIStatistics.model.Response;
import com.number26.APIStatistics.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static java.lang.Double.NaN;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    BucketManager bucketManager;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    public void shouldReturnInvalidIfTransactionTimeIsOld(){
        Transaction transaction = new Transaction(1.0, 1431883849634L);
        doNothing().when(bucketManager).addToBucket(any(Transaction.class));
        Status actual = transactionService.processTransaction(transaction);
        assertEquals(actual, Status.INVALID);
    }

    @Test
    public void shouldReturnInvalidIfTransactionTimeIsAheadOfTime(){
        Transaction transaction = new Transaction(1.0, 9431883849634L);
        doNothing().when(bucketManager).addToBucket(any(Transaction.class));
        Status actual = transactionService.processTransaction(transaction);
        assertEquals(actual, Status.INVALID);
    }

    @Test
    public void shouldReturnSuccessOnAValidTransaction(){
        long timeStamp = LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(19800)).toEpochMilli();
        Transaction transaction = new Transaction(1.0, timeStamp);
        doNothing().when(bucketManager).addToBucket(any(Transaction.class));
        Status actual = transactionService.processTransaction(transaction);
        assertEquals(actual, Status.SUCCESS);
    }
}