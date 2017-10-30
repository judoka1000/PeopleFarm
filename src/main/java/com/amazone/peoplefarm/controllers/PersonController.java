package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.amazone.peoplefarm.model.*;


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
    public String main() {
        return "main";
    }
    
    @ResponseBody
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public Iterable<Person> getPersons(){
        Iterable<Person> persons = personService.findAll();
        return persons;
    }

    //TODO: - PUT  /person/settask/:task/:id      -> set task for person with id
    @ResponseBody
    @RequestMapping(value = "/person/settask/{task}/{id}", method = RequestMethod.PUT)
    public String setTask(@PathVariable String task, @PathVariable int id){
       Person person = personService.findOne(id);
       Status state = person.getStatus();
        switch (task){
            case "eating":
                int food = 100;
                state.setHunger(state.getHunger()+ food);
                break;
            case "sleeping":
                int sleepTime = 100;
                state.setTiredness(state.getTiredness()+sleepTime);
                break;
            case "captcha":
                break;
            case "dying":
                break;
            default:
                break;
        }
        person.setStatus(state);
        personService.save(person);
        return "main";
    }
}
