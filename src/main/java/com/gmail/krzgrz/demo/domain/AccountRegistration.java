package com.gmail.krzgrz.demo.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * This class keeps account metadata, such as first/last name.
 * @author kgrzeda
 */
public class AccountRegistration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PESEL pesel;
    private String firstName;
    private String lastName;

    /**
     * Initial account balance in PLN.
     * TODO: that does not really belong in here, think of moving it somewhere.
     */
    private BigDecimal initialBalancePLN;

    public AccountRegistration (){
    }

    public AccountRegistration (PESEL pesel, String firstName, String lastName, BigDecimal initialBalancePLN) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.initialBalancePLN = initialBalancePLN;
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

    public BigDecimal getInitialBalancePLN() {
        return initialBalancePLN;
    }
}
