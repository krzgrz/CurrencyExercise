package com.gmail.krzgrz.demo.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/** A lightweight DTO for an {@link Account}. */
public class AccountSummary {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PESEL pesel;
    private String firstName;
    private String lastName;
    private BigDecimal balancePLN;
    private BigDecimal balanceUSD;

    public AccountSummary(PESEL pesel, String firstName, String lastName, BigDecimal balancePLN, BigDecimal balanceUSD) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balancePLN = balancePLN;
        this.balanceUSD = balanceUSD;
    }

    public PESEL getPesel() {
        return pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getBalancePLN() {
        return balancePLN;
    }

    public BigDecimal getBalanceUSD() {
        return balanceUSD;
    }
}
