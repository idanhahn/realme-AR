package com.realme_demo.realmeapp.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.realme_demo.realmeapp.activities.rm_stylify.StylifyItem;
import com.realme_demo.realmeapp.vu.models.Obj3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by idanhahn on 8/17/2016.
 */

// hold current user

public class RmUser {

    private final static String TAG = "RmUser";

    private static RmUser ourInstance = new RmUser();

    public static RmUser getInstance() {
        return ourInstance;
    }

    private RmUser() {
        // default username
        mUserName = "USER1";

        mUserSet = false;
        likeCount = 0;
        cameraCount = 0;
        videoCount = 0;
        stylifyList = new ArrayList<>();
        models = new HashMap<>();


    }

    private Context c;

    private Boolean mUserSet;
    private String mUserName;
    private String mPassword;
    private Bitmap mSelfie;

    private int likeCount;
    private int cameraCount;
    private int videoCount;
    private float ratingCount;

    List<StylifyItem> stylifyList;

    HashMap<String,Obj3D> models;


    public void init(Context c, RmUserCallback cb){
        this.c = c;
        loadInBackground(c,cb);
    }

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


    public Obj3D getModel(String key){
        return models.get(key);
    }


    public Obj3D getCurModel(){
        String key = null;
        switch (mUserName){
            case "USER1":
                key = "mickey";
                break;
            case "USER2":
                key = "harley";
                break;
            default:
                key = "mickey";
                break;
        }

        return getModel(key);
    }



    public void loadInBackground(Context c, RmUserCallback cb){

        Runnable pT = new LoadThread(c, cb);
        new Thread(pT).start();
    }

    private class LoadThread implements Runnable {

        Context c;
        RmUserCallback cb;

        public LoadThread(Context c, RmUserCallback cb) {
            this.c = c;
            this.cb = cb;
        }

        public void run() {
            models.put("mickey",new Obj3D(c,"mickey",0.5f));
            models.put("harley",new Obj3D(c,"harley",200.0f));
            Log.d(TAG,"done IO");
            cb.doneIO();
        }
    }

}
