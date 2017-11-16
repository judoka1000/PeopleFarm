package com.amazone.peoplefarm.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class GameInfo {
    Integer score;
    private Boolean gameOver;

    public GameInfo(Integer score) {
        this.score = score;
        this.gameOver = getGameOver();
    }

    public Boolean getGameOver() {
        gameOver = score < -100;
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
