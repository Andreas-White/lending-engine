package com.peerlender.lendingengine.domain.exception;

public class LoanNotFoundException extends RuntimeException{

    public LoanNotFoundException(int loanId) {
        System.out.println("Loan with id: " + loanId + " not found");
    }
}
