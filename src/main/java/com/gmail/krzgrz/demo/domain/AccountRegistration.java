package com.gmail.krzgrz.demo.domain;

import java.math.BigDecimal;

/**
 *
 * @author kgrzeda
 */
public class AccountRegistration {

    private String pesel;

    private String firstName;

    private String lastName;

    private BigDecimal initialBalancePLN;

    public String getPesel() {
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

    public AccountRegistration(){

    }

    public AccountRegistration(String pesel, String firstName, String lastName, BigDecimal initialBalancePLN) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.initialBalancePLN = initialBalancePLN;
    }

}
