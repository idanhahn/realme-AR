package com.realme_demo.realmeapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.realme_demo.realmeapp.R;
import com.realme_demo.realmeapp.activities.rm_camera.RmCameraActivity;


// Gate way for all other activities
// TODO: find design pattern that fits this
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, VuMark.class);
        Intent intent = new Intent(this, RmCameraActivity.class);
        startActivity(intent);

    }
}
