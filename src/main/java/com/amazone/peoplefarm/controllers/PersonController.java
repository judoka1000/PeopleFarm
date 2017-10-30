package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.Abilities;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Status;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @ResponseBody
    @RequestMapping(value = "/hoi")
    public Person zegHoi(){
        Person person = new Person();
        person.setStatus(new Status());
        person.setAbilities(new Abilities());
        personService.save(person);
        return person;
    }

    @RequestMapping(value = "/main")
    public String maarten(){
        return "main";
    }

}
