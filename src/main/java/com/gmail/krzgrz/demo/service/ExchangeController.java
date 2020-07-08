package com.gmail.krzgrz.demo.service;

import com.gmail.krzgrz.demo.domain.ExchangeTransaction;
import com.gmail.krzgrz.demo.domain.PESEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.function.Function;

/**
 * Provides UI for accounts present in the application.
 */
@Controller
public class ExchangeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AccountDAO accountDAO;

    /** Given {@link ExchangeTransaction}, produces user-facing representation of the exchange rate. */
    private Function <ExchangeTransaction, String> exchangeRateFormatter = new Function <ExchangeTransaction, String> () {
        @Override
        public String apply (ExchangeTransaction exchangeTransaction) {
            switch (exchangeTransaction.getRateDirection()) {
                case BOUGHT_VS_SOLD:
                    return MessageFormat.format("1 {0} = {1} {2}",
                            exchangeTransaction.getCurrencyBought(),
                            exchangeTransaction.getExchangeRate(),
                            exchangeTransaction.getCurrencySold()
                    );
                case SOLD_VS_BOUGHT:
                    return MessageFormat.format("1 {2} = {1} {0}",
                            exchangeTransaction.getCurrencyBought(),
                            exchangeTransaction.getExchangeRate(),
                            exchangeTransaction.getCurrencySold()
                    );
            }
            return "?";
        }
    };

    /** Presents detailed view of a single account. */
    @GetMapping("/account/{id}")
    public ModelAndView getAccountHistory (@PathVariable String id) {
        ModelAndView mv = new ModelAndView ();
        mv.setViewName("one-account");
        // TODO handle null timestamp in a safe way
        // TODO set timezone
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setTimeZone(TimeZone.getTimeZone(Z));
        mv.addObject("exchangeRateFormatter", exchangeRateFormatter);
        mv.addObject("dateFormat", dateFormat);
        mv.addObject("currency1", Currency.getInstance("USD"));
        mv.addObject("currency2", Currency.getInstance("PLN"));
        mv.addObject("accountRegistration", accountDAO.getAccountRegistration(new PESEL (id)));
        mv.addObject("accountRegistration", accountDAO.getAccountRegistration(new PESEL (id)));
        mv.addObject("accountHistory", accountDAO.getAccountHistory(new PESEL (id)));
        return mv;
    }

    /** List all accounts in the application. */
    @GetMapping("/account")
    public ModelAndView getRegistration () {
        logger.info("getAllAccounts");
        ModelAndView mv = new ModelAndView ();
        mv.setViewName("all-accounts");
        mv.addObject("accountRegistrations", accountDAO.getAllAccountRegistrations());
        return mv;
    }
}
