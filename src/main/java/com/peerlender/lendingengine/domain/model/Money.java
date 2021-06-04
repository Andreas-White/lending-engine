package com.peerlender.lendingengine.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
public final class Money {

    public static final Money ZERO = new Money(Currency.USD, 0);

    @Id
    @GeneratedValue
    private long id;
    private Currency currency;
    private BigDecimal amount;

    public Money() {}

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_DOWN);
    }

    public Money add(final Money money) {
        if (this.currency != money.getCurrency())
            throw new IllegalArgumentException();
        return new Money(this.currency,this.amount.doubleValue() + money.getAmount());
    }

    public Money remove(final Money money) {
        if (this.currency != money.getCurrency() || money.getAmount() > this.amount.doubleValue())
            throw new IllegalArgumentException();
        return new Money(this.currency,amount.doubleValue() - money.getAmount());
    }

    public Money times(final double multiplier) {
        return new Money(Currency.USD, this.amount.doubleValue() * multiplier);
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount.doubleValue(), amount.doubleValue()) == 0 && currency == money.currency;
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
