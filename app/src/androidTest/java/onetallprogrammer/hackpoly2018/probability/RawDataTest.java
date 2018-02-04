package onetallprogrammer.hackpoly2018.probability;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joseph on 2/3/18.
 */
public class RawDataTest {
    RawData rawData = new RawData();
    float epsilon = 0.0001f;

    @Before
    public void setup(){
        rawData.addPoint(1.0);
        rawData.addPoint(2.0);
        rawData.addPoint(3.0);
    }

    @Test
    public void getMeanTest() throws Exception {
        Double expected = 2.0;
        Double mean = rawData.getMean();
//        Log.d("mean", String.valueOf(mean));
        boolean inRange = Math.abs(expected - mean) < epsilon;

        assertTrue(inRange);
    }

    @Test
    public void getSumTest() throws Exception{
        Double sum = rawData.getSum();
        Double expected = 6.0;
        boolean inRange = Math.abs(sum - expected) < epsilon;

        assertTrue(inRange);
    }

    @Test
    public void getStdDevTest() throws Exception{
        Double stdDev = rawData.getStdDev();
        Double expected = 0.8165;
        boolean inRange = Math.abs(expected - stdDev) < epsilon;

        assertTrue(inRange);
    }

}