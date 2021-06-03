package com.peerlender.lendingengine.application.model;

import com.peerlender.lendingengine.domain.model.Currency;
import com.peerlender.lendingengine.domain.model.Money;

import java.util.Objects;

public final class LoanRepaymentRequest {

    private final double amount;
    private final int loanId;

    public LoanRepaymentRequest(double amount, int loanId) {
        this.amount = amount;
        this.loanId = loanId;
    }

    public Money getAmount() {
        return new Money(Currency.USD, this.amount);
    }

    public int getLoanId() {
        return loanId;
    }

    @Override
    public String toString() {
        return "LoanRepaymentRequest{" +
                "amount=" + amount +
                ", loanId=" + loanId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanRepaymentRequest that = (LoanRepaymentRequest) o;
        return Double.compare(that.amount, amount) == 0 && loanId == that.loanId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, loanId);
    }
}
