package com.realme_demo.realmeapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.realme_demo.realmeapp.vu.ImageTarget.ImageTargets;


// Gate way for all other activities
// TODO: find design pattern that fits this
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, VuMark.class);
        Intent intent = new Intent(this, ImageTargets.class);

        startActivity(intent);

    }
}
