package com.gmail.krzgrz.demo.domain;


import java.math.BigDecimal;
import java.util.*;

/**
 * Represents a complete account for one customer, encompassing the account metadata, history and balances.
 */
public class Account {

    private AccountRegistration accountRegistration;

    private List <ExchangeTransaction> history = new ArrayList ();

    /** Returns metadata for this account. */
    public AccountRegistration getAccountRegistration () {
        return accountRegistration;
    }

    /** Sets metadata for this account. */
    public void setAccountRegistration (AccountRegistration accountRegistration) {
        this.accountRegistration = accountRegistration;
    }

    public List <ExchangeTransaction> getHistory () {
        return Collections.unmodifiableList(history);
    }

    public void addExchangeTransaction (ExchangeTransaction exchangeTransaction) {
        history.add(exchangeTransaction);
    }

    public AccountSummary getAccountSummary () {
        Map <Currency, BigDecimal> balances = new HashMap ();
        Currency pln = Currency.getInstance("PLN");
        Currency usd = Currency.getInstance("USD");
        balances.put(pln, new BigDecimal (0));
        balances.put(usd, new BigDecimal (0));
        for (ExchangeTransaction et : history) {
            balances.put(pln, balances.get(pln).add(et.getSignedAmount(pln)));
            balances.put(usd, balances.get(usd).add(et.getSignedAmount(usd)));
        }
        return new AccountSummary (
            accountRegistration.getPesel(),
            accountRegistration.getFirstName(),
            accountRegistration.getLastName(),
            balances.get(pln),
            balances.get(usd)
        );
    }
}
