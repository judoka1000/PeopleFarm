package com.amazone.peoplefarm.schedules;

import com.amazone.peoplefarm.services.GameLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Periodic {
    @Autowired
    private GameLogicService gameLogicService;

    public void execute() {
        gameLogicService.periodicUpdate(10);
    }
}
