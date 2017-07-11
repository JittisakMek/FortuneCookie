package com.egci428.fortunecookie;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

public class NewFortuneCookie extends AppCompatActivity implements SensorEventListener {

    private static ImageView imgView;
    private static int[] images = {R.drawable.closed_cookie, R.drawable.opened_cookie_gradea, R.drawable.opened_cookie_lucky, R.drawable.opened_cookie_panic, R.drawable.opened_cookie_surprise, R.drawable.opened_cookie_work};
//    String [] result = new String[]{"      Result:  You will get A", "      Result:  You're lucky", "      Result:  Don't Panic", "      Result:  Something surprise you today", "      Result:  Work Harder"};
    private static  Button shakeBtn;
    private boolean buttonState = false;
    private static TextView resultTxt, dateBox;
    int n = 0;
    String dateString;

    public static final int DETAIL_REQUEST_CODE = 1001;

    private SensorManager sensorManager; // for any kind of sensor use sensorManager
    private long lastUpdate;
    private CommentsDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fortune_cookie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgView = (ImageView)findViewById(R.id.imageView);
        imgView.setImageResource(images[0]);
        shakeBtn = (Button)findViewById(R.id.shakeBtn);
        shakeBtn.setText("SHAKE");
        resultTxt = (TextView)findViewById(R.id.resultTxt);
        resultTxt.setText("Result: No result");
        dateBox = (TextView)findViewById(R.id.dateBox);
        dateBox.setText("");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        dataSource = new CommentsDataSource(this);
        dataSource.open(); //connect to database
//        List<Comment> values = dataSource.getAllComments(); // reload all info and add into the list
//        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, values);
    }

//    public void listView(){
//        list_view = (ListView)findViewById(R.id.listCookie);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, )
//    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    public void OnClickButtonListener(View v){
//        ListView list_view = (ListView)findViewById(R.id.listCookie);
        if(shakeBtn.getText()== "SAVE") {
            System.out.println("n = "+n);
            buttonState = true;
            dataSource.createComment(Integer.toString(n), dateString);

            Intent intent = new Intent(NewFortuneCookie.this, MainActivity.class);
            shakeBtn.setText("It is saved");
            startActivityForResult(intent, DETAIL_REQUEST_CODE);
        }
        else if(buttonState==false){
            buttonState = true;
        }
        else {
            buttonState = false;
        }

//        if(buttonState==false){
//
//
//
//        }
//        else {
//            buttonState = false;
//        }

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////


    public void resultHandler(int n){

        switch (n){
            case 1:
                resultTxt.setText("      Result:  You will get A");
            case 2:
                resultTxt.setText("      Result:  You're lucky");
            case 3:
                resultTxt.setText("      Result:  Don't Panic");
            case 4:
                resultTxt.setText("      Result:  Something surprise you today");
            case 5:
                resultTxt.setText("      Result:  Work Harder");
        }

    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event){

        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);  //calculation for accelerometer
        long actualTime = System.currentTimeMillis();

        if (accelerationSquareRoot >= 10 && buttonState) //
        {
            if (actualTime - lastUpdate < 200) {  //200 = 2millisec
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT)
                    .show();
        // random generator
            Random rand = new Random();
            n = rand.nextInt(5)+1;
            System.out.println("n = "+n);

        //set image
            imgView.setImageResource(images[n]);

        //display result
            resultHandler(n);

        // set button text to (SAVED) when shaked
            shakeBtn.setText("SAVE");

        // to prevent users from shaking 2nd time
            buttonState = false;

        // set time and date on dateBox
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a");
            dateString = sdf.format(date);
            dateBox.setText("Date:  "+dateString);

        }
       else if(accelerationSquareRoot >= 2 && buttonState){
            shakeBtn.setText("SHAKING");
        }
        else if(buttonState){
            //delay
            shakeBtn.setText("SHAKE");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
