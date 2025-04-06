package com.example.ai;

import java.util.List;

import com.example.transaction.Transaction;

public class AiService implements AiInterface {

    @Override
    public boolean fraudDetection(double transactionAmount, double accountAvg) {
        return transactionAmount > accountAvg * 5;
    }

    @Override
    public String smartBudgeting(List<Transaction> transactions) {
        long withdrawalCount = transactions.stream().filter(t -> t.getType().equals("WITHDRAW")).count();
        return "You made " + withdrawalCount + " withdraws this period";
    }

}
