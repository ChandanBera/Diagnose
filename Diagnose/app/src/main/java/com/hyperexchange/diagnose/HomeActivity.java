package com.hyperexchange.diagnose;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperexchange.diagnose.Adapter.ViewPagerAdapter;
import com.hyperexchange.diagnose.Fragments.DeviceHardwareFragment;
import com.hyperexchange.diagnose.Fragments.DeviceInfoFragment;
import com.hyperexchange.diagnose.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 3/5/17.
 */

public class HomeActivity extends AppCompatActivity {
    ViewPager viewpager;
    TabLayout tabLayout;
    int per;
    String typestr="";
    TextView model,osVersion,more_details,battery_per,battery_state,shutter;
    LinearLayout infoWindow;
    TelephonyManager telephonyManager;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS=1;
    ProgressBar progressBar;
    Intent batteryStatus;
    Button nextBtn;

     boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        checkAndRequestPermissions();
        model            =(TextView)findViewById(R.id.model_no);
        osVersion        =(TextView)findViewById(R.id.osVersion);
        more_details     =(TextView)findViewById(R.id.more_details);
        progressBar      =(ProgressBar)findViewById(R.id.progressBar_batt);
        battery_per      = (TextView)    findViewById(R.id.battery_per);
        battery_state    = (TextView)    findViewById(R.id.battery_state);
        shutter          = (TextView)    findViewById(R.id.shutter);
        nextBtn          = (Button)findViewById(R.id.nextBtn) ;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        infoWindow       = (LinearLayout)findViewById(R.id.battrey_info_window);
        batteryStatus    = registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        additionalDetails();
        registerBatteryLevelReceiver();

        shutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    infoWindow.setVisibility(View.VISIBLE);
                    flag = false;
                } else{
                    infoWindow.setVisibility(View.GONE);
                    flag = true;
                }


            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest();
            }
        });


    }
    private void registerBatteryLevelReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(battery_receiver, filter);
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery_per.setText(String.valueOf(level) + "%");
            progressBar.setProgress(level);
            //chargingStatus();
        }
    };

    private BroadcastReceiver battery_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isPresent = intent.getBooleanExtra("present", false);
            String technology = intent.getStringExtra("technology");
            int plugged = intent.getIntExtra("plugged", -1);
            int scale = intent.getIntExtra("scale", -1);
            int health = intent.getIntExtra("health", 0);
            int status = intent.getIntExtra("status", 0);
            int rawlevel = intent.getIntExtra("level", -1);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);
            int level = 0;
            Bundle bundle = intent.getExtras();

            if (isPresent) {
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                String info = "Battery Level: " + level + "%\n";
                info += ("Technology: " + technology + "\n");
                info += ("Plugged: " + getPlugTypeString(plugged) + "\n");
                info += ("Health: " + getHealthString(health) + "\n");
                info += ("Status: " + getStatusString(status) + "\n");
                info += ("Voltage: " + voltage + "\n");
                info += ("Temperature: " + temperature + "\n");
                setBatteryLevelText(info);
            } else {
                setBatteryLevelText("Battery not present!!!");
            }
        }
    };

    private void setBatteryLevelText(String text) {
        battery_state.setText(text);
    }



    private  boolean checkAndRequestPermissions() {
        int read_phone= ContextCompat.checkSelfPermission(this,  Manifest.permission.READ_PHONE_STATE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadStorage = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
//        int camPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        int smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
//        int cl =ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        int fl  =   ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (contactPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
//        }
        if( read_phone!=PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add( Manifest.permission.READ_PHONE_STATE);
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
//        if (camPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CAMERA);
//        }
//        if (cl != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//        if (fl != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }

        if (!listPermissionsNeeded.isEmpty()){}
        ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
        return true;
    }


    private String getPlugTypeString(int plugged) {
        String plugType = "Unknown";
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugType = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugType = "USB";
                break;
        }
        return plugType;
    }
    private String getHealthString(int health) {
        String healthString = "Unknown";
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthString = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthString = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthString = "Over Voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthString = "Over Heat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthString = "Failure";
                break;
        }
        return healthString;
    }


    private String getStatusString(int status) {
        String statusString = "Unknown";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = "Charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusString = "Discharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = "Full";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = "Not Charging";
                break;
        }
        return statusString;
    }

    public void additionalDetails(){

        int phoneType = telephonyManager.getPhoneType();
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                typestr= "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM) :typestr= "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE): typestr= "N/A";
                break;
            default: break;
        }

//Getting Device ID
        String IMEI = telephonyManager.getDeviceId();

//Subscriber ID

        String IMSI = telephonyManager.getSubscriberId();

// Getting software version( not sdk version )
        String softwareVersion =  Build.VERSION.RELEASE;
        String manufacturer = Build.MANUFACTURER;
        String modelstr = Build.MODEL;

        String detaills ="<b>"+"IMEI-"+"</b>"+IMEI+
                "<b>"+" IMSI-"+"</b>"+IMSI+"\n"+
                "<b>"+" Phone Type-"+"</b>"+typestr+"\n"+
                "\n"+"<b>"+"SERIAL: "+"</b>" + Build.SERIAL+"\n"+
                "ID: " + Build.ID+"\n"+
                "user: " + Build.USER+"\n"+
                "BASE: " + Build.VERSION_CODES.BASE+"\n"+
                "INCREMENTAL " + Build.VERSION.INCREMENTAL+ "\n"+
                "BOARD: " + Build.BOARD+"\n"+
                "HOST " + Build.HOST+"\n"+
                "FINGERPRINT: "+Build.FINGERPRINT+"\n"+
                "Version Code: " + Build.VERSION.RELEASE;
        more_details.setText(Html.fromHtml(detaills));
        osVersion.setText(softwareVersion);
        model.setText(manufacturer+" "+modelstr);



    }
    private void moveToNetCheck() {
        Intent intent =   new Intent(HomeActivity.this, Memory.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }

    public void startTest(){


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                alertDialog.setTitle("Warning");

                // Setting Dialog Message
                alertDialog.setMessage("Please,Do not close application while  check is going on");

                alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        moveToNetCheck();
                        dialog.cancel();

                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });



        // Setting Dialog Title


        // Setting Negative "NO" Button


        // Showing Alert Message



    }




}
