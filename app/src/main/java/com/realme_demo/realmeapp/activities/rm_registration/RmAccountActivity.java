package com.realme_demo.realmeapp.activities.rm_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.realme_demo.realmeapp.R;
import com.realme_demo.realmeapp.activities.rm_stylify.RmStylifyActivity;
import com.realme_demo.realmeapp.data.RmUser;

/**
 * Created by idanhahn on 8/17/2016.
 */
public class RmAccountActivity extends AppCompatActivity{

    private final static String LOGTAG = "Account Activity";

    private RmUser user = RmUser.getInstance();


    private TextView mUsername;

    private ImageView mUserImg;

    private Button mBtnStylify;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if ( !user.getUserSet() ){
            Log.wtf(LOGTAG, "User not set");
            System.exit(1);
        }

        mUsername = (TextView) findViewById(R.id.account_username);
        mUsername.setText(user.getUserName());

        mUserImg = (ImageView) findViewById(R.id.account_user_img);
        mUserImg.setImageBitmap(user.getSelfie());

        mBtnStylify = (Button) findViewById(R.id.account_stylify);
        mBtnStylify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RmAccountActivity.this, RmStylifyActivity.class);
                startActivity(intent);
            }
        });

    }
}
