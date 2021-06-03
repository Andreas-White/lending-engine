package com.peerlender.lendingengine.domain.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Balance {

    @Id
    @GeneratedValue
    private long id;

    //@ElementCollection
    //@MapKeyClass(Currency.class)
    @MapKeyEnumerated(EnumType.STRING)
    @OneToMany(targetEntity = Money.class, cascade = CascadeType.ALL)
    private final Map<Currency,Money> moneyMap = new HashMap<>();

    public void topUp(final Money money) {
        if (moneyMap.get(money.getCurrency()) == null) {
            moneyMap.put(money.getCurrency(),money);
        }
        else {
            moneyMap.put(money.getCurrency(), moneyMap.get(money.getCurrency()).add(money));
        }
    }

    public void withdraw( final Money money) {
        final Money moneyInBalance = moneyMap.get(money.getCurrency());
        if (moneyInBalance == null)
            throw new IllegalStateException();
        else
            moneyMap.put(money.getCurrency(), moneyMap.get(money.getCurrency()).remove(money));
    }

    public long getId() {
        return id;
    }

    public Map<Currency, Money> getMoneyMap() {
        return moneyMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return id == balance.id && Objects.equals(moneyMap, balance.moneyMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moneyMap);
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", moneyMap=" + moneyMap +
                '}';
    }
}
