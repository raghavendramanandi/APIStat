package com.number26.APIStatistics.dao;

import com.number26.APIStatistics.model.SummarizedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public static void initializeStore(int size){
        if(summarizedTransactions == null)
            summarizedTransactions = new AtomicReferenceArray(size);
    }

    //For use only in test
    public static void resetStore(int size){
        summarizedTransactions = new AtomicReferenceArray(size);
    }

    public static void print() {
        for (int i = 0; i< summarizedTransactions.length(); i++){
            if(summarizedTransactions.get(i) != null) {
                logger.info("Index:" + i);
                logger.info(((SummarizedTransaction)summarizedTransactions.get(i)).toString());
            }
        }
    }

    synchronized public static SummarizedTransaction get(int summarizedTransactionIndex) {
        if(summarizedTransactions.get(summarizedTransactionIndex)!= null)
            return (SummarizedTransaction) summarizedTransactions.get(summarizedTransactionIndex);
        return null;
    }

    synchronized public static void add(int summarizedTransactionIndex, SummarizedTransaction summarizedTransaction) {
        summarizedTransactions.set(summarizedTransactionIndex, summarizedTransaction);
    }
}
