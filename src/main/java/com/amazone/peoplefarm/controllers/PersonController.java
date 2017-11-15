package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.exceptions.*;
import com.amazone.peoplefarm.models.GameState;
import com.amazone.peoplefarm.models.Person;
import com.amazone.peoplefarm.models.GameState;
import com.amazone.peoplefarm.models.Person;
import com.amazone.peoplefarm.services.*;
import com.amazone.peoplefarm.exceptions.GameStateException;
import com.amazone.peoplefarm.exceptions.GameStateNotFoundException;
import com.amazone.peoplefarm.exceptions.PersonException;
import com.amazone.peoplefarm.exceptions.PersonNotFoundException;
import com.amazone.peoplefarm.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.amazone.peoplefarm.models.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Controller
@SessionAttributes({"account"})
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
    @Autowired
    private ButtonService buttonService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public Response<Person> getPerson(@PathVariable int id) throws PersonNotFoundException {
        Person person = personService.findOne(id);
        if (person == null)
            throw new PersonNotFoundException("Person " + id + " not found");

        return new Response<>(true, person);
    }

    @ResponseBody
    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public Response deletePerson(Model model, @PathVariable int id) throws PersonException, PersonNotFoundException, AccountNotFoundException, GameStateNotFoundException {
        Person person = personService.findOne(id);
        if (person == null)
            throw new PersonNotFoundException("Person niet gevonden in database");

        if (person.getStatus().getHealth() != Status.Health.DEAD)
            throw new PersonException("Person is not dead");

        if (!model.containsAttribute("account"))
            throw new AccountNotFoundException("No account in session");

        Account account = accountService.findOne((Integer)model.asMap().get("account"));
        if(account == null)
            throw new AccountNotFoundException("No account in database");

        GameState gameState = account.getGameState();
        if(gameState == null)
            throw new GameStateNotFoundException("No gamestate in current account");

        if(!gameState.removePerson(person))
            throw new PersonNotFoundException("Person not found in gamestate");

        gameStateService.save(gameState);
        return new Response<>(true);
    }

    @RequestMapping(value = "/main")
    public String main(Model model) throws AccountNotFoundException, GameStateNotFoundException {
        if (!model.containsAttribute("account")) {
            return "login";
        } else {
            Account account = accountService.findOne((Integer) model.asMap().get("account"));
            if(account == null)
                return "login";

            System.out.println("Current account = " + account);
            if (account.getGameState() == null) {
                GameState gameState = gameLogicService.newGame();
                account.setGameState(gameState);
                accountService.save(account);
                System.out.println("New game gestart met id " + gameState.getId());
            }
        }
        return "main";
    }

    @ResponseBody
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public Response<List<Person>> getPersons(Model model) throws AccountNotFoundException, GameStateNotFoundException {
        if(!model.containsAttribute("account"))
            throw new AccountNotFoundException("Not logged in");

        Account account = accountService.findOne((Integer)model.asMap().get("account"));
        if(account == null)
            throw new AccountNotFoundException("Account not in database");

        if(account.getGameState() == null)
            throw new GameStateNotFoundException("No gamestate found in account");

        return new Response<>(true, account.getGameState().getPersons());
    }

    @ResponseBody
    @RequestMapping(value = "/createperson", method = RequestMethod.POST)
    public Response<Person> createPerson(Model model, @RequestBody Person person) throws AccountNotFoundException, GameStateNotFoundException {
        if(!model.containsAttribute("account"))
            throw new AccountNotFoundException("Not logged in");

        Account account = accountService.findOne((Integer)model.asMap().get("account"));
        if(account == null)
            throw  new AccountNotFoundException("Account not in database");

        if (account.getGameState() == null)
            throw new GameStateNotFoundException("Gamestate not in account");

        account.getGameState().addPerson(personLogicService.newPersonFrom(person));
        accountService.save(account);

        return new Response<Person>(true, person);
    }

    @ResponseBody
    @RequestMapping(value = "/person/settask/{task}/{id}", method = RequestMethod.PUT)
    public Response setTask(@PathVariable String task, @PathVariable int id, Model model) throws PersonNotFoundException, GameStateNotFoundException, GameStateException, AccountNotFoundException {
        if (!model.containsAttribute("account"))
            throw new AccountNotFoundException("Not logged in");

        Account account = accountService.findOne((Integer)model.asMap().get("account"));
        if(account == null)
            throw new AccountNotFoundException("No account in database");

        GameState gameState = account.getGameState();
        if (gameState == null)
            throw new GameStateNotFoundException("Gamestate niet gevonden in account.");

        Person person = gameState.getPerson(id);
        if (person == null)
            throw new PersonNotFoundException("Person met id " + id + " niet gevonden in database.");

        Status state = person.getStatus();
        Abilities abilities = person.getAbilities();
        Button button = buttonService.findByClickAction(task);
        switch (task) {
            case "sleeping":
                int sleepTime = 100;
                state.setTiredness(state.getTiredness() + sleepTime);
                personService.save(person);
                break;
            case "collecting":
                gameState.setScore(gameState.getScore() + (int) (state.getCurrentCaptchas() * GameLogicService.CAPTCHA_VALUE));
                person.getStatus().setCurrentCaptchas(0);
                accountService.save(account);
                break;
            case "dying":
                state.setHealth(Status.Health.DEAD);
                personService.save(person);
                break;
            default:
                if(button != null) {
                    System.out.println(button);
                    state.setTiredness(state.getTiredness() + button.getTiredness());
                    state.setHunger(state.getHunger() + button.getHunger());
                    if(button.getBonusSpeed()!=0) abilities.setBonusSpeed(button.getBonusSpeed());
                    if(button.getBonusIQ()!=0) abilities.setBonusIq(button.getBonusIQ());
                    if(button.getBonusMetabolism()!=0) abilities.setBonusMetabolism(button.getBonusMetabolism());
                    if(button.getBonusStamina()!=0) abilities.setBonusStamina(button.getBonusStamina());
                    if(button.getBonusSpeedDuration()!=0) abilities.setBonusSpeedDuration(button.getBonusSpeedDuration());
                    if(button.getBonusIQDuration()!=0) abilities.setBonusIqDuration(button.getBonusIQDuration());
                    if(button.getBonusMetabolismDuration()!=0) abilities.setBonusMetabolismDuration(button.getBonusMetabolismDuration());
                    if(button.getBonusStaminaDuration()!=0) abilities.setBonusStaminaDuration(button.getBonusStaminaDuration());
                    person.setStatus(state);
                    person.setAbilities(abilities);
                    personService.save(person);

                    gameState = gameStateService.findOne((Integer) model.asMap().get("gameState"));
                    if(gameState == null) throw new GameStateNotFoundException("Gamestate niet gevonden in database.");
                    gameState.setScore(gameState.getScore()-button.getUseCost());
                    gameStateService.save(gameState);
                } else {
                    System.out.println("Button null");
                }
                break;
        }
        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/person/settask/eating{food}/{id}", method = RequestMethod.PUT)
    public Response setTaskEating(@PathVariable String food, @PathVariable int id, Model model) throws PersonNotFoundException, GameStateNotFoundException, AccountNotFoundException {
        Person person = personService.findOne(id);
        if (person == null)
            throw new PersonNotFoundException("Person met id " + id + " niet gevonden in database.");

        Status state = person.getStatus();
        int nutrients = 0;
        int cost = 0;
        switch (food) {
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

        state.setHunger(state.getHunger() + nutrients);
        personService.save(person);

        if(!model.containsAttribute("account"))
            throw new AccountNotFoundException("Not logged in");

        Account account = accountService.findOne((Integer)model.asMap().get("account"));
        if(account == null)
            throw new AccountNotFoundException("Account not in database");

        GameState gameState = account.getGameState();
        if (gameState == null)
            throw new GameStateNotFoundException("Gamestate niet gevonden in account.");

        gameState.setScore(gameState.getScore() - cost);
        accountService.save(account);

        return new Response(true);
    }

    @ResponseBody
    @RequestMapping(value = "/person/settask/{task}/{id1}/{id2}", method = RequestMethod.PUT)
    public Response<Person> reproduce(@PathVariable int id1, @PathVariable int id2, Model model) throws PersonException, GameStateNotFoundException, AccountNotFoundException {
        if(!model.containsAttribute("account"))
            throw new AccountNotFoundException("Not logged in");

        Account account = accountService.findOne((Integer)model.asMap().get("account"));
        if(account == null)
            throw new AccountNotFoundException("Account not in database");

        GameState gameState = account.getGameState();
        if (gameState == null)
            throw new GameStateNotFoundException("Gamestate met id " + model.asMap().get("gameState") + " niet gevonden in database.");

        System.out.println("gameState: " + gameState);

        Person parent1 = personService.findOne(id1);
        if (parent1 == null)
            throw new PersonNotFoundException("Person met id " + id1 + " niet gevonden in database.");

        Person parent2 = personService.findOne(id2);
        if (parent2 == null)
            throw new PersonNotFoundException("Person met id " + id2 + " niet gevonden in database.");

        System.out.println("Person " + parent1.getId() + " and person " + parent2.getId() + " are reproducing ");

        Person newPerson = personLogicService.newChild(parent1, parent2);
        if (newPerson == null)
            throw new PersonException("Could not create child.");

        gameState.addPerson(newPerson);
        accountService.save(account);

        System.out.println("Person " + newPerson.getId() + " is born: " + newPerson);

        return new Response<Person>(true, newPerson);
    }
}
