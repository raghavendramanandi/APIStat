package com.number26.APIStatistics.dao;

import com.number26.APIStatistics.model.SummarizedTransaction;
import com.number26.APIStatistics.service.BucketManager;
import com.number26.APIStatistics.service.ConfigurationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Service
public class DataStore {

    @Autowired
    private static AtomicReferenceArray summarizedTransactions;
    private static final Logger logger = LoggerFactory.getLogger(DataStore.class);

    private DataStore(){
    }

    public static AtomicReferenceArray getReference(){
        if(summarizedTransactions == null){
            logger.error("Store is not initialized");
        }
        return summarizedTransactions;
    }

    public static void InitializeStore(int size){
        if(summarizedTransactions == null)
            summarizedTransactions = new AtomicReferenceArray(size);
    }

    public static void print() {
        for (int i = 0; i< summarizedTransactions.length(); i++){
            if(summarizedTransactions.get(i) != null) {
                System.out.println("Index:" + i);
                System.out.println(summarizedTransactions.get(i));
            }
        }
    }

    public static SummarizedTransaction get(int summarizedTransactionIndex) {
        if(summarizedTransactions.get(summarizedTransactionIndex)!= null)
            return (SummarizedTransaction) summarizedTransactions.get(summarizedTransactionIndex);
        return null;
    }

    public static void add(int summarizedTransactionIndex, SummarizedTransaction summarizedTransaction) {
        summarizedTransactions.set(summarizedTransactionIndex, summarizedTransaction);
    }
}
