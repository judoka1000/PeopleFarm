package com.amazone.peoplefarm.schedules;

import com.amazone.peoplefarm.model.Abilities;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Status;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class Periodic {
    @Autowired
    private PersonService personService;

    private void updatePersonStatus(Person person) {
        Status personStatus = person.getStatus();
        Abilities personAbilities = person.getAbilities();
        Random r = new Random();

        // Update age
        personStatus.setAge(personStatus.getAge() + 1);
        if(r.nextInt(70) < 1) {
            personStatus.setHealth(Status.Health.DEAD);
        }
        if(personStatus.getAge() >= 18 && person.getGender().equals(Person.Gender.CHILD)) {
            int[] sprites_male = {0, 1, 2, 3, 5, 7};
            int[] sprites_female = {4, 6, 8, 9};
            if(r.nextInt(2) < 1) {
                person.setGender(Person.Gender.MALE);
                person.setSprite(sprites_male[r.nextInt(sprites_male.length)]);
            } else {
                person.setGender(Person.Gender.FEMALE);
                person.setSprite(sprites_female[r.nextInt(sprites_female.length)]);
            }
        }

        // Update hunger
        personStatus.setHunger(personStatus.getHunger() - personAbilities.getMetabolism());
        // Minimum is 0
        if(personStatus.getHunger() == 0) {
            personStatus.setHealth(Status.Health.DEAD);
        }

        // Update tiredness
        personStatus.setTiredness(personStatus.getTiredness() - (100 - personAbilities.getStamina())/100);
    }

    private void updatePersonAbilities(Person person) {
        Abilities personAbilities = person.getAbilities();

        Random r = new Random();
        // Update IQ
        if(r.nextInt(100) < 10) {
            personAbilities.setIq(personAbilities.getIq() + 1);
        }
        // Update metabolisme
        if(r.nextInt(100) < 10) {
            personAbilities.setMetabolism(personAbilities.getMetabolism() + 1);
        }
        // Update speed
        if(r.nextInt(100) < 10) {
            personAbilities.setSpeed(personAbilities.getSpeed() + 1);
        }
        // Update stamina
        if(r.nextInt(100) < 10) {
            personAbilities.setStamina(personAbilities.getStamina() + 1);
        }
    }

    private void updateGameStatus() {
    }

    public void execute() {
        Iterable<Person> personList = personService.findAll();

        System.out.println("Updating person status");
        try {
            for(Person person : personList) {
                updatePersonStatus(person);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Updating person abilities");
        try {
            for(Person person : personList) {
                updatePersonAbilities(person);
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
