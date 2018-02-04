package onetallprogrammer.hackpoly2018;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import onetallprogrammer.hackpoly2018.probability.RawData;
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
    double authscore = 100.0;
    double authscoreTimeBetween = 100.0;
    File joeyUser;

    TextView outputView;

    float[] currPressSizeData = new float[passwordLength];
    RawData[] pressSizeRawData = new RawData[passwordLength];
    ArrayList<Double> pressSizeMeans = new ArrayList<>();
    ArrayList<Double> pressSizeStdDevs = new ArrayList<>();

    long[] currTimePressedDown = new long[passwordLength];
    RawData[] timePressedDownRawData = new RawData[passwordLength];
    ArrayList<Double> timePressedDownMeans = new ArrayList<>();
    ArrayList<Double> timePressedDownStdDevs = new ArrayList<>();

    long[] currTimeBetweenKeys = new long[passwordLength];
    RawData[] timeBetweenKeysRawData = new RawData[passwordLength];
    ArrayList<Double> timeBetweenKeysMeans = new ArrayList<>();
    ArrayList<Double> timeBetweenKeysStdDevs = new ArrayList<>();

    UserStylometrics userPressedDownStylometrics;
    UserStylometrics userTimeBetweenStylometrics;

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

        for(int i = 0; i < passwordLength; i++) {
            pressSizeRawData[i] = new RawData();
            timePressedDownRawData[i] = new RawData();
            timeBetweenKeysRawData[i] = new RawData();
        }

        outputView = (TextView)findViewById(R.id.textView2);

        //gets gridlayout that has all of the numbers on it
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

