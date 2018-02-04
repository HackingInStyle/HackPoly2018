package onetallprogrammer.hackpoly2018.probability;

import android.util.Log;

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
        rawData.addPoint(1);
        rawData.addPoint(2);
        rawData.addPoint(3);
    }

    @Test
    public void getMeanTest() throws Exception {
        float expected = 2.0f;
        float mean = rawData.getMean();
//        Log.d("mean", String.valueOf(mean));
        boolean inRange = Math.abs(expected - mean) < epsilon;

        assertTrue(inRange);
    }

    @Test
    public void getSumTest() throws Exception{
        float sum = rawData.getSum();
        float expected = 6.0f;
        boolean inRange = Math.abs(sum - expected) < epsilon;

        assertTrue(inRange);
    }

    @Test
    public void getStdDevTest() throws Exception{
        float stdDev = rawData.getStdDev();
        float expected = 0.8165f;
        boolean inRange = Math.abs(expected - stdDev) < epsilon;

        assertTrue(inRange);
    }

}