package com.gmail.krzgrz.demo.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class PESELTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testConstructor () {
        logger.info("testConstructor");
        PESEL uut = new PESEL ("12345678901"); // may start failing once checksum check is implemented
    }

    @Test
    public void testConstructorTooShort () {
        logger.info("testConstructorTooShort");
        try {
            PESEL uut = new PESEL("1234567890"); // too short
            fail("Missing exception.");
        } catch (IllegalArgumentException ex) {
            // correct behavior
        } catch (Exception ex) {
            fail("Invalid exception.");

        }
    }

    @Test
    public void testConstructorTooLong () {
        logger.info("testConstructorTooLong");
        try {
            PESEL uut = new PESEL("123456789012"); // too long
            fail("Missing exception.");
        } catch (IllegalArgumentException ex) {
            // correct behavior
        } catch (Exception ex) {
            fail("Invalid exception.");
        }
    }

    @Test
    public void testDateOfBirth () {
        logger.info("testDateOfBirth");
        PESEL uut = new PESEL("12110600000");
        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        assertEquals("1912-11-06 00", df.format(uut.getDateOfBirth()));
    }

    @Test
    public void testDateOfBirth21 () {
        logger.info("testDateOfBirth21");
        PESEL uut = new PESEL("12310600000");
        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        assertEquals("2012-11-06 00", df.format(uut.getDateOfBirth()));
    }

    @Test
    public void testDateOfBirthSummer () {
        logger.info("testDateOfBirthSummer");
        PESEL uut = new PESEL("12280600000");
        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        assertEquals("2012-08-06 00", df.format(uut.getDateOfBirth()));
    }
}