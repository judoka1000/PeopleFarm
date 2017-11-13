package com.amazone.peoplefarm.exceptions;

import com.amazone.peoplefarm.services.GameStateService;

public class GameStateNotFoundException extends GameStateException {
    public GameStateNotFoundException() {
    }

    public GameStateNotFoundException(String message) {
        super(message);
    }
}
