package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.ai.Ai;
import com.example.ai.AiService;
import com.example.bank.BankAccount;
import com.example.bank.BankService;
import com.example.loan.LoanManager;
import com.example.transaction.Transaction;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());

        // 1. Create 2 bank accounts
        BankAccount acc1 = new BankAccount(1000, "Falase Femi");
        acc1.setProvider("GTBank");

        BankAccount acc2 = new BankAccount(500, "falase Femi");
        acc2.setProvider("AccessBank");

        // 2. Link account
        acc1.linkAccount(acc2);
        acc2.linkAccount(acc1);

        // 3. Deposit to account 1
        acc1.deposit(200, "Salary deposit");
        logger.log(Level.INFO, "Acc1 balance after deposit: {0}", acc1.getBalance());

        // 4. Withdraw from account 1
        acc1.withdraw(100, "Buy groceries");
        logger.log(Level.INFO, "Acc1 balance after withdrawl: {0}", acc1.getBalance());

        // 5 Tranfer from acc1 to acc2
        try {
            acc1.transferBetweenAccounts("AccessBank", 300);
            logger.log(Level.INFO, "Transfer successful");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Transfer failed: {0}", e.getMessage());
        }

        // Transfer from acc2 to acc1
        try {
            acc2.transferBetweenAccounts("GTBank", 100);
            logger.log(Level.INFO, "Transfer successful");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Transfer failed: {0}", e.getMessage());
        }

        logger.log(Level.INFO, "Acc1 new balance: {0}", acc1.getBalance());
        logger.log(Level.INFO, "Acc2 new balance: {0}", acc2.getBalance());

        // 6 Lock and try withdraw (should fail)
        acc1.lockAccount();
        try {
            acc1.withdraw(50, "Try withdraw while locked");
        } catch (SecurityException se) {
            logger.log(Level.WARNING, "Withdraw failed: {0}", se.getMessage());
        }

        // 7 Unlock with wrong and correct codes
        logger.log(Level.INFO, "Unlock with wrong code: {0}", acc1.unlockAccount("2345"));
        logger.log(Level.INFO, "Unlock with correct code: {0}", acc1.unlockAccount("1234"));

        // 8 Consolidated balance
        double[] consolidated = acc1.getConsolatedBalance();
        logger.log(Level.INFO, "Total balance (All Linked): {0}", consolidated[0]);
        logger.log(Level.INFO, "Total Savings (All Linked): {0}", consolidated[1]);

        // Save primary account to file
        BankService.saveAccountToFile(acc1, "account_backup.json");

        // Load account from file and show final balance
        BankAccount restored = BankService.loadAccountFromFile("account_backup.json");
        if (restored != null) {
            logger.log(Level.INFO, "Restored balance: {0}", restored.getBalance());
            logger.log(Level.INFO, "Restored savings: {0}", restored.getSavings());
        } else {
            logger.log(Level.SEVERE, "Failed to restore account from file.");
        }

        // Create a list of sample transactions
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(100.0, "Food", "WITHDRAW"));
        transactions.add(new Transaction(500.0, "Salary", "DEPOSIT"));
        transactions.add(new Transaction(200.0, "Bills", "WITHDRAW"));
        transactions.add(new Transaction(50.0, "Snacks", "WITHDRAW"));
        transactions.add(new Transaction(100.0, "Gift", "DEPOSIT"));

        // Create the AI service instance
        AiService aiService = new AiService();

        // Test smart budgeting
        String analysis = Ai.analyzeTransactions(aiService, transactions);
        logger.log(Level.INFO, "AI Analysis:\n{0}", analysis);
        logger.log(Level.INFO, "AI Analysis:\n{0}", analysis);

        // Test fraud detection
        double transactionAmount = 3000.0;
        double avgAccountTransaction = 200.0;

        boolean isFraud = aiService.fraudDetection(transactionAmount, avgAccountTransaction);
        if (isFraud) {
            logger.log(Level.WARNING, "FRAUD DETECTED!");
        } else {
            logger.log(Level.INFO, "Transaction is safe.");
        }

        // Borrow loan
        LoanManager loanManager = new LoanManager(acc1);
        loanManager.borrow(2000, "Yearly");
        loanManager.repayLoan(2000);

    }
}
