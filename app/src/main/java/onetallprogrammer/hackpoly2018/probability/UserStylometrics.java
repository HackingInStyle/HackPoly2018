package onetallprogrammer.hackpoly2018.probability;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseph on 2/4/18.
 */

public class UserStylometrics {

    private ArrayList<Double> pressSizeMeans = new ArrayList<>();
    private ArrayList<Double> pressSizeStdDevs = new ArrayList<>();
    private int passwordLength;

    public UserStylometrics(ArrayList<Double> pressSizeMeans, ArrayList<Double> pressSizeStdDevs, int passwordLength) {
        this.pressSizeMeans = pressSizeMeans;
        this.pressSizeStdDevs = pressSizeStdDevs;
        this.passwordLength = passwordLength;
    }

    public double getAuthorizationScore(ArrayList<Double> authProfile) {
        double pressSizeScore = 0.0;

        if(pressSizeMeans.size() == passwordLength && authProfile.size() == passwordLength) {

            for (int i = 0; i < passwordLength; i++) {
                NormalDistribution distribution = new NormalDistribution(pressSizeMeans.get(i), pressSizeStdDevs.get(i));
                pressSizeScore += Scorer.getScore(distribution, authProfile.get(i)) / passwordLength;
            }
        }

        return pressSizeScore;
    }
}