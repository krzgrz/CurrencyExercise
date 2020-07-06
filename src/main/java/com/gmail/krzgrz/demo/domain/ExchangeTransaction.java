package com.gmail.krzgrz.demo.domain;

import java.util.Date;

/**
 *
 * @author kgrzeda
 */
public class ExchangeTransaction {
    
    Currency currencySold;
    Currency currencyBought;
    Long amountSold;
    Long amountBought;

    Double exchangeRate;
    Date exchangeTime;
    boolean exchange;
}
