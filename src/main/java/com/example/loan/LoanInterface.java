package com.example.loan;

import org.json.JSONObject;

public interface LoanInterface {
    boolean borrow(double amount, String loanType);

    boolean repayLoan(double amount);

    JSONObject serialize();
}
