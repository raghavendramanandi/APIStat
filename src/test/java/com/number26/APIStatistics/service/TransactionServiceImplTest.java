package com.number26.APIStatistics.service;

import com.number26.APIStatistics.enums.Status;
import com.number26.APIStatistics.helper.ConfigurationHelper;
import com.number26.APIStatistics.helper.Util;
import com.number26.APIStatistics.manager.BucketManager;
import com.number26.APIStatistics.model.Transaction;
import com.number26.APIStatistics.serviceImpl.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    BucketManager bucketManager;

    @Mock
    ConfigurationHelper configurationHelper;

    @Mock
    Util util;

    @Spy
    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    public void shouldReturnInvalidIfTransactionTimeIsOld(){
        Transaction transaction = new Transaction(1.0, 1431883849634L);
        when(configurationHelper.getTimeIntervalInSeconds()).thenReturn(60);
        when(util.now()).thenReturn(LocalDateTime.now());
        Status actual = transactionService.processTransaction(transaction);
        assertEquals(Status.INVALID, actual);
    }

    @Test
    public void shouldReturnInvalidIfTransactionTimeIsAheadOfTime(){
        Transaction transaction = new Transaction(1.0, 9431883849634L);
        when(configurationHelper.getTimeIntervalInSeconds()).thenReturn(60);
        when(util.now()).thenReturn(LocalDateTime.now());
        Status actual = transactionService.processTransaction(transaction);
        assertEquals(Status.INVALID, actual);
    }

    @Test
    public void shouldReturnSuccessOnAValidTransaction(){
        long timeStamp = LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        Transaction transaction = new Transaction(1.0, timeStamp);
        doReturn(true).when(transactionService).validateTransaction(any(),any());
        doNothing().when(bucketManager).addToBucket(any());
        Status actual = transactionService.processTransaction(transaction);
        assertEquals(Status.SUCCESS, actual);
    }
}