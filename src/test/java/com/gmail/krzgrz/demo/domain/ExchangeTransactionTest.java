package com.gmail.krzgrz.demo.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeTransactionTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testConstructor () {
        logger.info("testConstructor");
        ExchangeTransaction uut = new ExchangeTransaction (Currency.PLN, new BigDecimal (3), Currency.USD, null);
    }

    @Test
    public void testPLN2USD () {
        logger.info("testPLN2USD");
        ExchangeTransaction uut = new ExchangeTransaction (Currency.PLN, new BigDecimal (3), Currency.USD, null);
        uut.setExchangeRate(new BigDecimal (4));
        uut.setRateDirection(ExchangeTransaction.RateDirection.BOUGHT_VS_SOLD);
        uut.setExchangeTimestamp(new Date());
        //
        assertEquals(new BigDecimal (0.75), uut.getAmountBought());
    }
}