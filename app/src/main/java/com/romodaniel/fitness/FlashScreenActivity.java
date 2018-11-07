package com.romodaniel.fitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class FlashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);

                    Class activity = MainActivity.class;
                    startActivity(new Intent(FlashScreenActivity.this, activity));

                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
