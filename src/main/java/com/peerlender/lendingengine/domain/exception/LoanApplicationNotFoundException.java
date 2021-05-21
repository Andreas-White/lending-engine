package com.peerlender.lendingengine.domain.exception;

public class LoanApplicationNotFoundException extends RuntimeException{

    public LoanApplicationNotFoundException(long loanApplicationId) {
        System.out.println("Loan application with id: " + loanApplicationId + " not found");
    }
}
