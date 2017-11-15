package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.AccountNotFoundException;
import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
import com.amazone.peoplefarm.models.Account;
import com.amazone.peoplefarm.models.DevSettings;
import com.amazone.peoplefarm.models.GameState;
import com.amazone.peoplefarm.models.Response;
import com.amazone.peoplefarm.services.AccountService;
import com.amazone.peoplefarm.services.GameLogicService;
import com.amazone.peoplefarm.services.GameStateService;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes({"account"})
public class GameStateController {

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private PersonService personService;

    @Autowired
    private GameLogicService gameLogicService;

    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping(value = "/newgame", method = RequestMethod.POST)
    public Response newGame(Model model, HttpServletResponse httpResponse) throws Exception {
        Account account = accountService.findOne((Integer) model.asMap().get("account"));
        account.setGameState(gameLogicService.newGame());
        accountService.save(account);
        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/score")
    public Response<String> getGameState(Model model, HttpServletResponse httpResponse) throws GameStateNotFoundException, AccountNotFoundException {
        if (!model.containsAttribute("account")) {
            throw new AccountNotFoundException("Current session does not have account");
        } else {
            GameState gameState = accountService.findOne((Integer) model.asMap().get("account")).getGameState();
            if (gameState == null) {
                throw new GameStateNotFoundException("Gamestate does not exist in account");
            }
            return new Response<>(true, ((Integer) gameState.getScore()).toString());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/mortal", method = RequestMethod.PUT)
    public Response flipMortality(Model model, HttpServletResponse httpResponse) {
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        gameState.getDevSettings().setMortal(!gameState.getDevSettings().isMortal());
        gameStateService.save(gameState);
        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/rename/{name}", method = RequestMethod.POST)
    public Response changePlayerName(Model model, @PathVariable String name, HttpServletResponse httpResponse) {
        GameState gameState = accountService.findOne((Integer) model.asMap().get("account")).getGameState();
        gameState.setPlayerName(name);
        gameStateService.save(gameState);
        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/rename", method = RequestMethod.GET)
    public Response<Map<String, String>> getPlayerName(Model model, HttpServletResponse httpResponse) {
        GameState gameState = accountService.findOne((Integer) model.asMap().get("account")).getGameState();
        Map<String, String> a = new HashMap<>();
        a.put("name", gameState.getPlayerName());
        return new Response<>(true, a);
    }

    @ResponseBody
    @RequestMapping(value = "/getDevSettings")
    public Response<DevSettings> getDevsettings(Model model, HttpServletResponse httpResponse) {
        GameState gameState = accountService.findOne((Integer) model.asMap().get("account")).getGameState();
        return new Response<>(true, gameState.getDevSettings());
    }

    @ResponseBody
    @RequestMapping(value = "/putDevSettings", method = RequestMethod.PUT)
    public Response putDevsettings(Model model, @RequestBody DevSettings devSettings, HttpServletResponse httpResponse) {
        GameState gameState = accountService.findOne((Integer) model.asMap().get("account")).getGameState();
        gameState.setDevSettings(devSettings);
        gameState.setScore(gameState.getScore() + devSettings.getAddScore());
        gameStateService.save(gameState);
        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/buttons", method = RequestMethod.GET)
    public Response<List<Button>> getButtons(Model model, HttpServletResponse httpResponse) {
        try{
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            return new Response<>(true, gameState.getButtons());
        } catch (Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response<>(false, e);
        }
    }
}