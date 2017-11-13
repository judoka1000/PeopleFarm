package com.amazone.peoplefarm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {
    @Autowired
    Random random;

    /* Make a decision based on probability */
    public boolean getProbability(double probability) {
        return (random.nextDouble() < probability);
    }

    public double getFactor(double max){
        // Return random number between -max and max
        double polarity = random.nextBoolean() ? -1 : 1;
        return polarity * random.nextDouble() * max / 10;
    }

    public int nextInt(int max) {
        return random.nextInt(max);
    }

    /* Draw normally distributed floating number */
    public double getNormal(double mean, double deviation) {
        return mean + (deviation*random.nextGaussian());
    }

    /* Draw normally distibuted number and make it integer */
    public int getNormalInt(int mean, int deviation) {
        return (int)getNormal(mean, deviation);
    }

    /* Draw normally distributed number bounded between min and max */
    public int getBoundedNormalInt(int mean, int deviation, int min, int max) {
        int value;
        do {
            value = getNormalInt(mean, deviation);
        } while(value <= min || value >= max);
        return value;
    }
}
