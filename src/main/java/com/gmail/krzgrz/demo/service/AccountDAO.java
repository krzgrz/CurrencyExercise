package com.gmail.krzgrz.demo.service;

import com.gmail.krzgrz.demo.domain.*;
import com.gmail.krzgrz.demo.domain.Currency;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AccountDAO {

    private Map <PESEL, Account> accounts = new HashMap ();

//    private Map <PESEL, List <ExchangeTransaction>> histories = new HashMap ();

    @PostConstruct
    public void start () throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        save(new AccountRegistration ("12345678901", "Adam", "Mickiewicz", new BigDecimal (123)));
        AccountRegistration accountRegistration = new AccountRegistration ("11122233344", "Adam", "Asnyk", new BigDecimal (123));
        save(accountRegistration);

        Account account = accounts.get(new PESEL (accountRegistration.getPesel()));

        ExchangeTransaction exchangeTransaction = new ExchangeTransaction (Currency.PLN, new BigDecimal (7), Currency.USD, null);
        exchangeTransaction.setRateDirection(ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT);
        exchangeTransaction.setExchangeRate(new BigDecimal(1.23));
        exchangeTransaction.setExchangeTimestamp(dateFormat.parse("2020-07-04 13:23:45"));
        account.addExchangeTransaction(exchangeTransaction);
//        histories.get(new PESEL ("12345678901")).add();
//        histories.get(new PESEL ("12345678901")).get(0).setRateDirection(ExchangeTransaction.RateDirection.SOLD_VS_BOUGHT);
//        histories.get(new PESEL ("12345678901")).get(0).setExchangeRate(new BigDecimal(1.23));
//        histories.get(new PESEL ("12345678901")).get(0).setExchangeTimestamp(dateFormat.parse("2020-07-04 13:23:45"));
    }

    /**
     * Returns account metadata for given PESEL.
     * @param pesel
     * @return  May be null if not found.
     */
    public AccountRegistration getAccountRegistration (PESEL pesel) {
        Account account = accounts.get(pesel);
        if (account == null) {
            return null; // OK, not found.
        }
        return account.getAccountRegistration();
    }

    /**
     * Returns account metadata for all accounts in the application.
     * @return  Never null but may be empty.
     */
    public Collection <AccountRegistration> getAllAccountRegistrations () {
        return accounts.values().stream().map(Account::getAccountRegistration).collect(Collectors.toList());
    }

    public void save (AccountRegistration accountRegistration) {
        PESEL pesel = new PESEL (accountRegistration.getPesel());
        synchronized (accounts) {
            if (accounts.containsKey(pesel)) {
                throw new IllegalArgumentException ("Duplicate");
            }
            Account account = new Account ();
            account.setAccountRegistration(accountRegistration);
            accounts.put(pesel, account);
//            histories.put(pesel, new ArrayList ());
        }
    }

    public Collection <ExchangeTransaction> getAccountHistory (PESEL pesel) {
        Account account = accounts.get(pesel);
        // TODO error handling
        return account.getHistory();
    }

    public void save (AccountRegistration accountRegistration, ExchangeTransaction exchangeTransaction) {
        PESEL pesel = new PESEL (accountRegistration.getPesel());
        Account account = accounts.get(pesel);
        account.addExchangeTransaction(exchangeTransaction);
    }

}
