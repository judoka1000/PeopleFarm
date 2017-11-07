package com.amazone.peoplefarm.interfaces;

import com.amazone.peoplefarm.model.GameState;

public interface GameLogicInterface {
    public GameState newGame();
    public void periodicUpdate(double period);
}

