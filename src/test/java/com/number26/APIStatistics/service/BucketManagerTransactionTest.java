package com.number26.APIStatistics.service;

import com.number26.APIStatistics.dao.DataStore;
import com.number26.APIStatistics.helper.ConfigurationHelper;
import com.number26.APIStatistics.manager.BucketManager;
import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BucketManagerTransactionTest {
    @Mock
    ConfigurationHelper configurationHelper;

    @InjectMocks
    BucketManager bucketManager;

    @Before
    public void runBefore(){
        DataStore.resetStore(60);
    }

    @Test
    public void shouldAddTransactionsToRespectiveIndexes(){
        Transaction transaction1 = new Transaction(1, 1531969990477L);
        Transaction transaction2 = new Transaction(2, 1531969991477L);
        Transaction transaction3 = new Transaction(3, 1531969992477L);


        bucketManager.addToBucket(transaction1);
        bucketManager.addToBucket(transaction2);
        bucketManager.addToBucket(transaction3);

        System.out.println(">"+DataStore.get(10));
        assertSummarizedTransaction(DataStore.get(10), new SummarizedTransaction(LocalDateTime.of(2018, 7, 19, 8, 43, 10,477000000),  1, 1.0,1.0,1.0));

        assertSummarizedTransaction(DataStore.get(11), new SummarizedTransaction(LocalDateTime.of(2018, 7, 19, 8, 43, 11, 477000000), 1, 2.0,2.0,2.0));

        assertSummarizedTransaction(DataStore.get(12), new SummarizedTransaction(LocalDateTime.of(2018, 7, 19, 8, 43, 12, 477000000), 1, 3.0,3.0,3.0));
    }

    @Test
    public void shouldAggregateTransactionIfAddedToSameIndex(){
        Transaction transaction2 = new Transaction(2, 1531969991477L);

        Transaction transaction5 = new Transaction(5, 1531969991477L);

        bucketManager.addToBucket(transaction2);
        bucketManager.addToBucket(transaction5);

        assertSummarizedTransaction(DataStore.get(11), new SummarizedTransaction(LocalDateTime.of(2018, 7, 19, 8, 43, 11, 477000000), 2, 7.0,5.0,2.0));

    }

    @Test
    public void shouldReplaceOldSummary(){
        Transaction transaction5 = new Transaction(5, 1531969931477L);
        bucketManager.addToBucket(transaction5);

        assertSummarizedTransaction(DataStore.get(11), new SummarizedTransaction(LocalDateTime.of(2018, 7, 19, 8, 42, 11, 477000000), 1, 5.0,5.0,5.0));

        Transaction transaction2 = new Transaction(2, 1531969991477L);
        bucketManager.addToBucket(transaction2);

        assertSummarizedTransaction(DataStore.get(11), new SummarizedTransaction(LocalDateTime.of(2018, 7, 19, 8, 43, 11, 477000000), 1, 2.0,2.0,2.0));

    }



    private void assertSummarizedTransaction(SummarizedTransaction actual, SummarizedTransaction expected) {
        assertEquals(expected.getTime(), actual.getTime());
        assertEquals(expected.getSum(), actual.getSum(), 0.0);
        assertEquals(expected.getMin(), actual.getMin(), 0.0);
        assertEquals(expected.getMax(), actual.getMax(), 0.0);
    }


}
