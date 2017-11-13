package com.amazone.peoplefarm.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {
    /* Make a decision based on probability */
    public boolean getProbability(double probability) {
        Random r = new Random();
        return (r.nextDouble() < probability);
    }

    public double getFactor(double max){
        Random r = new Random();
        // Return random number between -max and max
        double polarity = r.nextBoolean() ? -1 : 1;
        return polarity * r.nextDouble() * max / 10;
    }

    public int nextInt(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    /* Draw normally distributed floating number */
    public double getNormal(double mean, double deviation) {
        Random r = new Random();
        return mean + (deviation*r.nextGaussian());
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
