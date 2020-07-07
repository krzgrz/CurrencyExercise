package com.gmail.krzgrz.demo.domain;

import javax.print.attribute.standard.MediaSizeName;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

/**
 * An instance of this class represents one transaction of exchanging one currency for another.
 * <p>
 * It may be in one of three states: ordered, ready or completed.
 * </p><p>
 * An {@link ExchangeTransaction} is created using {@link #ExchangeTransaction(Currency, BigDecimal, Currency, BigDecimal)}
 * in the ordered state. That state represents a customer's request to exchange one currency for another and consists
 * of two distinct currencies (one being sold, the other being bought) and exactly one positive amount expressed
 * in either currency. The amount expressed in the other currency must be omitted at this stage.
 * </p><p>
 * An ordered {@link ExchangeTransaction} is then amended with exchange rate ({@link #setExchangeRate(BigDecimal)}
 * and {@link #setRateDirection(RateDirection)}) necessary for its execution. Once amended, the exchange transaction
 * becomes "ready".
 * </p><p>
 * Finally, the transaction becomes completed with a call to {@link #setExchangeTimestamp(Date)}.
 * That call also calculates one of the amounts, which was omitted during creation.
 * </p>
 * @author kgrzeda
 */
public class ExchangeTransaction {

    private final static MathContext mathContext = new MathContext (2, RoundingMode.HALF_UP);

    private Currency currencySold;
    private BigDecimal amountSold;
    private Currency currencyBought;
    private BigDecimal amountBought;


    private BigDecimal exchangeRate;

    Date exchangeTimestamp;
    RateDirection rateDirection;

    /**
     * Creates a new exchange transaction in the <b>ordered</b> state.
     * @param currencySold  Currency to be sold in this transaction. Not null.
     * @param amountSold  Positive or null. Should be given if and only if "amountBought" is omitted.
     * @param currencyBought  Currency to be bought in this transaction. Not null.
     * @param amountBought  Positive or null. Should be given if and only if "amountSold" is omitted.
     */
    public ExchangeTransaction (Currency currencySold, BigDecimal amountSold, Currency currencyBought, BigDecimal amountBought) {
        this.currencySold = currencySold;
        this.currencyBought = currencyBought;
        this.amountSold = amountSold != null ? amountSold.setScale(2) : null;
        this.amountBought = amountBought != null ? amountBought.setScale(2) : null;
    }

    /**
     * Returns the currency being sold in this transaction.
     * @return  Not null.
     */
    public Currency getCurrencySold () {
        return currencySold;
    }

    /**
     * Returns the amount of {@link #getCurrencySold()} being sold in this transaction.
     * Should be positive.
     */
    public BigDecimal getAmountSold () {
        return amountSold;
    }

    /**
     * Returns the currency being bought in this transaction.
     * @return  Not null.
     */
    public Currency getCurrencyBought () {
        return currencyBought;
    }

    /**
     * Returns the amount of {@link #getCurrencyBought()} being bought in this transaction.
     * Should be positive.
     */
    public BigDecimal getAmountBought () {
        return amountBought;
    }

    /**
     * Returns the exchange rate between the currencies involved in the transaction.
     * See {@link #getRateDirection()} on how to interpret it.
     * @return
     */
    public BigDecimal getExchangeRate () {
        return exchangeRate;
    }

    /**
     * Sets the exchange rate between the currencies involved in the transaction.
     * See {@link #setRateDirection(RateDirection)} on how to interpret it.
     * @return
     */
    public void setExchangeRate (BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getExchangeTimestamp () {
        // FIXME leaky accessor
        return exchangeTimestamp;
    }

    public void setExchangeTimestamp (Date exchangeTimestamp) {
        if (exchangeTimestamp == null) {
            throw new IllegalArgumentException ();
        }
        if ((rateDirection == null) || (exchangeRate == null)) {
            throw new IllegalStateException ();
        }
        if ((amountSold == null) && (amountBought != null)) {
            switch (rateDirection) {
                case BOUGHT_VS_SOLD:
                    amountSold = amountBought.multiply(exchangeRate, mathContext);
                    break;
                case SOLD_VS_BOUGHT:
                    amountSold = amountBought.divide(exchangeRate, mathContext);
                    break;
                default:
                    throw new IllegalArgumentException ();
            }
            amountSold = amountSold.setScale(2);
        } else if ((amountBought == null) && (amountSold != null)) {
            switch (rateDirection) {
                case SOLD_VS_BOUGHT:
                    amountBought = amountSold.multiply(exchangeRate, mathContext);
                    break;
                case BOUGHT_VS_SOLD:
                    amountBought = amountSold.divide(exchangeRate, mathContext);
                    break;
                default:
                    throw new IllegalArgumentException ();
            }
            amountBought = amountBought.setScale(2);
        } else {
            throw new IllegalStateException ();
        }
        // FIXME leaky accessor
        this.exchangeTimestamp = exchangeTimestamp;
    }

    public RateDirection getRateDirection () {
        return rateDirection;
    }

    public void setRateDirection (RateDirection rateDirection) {
        this.rateDirection = rateDirection;
    }

    public boolean isProperlyOrdered () {
        if ((currencySold == null) || (currencyBought == null) || (currencySold == currencyBought)) {
            return false;
        }
        // Exactly one amount must be specified in a properly ordered exchange transaction...
        if ((amountSold != null) == (amountBought != null)) {
            return false;
        }
        if ((exchangeRate != null) || (exchangeTimestamp != null) && (rateDirection != null)) {
            return false;
        }
        return true;
    }


    public BigDecimal getSignedAmount (Currency currency) {
        if (currency == currencySold) {
            return amountSold != null ? amountSold.negate() : null;
        } else if (currency == currencyBought) {
            return amountBought;
        }
        throw new IllegalArgumentException ("Invalid currency.");
    }

    /**
     * Specifies how to interpret the numeric value of the exchange rate,
     * ie. whether value of 4 means 1 USD = 4 PLN or 1 PLN = 4 USD.
     */
    public static enum RateDirection {

        /** The rate will be expressed as "1 BOUGHT = xxx SOLD". */
        BOUGHT_VS_SOLD,

        /** The rate will be expressed as "1 SOLD = xxx BOUGHT". */
        SOLD_VS_BOUGHT;
    }
}
