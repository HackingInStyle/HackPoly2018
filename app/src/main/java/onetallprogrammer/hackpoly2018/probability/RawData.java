package onetallprogrammer.hackpoly2018.probability;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseph on 2/3/18.
 */

public class RawData {

    ArrayList<Double> data;

    public RawData(){
        data = new ArrayList<Double>();
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }

    public Double getPoint(int index){
        return data.get(index);
    }

    public void addPoint(Double point){
        data.add(point);
    }

    public int getSize(){
        return data.size();
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
