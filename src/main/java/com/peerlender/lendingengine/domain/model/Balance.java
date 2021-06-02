package com.peerlender.lendingengine.domain.model;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Balance {

    private Map<Currency,Money> moneyMap = new HashMap<>();

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
}
