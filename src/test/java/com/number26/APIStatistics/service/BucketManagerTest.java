package com.number26.APIStatistics.service;

import com.number26.APIStatistics.dao.DataStore;
import com.number26.APIStatistics.helper.ConfigurationHelper;
import com.number26.APIStatistics.manager.BucketManager;
import com.number26.APIStatistics.model.SummarizedTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BucketManagerTest {

    @Mock
    ConfigurationHelper configurationHelper;

    @InjectMocks
    BucketManager bucketManager;

    @Before
    public void runBefore(){
        DataStore.resetStore(60);
        DataStore.add(1, new SummarizedTransaction(LocalDateTime.of(2018,7,14,9,0,1),1,2,2, 2));
        DataStore.add(11, new SummarizedTransaction(LocalDateTime.of(2018,7,14,9,1,11),2,2,1, 1));
        DataStore.add(21, new SummarizedTransaction(LocalDateTime.of(2018,7,14,9,1,21),4,8,4, 1));
        DataStore.add(31, new SummarizedTransaction(LocalDateTime.of(2018,7,14,9,1,31),1,2,2, 2));
        DataStore.add(41, new SummarizedTransaction(LocalDateTime.of(2018,7,14,9,5,41),2,5,5, 0));
        DataStore.add(51, new SummarizedTransaction(LocalDateTime.of(2018,7,14,9,5,52),1,2,2, 2));
    }

    @Test
    public void shouldReturnValidSummarizedTransaction() {

        when(configurationHelper.getTimeIntervalInSeconds()).thenReturn(60);
        int count = (bucketManager.getAllStatisticsFor(LocalDateTime.of(2018,7,14,9,5,52))).size();
        assertEquals(2, count);
    }
}