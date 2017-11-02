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
        gameState.setPlayerName("Anonymous");
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


    public Person newChild(Person parent1, Person parent2, GameState gameState){
        double maxVariance = 0.3;
        if (parent1.getGender() == parent2.getGender()) {
            System.out.println("Two people of the same gender cannot reproduce :(");
        } else if (parent1.getStatus().getAge() <= 18 || parent2.getStatus().getAge() <= 18) {
            System.out.println("A person younger than 18 cannot reproduce :(");
        } else {
            int avgSpeed = (parent1.getAbilities().getSpeed() + parent2.getAbilities().getSpeed())/2;
            int speed = (int) Math.round(avgSpeed + (avgSpeed * getFactor(maxVariance)));

            int avgIq = (parent1.getAbilities().getIq() + parent2.getAbilities().getIq())/2;
            int iq = (int) Math.round(avgIq + (avgIq * getFactor(maxVariance)));

            int avgMetabolism = (parent1.getAbilities().getMetabolism() + parent2.getAbilities().getMetabolism())/2;
            int metabolism = (int) Math.round(avgMetabolism + (avgMetabolism * getFactor(maxVariance)));

            int avgStamina = (parent1.getAbilities().getStamina() + parent2.getAbilities().getStamina())/2;
            int stamina = (int) Math.round(avgStamina + (avgStamina * getFactor(maxVariance)));

            Person person = newPerson();

            person.getAbilities().setSpeed(speed);
            person.getAbilities().setIq(iq);
            person.getAbilities().setMetabolism(metabolism);
            person.getAbilities().setStamina(stamina);

            return person;
        }
        return null;
    }

    double getFactor(double max){
        Random r = new Random();
        // Return random number between -max and max
        double polarity = r.nextBoolean() ? -1 : 1;
        return polarity * ((double)r.nextInt((int)(max*10))/100);
    }
}
