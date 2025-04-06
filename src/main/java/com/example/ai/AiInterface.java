package com.example.ai;

import java.util.List;

import com.example.transaction.Transaction;

public interface AiInterface {
    boolean fraudDetection(double transactionAmount, double accountAvg);

    String smartBudgeting(List<Transaction> transactions);
}
