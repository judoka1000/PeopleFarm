package com.amazone.peoplefarm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    int buttonID;


    private String name;
    private String clickAction;
    private String image;

    public int getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public int getUseCost() {
        return useCost;
    }

    public void setUseCost(int useCost) {
        this.useCost = useCost;
    }

    private int buyCost;
    private int useCost;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false)
//    @JsonBackReference
//    private GameState gamestate;
    public Button(){

    }

    public Button(String name, String clickAction, String image) {
        this.name = name;
        this.clickAction = clickAction;
        this.image = image;
    }

    public int getId() {
        return buttonID;
    }

    public void setId(int id) {
        this.buttonID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public GameState getGamestate() {
//        return gamestate;
//    }
//
//    public void setGamestate(GameState gamestate) {
//        this.gamestate = gamestate;
//    }

    @Override
    public String toString() {
        return "Button{" +
                "id=" + buttonID +
                ", name='" + name + '\'' +
                ", clickAction='" + clickAction + '\'' +
                ", image='" + image + '\'' +
//                ", gamestate=" + gamestate +
                '}';
    }
}

