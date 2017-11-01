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

        // Update hunger
        personStatus.setHunger(personStatus.getHunger() - personAbilities.getMetabolism());
        // Minimum is 0
        if(personStatus.getHunger() == 0) {
            personStatus.setHealth(Status.Health.DEAD);
        }

        // Update tiredness
        personStatus.setTiredness(personStatus.getTiredness() - (100 - personAbilities.getStamina())/100);

        personStatus.setCurrentCaptchas(personStatus.getCurrentCaptchas() + (int)Math.round( (personAbilities.getSpeed() * 0.1) + (personAbilities.getIq() * 0.1) * (personStatus.getTiredness() * 0.01)) );
        //System.out.println("add c " + (int)( (personAbilities.getSpeed() * 0.1) + (personAbilities.getIq() * 0.1) * (personStatus.getTiredness() * 0.01)));
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
