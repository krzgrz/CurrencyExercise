package com.gmail.krzgrz.demo.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}