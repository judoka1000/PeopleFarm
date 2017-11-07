package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.interfaces.GameLogicInterface;
import com.amazone.peoplefarm.model.Abilities;
import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameLogicService implements GameLogicInterface {
    @Autowired
    PersonService personService;

    @Autowired
    PersonLogicService personLogicService;

    public static final double CAPTCHA_VALUE = 0.60;

    public GameState newGame() {
        GameState gameState = new GameState();
        gameState.setPlayerName("Anonymous");
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
        gameState.addPerson(personLogicService.newPerson());
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
