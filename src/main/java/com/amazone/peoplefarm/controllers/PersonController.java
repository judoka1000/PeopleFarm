package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.models.GameState;
import com.amazone.peoplefarm.models.Person;
import com.amazone.peoplefarm.exceptions.GameStateException;
import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
import com.amazone.peoplefarm.exceptions.PersonException;
import com.amazone.peoplefarm.exceptions.PersonNotFoundException;
import com.amazone.peoplefarm.models.GameState;
import com.amazone.peoplefarm.models.Person;
import com.amazone.peoplefarm.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.amazone.peoplefarm.models.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
@SessionAttributes({"gameState", "account"})
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private GameStateService gameStateService;
    @Autowired
    private GameLogicService gameLogicService;
    @Autowired
    private PersonLogicService personLogicService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

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
        if(!model.containsAttribute("account")){
            return "login";
        } else {
            Account account = accountService.findOne((Integer) model.asMap().get("account"));
            System.out.println("Current account = " + account);
//            if(account.getGameState()==null) model.addAttribute("gameState", account.getGameState().getId());
            if (!model.containsAttribute("gameState")) {
                GameState gameState = gameLogicService.newGame();
                gameStateService.save(gameState);
                account.setGameState(gameState);
                accountService.save(account);
                model.addAttribute("gameState", gameState.getId());
                System.out.println("New game gestart met id " + gameState.getId());
            } else {
                    if (account.getGameState()==null){
                        GameState gameState = gameLogicService.newGame();
                        gameStateService.save(gameState);
                        account.setGameState(gameState);
                        accountService.save(account);
                        model.addAttribute("gameState", gameState.getId());
                        System.out.println("New game gestart met id " + gameState.getId());

                    } else if ((Integer) model.asMap().get("gameState") != account.getGameState().getId()){
                        System.out.println("Reloaded account = " + (Integer) model.asMap().get("account"));
                        GameState gameState = gameStateService.findOne(account.getGameState().getId());
                        System.out.println("Reloaded gamestate = " + gameState);
                        model.addAttribute("gameState", gameState.getId());
                    }
                }
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
    public Response<Person> reproduce(@PathVariable int id1, @PathVariable int id2, Model model, HttpServletResponse httpResponse){
        try {
            GameState gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
            if(gameState == null) throw new GameStateNotFoundException("Gamestate met id " + model.asMap().get("gameState") + " niet gevonden in database.");

            System.out.println("gameState: " + gameState);

            Person parent1 = personService.findOne(id1);
            if (parent1 == null) throw new PersonNotFoundException("Person met id " + id1 + " niet gevonden in database.");

            Person parent2 = personService.findOne(id2);
            if (parent2 == null) throw new PersonNotFoundException("Person met id " + id2 + " niet gevonden in database.");

            System.out.println("Person " + parent1.getId() + " and person " + parent2.getId() + " are reproducing ");


            Person newPerson = personLogicService.newChild(parent1, parent2);
            //Map<String, String> response = new HashMap<String, String>();
            if (newPerson == null) throw new PersonException("Could not create child.");
            gameState.addPerson(newPerson);
            newPerson.setGamestate(gameState);
            personService.save(newPerson);
            System.out.println("Person " + newPerson.getId() + " is born: " + newPerson);

            return new Response<Person>(true,newPerson);
        } catch(PersonNotFoundException e) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Response(false, e);
        } catch (PersonException e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        } catch(GameStateNotFoundException e){
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Response(false, e);
        } catch(Exception e){
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Response(false, e);
        }
    }
}
