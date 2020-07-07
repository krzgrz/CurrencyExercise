package com.gmail.krzgrz.demo.service;

import com.gmail.krzgrz.demo.domain.Currency;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RateService {

    /**
     * Returns current exchange rate for given currency pair.
     * @param currency1
     * @param currency2
     * @return  Should be interpreted as "1 currency1 = nnn currency2".
     */
    public BigDecimal getExchangeRate (Currency currency1, Currency currency2) {
        return new BigDecimal (1.23);
    }
}
