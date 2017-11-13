package com.amazone.peoplefarm.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring-context.xml" })
public class RandomServiceTest {
    @Autowired
    private RandomService randomService;

    private class ProbabilityEvent<T> {
        public ProbabilityEvent(double probability, T value) {
            this.probability = probability;
            this.value = value;
        }

        double probability;
        int count;
        T value;
    }

    private interface PExperiment<T> {
        T doExperiment();
    }

    private class PTest<T> {
        private ProbabilityEvent<T>[] events;

        private PTest(ProbabilityEvent... events) {
            this.events = events;
        }

        private boolean pTest(int repetitions, PExperiment<T> experiment)
        {
            for(int i = 0; i < repetitions; i++) {
                T tempVal = experiment.doExperiment();
                for(ProbabilityEvent<T> event: events) {
                    if(event.value.equals(tempVal)) {
                        event.count++;
                    }
                }
            }

            double pValues[] = {3.84, 5.99, 7.81, 9.49, 11.07, 12.53, 14.07, 15.51, 16.92, 18.31};

            double chiSquare = 0.0;
            for(ProbabilityEvent<T> event : events) {
                double t = event.count - (event.probability * repetitions);
                chiSquare += t*t/(event.probability * repetitions);
            }

            return (chiSquare <= pValues[events.length-1]);
        }
    }

    @Test
    public void testGetProbability() {
        assertEquals(false, randomService.getProbability(0.0));
        assertEquals(true, randomService.getProbability(1.0));

        assertEquals(true, new PTest<Boolean>(
                new ProbabilityEvent<>(0.25,true),
                new ProbabilityEvent<>(0.75,false)
        ).pTest(100000, () -> {return randomService.getProbability(0.25);} ));
    }

    @Test
    public void testGetFactor() {

    }
}
