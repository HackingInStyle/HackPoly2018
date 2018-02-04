package onetallprogrammer.hackpoly2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import onetallprogrammer.hackpoly2018.probability.RawData;

public class MainActivity extends AppCompatActivity {

    float pressSizeBig = 0;
    long lastPressTime = 0;
    long timeBetweenKeys = 0;
    long timeStartPress = 0;
    long timePressedDown = 0;
    boolean pressed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RawData data = new RawData();

        data.addPoint(1.0);
        data.addPoint(2.0);
        data.addPoint(3.0);

        Log.i("mean", String.valueOf(data.getMean()));

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for(int i=0; i < 10; i++) {
            Button button = (Button)gridLayout.getChildAt(i);
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float pressure = event.getPressure(0);
                    float size = event.getSize();
                    Button temp = (Button) v;
                    if (size > pressSizeBig) {
                        pressSizeBig = size;
                    }
                    if(event.getAction() == MotionEvent.ACTION_DOWN && !pressed) {
                        timeStartPress = System.currentTimeMillis();
                        timeBetweenKeys = timeStartPress - lastPressTime;
                        pressed = true;
                    }
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        lastPressTime = System.currentTimeMillis();
                        timePressedDown = lastPressTime - timeStartPress;
                        pressed = false;
                        System.out.println("Button " + temp.getText() + " Press : " + pressSizeBig + " Time Between (Previous)Keys: " + timeBetweenKeys + " Time Pressed Down: " + timePressedDown);
                        pressSizeBig = 0;
                        return true;
                    }
                    return true;
                }
            });
        }
    }
}