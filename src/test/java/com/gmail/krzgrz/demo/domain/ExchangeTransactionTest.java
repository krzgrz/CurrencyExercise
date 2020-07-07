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
    public void testSellPLN2USD () {
        logger.info("testSellPLN2USD");
        ExchangeTransaction uut = new ExchangeTransaction (Currency.PLN, new BigDecimal (3.0), Currency.USD, null);
        uut.setExchangeRate(new BigDecimal (4));
        uut.setRateDirection(ExchangeTransaction.RateDirection.BOUGHT_VS_SOLD);
        //
        assertEquals(new BigDecimal (3.0).setScale(2), uut.getAmountSold());
        assertEquals(null, uut.getAmountBought());
        //
        uut.setExchangeTimestamp(new Date());
        //
        assertEquals(new BigDecimal (3.0).setScale(2), uut.getAmountSold());
        assertEquals(new BigDecimal (0.75).setScale(2), uut.getAmountBought());
        assertTrue(uut.getExchangeTimestamp() != null);
    }

    @Test
    public void testBuyPLN2USD () {
        logger.info("testBuyPLN2USD");
        ExchangeTransaction uut = new ExchangeTransaction (Currency.PLN, null, Currency.USD, new BigDecimal (0.75));
        uut.setExchangeRate(new BigDecimal (4));
        uut.setRateDirection(ExchangeTransaction.RateDirection.BOUGHT_VS_SOLD);
        //
        assertEquals(null, uut.getAmountSold());
        assertEquals(new BigDecimal (0.75).setScale(2), uut.getAmountBought());
        //
        uut.setExchangeTimestamp(new Date());
        //
        assertEquals(new BigDecimal (3.0).setScale(2), uut.getAmountSold());
        assertEquals(new BigDecimal (0.75).setScale(2), uut.getAmountBought());
        assertTrue(uut.getExchangeTimestamp() != null);
    }

    @Test
    public void testSellUSD2PLN () {
        logger.info("testSellUSD2PLN");
        ExchangeTransaction uut = new ExchangeTransaction (Currency.USD, new BigDecimal (3.0), Currency.PLN, null);
        uut.setExchangeRate(new BigDecimal (4));
        uut.setRateDirection(ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT);
        //
        assertEquals(new BigDecimal (3.0).setScale(2), uut.getAmountSold());
        assertEquals(null, uut.getAmountBought());
        //
        uut.setExchangeTimestamp(new Date());
        //
        assertEquals(new BigDecimal (3.0).setScale(2), uut.getAmountSold());
        assertEquals(new BigDecimal (12).setScale(2), uut.getAmountBought());
        assertTrue(uut.getExchangeTimestamp() != null);
    }

    @Test
    public void testBuyUSD2PLN () {
        logger.info("testBuyUSD2PLN");
        ExchangeTransaction uut = new ExchangeTransaction (Currency.USD, null, Currency.PLN, new BigDecimal (12));
        uut.setExchangeRate(new BigDecimal (4));
        uut.setRateDirection(ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT);
        //
        assertEquals(null, uut.getAmountSold());
        assertEquals(new BigDecimal (12).setScale(2), uut.getAmountBought());
        //
        uut.setExchangeTimestamp(new Date());
        //
        assertEquals(new BigDecimal (3.0).setScale(2), uut.getAmountSold());
        assertEquals(new BigDecimal (12).setScale(2), uut.getAmountBought());
        assertTrue(uut.getExchangeTimestamp() != null);
    }
}