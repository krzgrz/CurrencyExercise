package com.gmail.krzgrz.demo.domain;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete account for one customer, encompassing the account metadata, history and balances.
 */
public class Account {

    private AccountRegistration accountRegistration;

    private List <ExchangeTransaction> history = new ArrayList ();

    private BigDecimal balancePLN;
    private BigDecimal balanceUSD;

    /** Returns metadata for this account. */
    public AccountRegistration getAccountRegistration () {
        return accountRegistration;
    }

    /** Sets metadata for this account. */
    public void setAccountRegistration (AccountRegistration accountRegistration) {
        this.accountRegistration = accountRegistration;
    }

    /**
     * Returns current account balance in PLN.
     * @return  Never null but may be 0.
     */
    public BigDecimal getBalancePLN () {
        return balancePLN;
    };

    public void setBalancePLN (BigDecimal balancePLN) {
        this.balancePLN = balancePLN;
    };

    /**
     * Returns current account balance in USD.
     * @return  Never null but may be 0.
     */
    public BigDecimal getBalanceUSD () {
        return balanceUSD;
    }

    public void setBalanceUSD (BigDecimal balanceUSD) {
        this.balanceUSD = balanceUSD;
    }
}
