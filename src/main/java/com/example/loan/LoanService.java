package com.example.loan;

public class LoanService {
    public static String getLoanStatus(LoanManager loanManager) {
        return String.format("Loan Balance: $%.2f", loanManager.getLoanBalance());
    }
}