//        //assigns button with action
//        for(int i=0; i < 10; i++) {
//            Button button = (Button)gridLayout.getChildAt(i);
//            button.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    float size = event.getSize();
//                    Button temp = (Button) v;
//
//                    //keeps track of current key's largest press size
//                    if (size > pressSizeBig) {
//                        pressSizeBig = size;
//                    }
//
//                    //if the button is detected for the first time
//                    if(event.getAction() == MotionEvent.ACTION_DOWN && !pressed) {
//                        timeStartPress = System.currentTimeMillis();
//                        if(currPassIdx == 0) {
//                            timeBetweenKeys = 0;
//                        } else {
//                            timeBetweenKeys = timeStartPress - lastPressTime;
//                        }
//                        pressed = true;
//                        GridLayout passwordLayout = (GridLayout)findViewById(R.id.passwordLayout);
//                        View tempImg = passwordLayout.getChildAt(currPassIdx);
//                        tempImg.setVisibility(View.VISIBLE);
//                        currPassword[currPassIdx] = Integer.parseInt((String)temp.getText());
//                    }
//
//                    //if the button is no longer being pressed
//                    if(event.getAction() == MotionEvent.ACTION_UP) {
//                        lastPressTime = System.currentTimeMillis();
//                        timePressedDown = lastPressTime - timeStartPress;
//                        pressed = false;
//                        System.out.println("Button " + temp.getText() + " Press : " + pressSizeBig + " Time Between (Previous)Keys: " + timeBetweenKeys + " Time Pressed Down: " + timePressedDown);
//                        System.out.println(currPassIdx);
//                        currPressSizeData[currPassIdx] = pressSizeBig;
//                        currTimePressedDown[currPassIdx] = timePressedDown;
//                        currPassIdx++;
//                        if(currPassIdx == passwordLength){
//                            checkPassword();
//                        }
//                        pressSizeBig = 0;
//                        return true;
//                    }
//                    return true;
//                }
//            });
//        }

        for(int i = 0; i < 10; i++) {
            Button button = (Button)gridLayout.getChildAt(i);
            assignAction(button);
        }


    }

    private void assignAction(Button button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float size = event.getSize();
                Button numButton = (Button) v;

                //keeps track of current key's largest press size
                if (size > pressSizeBig) {
                    pressSizeBig = size;
                }

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buttonPressedDown(numButton);
                        break;
                    case MotionEvent.ACTION_UP:
                        buttonIsReleased(numButton);
                        break;
                }
                return true;
            }
        });
    }

    private void buttonIsReleased(Button numButton) {
        lastPressTime = System.currentTimeMillis();
        timePressedDown = lastPressTime - timeStartPress;
        pressed = false;
        currPressSizeData[currPassIdx] = pressSizeBig;
        currTimePressedDown[currPassIdx] = timePressedDown;
        currTimeBetweenKeys[currPassIdx] = timeBetweenKeys;
        currPassIdx++;
        if(currPassIdx == passwordLength){

            checkPassword();
            reset();
        }
        pressSizeBig = 0;
    }

    private void buttonPressedDown(Button numButton) {
        //does nothing if already pressed
        if(!pressed) {
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
            currPassword[currPassIdx] = Integer.parseInt((String)numButton.getText());
        }
    }

    void checkPassword() {
        boolean pass = true;
        for(int i = 0; i < passwordLength; i++) {
            if(password[i] != currPassword[i]) {
                reset();
                outputView.setBackgroundColor(Color.RED);
                outputView.setText("PASSWORD INCORRECT");
                return;
            }
        }
        if(pressSizeRawData[0].getSize() >= 4) {
            ArrayList<Double> pressedDownList = getDoubleList(currTimePressedDown);
            authscore = getAuthScore(userPressedDownStylometrics, pressedDownList);

            ArrayList<Double> timeBetweenList = getDoubleList(currTimeBetweenKeys);
            timeBetweenList.remove(0);
            authscoreTimeBetween = getAuthScore(userTimeBetweenStylometrics, timeBetweenList);

            System.out.println("Auth Score Time Between: " + authscoreTimeBetween);
            System.out.println("Auth Score Pressed Down: " + authscore);
        }
        if(authscoreTimeBetween > 0.15 && authscore > 0.25) {
            System.out.println("IS TRUE\n\n");
            if (pressSizeRawData[0].getSize() >= 3) {
                outputView.setBackgroundColor(Color.GREEN);
                outputView.setText("Access Granted");
            } else {
                outputView.setBackgroundColor(Color.CYAN);
                outputView.setText("Training...");
            }
            for (int i = 0; i < passwordLength; i++) {
                pressSizeRawData[i].addPoint(((double) currPressSizeData[i]));
                timePressedDownRawData[i].addPoint((double) currTimePressedDown[i]);
                timeBetweenKeysRawData[i].addPoint((double) currTimeBetweenKeys[i]);
            }
        } else {
            outputView.setBackgroundColor(Color.RED);
            outputView.setText("FAILED");
            reset();
            return;
        }
        if(timeBetweenKeysRawData[0].getSize() >= 4){
            for(int i = 0; i < passwordLength; i++) {
                addToData(timePressedDownMeans, i, timePressedDownRawData[i].getMean() );
                addToData(timePressedDownStdDevs, i, timePressedDownRawData[i].getStdDev());
                addToData(timeBetweenKeysMeans, i, timeBetweenKeysRawData[i].getMean());
                addToData(timeBetweenKeysStdDevs, i, timeBetweenKeysRawData[i].getStdDev());
            }
            timeBetweenKeysMeans.remove(0);
            timeBetweenKeysStdDevs.remove(0);
            userTimeBetweenStylometrics = new UserStylometrics(timeBetweenKeysMeans, timeBetweenKeysStdDevs);
            userPressedDownStylometrics = new UserStylometrics(timePressedDownMeans, timePressedDownStdDevs);
        }
    }

    private ArrayList<Double> getDoubleList(long[] currTimeBetweenKeys) {
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i < currTimeBetweenKeys.length; i++) {
            list.add((double)currTimeBetweenKeys[i]);
        }
        return list;
    }

    private void addToData(ArrayList<Double> dataList, int i, Double data) {
        if(dataList.size() < 6) {
            dataList.add(data);
        } else {
            dataList.set(i, data);
        }
    }

    private void reset() {
        GridLayout passwordLayout = (GridLayout)findViewById(R.id.passwordLayout);
        currPassIdx = 0;
        for(int i = 0; i < passwordLength; i++){
            View tempImg = passwordLayout.getChildAt(i);
            tempImg.setVisibility(View.INVISIBLE);
        }
//        timePressedDownMeans.clear();
//        timePressedDownStdDevs.clear();
    }

    private double getAuthScore(UserStylometrics userStylometrics, ArrayList<Double> currInput){
        return userStylometrics.getAuthorizationScore(currInput);
    }

}