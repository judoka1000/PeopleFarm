package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.services.PersonLogicService;
import com.amazone.peoplefarm.services.RandomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring-context.xml" })
public class RandomServiceTest {
    @Autowired
    private RandomService randomService;

    class ProbabilityEvent {
        public ProbabilityEvent(double probability) {
            this.probability = probability;
        }

        double probability;
        int count;
    }

    private boolean pTest(List<ProbabilityEvent> events)
    {
        double pValues[] = {3.84, 5.99, 7.81, 9.49, 11.07, 12.53, 14.07, 15.51, 16.92, 18.31};

        int totalEvents = 0;
        for(ProbabilityEvent event : events) {
            totalEvents += event.count;
        }
        double chiSquare = 0.0;
        for(ProbabilityEvent event : events) {
            double t = event.count - (event.probability * totalEvents);
            chiSquare += t*t/(event.probability * totalEvents);
        }

        return (chiSquare <= pValues[events.size()-1]);
    }

    @Test
    public void testGetProbability() {
        assertEquals(false, randomService.getProbability(0.0));
        assertEquals(true, randomService.getProbability(1.0));

        List<ProbabilityEvent> events = new ArrayList<>();
        events.add(new ProbabilityEvent(0.25));
        events.add(new ProbabilityEvent(0.75));
        for(int i = 0; i < 100000; i++) {
            if(randomService.getProbability(0.25)) {
                events.get(0).count++;
            } else {
                events.get(1).count++;
            }
        }

        assertEquals(true, pTest(events));
    }
}
