package com.gmail.krzgrz.demo.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeTransactionTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Currency usd = Currency.getInstance("USD");
    private Currency pln = Currency.getInstance("PLN");

    @Test
    public void testConstructor () {
        logger.info("testConstructor");
        ExchangeTransaction uut = new ExchangeTransaction (pln, new BigDecimal (3), usd, null);
    }

    @Test
    public void testSellPLN2USD () {
        logger.info("testSellPLN2USD");
        ExchangeTransaction uut = new ExchangeTransaction (pln, new BigDecimal (3.0), usd, null);
        uut.setExchangeRate(new BigDecimal (0.25));
//        assertEquals(ExchangeTransaction.RateDirection.BOUGHT_VS_SOLD, uut.getRateDirection());
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
        ExchangeTransaction uut = new ExchangeTransaction (pln, null, usd, new BigDecimal (0.75));
        uut.setExchangeRate(new BigDecimal (4));
        assertEquals(ExchangeTransaction.RateDirection.BOUGHT_VS_SOLD, uut.getRateDirection());
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
        ExchangeTransaction uut = new ExchangeTransaction (usd, new BigDecimal (3.0), pln, null);
        uut.setExchangeRate(new BigDecimal (4));
        assertEquals(ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT, uut.getRateDirection());
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
        ExchangeTransaction uut = new ExchangeTransaction (usd, null, pln, new BigDecimal (12));
        uut.setExchangeRate(new BigDecimal (0.25));
//        assertEquals(ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT, uut.getRateDirection());
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