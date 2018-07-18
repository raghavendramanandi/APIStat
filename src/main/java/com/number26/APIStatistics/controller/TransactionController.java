package com.number26.APIStatistics.controller;

import com.number26.APIStatistics.mapper.Mapper;
import com.number26.APIStatistics.model.Transaction;
import com.number26.APIStatistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    public TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
        return Mapper.responseMapper(transactionService.processTransaction(transaction));
    }
}
