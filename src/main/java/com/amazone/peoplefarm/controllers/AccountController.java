package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.models.Account;
import com.amazone.peoplefarm.exceptions.*;
import com.amazone.peoplefarm.models.Response;
import com.amazone.peoplefarm.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes({"account"})
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/create")
    public String new_account(){
        return "new_account";
    }

    @ResponseBody
    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    public Response<Boolean> checkCredentials(Model model, @RequestBody Account account) throws AccountException {
        Account user = accountService.findByUsername(account.getUsername());
        if (user.getPassword().equals(account.getPassword())) {
            System.out.println("password correct!");
            model.addAttribute("account", user.getId());
            return new Response<>(true, true);
        } else {
            System.out.println("password incorrect!");
            throw new AccountException("Password incorrect");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/createaccount", method = RequestMethod.POST)
    public Response<Integer> create(Model model, @RequestBody Account account) throws AccountException {
        if (accountService.findByUsername(account.getUsername()) != null) {
            throw new AccountException("Username already in use");
        }

        try {
            accountService.save(account);
            return new Response<>(true, account.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AccountException("Could not save the account");
        }
    }@ResponseBody
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public Response<Iterable<Account>> getAccounts(Model model) throws AccountException {
       return new Response<>(true, accountService.findAll());
    }
}