package com.realme_demo.realmeapp.data;

import android.graphics.Bitmap;

import com.realme_demo.realmeapp.activities.rm_stylify.StylifyItem;

import java.util.ArrayList;
import java.util.List;

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
        likeCount = 0;
        cameraCount = 0;
        videoCount = 0;
        stylifyList = new ArrayList<>();
    }


    private Boolean mUserSet;
    private String mUserName;
    private String mPassword;
    private Bitmap mSelfie;

    private int likeCount;
    private int cameraCount;
    private int videoCount;
    private float ratingCount;

    List<StylifyItem> stylifyList;



    public void setUser(){
        mUserSet = true;
    }


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public Bitmap getSelfie() {
        return mSelfie;
    }

    public void setSelfie(Bitmap selfie) {
        this.mSelfie = selfie;
    }

    public Boolean getUserSet(){
        return this.mUserSet;
    }

    public void addLike(){ this.likeCount++; }
    public void addCamera(){ this.cameraCount++; }
    public void addVideo(){ this.videoCount++; }

    public void addStilifyItem(StylifyItem stylifyItem){ stylifyList.add(stylifyItem); }
    public List<StylifyItem> getStylifyList(){ return stylifyList; }


}
