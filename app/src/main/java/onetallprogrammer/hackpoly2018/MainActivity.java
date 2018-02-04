package onetallprogrammer.hackpoly2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import onetallprogrammer.hackpoly2018.probability.RawData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RawData data = new RawData();

        data.addPoint(1);
        data.addPoint(2);
        data.addPoint(3);

        Log.i("mean", String.valueOf(data.getMean()));
    }
}