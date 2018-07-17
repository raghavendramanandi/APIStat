package com.number26.APIStatistics.controller;

import com.number26.APIStatistics.model.Transaction;
import com.number26.APIStatistics.service.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    public Statistics statistics;


    @RequestMapping("/")
    public String healthCheck() {
        System.out.println("Hello");
        return "OK";
    }

    @PostMapping("/transaction")
    public String addTransaction(@RequestBody Transaction transaction) {
        System.out.println("Here");
        System.out.println(statistics.processTransaction(transaction));
        return transaction.getAmount() + " " + transaction.getTimeStamp();
    }
}
