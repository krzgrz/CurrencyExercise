package com.gmail.krzgrz.demo.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.gmail.krzgrz.demo.domain.Currency;
import com.gmail.krzgrz.demo.domain.ExchangeTransaction;
import com.gmail.krzgrz.demo.domain.PESEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.krzgrz.demo.domain.AccountRegistration;
import org.springframework.web.server.ResponseStatusException;

/**
 * Provides REST API for our two services: account registration and exchange transactions.
 * As the code develops, it would be most likely split into two separate classes.
 */
@RestController
public class ExchangeRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    RateService rateService;

    @GetMapping("/rest-api/registration/{pesel}")
    public AccountRegistration getAccount (@PathVariable String pesel) {
        // TODO: verify that id looks like a PESEL, ie. 11 digits and maybe checksum as well
        // in order to differentiate "invalid argument" from "not found".
        AccountRegistration accountRegistration = accountDAO.getAccountRegistration(new PESEL (pesel));
        return accountRegistration;
    }

    @GetMapping("/rest-api/registration")
    public Collection <AccountRegistration> getAllAccounts () {
        return accountDAO.getAllAccountRegistrations();
    }

    /**
     * One may want to consider "PUT" instead of "POST".
     * @param accountRegistration
     * @return
     */
    @PostMapping("/rest-api/registration")
    public ResponseEntity <Void> createAccount (@RequestBody AccountRegistration accountRegistration) {
        // TODO Check DOB in the correct timezone
        logger.info("Post: " + accountRegistration);
        if (accountDAO.getAccountRegistration(accountRegistration.getPesel()) != null) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Account already exists.");
        }
        accountDAO.save(accountRegistration);
        return new  ResponseEntity <Void> (HttpStatus.CREATED);
    }

    @PostMapping("/rest-api/exchange/{pesel}")
    public ResponseEntity <Void> orderTransaction (@PathVariable String pesel, @RequestBody ExchangeTransaction exchangeTransaction) {
        if ( ! exchangeTransaction.isProperlyOrdered()) {
            throw new IllegalArgumentException();
        }
        // TODO: this code may need generalization if more currencies are to be supported...
        BigDecimal exchangeRate = rateService.getExchangeRate(Currency.USD, Currency.PLN);
        ExchangeTransaction.RateDirection rateDirection = null;
        if ((exchangeTransaction.getCurrencyBought() == Currency.USD) && (exchangeTransaction.getCurrencySold() == Currency.PLN)) {
            rateDirection = ExchangeTransaction.RateDirection.BOUGHT_VS_SOLD;
        } else if ((exchangeTransaction.getCurrencySold() == Currency.USD) && (exchangeTransaction.getCurrencyBought() == Currency.PLN)) {
            rateDirection = ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT;
        } else {
            throw new IllegalArgumentException ("Unsupported currency pair: " + exchangeTransaction.getCurrencySold() + "/" + exchangeTransaction.getCurrencyBought());
        }
        exchangeTransaction.setExchangeRate(exchangeRate);
        exchangeTransaction.setRateDirection(rateDirection);
        // Complete exchange transaction...
        exchangeTransaction.setExchangeTimestamp(new Date ());
        //
        AccountRegistration accountRegistration = accountDAO.getAccountRegistration(new PESEL (pesel));
        accountDAO.save(accountRegistration, exchangeTransaction);
        return new  ResponseEntity <Void> (HttpStatus.CREATED);
    }

}
