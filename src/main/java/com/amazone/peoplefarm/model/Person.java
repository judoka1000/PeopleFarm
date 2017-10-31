package com.amazone.peoplefarm.model;

import javax.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    public enum Gender {
        MALE,
        FEMALE,
        CHILD
    }

    @Enumerated(EnumType.STRING)
    Gender gender;

    int sprite;

    @Embedded
    Status status;

    @Embedded
    Abilities abilities;

    public Person() {
    }

    public Person(String kind) {
        Status newStatus = new Status();
        Abilities newAbilities = new Abilities();
        switch(kind) {
            default:
            case "adult":
                newStatus.setAge(20);
                newStatus.setTiredness(75);
                newStatus.setHunger(75);
                newStatus.setHealth(Status.Health.HEALTHY);
                newAbilities.setStamina(5);
                newAbilities.setSpeed(5);
                newAbilities.setMetabolism(5);
                newAbilities.setIq(5);
                this.setGender(Gender.FEMALE);
                break;
            case "child":
                newStatus.setAge(0);
                newStatus.setTiredness(50);
                newStatus.setHunger(50);
                newStatus.setHealth(Status.Health.HEALTHY);
                newAbilities.setStamina(3);
                newAbilities.setSpeed(3);
                newAbilities.setMetabolism(3);
                newAbilities.setIq(3);
                this.setGender(Gender.CHILD);
                break;
        }
        this.setStatus(newStatus);
        this.setAbilities(newAbilities);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Abilities getAbilities() {
        return abilities;
    }

    public void setAbilities(Abilities abilities) {
        this.abilities = abilities;
    }


}
