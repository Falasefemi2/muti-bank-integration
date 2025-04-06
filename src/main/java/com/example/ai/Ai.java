package com.example.ai;

import java.util.List;

import com.example.transaction.Transaction;

public class Ai {
    public static String analyzeTransactions(AiService ai, List<Transaction> transactions) {
        return ai.smartBudgeting(transactions);
    }
}
