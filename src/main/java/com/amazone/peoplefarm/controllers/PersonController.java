package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.GameStateException;
import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
import com.amazone.peoplefarm.exceptions.PersonException;
import com.amazone.peoplefarm.exceptions.PersonNotFoundException;
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
    public Response<Person> getPerson(@PathVariable int id, HttpServletResponse httpResponse){
        try {
            Person person = personService.findOne(id);
            if (person == null) throw new PersonNotFoundException("Person " + id + " not found");
            return new Response<>(true,person);
        } catch (PersonNotFoundException e){
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Response<>(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public Response deletePerson(Model model, @PathVariable int id, HttpServletResponse httpResponse) {
        try {
            Person person = personService.findOne(id);
            if(person == null) {
                throw new PersonNotFoundException("Person niet gevonden in database");
            } else {
                if (person.getStatus().getHealth() != Status.Health.DEAD) {
                    throw new PersonException("Niet dood");
                } else {
                    GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
                    List<Person> persons = gameState.getPersons();

                    if (persons.remove(person)) {
                        gameState.setPersons(persons);
                        gameStateService.save(gameState);

                        return new Response(true);
                    } else {
                        throw new PersonNotFoundException("Persoon niet gevonden in gamestate");
                    }
                }
            }
        } catch (PersonNotFoundException e) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Response(false, e);
        } catch (PersonException e){
            httpResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return new Response(false, e);
        } catch (Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        }
    }
    @RequestMapping(value = "/main")
    public String main(Model model) {
        if(!model.containsAttribute("gameState")){
            GameState gameState = gameLogicService.newGame();
            gameStateService.save(gameState);
            model.addAttribute("gameState", gameState.getId());
            System.out.println("New game gestart met id " + gameState.getId());
        }
        return "main";
    }

    @ResponseBody
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public Response<List<Person>> getPersons(Model model, HttpServletResponse httpResponse){
        try {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            return new Response<>(true, gameState.getPersons());
        } catch (Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response<>(false, e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/createperson", method = RequestMethod.POST)
    public Response<Person> createPerson(Model model, @RequestBody Person person, HttpServletResponse httpResponse){
        try {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            if(gameState == null) throw new GameStateNotFoundException("Gamestate met id " + model.asMap().get("gameState") + " niet gevonden in database.");
            person.setGamestate(gameState);
            gameState.addPerson(person);
            person.getStatus().setHealth(Status.Health.HEALTHY);
        } catch (GameStateNotFoundException e){
            httpResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            return new Response(false, e);
        }
        personService.save(person);
        return new Response<Person>(true,person);
    }

    @ResponseBody
    @RequestMapping(value = "/person/settask/{task}/{id}", method = RequestMethod.PUT)
    public Response setTask(@PathVariable String task, @PathVariable int id, Model model, HttpServletResponse httpResponse){
       try {
           Person person = personService.findOne(id);
           if (person == null) throw new PersonNotFoundException("Person met id " + id + " niet gevonden in database.");
           Status state = person.getStatus();
           switch (task) {
               case "sleeping":
                   int sleepTime = 100;
                   state.setTiredness(state.getTiredness() + sleepTime);
                   person.setStatus(state);
                   personService.save(person);
                   break;
               case "collecting":
                   if (!model.containsAttribute("gameState")) throw new GameStateException("Geen gamestate in sessie.");
                   GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
                   if(gameState == null) throw new GameStateNotFoundException("Gamestate niet gevonden in database.");
                   gameState.setScore(gameState.getScore() + (int) (state.getCurrentCaptchas() * GameLogicService.CAPTCHA_VALUE));
                   state.setCurrentCaptchas(0);
                   gameStateService.save(gameState);
                   person.setStatus(state);
                   personService.save(person);
                   break;
               case "dying":
                   state.setHealth(Status.Health.DEAD);
                   person.setStatus(state);
                   personService.save(person);
                   break;
               default:
                   break;
           }
           return new Response(true);
       } catch(PersonNotFoundException e) {
           httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
           return new Response(false, e);
       } catch(GameStateNotFoundException e){
           httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
           return new Response(false, e);
       } catch(GameStateException e){
           httpResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
           return new Response(false, e);
       } catch(Exception e){
           httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           return new Response(false, e);
       }
    }

    @ResponseBody
    @RequestMapping(value = "/person/settask/eating{food}/{id}", method = RequestMethod.PUT)
    public Response setTaskEating(@PathVariable String food, @PathVariable int id, Model model, HttpServletResponse httpResponse){
        try{
            Person person = personService.findOne(id);
            if (person == null) throw new PersonNotFoundException("Person met id " + id + " niet gevonden in database.");
            Status state = person.getStatus();
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
            person.setStatus(state);
            personService.save(person);
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            if(gameState == null) throw new GameStateNotFoundException("Gamestate niet gevonden in database.");
            gameState.setScore(gameState.getScore()-cost);
            gameStateService.save(gameState);
            return new Response(true);
        } catch(PersonNotFoundException e) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Response(false, e);
        } catch(GameStateNotFoundException e){
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Response(false, e);
        } catch(GameStateException e){
            httpResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            return new Response(false, e);
        } catch(Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        }
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
        Map<String, String> response = new HashMap<>();
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
