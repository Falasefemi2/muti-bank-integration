package com.example.bank;

import org.json.JSONObject;

public interface BankInterface {
    void deposit(double amount, String description) throws SecurityException;

    void withdraw(double amount, String description) throws SecurityException;

    void linkAccount(BankAccount account);

    void transferBetweenAccounts(String targetProvider, double amount) throws Exception;

    double[] getConsolatedBalance();

    void lockAccount();

    boolean unlockAccount(String verificationCode);

    JSONObject seriliaze();
}
