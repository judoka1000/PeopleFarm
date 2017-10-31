package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.services.GameStateService;
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

    @ResponseBody
    @RequestMapping(value = "/score")
    public String getGameState(Model model){
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        return "" + gameState.getScore();
    }

}
