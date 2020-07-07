package com.gmail.krzgrz.demo.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Wraps a string representation of a PESEL to provide some basic validation and parsing.
 */
public class PESEL {

    private static final String pattern = "\\d+";

    /** Date format needed to extract DOB from the PESEL. */
    private static final DateFormat dateFormat = new SimpleDateFormat ("yyMMdd");

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
