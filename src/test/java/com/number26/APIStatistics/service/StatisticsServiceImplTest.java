package com.number26.APIStatistics.service;

import com.number26.APIStatistics.manager.BucketManager;
import com.number26.APIStatistics.model.Response;
import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.serviceImpl.StatisticsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Double.NaN;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceImplTest {

    @Mock
    BucketManager bucketManager;

    @InjectMocks
    StatisticsServiceImpl statisticsServiceImpl;

    @Test
    public void shouldReturnZeroIfNoSummaryAvailable(){
        when(bucketManager.getAllStatisticsFor(any(LocalDateTime.class))).thenReturn(new ArrayList<>());
        Response actual = statisticsServiceImpl.getStatistics();
        assertResponse(actual, new Response(0,0.0,NaN,0.0,0.0));
    }

    @Test
    public void shouldReturnIfOneSummaryAvailable(){
        List<SummarizedTransaction> summarizedTransactions =  new ArrayList<>();
        summarizedTransactions.add(new SummarizedTransaction(LocalDateTime.now(),
                2,
                4,
                2,
                2));
        when(bucketManager.getAllStatisticsFor(any(LocalDateTime.class))).thenReturn(summarizedTransactions);
        Response actual = statisticsServiceImpl.getStatistics();
        assertResponse(actual, new Response(2,4.0,2,2.0,2.0));
    }

    @Test
    public void shouldReturnIfMultipleSummaryAvailable(){
        List<SummarizedTransaction> summarizedTransactions =  new ArrayList<>();
        summarizedTransactions.add(new SummarizedTransaction(LocalDateTime.now(),
                2,
                5,
                3,
                2));
        summarizedTransactions.add(new SummarizedTransaction(LocalDateTime.now(),
                3,
                9,
                5,
                1));
        when(bucketManager.getAllStatisticsFor(any(LocalDateTime.class))).thenReturn(summarizedTransactions);
        Response actual = statisticsServiceImpl.getStatistics();
        assertResponse(actual, new Response(5,14.0,2.8,1.0,5.0));
    }

    private void assertResponse(Response actual, Response expected) {
        assertEquals(expected.getCount(), actual.getCount());
        assertEquals(expected.getSum(), actual.getSum(), 0.0);
        assertEquals(expected.getAvg(), actual.getAvg(), 0.0);
        assertEquals(expected.getMin(), actual.getMin(), 0.0);
        assertEquals(expected.getMax(), actual.getMax(), 0.0);
    }
}