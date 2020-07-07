package com.gmail.krzgrz.demo.service;

import com.gmail.krzgrz.demo.domain.AccountRegistration;
import com.gmail.krzgrz.demo.domain.Currency;
import com.gmail.krzgrz.demo.domain.ExchangeTransaction;
import com.gmail.krzgrz.demo.domain.PESEL;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class AccountDAO {

    private Map <PESEL, AccountRegistration> accounts = new HashMap ();

    private Map <PESEL, List <ExchangeTransaction>> histories = new HashMap ();

    @PostConstruct
    public void start () throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        save(new AccountRegistration ("12345678901", "Adam", "Mickiewicz", new BigDecimal (123)));
        save(new AccountRegistration ("11122233344", "Adam", "Asnyk", new BigDecimal (123)));
        histories.get(new PESEL ("12345678901")).add(new ExchangeTransaction (Currency.PLN, new BigDecimal (7), Currency.USD, null));
        histories.get(new PESEL ("12345678901")).get(0).setRateDirection(ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT);
        histories.get(new PESEL ("12345678901")).get(0).setExchangeRate(new BigDecimal(1.23));
        histories.get(new PESEL ("12345678901")).get(0).setExchangeTimestamp(dateFormat.parse("2020-07-04 13:23:45"));
    }

    public AccountRegistration get (PESEL pesel) {
        AccountRegistration accountRegistration = accounts.get(pesel);
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
        PESEL pesel = new PESEL (accountRegistration.getPesel());
        synchronized (accounts) {
            if (accounts.containsKey(pesel)) {
                throw new RuntimeException("Duplicate");
            }
            accounts.put(pesel, accountRegistration);
            histories.put(pesel, new ArrayList ());
        }
    }

    public Collection <ExchangeTransaction> getAccountHistory (PESEL pesel) {
        List <ExchangeTransaction> history = histories.getOrDefault(pesel, new ArrayList ());
        return history;
    }

    public void save (AccountRegistration accountRegistration, ExchangeTransaction exchangeTransaction) {
        PESEL pesel = new PESEL (accountRegistration.getPesel());
        histories.get(pesel).add(exchangeTransaction);
    }

}
