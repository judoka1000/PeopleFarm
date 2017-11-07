package com.amazone.peoplefarm.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {
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

    public double getNormal(double mean, double deviation) {
        Random r = new Random();
        return mean + (deviation*r.nextGaussian());
    }

    public int getNormalInt(int mean, int deviation) {
        Random r = new Random();
        return (int)(mean + (deviation*r.nextGaussian()));
    }

    public int getBoundedNormalInt(int mean, int deviation, int min, int max) {
        int value;
        do {
            value = getNormalInt(mean, deviation);
        } while(value <= min || value >= max);
        return value;
    }
}
