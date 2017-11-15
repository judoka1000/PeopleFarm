package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.models.Account;
import com.amazone.peoplefarm.exceptions.*;
import com.amazone.peoplefarm.models.Response;
import com.amazone.peoplefarm.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public Response<Boolean> checkCredentials(Model model, @RequestBody Account account) {
        try {
            Account user = accountService.findByUsername(account.getUsername());
            if (user.getPassword().equals(account.getPassword())){
                System.out.println("password correct!");
                model.addAttribute("account", user.getId());
                return new Response<>(true, true);
            }
            else{
                System.out.println("password incorrect!");
                throw new AccountException("Password incorrect");
            }
        }
        catch (Exception e){
            return new Response<>(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/createaccount", method = RequestMethod.POST)
    public Response<Integer> create(Model model, @RequestBody Account account) {
        try {
            if(accountService.findByUsername(account.getUsername())!=null){
                throw new AccountException("Username already in use");
            }
            accountService.save(account);
            return new Response<>(true, account.getId());
        } catch(AccountException e) {
            System.out.println(e.getMessage());
            return new Response<>(false, e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e = new AccountException("Could not save the account");
            return new Response<>(false, e);
        }
    }

}