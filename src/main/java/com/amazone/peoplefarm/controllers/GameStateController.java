package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Response;
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

    @ResponseBody
    @RequestMapping(value = "/newgame", method = RequestMethod.POST)
    public Response newGame(Model model) {
        if(model.containsAttribute("gameState")){
            gameStateService.delete((Integer)model.asMap().get("gameState"));
        }
        GameState gameState = new GameState();
        gameState.addPerson(new Person("adult"));
        gameState.addPerson(new Person("adult"));
        gameState.addPerson(new Person("adult"));
        gameState.addPerson(new Person("child"));
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
        if(gameState.getDevSettings().isMortal()) gameState.getDevSettings().setMortal(false);
        else gameState.getDevSettings().setMortal(true);
        return new Response(true);
    }

}
