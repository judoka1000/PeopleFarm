package com.amazone.peoplefarm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    public enum Gender {
        MALE,
        FEMALE
    }

    @Enumerated(EnumType.STRING)
    Gender gender;

    int sprite;

    @Embedded
    Status status;

    @Embedded
    Abilities abilities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @JsonBackReference
    GameState gamestate;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", gender=" + gender +
                ", sprite=" + sprite +
                ", status=" + status +
                ", abilities=" + abilities +
                '}';
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

    public GameState getGamestate() {
        return gamestate;
    }

    public void setGamestate(GameState gamestate) {
        this.gamestate = gamestate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getId() != person.getId()) return false;
        return getGamestate() != null ? getGamestate().getId() == person.getGamestate().getId() : person.getGamestate() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getGamestate() != null ? getGamestate().hashCode() : 0);
        return result;
    }
}
