package com.amazone.peoplefarm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Embeddable
public class Status {
    int hunger;
    int tiredness;
    int age;

    public enum Health {
        HEALTHY,
        DEAD
    }

    @Enumerated(EnumType.STRING)
    Health health;

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

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }
}
