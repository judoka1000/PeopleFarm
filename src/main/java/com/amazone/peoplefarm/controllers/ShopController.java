package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.AccountNotFoundException;
import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
import com.amazone.peoplefarm.models.*;
import com.amazone.peoplefarm.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@SessionAttributes("account")
public class ShopController {

    @Autowired
    private ButtonService buttonService;

    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping(value = "/shop/buy/{id}", method = RequestMethod.GET)
    public Response<Button> buy(Model model, @PathVariable int id) throws AccountNotFoundException, GameStateNotFoundException {
        if(!model.containsAttribute("account"))
            throw new AccountNotFoundException("Not logged in");

        Account account = accountService.findOne((Integer)model.asMap().get("account"));
        if(account == null)
            throw new AccountNotFoundException("Account not in database");

        GameState gameState = account.getGameState();
        if (gameState == null)
            throw new GameStateNotFoundException("Gamestate met id " + model.asMap().get("gameState") + " niet gevonden in database.");

        Button button = buttonService.findOne(id);
        gameState.setScore(gameState.getScore() - button.getBuyCost());
        gameState.addButton(button);
        accountService.save(account);
        return new Response<Button>(true, button);
    }

    @ResponseBody
    @RequestMapping(value = "/shop/buttons", method = RequestMethod.GET)
    public Response<List<Button>> buy(Model model, HttpServletResponse httpResponse) {
        return new Response<>(true, (List) buttonService.findAll());
    }

}