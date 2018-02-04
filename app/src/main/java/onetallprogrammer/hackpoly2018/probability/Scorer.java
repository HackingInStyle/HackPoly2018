package onetallprogrammer.hackpoly2018.probability;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Scorer {

    public static double getScore(NormalDistribution distribution, double x){
        if(x > distribution.getMean())
            return 2 * (1 - distribution.cumulativeProbability(x));
        else
            return 2 * distribution.cumulativeProbability(x);
    }
}
