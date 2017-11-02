package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.DevSettings;
import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Response;
import com.amazone.peoplefarm.services.GameLogicService;
import com.amazone.peoplefarm.services.GameStateService;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@SessionAttributes("gameState")
public class GameStateController {

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private PersonService personService;

    @Autowired
    private GameLogicService gameLogicService;

    @ResponseBody
    @RequestMapping(value = "/newgame", method = RequestMethod.POST)
    public Response newGame(Model model) {
        if(model.containsAttribute("gameState")){
            gameStateService.delete((Integer)model.asMap().get("gameState"));
        }
        GameState gameState = gameLogicService.newGame();
        gameStateService.save(gameState);
        model.addAttribute("gameState", gameState.getId());
        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/score")
    public String getGameState(Model model){
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        return "" + gameState.getScore();
    }

    @ResponseBody
    @RequestMapping(value = "/mortal", method = RequestMethod.PUT)
    public Response flipMortality(Model model){
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        gameState.getDevSettings().setMortal(!gameState.getDevSettings().isMortal());
        gameStateService.save(gameState);
        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/getDevSettings")
    public DevSettings getDevsettings(Model model){
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        return gameState.getDevSettings();
    }

    @ResponseBody
    @RequestMapping(value = "/putDevSettings", method = RequestMethod.PUT)
    public Response putDevsettings(Model model, @RequestBody DevSettings devSettings){
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        gameState.setDevSettings(devSettings);
        gameStateService.save(gameState);
        return new Response(true);
    }

}
