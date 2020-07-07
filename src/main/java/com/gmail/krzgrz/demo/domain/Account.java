package com.gmail.krzgrz.demo.domain;


import java.math.BigDecimal;

/**
 * Represents a complete account for one customer, encompassing the account metadata, history and balances.
 */
public class Account {

    AccountRegistration accountRegistration;
    private BigDecimal balancePLN;
    private BigDecimal balanceUSD;

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
