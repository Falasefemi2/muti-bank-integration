package com.example.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.loan.LoanManager;
import com.example.transaction.Transaction;

public class BankAccount implements BankInterface {
    private double balance;
    private double savings;
    private String accountName;
    private String provider;
    private double autoSavingPercent = 0.05;
    private boolean isLocked = false;
    private List<BankAccount> linkedAccounts;
    private List<Transaction> transactionHistory;
    private LoanManager loanManager;
    private ReentrantLock lock;

    // Constructor
    public BankAccount(double initialAmount, String accountName) {
        this.balance = initialAmount;
        this.accountName = accountName;
        this.linkedAccounts = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
        // this.loanManager = new LoanManager(this);
        this.lock = new ReentrantLock();
    }

    public double getSavings() {
        return savings;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getBalance() {
        return balance;
    }

    public LoanManager getLoanManager() {
        return loanManager;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public void deposit(double amount, String description) throws SecurityException {
        lock.lock();
        try {
            if (isLocked) {
                throw new SecurityException("Account is locked");
            }
            double savingAmount = amount * autoSavingPercent;
            this.savings += savingAmount;
            this.balance += (amount - savingAmount);
            transactionHistory.add(new Transaction(amount, description, "DEPOSIT"));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(double amount, String description) throws SecurityException {
        lock.lock();
        try {
            if (isLocked) {
                throw new SecurityException("Account is locked");
            }
            if (balance < amount) {
                throw new IllegalStateException("Insufficient funds");
            }
            this.balance -= amount;
            transactionHistory.add(new Transaction(amount, description, "WITHDRAWAL"));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void linkAccount(BankAccount account) {
        linkedAccounts.add(account);
    }

    @Override
    public void transferBetweenAccounts(String targetProvider, double amount) throws Exception {
        lock.lock();
        try {
            if (isLocked)
                throw new SecurityException("Account is locked");
            BankAccount target = linkedAccounts.stream()
                    .filter(acc -> acc.getProvider().equals(targetProvider))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Target account not found"));
            this.withdraw(amount, "Transfer to " + targetProvider);
            target.deposit(amount, "Transfer from " + this.provider);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public double[] getConsolatedBalance() {
        lock.lock();
        try {
            double totalBalance = this.balance;
            double totalSavings = this.savings;
            for (BankAccount acc : linkedAccounts) {
                totalBalance += acc.getBalance();
                totalSavings += acc.getSavings();
            }
            return new double[] { totalBalance, totalSavings };
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void lockAccount() {
        this.isLocked = true;
    }

    @Override
    public boolean unlockAccount(String verificationCode) {
        if (verificationCode.equals("1234")) {
            this.isLocked = false;
            return true;
        }
        return false;
    }

    @Override
    public JSONObject seriliaze() {
        lock.lock();
        try {
            JSONObject json = new JSONObject();
            json.put("balance", balance);
            json.put("savings", savings);
            json.put("accountName", accountName);
            json.put("provider", provider);
            // boolean autoSavingPercent;
            json.put("autoSavingsPercent", autoSavingPercent);
            json.put("isLocked", isLocked);

            JSONArray transactionsArray = new JSONArray();
            for (Transaction t : transactionHistory) {
                transactionsArray.put(t.serialize());
            }
            json.put("transactionHistory", transactionsArray);

            JSONArray linkedArray = new JSONArray();
            for (BankAccount acc : linkedAccounts) {
                JSONObject linkedJson = new JSONObject();
                linkedJson.put("accountName", acc.accountName);
                linkedJson.put("provider", acc.provider);
                linkedJson.put("balance", acc.balance);
                linkedArray.put(linkedJson);
            }
            json.put("linkedAccounts", linkedArray);

            if (this.loanManager != null) {
                json.put("loanManager", this.loanManager.serialize());
            } else {
                json.put("loanManager", JSONObject.NULL);
            }
            return json;
        } finally {
            lock.unlock();
        }
    }

    // Removed duplicate deserialize method to resolve the error.
    public static BankAccount deserialize(JSONObject json) {
        BankAccount acc = new BankAccount(
                json.getDouble("balance"), // use "balance", not "initialAmount"
                json.getString("accountName"));
        acc.savings = json.getDouble("savings");
        acc.provider = json.getString("provider");
        acc.autoSavingPercent = json.getDouble("autoSavingsPercent");
        acc.isLocked = json.getBoolean("isLocked");

        // You can also restore transactionHistory, linkedAccounts, etc.

        return acc;
    }

}
