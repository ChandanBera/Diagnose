package com.hyperexchange.diagnose;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyperexchange.diagnose.myFont.MyFont;

import java.util.ArrayList;
import java.util.List;

public class FlashActivity extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        Button flash = (Button)findViewById(R.id.startBtn);

        try {
            MyFont.applyCustomFont((ViewGroup) this.findViewById(android.R.id.content).getRootView(), MyFont.TYPEFACE.FontLight(this));
        }catch (Exception e){
            e.printStackTrace();
        }
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToHome();
            }
        });

        long s          =   freeRamMemorySize();

    }


    private long freeRamMemorySize()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        return availableMegs;
    }

    private void moveToHome() {
        Intent intent =   new Intent(FlashActivity.this, HomeActivity.class);//HomeActivity
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private  boolean checkAndRequestPermissions() {
//        <uses-permission android:name="android.permission.INTERNET" />
//        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
//        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        int read_phone= ContextCompat.checkSelfPermission(this,  Manifest.permission.READ_PHONE_STATE);
        int netState=ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_NETWORK_STATE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadStorage = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        int camPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        int smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
//        int cl =ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        int fl  =   ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (contactPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
//        }
        if( read_phone!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add( Manifest.permission.READ_PHONE_STATE);
        }
        if( netState!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add( Manifest.permission.ACCESS_NETWORK_STATE);
        }

        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
//        if (smsPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
//        }
        if (camPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
//        if (cl != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//        if (fl != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
        Log.d("++++", "checkAndRequestPermissions: "+listPermissionsNeeded);

        if (!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
        }

        return true;
    }
}
