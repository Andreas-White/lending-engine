package com.peerlender.lendingengine.domain.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private User borrower;
    @ManyToOne
    private User lender;
    @OneToOne(cascade = CascadeType.ALL)
    private Money amount;
    private double interestRate;
    private LocalDate dateLent;
    private LocalDate dateDue;
    @OneToOne(cascade = CascadeType.ALL)
    private Money amountRepaid;

    public Loan() {}

    public Loan(User lender, LoanApplication loanApplication) {
        this.borrower = loanApplication.getBorrower();
        this.lender = lender;
        this.amount = loanApplication.getAmount();
        this.interestRate = loanApplication.getInterestRate();
        this.dateLent = LocalDate.now();
        this.dateDue = LocalDate.now().plusDays(loanApplication.getRepaymentTerm());
        this.amountRepaid = Money.ZERO;
    }

    public void repay(final Money money) {
        borrower.withdraw(money);
        lender.topUp(money);
        amountRepaid = amountRepaid.add(money);
    }

    public int getId() {
        return id;
    }

    public User getBorrower() {
        return borrower;
    }

    public User getLender() {
        return lender;
    }

    public Money getAmountRepaid() {
        return amountRepaid;
    }

    public Money getAmountOwed() {
        return this.amount.times(1 + interestRate/100d).remove(amountRepaid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id && Double.compare(loan.interestRate, interestRate) == 0 && Objects.equals(borrower, loan.borrower) && Objects.equals(lender, loan.lender) && Objects.equals(amount, loan.amount) && Objects.equals(dateLent, loan.dateLent) && Objects.equals(dateDue, loan.dateDue) && Objects.equals(amountRepaid, loan.amountRepaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, borrower, lender, amount, interestRate, dateLent, dateDue, amountRepaid);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "Id=" + id +
                ", borrower=" + borrower +
                ", lender=" + lender +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", dateLent=" + dateLent +
                ", dateDue=" + dateDue +
                ", amountRepaid=" + amountRepaid +
                '}';
    }
}
