package com.gmail.krzgrz.demo.service;

import com.gmail.krzgrz.demo.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExchangeController {

    @Autowired
    AccountDAO accountDAO;

    @GetMapping("/account/{id}")
    public ModelAndView getAccountHistory (@PathVariable String id) {
        ModelAndView mv = new ModelAndView ();
        mv.setViewName("account");
        mv.addObject("currency1", Currency.USD);
        mv.addObject("currency2", Currency.PLN);
        mv.addObject("accountRegistration", accountDAO.get(id));
        mv.addObject("accountRegistration", accountDAO.get(id));
        mv.addObject("accountHistory", accountDAO.getAccountHistory(id));
        return mv;
    }
}
