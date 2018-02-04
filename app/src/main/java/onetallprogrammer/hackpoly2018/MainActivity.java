package onetallprogrammer.hackpoly2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;

import onetallprogrammer.hackpoly2018.probability.RawData;
import onetallprogrammer.hackpoly2018.probability.Scorer;
import onetallprogrammer.hackpoly2018.probability.UserStylometrics;

public class MainActivity extends AppCompatActivity {

    final int passwordLength = 6;
    float pressSizeBig = 0;
    long lastPressTime = 0;
    long timeBetweenKeys = 0;
    long timeStartPress = 0;
    long timePressedDown = 0;
    boolean pressed = false;
    int[] currPassword = new int[passwordLength];
    int[] password = {3, 8, 6, 0, 5, 1};
    int currPassIdx = 0;

    float[] currPressSizeData = new float[passwordLength];
    RawData[] pressSizeRawData = new RawData[passwordLength];
    ArrayList<Double> pressSizeMeans = new ArrayList<>();
    ArrayList<Double> pressSizeStdDevs = new ArrayList<>();

    long[] currTimePressedDown = new long[passwordLength];
    RawData[] timePressedDownRawData = new RawData[passwordLength];
    ArrayList<Double> timePressedDownMeans = new ArrayList<>();
    ArrayList<Double> timePressedDownStdDevs = new ArrayList<>();

    UserStylometrics userStylometrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArrayList<Double> means = new ArrayList<>();
//        ArrayList<Double> stdDevs = new ArrayList<>();
//        ArrayList<Double> authAttempt = new ArrayList<>();
//
//        for(int i = 1; i < 5; i++){
//            means.add((double) i);
//            authAttempt.add((double) i - 0.1);
//            stdDevs.add((double) i / 1.0);
//        }
//
//        UserStylometrics stylo = new UserStylometrics(means, stdDevs, 4);
//
//        Log.i("stylo", String.valueOf(stylo.getAuthorizationScore(authAttempt)));

        RawData data = new RawData();
        data.addPoint(0.0137254912);
        data.addPoint(0.017647059634);
        data.addPoint(0.017647059634);

        Log.d("std", String.valueOf(data.getStdDev()));


        for(int i = 0; i < passwordLength; i++) {
            pressSizeRawData[i] = new RawData();
            timePressedDownRawData[i] = new RawData();
        }

        //gets gridlayout that has all of the numbers on it
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        //assigns button with action
        for(int i=0; i < 10; i++) {
            Button button = (Button)gridLayout.getChildAt(i);
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float size = event.getSize();
                    Button temp = (Button) v;

                    //keeps track of current key's largest press size
                    if (size > pressSizeBig) {
                        pressSizeBig = size;
                    }

                    //if the button is detected for the first time
                    if(event.getAction() == MotionEvent.ACTION_DOWN && !pressed) {
                        timeStartPress = System.currentTimeMillis();
                        if(currPassIdx == 0) {
                            timeBetweenKeys = 0;
                        } else {
                            timeBetweenKeys = timeStartPress - lastPressTime;
                        }
                        pressed = true;
                        GridLayout passwordLayout = (GridLayout)findViewById(R.id.passwordLayout);
                        View tempImg = passwordLayout.getChildAt(currPassIdx);
                        tempImg.setVisibility(View.VISIBLE);
                        currPassword[currPassIdx] = Integer.parseInt((String)temp.getText());
                    }

                    //if the button is no longer being pressed
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        lastPressTime = System.currentTimeMillis();
                        timePressedDown = lastPressTime - timeStartPress;
                        pressed = false;
                        System.out.println("Button " + temp.getText() + " Press : " + pressSizeBig + " Time Between (Previous)Keys: " + timeBetweenKeys + " Time Pressed Down: " + timePressedDown);
                        System.out.println(currPassIdx);
                        currPressSizeData[currPassIdx] = pressSizeBig;
                        currTimePressedDown[currPassIdx] = timePressedDown;
                        currPassIdx++;
                        if(currPassIdx == passwordLength){
                            checkPassword();
                        }
                        pressSizeBig = 0;
                        return true;
                    }
                    return true;
                }
            });
        }

    }
    void checkPassword() {
        boolean pass = true;
        for(int i = 0; i < passwordLength; i++) {
            if(password[i] != currPassword[i]) {
                reset();
                return;
            }
        }
        if(userStylometrics != null) {
            System.out.println("Auth Score: " + getAuthScore());
            //do something with authscore
        }
        System.out.println("IS TRUE");
        for(int i = 0; i < passwordLength; i++){
            pressSizeRawData[i].addPoint(((double)currPressSizeData[i]));
            timePressedDownRawData[i].addPoint((double)currTimePressedDown[i]);
        }
        reset();
        if(pressSizeRawData[0].getSize() >= 3){
            for(int i = 0; i < passwordLength; i++) {
                pressSizeMeans.add(pressSizeRawData[i].getMean());
                pressSizeStdDevs.add(pressSizeRawData[i].getStdDev());
                timePressedDownMeans.add(timePressedDownRawData[i].getMean());
                timePressedDownStdDevs.add(timePressedDownRawData[i].getStdDev());
                System.out.println("Button " + i + ": Mean=" + timePressedDownMeans.get(i) + " StdDev=" + timePressedDownStdDevs.get(i));
            }
            userStylometrics = new UserStylometrics(timePressedDownMeans, timePressedDownStdDevs);
        }
    }

    private void reset() {
        GridLayout passwordLayout = (GridLayout)findViewById(R.id.passwordLayout);
        currPassIdx = 0;
        for(int i = 0; i < passwordLength; i++){
            View tempImg = passwordLayout.getChildAt(i);
            tempImg.setVisibility(View.INVISIBLE);
        }
    }

    private double getAuthScore(){
        ArrayList<Double> currUserTimePressedDown = new ArrayList<>();
        for(int i = 0; i < passwordLength; i++) {
            currUserTimePressedDown.add((double)currTimePressedDown[i]);
        }
        return userStylometrics.getAuthorizationScore(currUserTimePressedDown);
    }
}