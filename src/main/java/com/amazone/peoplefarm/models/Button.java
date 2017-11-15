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

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    private String sound="";

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

    public int getButtonID() {
        return buttonID;
    }

    public void setButtonID(int buttonID) {
        this.buttonID = buttonID;
    }

    public int getBonusSpeed() {
        return bonusSpeed;
    }

    public void setBonusSpeed(int bonusSpeed) {
        this.bonusSpeed = bonusSpeed;
    }

    private int useCost;

    private int bonusSpeed;
    private int bonusIQ;
    private int bonusMetabolism;
    private int bonusStamina;

    private int bonusSpeedDuration;
    private int bonusIQDuration;
    private int bonusMetabolismDuration;
    private int bonusStaminaDuration;

    private int hunger;
    private int tiredness;

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

    public int getBonusIQ() {
        return bonusIQ;
    }

    public void setBonusIQ(int bonusIQ) {
        this.bonusIQ = bonusIQ;
    }

    public int getBonusMetabolism() {
        return bonusMetabolism;
    }

    public void setBonusMetabolism(int bonusMetabolism) {
        this.bonusMetabolism = bonusMetabolism;
    }

    public int getBonusStamina() {
        return bonusStamina;
    }

    public void setBonusStamina(int bonusStamina) {
        this.bonusStamina = bonusStamina;
    }

    public int getBonusSpeedDuration() {
        return bonusSpeedDuration;
    }

    public void setBonusSpeedDuration(int bonusSpeedDuration) {
        this.bonusSpeedDuration = bonusSpeedDuration;
    }

    public int getBonusIQDuration() {
        return bonusIQDuration;
    }

    public void setBonusIQDuration(int bonusIQDuration) {
        this.bonusIQDuration = bonusIQDuration;
    }

    public int getBonusMetabolismDuration() {
        return bonusMetabolismDuration;
    }

    public void setBonusMetabolismDuration(int bonusMetabolismDuration) {
        this.bonusMetabolismDuration = bonusMetabolismDuration;
    }

    public int getBonusStaminaDuration() {
        return bonusStaminaDuration;
    }

    public void setBonusStaminaDuration(int bonusStaminaDuration) {
        this.bonusStaminaDuration = bonusStaminaDuration;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getTiredness() {
        return tiredness;
    }

    public void setTiredness(int tiredness) {
        this.tiredness = tiredness;
    }

    @Override
    public String toString() {
        return "Button{" +
                "buttonID=" + buttonID +
                ", name='" + name + '\'' +
                ", clickAction='" + clickAction + '\'' +
                ", image='" + image + '\'' +
                ", buyCost=" + buyCost +
                ", useCost=" + useCost +
                ", bonusSpeed=" + bonusSpeed +
                ", bonusIQ=" + bonusIQ +
                ", bonusMetabolism=" + bonusMetabolism +
                ", bonusStamina=" + bonusStamina +
                ", bonusSpeedDuration=" + bonusSpeedDuration +
                ", bonusIQDuration=" + bonusIQDuration +
                ", bonusMetabolismDuration=" + bonusMetabolismDuration +
                ", bonusStaminaDuration=" + bonusStaminaDuration +
                ", hunger=" + hunger +
                ", tiredness=" + tiredness +
                '}';
    }
}

