package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.ai.Ai;
import com.example.ai.AiService;
import com.example.bank.BankAccount;
import com.example.bank.BankService;
import com.example.transaction.Transaction;

public class Main {
    public static void main(String[] args) {
        // Create primary account
        BankAccount primary = new BankAccount(1000, "Primary Account");
        primary.setProvider("PrimaryBank");

        // Create and link 4 secondary accounts
        for (int i = 1; i <= 4; i++) {
            BankAccount acc = new BankAccount(1000 * i, "Account " + i);
            acc.setProvider("Bank" + i);
            acc.deposit(500, "Initial top-up for Account " + i);

            // Link each to the primary account
            primary.linkAccount(acc);

            // Also link primary to them so transfers will work
            acc.linkAccount(primary);

            // Each account sends 300 to the primary
            try {
                acc.transferBetweenAccounts("PrimaryBank", 300);
            } catch (Exception e) {
                System.err.println("Transfer failed for Account " + i + ": " + e.getMessage());
            }
        }

        // Save primary account to file
        BankService.saveAccountToFile(primary, "account_backup.json");

        // Load account from file and show final balance
        BankAccount restored = BankService.loadAccountFromFile("account_backup.json");
        if (restored != null) {
            System.out.printf("✅ Restored balance: $%.2f%n", restored.getBalance());
            System.out.printf("✅ Restored savings: $%.2f%n", restored.getSavings());
        } else {
            System.err.println("❌ Failed to restore account from file.");
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
        System.out.println("🧠 AI Analysis:");
        System.out.println(analysis); // Should say something like: You made 3 withdraws this period

        // Test fraud detection
        double transactionAmount = 3000.0;
        double avgAccountTransaction = 200.0;

        boolean isFraud = aiService.fraudDetection(transactionAmount, avgAccountTransaction);
        if (isFraud) {
            System.out.println("⚠️ FRAUD DETECTED!");
        } else {
            System.out.println("✅ Transaction is safe.");
        }

    }
}
