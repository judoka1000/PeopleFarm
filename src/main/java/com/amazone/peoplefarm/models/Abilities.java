package com.amazone.peoplefarm.models;

import javax.persistence.*;

@Embeddable
public class Abilities {
    private int speed;
    private int iq;
    private int metabolism;
    private int stamina;

    private int bonusSpeed;
    private int bonusIq;
    private int bonusMetabolism;
    private int bonusStamina;

    private int bonusSpeedDuration;
    private int bonusIqDuration;
    private int bonusMetabolismDuration;
    private int bonusStaminaDuration;

    @Override
    public String toString() {
        return "Abilities{" +
                "speed=" + speed +
                ", iq=" + iq +
                ", metabolism=" + metabolism +
                ", stamina=" + stamina +
                ", bonusSpeed=" + getBonusSpeed() +
                ", bonusIq=" + getBonusIq() +
                ", bonusMetabolism=" + getBonusMetabolism() +
                ", bonusStamina=" + getBonusStamina() +
                '}';
    }



    public int getSpeed() {
        return speed;
    }
    public int getTotalSpeed() { return speed+bonusSpeed; }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getIq() {
        return iq;
    }
    public int getTotalIq() { return iq+ bonusIq; }

    public void setIq(int iq) {
        this.iq = iq;
    }

    public int getMetabolism() {
        return metabolism;
    }
    public int getTotalMetabolism() { return metabolism+bonusMetabolism;}

    public void setMetabolism(int metabolism) {
        this.metabolism = metabolism;
    }

    public int getStamina() {
        return stamina;
    }
    public int getTotalStamina() { return stamina+bonusStamina; }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getBonusSpeed() {
        return bonusSpeed;
    }

    public void setBonusSpeed(int bonusSpeed) {
        this.bonusSpeed = bonusSpeed;
    }

    public int getBonusIq() {
        return bonusIq;
    }

    public void setBonusIq(int bonusIq) {
        this.bonusIq = bonusIq;
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
        this.bonusSpeedDuration = bonusSpeedDuration < 0 ? 0 : bonusSpeedDuration;
        if(this.bonusSpeedDuration == 0) {
            setBonusSpeed(0);
        }
    }

    public int getBonusIqDuration() {
        return bonusIqDuration;
    }

    public void setBonusIqDuration(int bonusIqDuration) {
        this.bonusIqDuration = bonusIqDuration < 0 ? 0 : bonusIqDuration;
        if(this.bonusIqDuration == 0) setBonusIq(0);
    }

    public int getBonusMetabolismDuration() {
        return bonusMetabolismDuration;
    }

    public void setBonusMetabolismDuration(int bonusMetabolismDuration) {
        this.bonusMetabolismDuration = bonusMetabolismDuration < 0 ? 0 : bonusMetabolismDuration;
        if(this.bonusMetabolismDuration == 0) setBonusMetabolism(0);
    }

    public int getBonusStaminaDuration() {
        return bonusStaminaDuration;
    }

    public void setBonusStaminaDuration(int bonusStaminaDuration) {
        this.bonusStaminaDuration = bonusStaminaDuration < 0 ? 0 : bonusStaminaDuration;
        if(this.bonusStaminaDuration == 0) setBonusStamina(0);
    }

    public void tickBonusCounters(){
        setBonusSpeedDuration(getBonusSpeedDuration() -  1);
        setBonusIqDuration(getBonusIqDuration() -  1);
        setBonusMetabolismDuration(getBonusMetabolismDuration() -  1);
        setBonusStaminaDuration(getBonusStaminaDuration() -  1);
    }

}
