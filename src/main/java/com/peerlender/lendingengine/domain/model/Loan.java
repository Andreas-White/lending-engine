package com.peerlender.lendingengine.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    private int Id;
    @ManyToOne
    private User borrower;
    @ManyToOne
    private User lender;
    private int amount;
    private double interestRate;
    private LocalDate dateLent;
    private LocalDate dateDue;

    public Loan() {}

    public Loan(User lender, LoanApplication loanApplication) {
        this.borrower = loanApplication.getBorrower();
        this.lender = lender;
        this.amount = loanApplication.getAmount();
        this.interestRate = loanApplication.getInterestRate();
        this.dateLent = LocalDate.now();
        this.dateDue = LocalDate.now().plusDays(loanApplication.getRepaymentTerm());
    }

    public int getId() {
        return Id;
    }

    public User getBorrower() {
        return borrower;
    }

    public User getLender() {
        return lender;
    }

    public int getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public LocalDate getDateLent() {
        return dateLent;
    }

    public LocalDate getDateDue() {
        return dateDue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Id == loan.Id && amount == loan.amount && Double.compare(loan.interestRate, interestRate) == 0 && Objects.equals(borrower, loan.borrower) && Objects.equals(lender, loan.lender) && Objects.equals(dateLent, loan.dateLent) && Objects.equals(dateDue, loan.dateDue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, borrower, lender, amount, interestRate, dateLent, dateDue);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "Id=" + Id +
                ", borrower=" + borrower +
                ", lender=" + lender +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", dateLent=" + dateLent +
                ", dateDue=" + dateDue +
                '}';
    }
}
