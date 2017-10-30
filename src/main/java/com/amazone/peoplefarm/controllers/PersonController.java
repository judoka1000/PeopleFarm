package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @ResponseBody
    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable int id){
        Person person = personService.findOne(id);
        return person;
    }

    @RequestMapping(value = "/main")
    public String main(){
        return "main";

    @ResponseBody
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public Iterable<Person> getPersons(){
        Iterable<Person> persons = personService.findAll();
        return persons;
    }

}
