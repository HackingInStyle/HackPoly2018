package onetallprogrammer.hackpoly2018.probability;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseph on 2/3/18.
 */

public class RawData {

    List<Float> data;

    public RawData(){
        data = new ArrayList<Float>();
    }

    public List<Float> getData() {
        return data;
    }

    public void setData(List<Float> data) {
        this.data = data;
    }

    public void addPoint(float point){
        data.add(point);
    }

    public float getMean(){

        if(data.isEmpty()) return 0;

        return getSum() / data.size();
    }

    public float getSum(){
        float sum = 0;

        for(int i = 0; i < data.size(); i++) {
            sum += data.get(i);
        }

        return sum;
    }

    public float getStdDev(){
        float mean = getMean();
        Double sumOfSquares = 0.0;

        for(int i = 0; i < data.size(); i++){
            sumOfSquares += Math.pow(data.get(i) - mean, 2);
        }

        return (float) Math.sqrt(sumOfSquares / data.size());
    }

}
