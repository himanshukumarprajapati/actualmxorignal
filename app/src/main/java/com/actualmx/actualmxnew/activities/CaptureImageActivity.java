package com.actualmx.actualmxnew.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.utill.Utility;

public class CaptureImageActivity extends Activity {
    private static final int SELECT_PICTURE = 1;
    Button camera;
    public static final int INTENT_CAMERA = 111;
    public static final int INTENT_GALLERY = 112;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    Button galary;
    private boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camara_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        camera=(Button)findViewById(R.id.camera);
        galary=(Button)findViewById(R.id.galary);
        result= Utility.checkPermission(CaptureImageActivity.this);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(result) {
                   Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivityForResult(takePicture, 0);
               }

        } });

        galary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    this.setResult(112,imageReturnedIntent);
                    this.finish();
                    //Uri selectedImage = imageReturnedIntent.getData();
                    //imageview.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    this.setResult(111,imageReturnedIntent);
                    this.finish();
                   /* Uri selectedImage = imageReturnedIntent.getData();
                    //imageview.setImageURI(selectedImage);*/
                }
                break;
        }
    }
}
