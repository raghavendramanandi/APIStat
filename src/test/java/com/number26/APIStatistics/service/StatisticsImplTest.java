package com.number26.APIStatistics.service;

import com.number26.APIStatistics.model.Transaction;
import javafx.beans.binding.When;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class StatisticsImplTest {

    @Autowired
    private Environment environment;


    @Test
    public void should(){
        System.out.println(environment.getProperty("app.subdomain"));
        System.out.println(environment.getProperty("app.rootsubdomain","Default Value"));
    }

    @Test
    public void shouldEvaluateBeforeTimeAsTrue(){
        StatisticsImpl statistics = new StatisticsImpl();

        Transaction transaction = new Transaction(10.0, 1531829973000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 18, 18, 47, 53);

        assertTrue(statistics.validateFutureTransactions(transaction, localDateTime));
    }
    @Test
    public void shouldEvaluateAfterTimeAsFalse(){
        StatisticsImpl statistics = new StatisticsImpl();

        Transaction transaction = new Transaction(10.0, 1532829973000L);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 7, 18, 18, 47, 53);
        assertFalse(statistics.validateFutureTransactions(transaction, localDateTime));
    }
}