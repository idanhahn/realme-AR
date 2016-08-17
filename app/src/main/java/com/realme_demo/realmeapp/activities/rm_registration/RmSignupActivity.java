package com.realme_demo.realmeapp.activities.rm_registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.realme_demo.realmeapp.R;

/**
 * Created by idanhahn on 8/17/2016.
 */
public class RmSignupActivity extends AppCompatActivity{

    private TextView mUserName;
    private TextView mPassword;

    private Button mSignup;
    private Button mSignupShirt;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUserName = (TextView) findViewById(R.id.signup_username);
        mPassword = (TextView) findViewById(R.id.signup_password);

        mSignup = (Button) findViewById(R.id.signup_button);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // sign up without shirt

            }
        });

        mSignupShirt = (Button) findViewById(R.id.signup_shirt_button);
        mSignupShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // sign up with shirt, go to selfie


            }
        });


    }
}
