package onetallprogrammer.hackpoly2018.probability;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joseph on 2/3/18.
 */
public class ScorerTest {
    RawData data = new RawData();
    Scorer scorer = null;
    double epsilon = 0.000001;

    @Before
    public void setup(){
        data.addPoint(1.0);
        data.addPoint(2.0);
        data.addPoint(3.0);

        scorer = new Scorer(data);
    }

    @Test
    public void getProbability() throws Exception {
        double prob1 = scorer.getProbability(1.1835035);
        double prob2 = scorer.getProbability(2.8164965);
        boolean inRange = Math.abs(prob1 - prob2) < epsilon;

        assertTrue(inRange);
    }

}