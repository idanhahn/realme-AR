package com.realme_demo.realmeapp.activities.rm_menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.realme_demo.realmeapp.R;
import com.realme_demo.realmeapp.activities.rm_registration.RmLoginActivity;
import com.realme_demo.realmeapp.activities.rm_registration.RmSignupActivity;
import com.realme_demo.realmeapp.activities.rm_shop.RmShopActivity;

/**
 * Created by idanhahn on 8/17/2016.
 */
public class RmMenuActivity extends AppCompatActivity{

    private Button mBtnLogin;
    private Button mBtnSignup;
    private Button mBtnShop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mBtnLogin = (Button) findViewById(R.id.menu_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RmMenuActivity.this, RmLoginActivity.class);
                startActivity(intent);

            }
        });


        mBtnSignup = (Button) findViewById(R.id.menu_signup);
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RmMenuActivity.this, RmSignupActivity.class);
                startActivity(intent);

            }
        });

        mBtnShop = (Button) findViewById(R.id.menu_accessorize);
        mBtnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RmMenuActivity.this, RmShopActivity.class);
                startActivity(intent);


            }
        });


    }
}
