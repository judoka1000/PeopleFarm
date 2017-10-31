package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.services.GameStateService;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.amazone.peoplefarm.model.*;


@Controller
@SessionAttributes("gameState")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private GameStateService gameStateService;

    @ResponseBody
    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable int id){
        Person person = personService.findOne(id);
        return person;
    }

    @RequestMapping(value = "/main")
    public String main(Model model) {
        if(!model.containsAttribute("gameState")){
            GameState gameState = new GameState();
            gameStateService.save(gameState);
            model.addAttribute("gameState", gameState.getId());
        }
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
    public Response setTask(@PathVariable String task, @PathVariable int id){
       Person person = personService.findOne(id);
       Status state = person.getStatus();
       Response response = new Response(false);
        switch (task){
            case "eating":
                int food = 100;
                state.setHunger(state.getHunger()+ food);
                response.setSucces(true);
                break;
            case "sleeping":
                int sleepTime = 100;
                state.setTiredness(state.getTiredness()+sleepTime);
                response.setSucces(true);
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
        return response;
    }
}
