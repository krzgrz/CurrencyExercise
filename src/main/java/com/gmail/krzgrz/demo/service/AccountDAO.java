package com.gmail.krzgrz.demo.service;

import com.gmail.krzgrz.demo.domain.AccountRegistration;
import com.gmail.krzgrz.demo.domain.Currency;
import com.gmail.krzgrz.demo.domain.ExchangeTransaction;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Component
public class AccountDAO {

    private Map <String, AccountRegistration> accounts = new HashMap ();

    private Map <String, List <ExchangeTransaction>> histories = new HashMap ();

    @PostConstruct
    public void start () {
        save(new AccountRegistration ("111", "Adam", "Mickiewicz", new BigDecimal (123)));
        save(new AccountRegistration ("222", "Adam", "Asnyk", new BigDecimal (123)));
        histories.get("111").add(new ExchangeTransaction (Currency.PLN, new BigDecimal (7), Currency.USD, new BigDecimal (3)));
    }

    public AccountRegistration get (String id) {
        AccountRegistration accountRegistration = accounts.get(id);
        if (accountRegistration == null) {
            // for now
//            accountRegistration = new AccountRegistration (id,"","",new BigDecimal(0));
        }
        return accountRegistration;
    }

    public Collection <AccountRegistration> getAllAccounts () {
        return accounts.values();
    }

    public void save (AccountRegistration accountRegistration) {
        String pesel = accountRegistration.getPesel();
        synchronized (accounts) {
            if (accounts.containsKey(pesel)) {
                throw new RuntimeException("Duplicate");
            }
            accounts.put(pesel, accountRegistration);
            histories.put(pesel, new ArrayList ());
        }
    }

    public Collection <ExchangeTransaction> getAccountHistory (String id) {
        List <ExchangeTransaction> history = histories.getOrDefault(id, new ArrayList ());
//        history.add(new ExchangeTransaction (Currency.PLN, new BigDecimal (2), Currency.USD, new BigDecimal (3)));
        return history;
    }

    public void save (AccountRegistration accountRegistration, ExchangeTransaction exchangeTransaction) {
        String pesel = accountRegistration.getPesel();
        histories.get(pesel).add(exchangeTransaction);
    }

}
