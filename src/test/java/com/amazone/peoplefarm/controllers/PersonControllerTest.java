package com.amazone.peoplefarm.controllers;

import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import com.amazone.peoplefarm.model.Status;
import com.amazone.peoplefarm.services.GameLogicService;
import com.amazone.peoplefarm.services.GameStateService;
import com.amazone.peoplefarm.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring-context.xml" })
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private GameLogicService gameLogicService;

    @Autowired
    private GameStateService gameStateService;

    @InjectMocks
    private PersonController personController;

    @Autowired
    private WebApplicationContext context;

    @PersistenceContext
    private EntityManager em;

    private int gameStateId;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        GameState gameState = gameLogicService.newGame();
        gameStateService.save(gameState);
        gameStateId = gameState.getId();
    }

    @Test
    public void createPerson() throws Exception {
        Person person = gameLogicService.newPerson();
        //when(personController.createPerson(null, person)).thenReturn(person);

        this.mockMvc.perform(post("/createperson").contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("gameState", gameStateId)
                .content(asJsonString(person))).andExpect(status().isOk());

        //verifyZeroInteractions(personService);
        //verifyZeroInteractions(gameLogicService);
        //verifyZeroInteractions(gameStateService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
