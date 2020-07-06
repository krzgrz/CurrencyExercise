package com.gmail.krzgrz.demo.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Map <String, AccountRegistration> accounts = new HashMap ();

    @GetMapping("/registration/{id}")
    public AccountRegistration getAccount (@PathVariable String id) {
        // TODO: verify that id looks like a PESEL, ie. 11 digits and maybe checksum as well
        // in order to differentiate "invalid argument" from "not found".
        AccountRegistration accountRegistration = accounts.get(id);
        if (accountRegistration == null) {
            // for now
            accountRegistration = new AccountRegistration (id,"","",new BigDecimal(0));
        }
        return accountRegistration;
    }

    @GetMapping("/rest-api/registration")
    public Collection <AccountRegistration> getAllAccounts () {
        return accounts.values();
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
        String pesel = accountRegistration.getPesel();
        synchronized (accounts) {
            if (accounts.containsKey(pesel)) {
                throw new RuntimeException("Duplicate");
            }
            accounts.put(pesel, accountRegistration);
        }
        return new  ResponseEntity <Void> (HttpStatus.CREATED);
    }

}
