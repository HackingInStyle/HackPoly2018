package onetallprogrammer.hackpoly2018.probability;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseph on 2/3/18.
 */

public class RawData {

    List<Double> data;

    public RawData(){
        data = new ArrayList<Double>();
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public void addPoint(Double point){
        data.add(point);
    }

    public Double getMean(){

        if(data.isEmpty()) return 0.0;

        return getSum() / data.size();
    }

    public Double getSum(){
        Double sum = 0.0;

        for(int i = 0; i < data.size(); i++) {
            sum += data.get(i);
        }

        return sum;
    }

    public Double getStdDev(){
        Double mean = getMean();
        Double sumOfSquares = 0.0;

        for(int i = 0; i < data.size(); i++){
            sumOfSquares += Math.pow(data.get(i) - mean, 2);
        }

        return Math.sqrt(sumOfSquares / data.size());
    }

}
