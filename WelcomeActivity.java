package com.javatpoint.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tv_name;

    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        imageView = (ImageView)findViewById(R.id.imageview);
        tv_name = (TextView)findViewById(R.id.tv_name);

        timer = new Thread() {

            @Override
            public void run() {
                try {

                    synchronized (this) {
                        wait(5000);
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        
        timer.start();
    }
}