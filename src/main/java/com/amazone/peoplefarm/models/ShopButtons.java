package com.amazone.peoplefarm.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class ShopButtons {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    int id;
//
//    String playerName;
//
//    @Embedded
//    DevSettings devSettings = new DevSettings();
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gamestate", orphanRemoval = true)
//    @JsonManagedReference
//    List<Person> persons = new ArrayList<Person>();
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gamestate", orphanRemoval = true)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @JsonManagedReference
//    private List<Button> buttons = new ArrayList<Button>();
//
//
//    public ShopButtons() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public void setScore(int score) {
//        this.score = score;
//    }
//
//    public String getPlayerName() {
//        return playerName;
//    }
//
//    public void setPlayerName(String playerName) {
//        this.playerName = playerName;
//    }
//
//    public List<Person> getPersons() {
//        return persons;
//    }
//
//    public void setPersons(List<Person> persons) {
//        this.persons = persons;
//    }
//
//    public void addPerson(Person person) {
//        person.setGamestate(this);
//        this.persons.add(person);
//    }
//
//    public DevSettings getDevSettings() {
//        return devSettings;
//    }
//
//    public void setDevSettings(DevSettings devSettings) {
//        this.devSettings = devSettings;
//    }
//
//    public List<Button> getButtons() {
//        System.out.println(buttons);
//        return buttons;
//    }
//
//    public void setButtons(List<Button> buttons) {
//        this.buttons = buttons;
//    }
//
//    public void addButton(Button button){
//        button.setGamestate(this);
//        this.buttons.add(button);
//    }
}
