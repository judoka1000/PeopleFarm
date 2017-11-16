package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.interfaces.GameLogicInterface;
import com.amazone.peoplefarm.models.Button;
import com.amazone.peoplefarm.models.GameState;
import com.amazone.peoplefarm.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameLogicService implements GameLogicInterface {
    @Autowired
    PersonService personService;

    @Autowired
    PersonLogicService personLogicService;

    @Autowired
    ButtonService buttonService;

    public static final double CAPTCHA_VALUE = 0.60;

    public void addButton(){
        GameState gamestate = new GameState();
        gamestate.addButton(new Button("Hamburger", "eatHamburger", "hamburger.png"));
    }

    public GameState newGame() {
        GameState gameState = new GameState();
        gameState.setPlayerName("Anonymous");
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());

        //gameState.addButton(buttonService.findByName("Hamburger"));
        gameState.addButton(buttonService.findByName("Sleep"));
        //gameState.addButton(buttonService.findByName("Reproducing"));
        gameState.addButton(buttonService.findByName("None"));
        gameState.addButton(buttonService.findByName("Info"));
        gameState.addButton(buttonService.findByName("Compare"));
        gameState.addButton(buttonService.findByName("Collect"));
        return gameState;
    }

    public void periodicUpdate(double period) {
        Iterable<Person> personList = personService.findAlive();

        System.out.println("Updating person status");
        try {
            for(Person person : personList) {
                personLogicService.updatePersonStatus(person);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Updating person abilities");
        try {
            for(Person person : personList) {
                personLogicService.updatePersonAbilities(person);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Updating game states");
        try {
            System.out.println("Nothing to do");
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Save persons");
        try {
            for(Person person : personList) {
                personService.save(person);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Done updating");
    }
}
