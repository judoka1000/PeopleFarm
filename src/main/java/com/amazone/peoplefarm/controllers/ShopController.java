package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
import com.amazone.peoplefarm.models.*;
import com.amazone.peoplefarm.services.ButtonService;
import com.amazone.peoplefarm.services.GameLogicService;
import com.amazone.peoplefarm.services.GameStateService;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@SessionAttributes("gameState")
public class ShopController {

    @Autowired
    private ButtonService buttonService;

    @Autowired
    private GameStateService gameStateService;

    @ResponseBody
    @RequestMapping(value = "/shop/buy/{id}", method = RequestMethod.GET)
    public Response<Button> buy(Model model, @PathVariable int id, HttpServletResponse httpResponse) {
        try {
            if (!model.containsAttribute("gameState")) throw new GameStateNotFoundException("Gamestate is: null");
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            //if(gameState == null) throw new GameStateNotFoundException("Gamestate is: null");
            Button button = buttonService.findOne(id);
            gameState.setScore(gameState.getScore() - button.getBuyCost());
            gameState.addButton(button);
            gameStateService.save(gameState);
            return new Response<Button>(true, button);
        } catch (GameStateNotFoundException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new Response<>(false, e);
        } catch (Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response<>(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/shop/buttons", method = RequestMethod.GET)
    public Response<List<Button>> buy(Model model, HttpServletResponse httpResponse) {
        return new Response<>(true, (List) buttonService.findAll());
    }

}