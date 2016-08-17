package com.realme_demo.realmeapp.data;

import android.graphics.Bitmap;

/**
 * Created by idanhahn on 8/17/2016.
 */

// hold current user

public class RmUser {
    private static RmUser ourInstance = new RmUser();

    public static RmUser getInstance() {
        return ourInstance;
    }

    private RmUser() {
        mUserSet = false;
    }


    private Boolean mUserSet;
    private String mUserName;
    private String mPassword;
    private Bitmap selfie;

    public void setUser(){
        mUserSet = true;
    }


    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public Bitmap getSelfie() {
        return selfie;
    }

    public void setSelfie(Bitmap selfie) {
        this.selfie = selfie;
    }

    public Boolean getUserSet(){
        return this.mUserSet;
    }

}
