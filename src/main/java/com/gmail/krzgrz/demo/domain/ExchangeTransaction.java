package com.gmail.krzgrz.demo.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author kgrzeda
 */
public class ExchangeTransaction {
    
    Currency currencySold;
    BigDecimal amountSold;

    Currency currencyBought;
    BigDecimal amountBought;

    public ExchangeTransaction (Currency currencySold, BigDecimal amountSold, Currency currencyBought, BigDecimal amountBought) {
        this.currencySold = currencySold;
        this.currencyBought = currencyBought;
        this.amountSold = amountSold;
        this.amountBought = amountBought;
    }

    Double exchangeRate;

    Date exchangeTime;
    Boolean rateDirection;

    public BigDecimal getSignedAmount (Currency currency) {
        if (currency == currencySold) {
            return amountSold != null ? amountSold.negate() : null;
        } else if (currency == currencyBought) {
            return amountBought;
        }
        throw new IllegalArgumentException ("Invalid currency.");
    }


    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public void setRateDirection(Boolean rateDirection) {
        this.rateDirection = rateDirection;
    }

    public boolean isProperlyOrdered () {
        if ((currencySold == null) || (currencyBought == null)) {
            return false;
        }
        // Exactly one amount must be specified in a properly ordered exchange transaction...
        if ((amountSold != null) == (amountBought != null)) {
            return false;
        }
        if ((exchangeRate != null) || (exchangeTime != null) && (rateDirection != null)) {
            return false;
        }
        return true;
    }

}
