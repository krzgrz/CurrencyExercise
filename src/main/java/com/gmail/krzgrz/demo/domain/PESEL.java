package com.gmail.krzgrz.demo.domain;

import java.util.Date;
import java.util.regex.Pattern;

public class PESEL {

    private static final String pattern = "\\d+";

    private String pesel;

    public PESEL (String pesel) {
        if ( ! pesel.matches(pattern)) {
            throw new IllegalArgumentException ("PESEL must consist of 11 digits.");
        }
        // TODO: check checksum
        this.pesel = pesel;
    }

    public String toString () {
        return pesel;
    }

    public Date getDateOfBirth () {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
