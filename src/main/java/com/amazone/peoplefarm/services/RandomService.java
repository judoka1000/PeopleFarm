package com.amazone.peoplefarm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.Math.sqrt;
import static org.apache.commons.math3.special.Erf.erfc;
import static org.apache.commons.math3.util.FastMath.log;

@Service
public class RandomService {
    @Autowired
    private Random random;

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

    public boolean getDiffNormalProbability(double mean, double deviation, double lowerValue, double higherValue) {
        double probability = erfc((lowerValue - mean)/(deviation*sqrt(2)));
        probability /= erfc((higherValue - mean)/(deviation * sqrt(2)));
        probability = log(probability);

        if(probability < 0.0) {
            return false;
        } else if(probability > 1.0) {
            return true;
        }

        return getProbability(probability);
    }
}
