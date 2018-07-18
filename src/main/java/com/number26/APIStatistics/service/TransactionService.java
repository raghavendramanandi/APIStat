package com.number26.APIStatistics.service;

import com.number26.APIStatistics.enums.Status;
import com.number26.APIStatistics.model.Transaction;

public interface TransactionService {
    Status processTransaction(Transaction transaction);
}
