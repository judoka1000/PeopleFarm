package com.amazone.peoplefarm.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private int id;

    private int score;

    String playerName;

    @Embedded
    DevSettings devSettings = new DevSettings();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gamestate", orphanRemoval = true)
    @JsonManagedReference
    List<Person> persons = new ArrayList<Person>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Button> buttons = new ArrayList<Button>();

    public GameState() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Person getPerson(int id) {
        for(Person p : getPersons()) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void addPerson(Person person) {
        person.setGamestate(this);
        this.persons.add(person);
    }

    public boolean removePerson(Person person) {
        return this.persons.remove(person);
    }

    public DevSettings getDevSettings() {
        return devSettings;
    }

    public void setDevSettings(DevSettings devSettings) {
        this.devSettings = devSettings;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public Button addButton(Button button){
        this.buttons.add(button);
        return(button);
    }
}
