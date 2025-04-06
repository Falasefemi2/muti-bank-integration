package com.example.transaction;

import java.util.List;

public class TransactionService {
    public static String getTransactionHistory(List<Transaction> transactions) {
        StringBuilder history = new StringBuilder("Transaction History:\n");
        for (Transaction t : transactions) {
            history.append(t.toString()).append("\n");
        }
        return history.toString();
    }
}
