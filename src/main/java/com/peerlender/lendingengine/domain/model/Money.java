package com.peerlender.lendingengine.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public final class Money {

    @Id
    @GeneratedValue
    private long id;
    private Currency currency;
    private double amount;

    public Money() {}

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Money add(final Money money) {
        if (this.currency != money.getCurrency())
            throw new IllegalArgumentException();
        return new Money(this.currency,this.amount + money.getAmount());
    }

    public Money remove(final Money money) {
        if (this.currency != money.getCurrency() || money.getAmount() > this.amount)
            throw new IllegalArgumentException();
        return new Money(this.currency,amount - money.getAmount());
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount, amount) == 0 && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
