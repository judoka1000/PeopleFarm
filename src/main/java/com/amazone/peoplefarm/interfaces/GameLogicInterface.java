package com.amazone.peoplefarm.interfaces;

import com.amazone.peoplefarm.models.GameState;

public interface GameLogicInterface {
    public GameState newGame();
    public void periodicUpdate(double period);
}

