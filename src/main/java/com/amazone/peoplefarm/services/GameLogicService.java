package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.model.Abilities;
import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameLogicService {
    @Autowired
    PersonService personService;

    public static final double CAPTCHA_VALUE = 0.60;

    public GameState newGame() {
        GameState gameState = new GameState();
        gameState.addPerson(newPerson());
        gameState.addPerson(newPerson());
        gameState.addPerson(newPerson());
        gameState.addPerson(newPerson());
        gameState.addPerson(newPerson());
        gameState.addPerson(newPerson());
        return gameState;
    }

    public Person newPerson() {
        Random r = new Random();

        int[] sprites_male = {0, 1, 2, 3, 5, 7};
        int[] sprites_female = {4, 6, 8, 9};

        Person person = new Person();
        person.setGender(r.nextBoolean() ? Person.Gender.MALE : Person.Gender.FEMALE);
        if(person.getGender() == Person.Gender.MALE) {
            person.setSprite(sprites_male[r.nextInt(sprites_male.length)]);
        } else {
            person.setSprite(sprites_female[r.nextInt(sprites_female.length)]);
        }

        person.setStatus(new Status());
        person.getStatus().setHunger(100);
        person.getStatus().setTiredness(100);
        person.getStatus().setAge(0);
        person.getStatus().setHealth(Status.Health.HEALTHY);
        person.getStatus().setCurrentCaptchas(0);

        person.setAbilities(new Abilities());
        person.getAbilities().setIq(r.nextInt(40) + 80);
        person.getAbilities().setMetabolism(r.nextInt(5) + 3);
        person.getAbilities().setSpeed(r.nextInt(5) + 3);
        person.getAbilities().setStamina(r.nextInt(5) + 3);

        return person;
    }
}
