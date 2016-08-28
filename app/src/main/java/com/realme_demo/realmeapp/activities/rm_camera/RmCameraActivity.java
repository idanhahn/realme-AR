/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.


Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.realme_demo.realmeapp.activities.rm_camera;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.realme_demo.realmeapp.R;
import com.realme_demo.realmeapp.activities.rm_demo_menu.RmDemoMenuActivity;
import com.realme_demo.realmeapp.activities.rm_menu.RmMenuActivity;
import com.realme_demo.realmeapp.activities.rm_shop.RmShopActivity;
import com.realme_demo.realmeapp.data.RmUser;
import com.realme_demo.realmeapp.fontAwesome.DrawableAwesome;
import com.realme_demo.realmeapp.vu.VuControl;
import com.realme_demo.realmeapp.vu.VuException;
import com.realme_demo.realmeapp.vu.VuSession;
import com.realme_demo.realmeapp.vu.utils.SampleApplicationGLView;
import com.realme_demo.realmeapp.vu.utils.Texture;
import com.vuforia.CameraDevice;
import com.vuforia.DataSet;
import com.vuforia.ObjectTracker;
import com.vuforia.State;
import com.vuforia.STORAGE_TYPE;
import com.vuforia.Trackable;
import com.vuforia.Tracker;
import com.vuforia.TrackerManager;
import com.vuforia.Vuforia;

public class RmCameraActivity extends AppCompatActivity implements VuControl
{
    private static final String LOGTAG = "ImageTargets";
    
    VuSession vuforiaAppSession;
    
    private DataSet mCurrentDataset;
    private int mCurrentDatasetSelectionIndex = 0;
    private int mStartDatasetsIndex = 0;
    private int mDatasetsNumber = 0;
    private ArrayList<String> mDatasetStrings = new ArrayList<String>();
    
    // Our OpenGL view:
    private SampleApplicationGLView mGlView;
    
    // Our renderer:
    private RmCameraRenderer mRenderer;
    
    private GestureDetector mGestureDetector;
    
    // The textures we will use for rendering:
    private Vector<Texture> mTextures;

    private RmUser user = RmUser.getInstance();


    private boolean mSwitchDatasetAsap = false;
    private boolean mFlash = false;
    private boolean mContAutofocus = false;
    private boolean mExtendedTracking = false;
    
    private View mFlashOptionView;
    
    private RelativeLayout mUILayout;
    
    // Alert Dialog used to display SDK errors
    private AlertDialog mErrorDialog;
    
    boolean mIsDroidDevice = false;

    // button for additional information when shirt is found
    private Button mBtnEye;

    // realMe toolBar
    private LinearLayout mLLrealMe;
    private FloatingActionButton mBtnShop;
    private FloatingActionButton mBtnHeart;
    private FloatingActionButton mBtnCamera;
    private FloatingActionButton mBtnVideo;

    // realMe menu
    private Button mBtnMenu;


    private ImageView mIVStatus;

    
    // Called when the activity first starts or the user navigates back to an
    // activity.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);
        
        vuforiaAppSession = new VuSession(this);
        
        startLoadingAnimation();

        user.init(this);


        //mDatasetStrings.add("StonesAndChips.xml");
        //mDatasetStrings.add("Tarmac.xml");
        mDatasetStrings.add("test1.xml");

