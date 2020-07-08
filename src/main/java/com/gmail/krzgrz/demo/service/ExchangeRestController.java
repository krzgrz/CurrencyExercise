package com.gmail.krzgrz.demo.service;

import java.math.BigDecimal;
import java.util.*;

import com.gmail.krzgrz.demo.domain.*;
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

import org.springframework.web.server.ResponseStatusException;

/**
 * Provides REST API for our two services: account registration and exchange transactions.
 * As the code develops, it would be most likely split into two separate classes.
 * TODO: error handling
 */
@RestController
public class ExchangeRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AccountDAO accountDAO;

    private RateService rateService;

    @Autowired
    public void setAccountDAO (AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Autowired
    public void setRateService (RateService rateService) {
        this.rateService = rateService;
    }

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
        logger.info("Post: " + accountRegistration);
        logger.info("Post: " + accountRegistration.getInitialBalancePLN());
        if (accountDAO.getAccountRegistration(accountRegistration.getPesel()) != null) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Account already exists.");
        }
        if ( ! isAgeEligible(accountRegistration, new Date ())) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "Ineligible age.");
        }
        accountDAO.save(accountRegistration);
        return new  ResponseEntity <Void> (HttpStatus.CREATED);
    }

    @GetMapping("/rest-api/account/{pesel}/summary")
    public AccountSummary getAccountSummary (@PathVariable String pesel) {
        Account account = accountDAO.getAccount(new PESEL (pesel));
        if (account == null) {
            throw new ResponseStatusException (HttpStatus.NOT_FOUND);
        }
        return account.getAccountSummary();
    }


    @PostMapping("/rest-api/exchange/{pesel}")
    public ResponseEntity <Void> orderTransaction (@PathVariable String pesel, @RequestBody ExchangeTransaction exchangeTransaction) {
        if ( ! exchangeTransaction.isProperlyOrdered()) {
            throw new IllegalArgumentException();
        }
        // TODO: this code may need generalization if more currencies are to be supported...
        Currency usd = Currency.getInstance("USD");
        Currency pln = Currency.getInstance("PLN");
        BigDecimal exchangeRate = rateService.getExchangeRate(usd, pln);
        ExchangeTransaction.RateDirection rateDirection = null;
        if ((exchangeTransaction.getCurrencyBought() == usd) && (exchangeTransaction.getCurrencySold() == pln)) {
            rateDirection = ExchangeTransaction.RateDirection.BOUGHT_VS_SOLD;
        } else if ((exchangeTransaction.getCurrencySold() == usd) && (exchangeTransaction.getCurrencyBought() == pln)) {
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

    /**
     * Checks whether the customer specified in given {@link AccountRegistration} is of an appropriate age to open an account.
     * @param now  the current time; parameterized for testability
     * @return  {@code true} if OK to open.
     */
    protected boolean isAgeEligible (AccountRegistration accountRegistration, Date now) {
        Date dob = accountRegistration.getPesel().getDateOfBirth();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, -18);
        Date cutoff = calendar.getTime();
        // Assuming you may open an account on your 18th birthday...
        return dob.compareTo(cutoff) <= 0;
    }

}
