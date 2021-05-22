package com.peerlender.lendingengine.application.model;

import java.util.Objects;

public final class LoanRequest {

    private final int amount;
    private final long borrowerId;
    private final long repaymentTerm;
    private final double interestRate;

    public LoanRequest(int amount, long borrowerId, int repaymentTerm, double interestRate) {
        this.amount = amount;
        this.borrowerId = borrowerId;
        this.repaymentTerm = repaymentTerm;
        this.interestRate = interestRate;
    }

    public int getAmount() {
        return amount;
    }

    public long getBorrowerId() {
        return borrowerId;
    }

    public long getRepaymentTerm() { return repaymentTerm; }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanRequest that = (LoanRequest) o;
        return amount == that.amount && borrowerId == that.borrowerId && repaymentTerm == that.repaymentTerm && Double.compare(that.interestRate, interestRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, borrowerId, repaymentTerm, interestRate);
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
                "amount=" + amount +
                ", borrowerId=" + borrowerId +
                ", daysToRepay=" + repaymentTerm +
                ", interestRate=" + interestRate +
                '}';
    }
}
