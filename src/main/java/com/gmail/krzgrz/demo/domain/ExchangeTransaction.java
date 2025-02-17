package com.gmail.krzgrz.demo.domain;

import javax.print.attribute.standard.MediaSizeName;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Date;
import java.util.Optional;

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
 * An ordered {@link ExchangeTransaction} is then amended with exchange rate ({@link #setExchangeRate(BigDecimal)})
 * necessary for its execution. Once amended, the exchange transaction becomes "ready".
 * </p><p>
 * Finally, the transaction becomes completed with a call to {@link #setExchangeTimestamp(Date)}.
 * That call also calculates one of the amounts, which was omitted during creation.
 * </p>
 * @author kgrzeda
 */
public class ExchangeTransaction {

    private static final int SCALE = 2;

    private Currency currencySold;
    private Optional <BigDecimal> amountSold;
    private Currency currencyBought;
    private Optional <BigDecimal> amountBought;

    private BigDecimal exchangeRate;
    private Date exchangeTimestamp;
    private RateDirection rateDirection;

    /**
     * Creates a new exchange transaction in the <b>ordered</b> state.
     * @param currencySold  Currency to be sold in this transaction. Not null.
     * @param amountSold  Positive or null. Should be given if and only if "amountBought" is omitted.
     * @param currencyBought  Currency to be bought in this transaction. Not null.
     * @param amountBought  Positive or null. Should be given if and only if "amountSold" is omitted.
     */
    public ExchangeTransaction (Currency currencySold, BigDecimal amountSold, Currency currencyBought, BigDecimal amountBought) {
        if ((amountSold != null) && (amountBought == null)) {
            this.rateDirection = RateDirection.SOLD_VS_BOUGHT;
        } else if ((amountSold == null) && (amountBought != null)) {
            this.rateDirection = RateDirection.BOUGHT_VS_SOLD;
        } else {
            throw new IllegalArgumentException("Exactly one amount must be given.");
        }
        //
        this.currencySold = currencySold;
        this.currencyBought = currencyBought;
        this.amountSold = Optional.ofNullable(amountSold).map((BigDecimal amount) -> amount.setScale(SCALE, BigDecimal.ROUND_HALF_UP));
        this.amountBought = Optional.ofNullable(amountBought).map((BigDecimal amount) -> amount.setScale(SCALE, BigDecimal.ROUND_HALF_UP));
        this.amountSold.ifPresent((BigDecimal amount) -> {
            if (amount.signum() <= 0) throw new IllegalArgumentException("If amount sold is given, it must be positive.");
        });
        this.amountBought.ifPresent((BigDecimal amount) -> {
            if (amount.signum() <= 0) throw new IllegalArgumentException("If amount bought is given, it must be positive.");
        });
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
    public Optional <BigDecimal> getAmountSold () {
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
    public Optional <BigDecimal> getAmountBought () {
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
     * See {@link #getRateDirection()} on how to interpret it.
     * @return
     */
    public void setExchangeRate (BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public RateDirection getRateDirection () {
        return rateDirection;
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
        if (( ! amountSold.isPresent()) && amountBought.isPresent()) {
            amountSold = Optional.of(amountBought.get().multiply(exchangeRate).setScale(SCALE, BigDecimal.ROUND_HALF_UP));
        } else if (( ! amountBought.isPresent()) && (amountSold.isPresent())) {
            amountBought = Optional.of(amountSold.get().multiply(exchangeRate).setScale(SCALE, BigDecimal.ROUND_HALF_UP));
        } else {
            throw new IllegalStateException ();
        }
        // FIXME leaky accessor
        this.exchangeTimestamp = exchangeTimestamp;
    }

    /**
     * Checks whether this exchange transaction is in the "ordered" state.
     * @return
     */
    public boolean isProperlyOrdered () {
        if ((currencySold == null) || (currencyBought == null) || (currencySold == currencyBought)) {
            return false;
        }
        // Exactly one amount must be specified in a properly ordered exchange transaction...
        if ((amountSold.isPresent()) == (amountBought.isPresent())) {
            return false;
        }
        if (rateDirection == null) {
            return false;
        }
        if ((exchangeRate != null) || (exchangeTimestamp != null)) {
            return false;
        }
        return true;
    }

    /**
     * Returns amount of given currency involved in this transaction; positive if bought, negative if sold.
     * @param currency
     * @return
     */
    public BigDecimal getSignedAmount (Currency currency) {
        if (currency == currencySold) {
            return amountSold != null ? amountSold.get().negate() : null;
        } else if (currency == currencyBought) {
            return amountBought.get();
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
