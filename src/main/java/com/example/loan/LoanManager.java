package com.example.loan;

import org.json.JSONObject;

import com.example.bank.BankAccount;

public class LoanManager implements LoanInterface {
    private BankAccount account;
    private double loanBalance;

    public LoanManager(BankAccount account) {
        this.account = account;
    }

    public double getLoanBalance() {
        return loanBalance;
    }

    @Override
    public boolean borrow(double amount, String loanType) {
        if (loanBalance > 0) {
            return false;
        }
        this.loanBalance += amount;
        account.deposit(amount, "Loan: " + loanType);
        return true;
    }

    @Override
    public boolean repayLoan(double amount) {
        if (loanBalance <= 0 || amount > account.getBalance())
            return false;
        account.withdraw(amount, "Loan repayment");
        loanBalance -= amount;
        return true;
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("loanBalance", loanBalance);
        return json;
    }

}