        vuforiaAppSession
            .initAR(this, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        mGestureDetector = new GestureDetector(this, new GestureListener());
        
        // Load any sample specific textures:
        mTextures = new Vector<Texture>();
        loadTextures();
        
        mIsDroidDevice = Build.MODEL.toLowerCase().startsWith(
            "droid");

        mLLrealMe = (LinearLayout) findViewById(R.id.camera_overlay_found_toolbar);

        mBtnShop = (FloatingActionButton) findViewById(R.id.camera_overlay_found_shop);
        mBtnShop.setImageDrawable(new DrawableAwesome.DrawableAwesomeBuilder(this, R.string.fa_shopping_cart).build());
        mBtnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // shop activity
                Intent intent = new Intent(RmCameraActivity.this, RmShopActivity.class);
                RmCameraActivity.this.startActivity(intent);

            }
        });

        mBtnHeart = (FloatingActionButton)findViewById(R.id.camera_overlay_found_heart);
        mBtnHeart.setImageDrawable(new DrawableAwesome.DrawableAwesomeBuilder(this, R.string.fa_heart).build());
        mBtnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOGTAG,"click like");

            }
        });


        mBtnCamera = (FloatingActionButton) findViewById(R.id.camera_overlay_found_camera);
        mBtnCamera.setImageDrawable(new DrawableAwesome.DrawableAwesomeBuilder(this, R.string.fa_camera).build());
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOGTAG,"click camera");

            }
        });

        mBtnVideo = (FloatingActionButton) findViewById(R.id.camera_overlay_found_video);
        mBtnVideo.setImageDrawable(new DrawableAwesome.DrawableAwesomeBuilder(this, R.string.fa_video_camera).build());
        mBtnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOGTAG,"click video");

            }
        });


        mBtnMenu = (Button) findViewById(R.id.camera_overlay_menu);
        mBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOGTAG,"click menu");
                Intent intent = new Intent(RmCameraActivity.this, RmDemoMenuActivity.class);
                startActivity(intent);

            }
        });


        mIVStatus = (ImageView) findViewById(R.id.camera_overlay_status);

    }
    
    // Process Single Tap event to trigger autofocus
    private class GestureListener extends
        GestureDetector.SimpleOnGestureListener
    {
        // Used to set autofocus one second after a manual focus is triggered
        private final Handler autofocusHandler = new Handler();

        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }
        
        
        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            // Generates a Handler to trigger autofocus
            // after 1 second
            autofocusHandler.postDelayed(new Runnable()
            {
                public void run()
                {
                    boolean result = CameraDevice.getInstance().setFocusMode(
                        CameraDevice.FOCUS_MODE.FOCUS_MODE_TRIGGERAUTO);
                    
                    if (!result)
                        Log.e("SingleTapUp", "Unable to trigger focus");
                }
            }, 1000L);
            
            return true;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);

            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
            switchUser();

            //mRenderer.restartRender();


        }
    }
    
    
    // We want to load specific textures from the APK, which we will later use
    // for rendering.
    
    private void loadTextures()
    {
        mTextures.add(Texture.loadTextureFromApk("TextureTeapotBrass.png",
            getAssets()));
        mTextures.add(Texture.loadTextureFromApk("TextureTeapotBlue.png",
            getAssets()));
        mTextures.add(Texture.loadTextureFromApk("TextureTeapotRed.png",
            getAssets()));
        mTextures.add(Texture.loadTextureFromApk("ImageTargets/Buildings.jpeg",
            getAssets()));
        mTextures.add(Texture.loadTextureFromApk( "capsule0.jpg", getAssets()) );
    }
    
    
    // Called when the activity will start interacting with the user.
    @Override
    protected void onResume()
    {
        Log.d(LOGTAG, "onResume");
        super.onResume();
        
        // This is needed for some Droid devices to force portrait
        if (mIsDroidDevice)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        
        try
        {
            vuforiaAppSession.resumeAR();
        } catch (VuException e)
        {
            Log.e(LOGTAG, e.getString());
        }
        
        // Resume the GL view:
        if (mGlView != null)
        {
            mGlView.setVisibility(View.VISIBLE);
            mGlView.onResume();
        }
        
    }
    
    
    // Callback for configuration changes the activity handles itself
    @Override
    public void onConfigurationChanged(Configuration config)
    {
        Log.d(LOGTAG, "onConfigurationChanged");
        super.onConfigurationChanged(config);
        
        vuforiaAppSession.onConfigurationChanged();
    }
    
    
    // Called when the system is about to start resuming a previous activity.
    @Override
    protected void onPause()
    {
        Log.d(LOGTAG, "onPause");
        super.onPause();
        
        if (mGlView != null)
        {
            mGlView.setVisibility(View.INVISIBLE);
            mGlView.onPause();
        }
        
        // Turn off the flash
        if (mFlashOptionView != null && mFlash)
        {
            // OnCheckedChangeListener is called upon changing the checked state
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                ((Switch) mFlashOptionView).setChecked(false);
            } else
            {
                ((CheckBox) mFlashOptionView).setChecked(false);
            }
        }
        
        try
        {
            vuforiaAppSession.pauseAR();
        } catch (VuException e)
        {
            Log.e(LOGTAG, e.getString());
        }
    }
    
    
    // The final call you receive before your activity is destroyed.
    @Override
    protected void onDestroy()
    {
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();
        
        try
        {
            vuforiaAppSession.stopAR();
        } catch (VuException e)
        {
            Log.e(LOGTAG, e.getString());
        }
        
        // Unload texture:
        mTextures.clear();
        mTextures = null;
        
        System.gc();
    }
    
    
    // Initializes AR application components.
    private void initApplicationAR()
    {
        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = Vuforia.requiresAlpha();
        
        mGlView = new SampleApplicationGLView(this);
        mGlView.init(translucent, depthSize, stencilSize);

        mRenderer = new RmCameraRenderer(this, vuforiaAppSession);
        mRenderer.setTextures(mTextures);
        mGlView.setRenderer(mRenderer);
    }
    
    
    private void startLoadingAnimation()
    {

        mUILayout = (RelativeLayout) View.inflate(this, R.layout.camera_overlay,
            null);
        
        mUILayout.setVisibility(View.VISIBLE);
        mUILayout.setBackgroundColor(Color.BLACK);
        
        // Adds the inflated layout to the view
        addContentView(mUILayout, new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));

        // font awesome support
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        mBtnEye = (Button) findViewById(R.id.camera_overlay_eye);
        mBtnEye.setTypeface(fontAwesomeFont);

        mBtnEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOGTAG,"Click");
                if (mLLrealMe.getVisibility() == View.VISIBLE)
                    mLLrealMe.setVisibility(View.INVISIBLE);
                else
                    mLLrealMe.setVisibility(View.VISIBLE);
            }
        });



    }
    
    
    // Methods to load and destroy tracking data.
    @Override
    public boolean doLoadTrackersData()
    {
        TrackerManager tManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) tManager
            .getTracker(ObjectTracker.getClassType());
        if (objectTracker == null)
            return false;
        
        if (mCurrentDataset == null)
            mCurrentDataset = objectTracker.createDataSet();
        
        if (mCurrentDataset == null)
            return false;
        
        if (!mCurrentDataset.load(
            mDatasetStrings.get(mCurrentDatasetSelectionIndex),
            STORAGE_TYPE.STORAGE_APPRESOURCE))
            return false;
        
        if (!objectTracker.activateDataSet(mCurrentDataset))
            return false;
        
        int numTrackables = mCurrentDataset.getNumTrackables();
        for (int count = 0; count < numTrackables; count++)
        {
            Trackable trackable = mCurrentDataset.getTrackable(count);
            if(isExtendedTrackingActive())
            {
                trackable.startExtendedTracking();
            }
            
            String name = "Current Dataset : " + trackable.getName();
            trackable.setUserData(name);
            Log.d(LOGTAG, "UserData:Set the following user data "
                + (String) trackable.getUserData());
        }
        
        return true;
    }
    
    
    @Override
    public boolean doUnloadTrackersData()
    {
        // Indicate if the trackers were unloaded correctly
        boolean result = true;
        
        TrackerManager tManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) tManager
            .getTracker(ObjectTracker.getClassType());
        if (objectTracker == null)
            return false;
        
        if (mCurrentDataset != null && mCurrentDataset.isActive())
        {
            if (objectTracker.getActiveDataSet().equals(mCurrentDataset)
                && !objectTracker.deactivateDataSet(mCurrentDataset))
            {
                result = false;
            } else if (!objectTracker.destroyDataSet(mCurrentDataset))
            {
                result = false;
            }
            
            mCurrentDataset = null;
        }
        
        return result;
    }
    
    
    @Override
    public void onInitARDone(VuException exception)
    {
        
        if (exception == null)
        {
            initApplicationAR();
            
            mRenderer.setActive(true);
            
            // Now add the GL surface view. It is important
            // that the OpenGL ES surface view gets added
            // BEFORE the camera is started and video
            // background is configured.
            addContentView(mGlView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
            
            // Sets the UILayout to be drawn in front of the camera
            mUILayout.bringToFront();
            
            // Sets the layout background to transparent
            mUILayout.setBackgroundColor(Color.TRANSPARENT);
            
            try
            {
                vuforiaAppSession.startAR(CameraDevice.CAMERA_DIRECTION.CAMERA_DIRECTION_DEFAULT);
            } catch (VuException e)
            {
                Log.e(LOGTAG, e.getString());
            }
            
            boolean result = CameraDevice.getInstance().setFocusMode(
                CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO);
            
            if (result)
                mContAutofocus = true;
            else
                Log.e(LOGTAG, "Unable to enable continuous autofocus");

        } else
        {
            Log.e(LOGTAG, exception.getString());
            showInitializationErrorMessage(exception.getString());
        }
    }
    
    
    // Shows initialization error messages as System dialogs
    public void showInitializationErrorMessage(String message)
    {
        final String errorMessage = message;
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                if (mErrorDialog != null)
                {
                    mErrorDialog.dismiss();
                }
                
                // Generates an Alert Dialog to show the error message
                AlertDialog.Builder builder = new AlertDialog.Builder(
                    RmCameraActivity.this);
                builder
                    .setMessage(errorMessage)
                    .setTitle(getString(R.string.INIT_ERROR))
                    .setCancelable(false)
                    .setIcon(0)
                    .setPositiveButton(getString(R.string.button_OK),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                finish();
                            }
                        });
                
                mErrorDialog = builder.create();
                mErrorDialog.show();
            }
        });
    }
    
    
    @Override
    public void onVuforiaUpdate(State state)
    {
        if (mSwitchDatasetAsap)
        {
            mSwitchDatasetAsap = false;
            TrackerManager tm = TrackerManager.getInstance();
            ObjectTracker ot = (ObjectTracker) tm.getTracker(ObjectTracker
                .getClassType());
            if (ot == null || mCurrentDataset == null
                || ot.getActiveDataSet() == null)
            {
                Log.d(LOGTAG, "Failed to swap datasets");
                return;
            }
            
            doUnloadTrackersData();
            doLoadTrackersData();

        }
        if ( state.getNumTrackableResults() > 0 ){
            Log.d(LOGTAG,"Found trackable");
            mBtnEye.setText(getResources().getString(R.string.fa_eye));
            mBtnEye.setEnabled(true);
        } else {
            Log.d(LOGTAG,"No trackable");
            mBtnEye.setText(getResources().getString(R.string.fa_low_vision));
            mBtnEye.setEnabled(false);
        }
    }
    
    
    @Override
    public boolean doInitTrackers()
    {
        // Indicate if the trackers were initialized correctly
        boolean result = true;
        
        TrackerManager tManager = TrackerManager.getInstance();
        Tracker tracker;
        
        // Trying to initialize the image tracker
        tracker = tManager.initTracker(ObjectTracker.getClassType());
        if (tracker == null)
        {
            Log.e(
                LOGTAG,
                "Tracker not initialized. Tracker already initialized or the camera is already started");
            result = false;
        } else
        {
            Log.i(LOGTAG, "Tracker successfully initialized");
        }
        return result;
    }
    
    
    @Override
    public boolean doStartTrackers()
    {
        // Indicate if the trackers were started correctly
        boolean result = true;
        
        Tracker objectTracker = TrackerManager.getInstance().getTracker(
            ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.start();
        
        return result;
    }
    
    
    @Override
    public boolean doStopTrackers()
    {
        // Indicate if the trackers were stopped correctly
        boolean result = true;
        
        Tracker objectTracker = TrackerManager.getInstance().getTracker(
            ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.stop();
        
        return result;
    }
    
    
    @Override
    public boolean doDeinitTrackers()
    {
        // Indicate if the trackers were deinitialized correctly
        boolean result = true;
        
        TrackerManager tManager = TrackerManager.getInstance();
        tManager.deinitTracker(ObjectTracker.getClassType());
        
        return result;
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mGestureDetector.onTouchEvent(event);
    }
    
    
    boolean isExtendedTrackingActive()
    {
        return mExtendedTracking;
    }
    
    final public static int CMD_BACK = -1;
    final public static int CMD_EXTENDED_TRACKING = 1;
    final public static int CMD_AUTOFOCUS = 2;
    final public static int CMD_FLASH = 3;
    final public static int CMD_CAMERA_FRONT = 4;
    final public static int CMD_CAMERA_REAR = 5;
    final public static int CMD_DATASET_START_INDEX = 6;
    
    private void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    // user related functions
    private void switchUser() {

        if (user.getUserName().equals("USER1")){
            user.setUserName("USER2");
            mIVStatus.setImageResource(R.drawable.circle_blue);
        } else {
            user.setUserName("USER1");
            mIVStatus.setImageResource(R.drawable.circle_white);
        }

    }

}
