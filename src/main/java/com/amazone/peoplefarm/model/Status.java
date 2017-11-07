package com.amazone.peoplefarm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Embeddable
public class Status {
    public enum Health {
        HEALTHY,
        DEAD
    }

    int hunger;
    int tiredness;
    int age;
    int ageOfDeath;
    int currentCaptchas;

    @Enumerated(EnumType.STRING)
    Health health;

    @Override
    public String toString() {
        return "Status{" +
                "hunger=" + hunger +
                ", tiredness=" + tiredness +
                ", age=" + age +
                ", currentCaptchas=" + currentCaptchas +
                ", health=" + health +
                '}';
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        if(hunger<=0) hunger = 0;
        if(hunger > 100) hunger = 100;
        this.hunger = hunger;
    }

    public int getTiredness() {
        return tiredness;
    }

    public void setTiredness(int tiredness) {
        if(tiredness<=0) tiredness = 0;
        if(tiredness > 100) tiredness = 100;
        this.tiredness = tiredness;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeOfDeath() {
        return ageOfDeath;
    }

    public void setAgeOfDeath(int ageOfDeath) {
        if(ageOfDeath <= age) {
            throw new IllegalArgumentException("Age of death cannot be lower than the current age");
        }
        this.ageOfDeath = ageOfDeath;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public int getCurrentCaptchas() {
        return currentCaptchas;
    }

    public void setCurrentCaptchas(int currentCaptchas) {
        if(currentCaptchas<=0) currentCaptchas = 0;
        if(currentCaptchas > 10) currentCaptchas = 10;
        this.currentCaptchas = currentCaptchas;
    }
}
