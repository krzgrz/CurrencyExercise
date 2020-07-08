package com.gmail.krzgrz.demo.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Wraps a string representation of a PESEL to provide some basic validation and parsing.
 */
public class PESEL {

    private static final String pattern = "\\d{11}";

    /** Timezone for extracting DOB from the PESEL. */
    public static final TimeZone timeZone = TimeZone.getTimeZone("Europe/Warsaw");

    @JsonValue
    private String pesel;

    public PESEL (String pesel) {
        if ( ! pesel.matches(pattern)) {
            throw new IllegalArgumentException ("PESEL must consist of 11 digits.");
        }
        // TODO: check checksum
        this.pesel = pesel;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PESEL pesel1 = (PESEL) o;
        return pesel.equals(pesel1.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

    public String toString () {
        return pesel;
    }

    /**
     * Extracts person's date of bit\rth from this PESEL.
     * @return  Midnight in the selected {@link #timeZone} on the person's DOB.
     */
    public Date getDateOfBirth () {
        String date = pesel.substring(0, 6);
        Integer year = Integer.parseInt(pesel.substring(0, 2));
        Integer month = Integer.parseInt(pesel.substring(2, 4));
        Integer day = Integer.parseInt(pesel.substring(4, 6));
        Integer [] centuries = {1900, 2000, 2100, 2200, 1800};
        for (int i = 0; i < centuries.length; i++) {
            if ((month - i * 20 >= 1) && (month - i * 20 <= 12)) {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTimeZone(timeZone);
                calendar.set(centuries[i] + year, month - i * 20 - 1, day, 0, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                return new Date (calendar.getTimeInMillis());
            }
        }
        throw new IllegalArgumentException ();
    }
}
