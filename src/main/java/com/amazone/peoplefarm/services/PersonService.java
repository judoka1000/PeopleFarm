package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PersonService extends CrudRepository<Person,Integer>{
    @Query("SELECT p FROM Person p WHERE p.status.health != 'DEAD'")
    public Iterable<Person> findAlive();
}
