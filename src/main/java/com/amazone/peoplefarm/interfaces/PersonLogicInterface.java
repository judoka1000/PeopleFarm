package com.amazone.peoplefarm.interfaces;

import com.amazone.peoplefarm.model.Person;

public interface PersonLogicInterface {
    public Person newPerson();
    public Person newChild(Person parent1, Person parent2);
    public void updatePersonStatus(Person person);
    public void updatePersonAbilities(Person person);
}
