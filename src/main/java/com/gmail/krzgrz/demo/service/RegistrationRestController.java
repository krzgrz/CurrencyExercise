package com.gmail.krzgrz.demo.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.gmail.krzgrz.demo.domain.ExchangeTransaction;
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

@RestController
public class RegistrationRestController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AccountDAO accountDAO;

    @GetMapping("/registration/{id}")
    public AccountRegistration getAccount (@PathVariable String id) {
        // TODO: verify that id looks like a PESEL, ie. 11 digits and maybe checksum as well
        // in order to differentiate "invalid argument" from "not found".
        AccountRegistration accountRegistration = accountDAO.get(id);
        return accountRegistration;
    }

    @GetMapping("/rest-api/registration")
    public Collection <AccountRegistration> getAllAccounts () {
        return accountDAO.getAllAccounts();
    }

    /**
     * One may want to consider "PUT" instead of "POST".
     * @param accountRegistration
     * @return
     */
    @PostMapping("/registration")
    public ResponseEntity <Void> createAccount (@RequestBody AccountRegistration accountRegistration) {
        // Check DOB in the correct timezone
        logger.info("Post: " + accountRegistration);
        logger.info("Post: " + accountDAO);
        accountDAO.save(accountRegistration);
        return new  ResponseEntity <Void> (HttpStatus.CREATED);
    }

    @PostMapping("/rest-api/exchange/{id}")
    public ResponseEntity <Void> orderTransaction (@PathVariable String id, @RequestBody ExchangeTransaction exchangeTransaction) {
        if ( ! exchangeTransaction.isProperlyOrdered()) {
            throw new IllegalArgumentException();
        }
        // TODO: get data!
        exchangeTransaction.setExchangeRate(1.23);
        exchangeTransaction.setExchangeTime(new Date ());
        exchangeTransaction.setRateDirection(true);
        //
        AccountRegistration accountRegistration = accountDAO.get(id);
        accountDAO.save(accountRegistration, exchangeTransaction);
        return new  ResponseEntity <Void> (HttpStatus.CREATED);
    }

}
