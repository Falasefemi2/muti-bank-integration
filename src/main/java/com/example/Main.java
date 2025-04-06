package com.example;

import com.example.bank.BankAccount;
import com.example.bank.BankService;

public class Main {
    public static void main(String[] args) {
        BankAccount primary = new BankAccount(2000, "primary account");
        primary.setProvider("Warpspeed");
        primary.deposit(1000, "Salary deposit");
        primary.withdraw(200, "food");

        BankAccount secondaryAccount = new BankAccount(4000, "secondaryAccount");
        secondaryAccount.deposit(2000, " Bills");

        primary.linkAccount(secondaryAccount);

        try {
            secondaryAccount.transferBetweenAccounts(primary.getProvider(), 300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BankService.saveAccountToFile(primary, "account_backup.json");

        BankAccount restored = BankService.loadAccountFromFile("account_backup.json");
        if (restored != null) {
            System.out.printf("Restored balance: $%.2f%n", restored.getBalance());

        }
    }
}