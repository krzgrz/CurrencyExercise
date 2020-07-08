package com.gmail.krzgrz.demo.service;

import com.gmail.krzgrz.demo.domain.AccountRegistration;
import com.gmail.krzgrz.demo.domain.PESEL;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRestControllerTest {

    @Test
    void testIsAgeEligibleOK () {
        ExchangeRestController uut = new ExchangeRestController ();
        AccountRegistration accountRegistration = new AccountRegistration (new PESEL ("99032100000"), "Henryk", "Sienkiewicz", new BigDecimal (123));
        assertEquals(true, uut.isAgeEligible(accountRegistration, new Date ()));
    }

    @Test
    void testIsAgeEligibleTooYoung () throws ParseException {
        ExchangeRestController uut = new ExchangeRestController ();
        AccountRegistration accountRegistration = new AccountRegistration (new PESEL ("99032100000"), "Henryk", "Sienkiewicz", new BigDecimal (123));
        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        assertEquals(false, uut.isAgeEligible(accountRegistration, df.parse("2010-03-20 00:00:00")));
    }

    @Test
    void testIsAgeEligibleTooYoungEdge () throws ParseException {
        ExchangeRestController uut = new ExchangeRestController ();
        AccountRegistration accountRegistration = new AccountRegistration (new PESEL ("99032100000"), "Henryk", "Sienkiewicz", new BigDecimal (123));
        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        assertEquals(false, uut.isAgeEligible(accountRegistration, df.parse("2017-03-20 23:59:59")));
    }

    @Test
    void testIsAgeEligibleOKEdge () throws ParseException {
        ExchangeRestController uut = new ExchangeRestController ();
        AccountRegistration accountRegistration = new AccountRegistration (new PESEL ("99032100000"), "Henryk", "Sienkiewicz", new BigDecimal (123));
        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        assertEquals(true, uut.isAgeEligible(accountRegistration, df.parse("2017-03-21 00:00:00")));
    }
}