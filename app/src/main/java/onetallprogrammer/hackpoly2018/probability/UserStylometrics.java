package onetallprogrammer.hackpoly2018.probability;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;

/**
 * Created by joseph on 2/4/18.
 */

public class UserStylometrics {

    private ArrayList<Double> timeInBetweenKeyPressMeans = new ArrayList<>();
    private ArrayList<Double> timeInBetweenKeyPressStdDevs = new ArrayList<>();

    public UserStylometrics(ArrayList<Double> timeInBetweenKeyPressMeans, ArrayList<Double> timeInBetweenKeyPressStdDevs) {
        this.timeInBetweenKeyPressMeans = timeInBetweenKeyPressMeans;
        this.timeInBetweenKeyPressStdDevs = timeInBetweenKeyPressStdDevs;
    }

    public double getAuthorizationScore(ArrayList<Double> authProfile) {
        double timeInBetweenScore = 0.0;
        if(timeInBetweenKeyPressMeans.size() == authProfile.size()) {

            for (int i = 0; i < timeInBetweenKeyPressMeans.size(); i++) {
                if(!(timeInBetweenKeyPressMeans.get(i) > 0) || !(timeInBetweenKeyPressStdDevs.get(i) > 0))
                    return 0.0;

                NormalDistribution distribution = new NormalDistribution(timeInBetweenKeyPressMeans.get(i), timeInBetweenKeyPressStdDevs.get(i));
                System.out.println(timeInBetweenKeyPressMeans.get(i) + " " + timeInBetweenKeyPressStdDevs.get(i));
                timeInBetweenScore += Scorer.getScore(distribution, authProfile.get(i)) / timeInBetweenKeyPressMeans.size();
            }
        }

        return timeInBetweenScore;
    }
}