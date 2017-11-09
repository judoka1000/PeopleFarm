package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.models.Account;
import com.amazone.peoplefarm.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public boolean checkCredentials(@RequestBody Account account) {
        try {
            Account user = accountService.findByUsername(account.getUsername());
            if (user.getPassword().equals(account.getPassword())){
                System.out.println("password correct!");
                return true;
            }
            else{
                System.out.println("password incorrect :(");
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/createaccount", method = RequestMethod.POST)
    public int create(@RequestBody Account account) {

        accountService.save(account);
        return account.getId();
    }

}
