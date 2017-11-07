package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.interfaces.PersonLogicInterface;
import com.amazone.peoplefarm.model.Abilities;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonLogicService implements PersonLogicInterface {
    @Autowired
    RandomService randomService;

    public Person newPerson() {
        int[] sprites_male = {0, 1, 2, 3, 5, 7};
        int[] sprites_female = {4, 6, 8, 9};

        Person person = new Person();
        person.setGender(randomService.getProbability(0.5) ? Person.Gender.MALE : Person.Gender.FEMALE);
        if(person.getGender() == Person.Gender.MALE) {
            person.setSprite(sprites_male[randomService.nextInt(sprites_male.length)]);
        } else {
            person.setSprite(sprites_female[randomService.nextInt(sprites_female.length)]);
        }

        person.setStatus(new Status());
        person.getStatus().setHunger(100);
        person.getStatus().setTiredness(100);
        person.getStatus().setAge(0);
        person.getStatus().setHealth(Status.Health.HEALTHY);
        person.getStatus().setCurrentCaptchas(0);
        person.getStatus().setAgeOfDeath(randomService.getBoundedNormalInt(70,20, 0, 120));

        person.setAbilities(new Abilities());
        person.getAbilities().setIq(randomService.getBoundedNormalInt(80, 40,60,120));
        person.getAbilities().setMetabolism(randomService.getBoundedNormalInt(3,5,0,10));
        person.getAbilities().setSpeed(randomService.getBoundedNormalInt(3,5,0,10));
        person.getAbilities().setStamina(randomService.getBoundedNormalInt(3,5,0,10));

        return person;
    }

    public Person newChild(Person parent1, Person parent2) {
        double maxVariance = 0.3;
        if (parent1.getGender() == parent2.getGender()) {
            System.out.println("Two people of the same gender cannot reproduce :(");
        } else if (parent1.getStatus().getAge() <= 18 || parent2.getStatus().getAge() <= 18) {
            System.out.println("A person younger than 18 cannot reproduce :(");
        } else {
            int avgSpeed = (parent1.getAbilities().getSpeed() + parent2.getAbilities().getSpeed())/2;
            int speed = (int) Math.round(avgSpeed + (avgSpeed * randomService.getFactor(maxVariance)));

            int avgIq = (parent1.getAbilities().getIq() + parent2.getAbilities().getIq())/2;
            int iq = (int) Math.round(avgIq + (avgIq * randomService.getFactor(maxVariance)));

            int avgMetabolism = (parent1.getAbilities().getMetabolism() + parent2.getAbilities().getMetabolism())/2;
            int metabolism = (int) Math.round(avgMetabolism + (avgMetabolism * randomService.getFactor(maxVariance)));

            int avgStamina = (parent1.getAbilities().getStamina() + parent2.getAbilities().getStamina())/2;
            int stamina = (int) Math.round(avgStamina + (avgStamina * randomService.getFactor(maxVariance)));

            Person person = newPerson();

            person.getAbilities().setSpeed(speed);
            person.getAbilities().setIq(iq);
            person.getAbilities().setMetabolism(metabolism);
            person.getAbilities().setStamina(stamina);

            return person;
        }
        return null;
    }

    public void updatePersonStatus(Person person) {
        Status personStatus = person.getStatus();
        Abilities personAbilities = person.getAbilities();

        // Update age
        personStatus.setAge(personStatus.getAge() + 1);

        if(person.getGamestate().getDevSettings().isMortal()) {
            // Person dies of old age
            if(personStatus.getAgeOfDeath() < personStatus.getAge()) {
                personStatus.setHealth(Status.Health.DEAD);
            }

            // Update hunger
            personStatus.setHunger(personStatus.getHunger() - personAbilities.getMetabolism());
            // Minimum is 0
            if(personStatus.getHunger() == 0) {
                personStatus.setHealth(Status.Health.DEAD);
            }
            // Update tiredness
            personStatus.setTiredness(personStatus.getTiredness() - (100 - personAbilities.getStamina()) / 100);

            personStatus.setCurrentCaptchas(personStatus.getCurrentCaptchas() + (int)Math.round( (personAbilities.getSpeed() * 0.1) + (personAbilities.getIq() * 0.01) * (personStatus.getTiredness() * 0.01)) );
        }
    }

    public void updatePersonAbilities(Person person) {
        Abilities personAbilities = person.getAbilities();

        // Update IQ
        if(randomService.getProbability(0.10)) {
            personAbilities.setIq(personAbilities.getIq() + 1);
        }
        // Update metabolisme
        if(randomService.getProbability(0.10)) {
            personAbilities.setMetabolism(personAbilities.getMetabolism() + 1);
        }
        // Update speed
        if(randomService.getProbability(0.10)) {
            personAbilities.setSpeed(personAbilities.getSpeed() + 1);
        }
        // Update stamina
        if(randomService.getProbability(0.10)) {
            personAbilities.setStamina(personAbilities.getStamina() + 1);
        }
    }
}
