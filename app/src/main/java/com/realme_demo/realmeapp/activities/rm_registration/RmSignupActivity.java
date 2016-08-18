package com.realme_demo.realmeapp.activities.rm_registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.realme_demo.realmeapp.R;
import com.realme_demo.realmeapp.activities.rm_shop.RmShopActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by idanhahn on 8/17/2016.
 */
public class RmSignupActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPUTRE = 1;

    private final static String LOGTAG = "Signup Activity";

    private TextView mUserName;
    private TextView mPassword;

    private Button mBtnSignupShirt;

    String mCurrentPhotoPath;

    private ImageView mSelfieImg;

    private LinearLayout mLlApprove;
    private Button mBtnApprove;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Log.d(LOGTAG, "Inside OnCreate");
        mUserName = (TextView) findViewById(R.id.signup_username);
        mPassword = (TextView) findViewById(R.id.signup_password);

        mBtnSignupShirt = (Button) findViewById(R.id.signup_shirt_button);
        mBtnSignupShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOGTAG, "selfie hit");
                //dispatchTakePictureIntent();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPUTRE );
                }

            }
        });

        // Image file
        mSelfieImg = (ImageView) findViewById(R.id.signup_selfie_img);

        mLlApprove = (LinearLayout) findViewById(R.id.signup_approve);

        mBtnApprove = (Button) findViewById(R.id.signup_approve_yes);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPUTRE && resultCode == RESULT_OK) {

            //galleryAddPic();
            //setPic();
            Bitmap image = (Bitmap) data.getExtras().get("data");
            mSelfieImg.setImageBitmap(image);
            mSelfieImg.setVisibility(View.VISIBLE);

            ViewGroup layout = (ViewGroup) mBtnSignupShirt.getParent();
            layout.removeView(mBtnSignupShirt);

            mLlApprove.setVisibility(View.VISIBLE);

            Log.d(LOGTAG, "finish image");

        } else {
            Log.wtf(LOGTAG, "Image was not created");
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.realme_demo.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPUTRE );
            }
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    private void setPic() {
        // Get the dimensions of the View
        /*
        int targetW = mSelfieImg.getWidth();
        int targetH = mSelfieImg.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mSelfieImg.setImageBitmap(bitmap);
        */




    }
}
