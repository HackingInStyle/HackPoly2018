package onetallprogrammer.hackpoly2018.probability;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Scorer {

    private RawData data = new RawData();
    private NormalDistribution distribution = new NormalDistribution();

    public Scorer(RawData data) {
        this.data = data;
        this.distribution = new NormalDistribution(data.getMean(), data.getStdDev());
    }

    public double getProbability(double x){
        if(x > distribution.getMean())
            return 2 * (1 - distribution.cumulativeProbability(x));
        else
            return 2 * distribution.cumulativeProbability(x);
    }
}
