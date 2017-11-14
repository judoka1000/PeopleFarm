package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
import com.amazone.peoplefarm.models.Button;
import com.amazone.peoplefarm.models.DevSettings;
import com.amazone.peoplefarm.models.GameState;
import com.amazone.peoplefarm.models.Response;
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
    public Response newGame(Model model, HttpServletResponse httpResponse) {
        try {
            if (!model.containsAttribute("gameState")) {
                throw new GameStateNotFoundException("Gamestate not in session");
            } else {
                try {
                    gameStateService.delete((Integer) model.asMap().get("gameState"));
                } catch (EmptyResultDataAccessException e) {
                    throw new GameStateNotFoundException("Gamestate not in database");
                }
            }
            GameState gameState = gameLogicService.newGame();
            gameStateService.save(gameState);
            model.addAttribute("gameState", gameState.getId());
            return new Response(true);
        } catch(GameStateNotFoundException e) {
            httpResponse.setStatus(498);
            return new Response(false, e);
        } catch(Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/score")
    public Response<String> getGameState(Model model, HttpServletResponse httpResponse){
        try {
            if (!model.containsAttribute("gameState")) {
                throw new GameStateNotFoundException("Gamestate is: null");
            } else {
                GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
                if(gameState == null) {
                    throw new GameStateNotFoundException("Gamestate does not exist in database");
                }
                return new Response<>(true, ((Integer)gameState.getScore()).toString());
            }
        } catch(GameStateNotFoundException e) {
            httpResponse.setStatus(498);
            return new Response(false, e);
        } catch(Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response<>(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/mortal", method = RequestMethod.PUT)
    public Response flipMortality(Model model, HttpServletResponse httpResponse){
        try {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            gameState.getDevSettings().setMortal(!gameState.getDevSettings().isMortal());
            gameStateService.save(gameState);
            return new Response(true);
        } catch (Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/rename/{name}", method = RequestMethod.POST)
    public Response changePlayerName(Model model, @PathVariable String name, HttpServletResponse httpResponse) {
        try {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            gameState.setPlayerName(name);
            gameStateService.save(gameState);
            return new Response(true);
        } catch (Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/rename", method = RequestMethod.GET)
    public Response<Map<String, String>> getPlayerName(Model model, HttpServletResponse httpResponse) {
        try{
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            Map<String, String> a = new HashMap<>();
            a.put("name", gameState.getPlayerName());
            return new Response<>(true, a);
    } catch (Exception e){
        httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new Response<>(false, e);
    }
    }

    @ResponseBody
    @RequestMapping(value = "/getDevSettings")
    public Response<DevSettings> getDevsettings(Model model, HttpServletResponse httpResponse){
        try {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            return new Response<>(true, gameState.getDevSettings());
        } catch(Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response<>(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/putDevSettings", method = RequestMethod.PUT)
    public Response putDevsettings(Model model, @RequestBody DevSettings devSettings, HttpServletResponse httpResponse){
        try {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            gameState.setDevSettings(devSettings);
            gameStateService.save(gameState);
            return new Response(true);
        } catch (Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        }
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