package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.services.GameLogicService;
import com.amazone.peoplefarm.services.GameStateService;
import com.amazone.peoplefarm.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.amazone.peoplefarm.model.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
@SessionAttributes("gameState")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private GameStateService gameStateService;
    @Autowired
    private GameLogicService gameLogicService;

    @ResponseBody
    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable int id, HttpServletResponse response){
        Person person = personService.findOne(id);
        if(person == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return person;
    }

    @ResponseBody
    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public Response deletePerson(Model model, @PathVariable int id) {
        System.out.println("!!!!!!!!!!!!!");
        Person person = personService.findOne(id);
        if(person.getStatus().getHealth() == Status.Health.DEAD) {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            List<Person> persons = gameState.getPersons();

            Person x = null;
            for(Person p:persons){
               if(p.getId() == id){
                   x =  p;
                   break;
               }
            }
            if(x!=null)persons.remove(x);

            gameState.setPersons(persons);
            gameStateService.save(gameState);

            System.out.println(persons);
            return new Response(true);
        }
        return new Response(false);
    }

    @RequestMapping(value = "/main")
    public String main(Model model) {
        if(!model.containsAttribute("gameState")){
            GameState gameState = gameLogicService.newGame();
            gameStateService.save(gameState);
            model.addAttribute("gameState", gameState.getId());
        }
        return "main";
    }

    @ResponseBody
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> getPersons(Model model){
        GameState gameState = gameStateService.findOne((Integer)model.asMap().get("gameState"));
        return gameState.getPersons();
    }

    //TODO: - PUT  /person/settask/:task/:id      -> set task for person with id
    @ResponseBody
    @RequestMapping(value = "/person/settask/{task}/{id}", method = RequestMethod.PUT)
    public Response setTask(@PathVariable String task, @PathVariable int id, Model model){
       Person person = personService.findOne(id);
       Status state = person.getStatus();
       Response response = new Response(false);
        switch (task){
            case "sleeping":
                int sleepTime = 100;
                state.setTiredness(state.getTiredness()+sleepTime);
                savePersonState(person, state, response);
                break;
            case "collecting":
                if(model.containsAttribute("gameState")){
                    GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
                    gameState.setScore(gameState.getScore() + (int)(state.getCurrentCaptchas() * GameLogicService.CAPTCHA_VALUE));
                    state.setCurrentCaptchas(0);
                    gameStateService.save(gameState);
                    savePersonState(person, state, response);
                }
                break;
            case "dying":
                state.setHealth(Status.Health.DEAD);
                savePersonState(person, state, response);
                break;
            default:
                break;
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/person/settask/eating{food}/{id}", method = RequestMethod.PUT)
    public Response setTaskEating(@PathVariable String food, @PathVariable int id, Model model){
        Person person = personService.findOne(id);
        Status state = person.getStatus();
        Response response = new Response(false);
        int nutrients = 0;
        int cost = 0;
        switch (food){
            case "hamburger":
                nutrients = 100;
                cost = 3;
                break;
            case "dogfood":
                nutrients = 50;
                cost = 1;
            default:
                break;
        }
        state.setHunger(state.getHunger()+ nutrients);
        savePersonState(person, state, response);
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        gameState.setScore(gameState.getScore()-cost);
        gameStateService.save(gameState);
        return response;
    }

    Response savePersonState(Person person , Status state, Response response){
        response.setSucces(true);
        person.setStatus(state);
        personService.save(person);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/person/settask/{task}/{id1}/{id2}", method = RequestMethod.PUT)
    public Map reproduce(@PathVariable int id1, @PathVariable int id2, Model model){
        GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
        System.out.println("gameState: " + gameState);

        Person parent1 = personService.findOne(id1);
        Person parent2 = personService.findOne(id2);
        System.out.println("Person " + parent1.getId() + " and person " + parent2.getId() + " are reproducing ");


        Person newPerson = gameLogicService.newChild(parent1,parent2,gameState);
        Map<String, String> response = new HashMap<String, String>();
        if(newPerson != null){
            gameState.addPerson(newPerson);
            newPerson.setGamestate(gameState);
            personService.save(newPerson);
            System.out.println("Person " + newPerson.getId() + " is born: " + newPerson);

            response.put("succes", "true");
            response.put("id",""+newPerson.getId());
        } else {
            response.put("succes", "false");
        }
        return response;
    }
}
