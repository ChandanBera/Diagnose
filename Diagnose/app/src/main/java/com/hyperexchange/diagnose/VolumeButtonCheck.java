package com.hyperexchange.diagnose;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperexchange.diagnose.Models.ResultData;

import java.util.Timer;
import java.util.TimerTask;

public class VolumeButtonCheck extends AppCompatActivity {
    ProgressDialog volUp,volDown;
    private int TimeCounter;
    ImageView pass_vol;
    Timer t=new Timer();
    TextView label_status_vol,label_status_volup,label_status_voldown;
    Button vol_pass;

    AlertDialog.Builder info,test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_volume_button_check);
        label_status_vol = (TextView)findViewById(R.id.label_status_vol);
        label_status_volup = (TextView)findViewById(R.id.label_status_volup);
        label_status_voldown = (TextView)findViewById(R.id.label_status_voldown);
        pass_vol = (ImageView)findViewById(R.id.pass_vol);
        vol_pass=(Button) findViewById(R.id.vol_pass);
        testVolButtons();
        vol_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTonext();
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    label_status_volup.setText("Volume Up Key : Working");
                    testVoldownButton();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    label_status_voldown.setText("Volume Down Key : Working");
                    label_status_vol.setText("Status: Working");
                    pass_vol.setVisibility(View.VISIBLE);
                    label_status_vol.setVisibility(View.VISIBLE);

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
//            ResultData.getInstance().setVolDownkey(false);
//           volDown.cancel();
//            volDown.dismiss();
//
//            Toast.makeText(VolumeButtonCheck.this,"test",Toast.LENGTH_LONG).show();
//            ResultData.getInstance().setVolDownkey(true);
//            return  true;
//
//        }
//                if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
////                    if (volUp.isShowing()){
////
////                        volUp.cancel();
////                        volUp.dismiss();
////                        ResultData.getInstance().setVolUpkey(true);
////
////                        voldownBox();
////                    }
//
//
//                    test.setNegativeButton("ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,int which) {
//                            dialog.cancel();
//
//                        }
//                    });
//
//
//                    //Toast.makeText(VolumeButtonCheck.this,"test",Toast.LENGTH_LONG).show();
//
//
////                    ResultData.getInstance().setVolUpkey(true);
////
////                    voldownBox();
//                    return  true;
//
//
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    public void testVolButtons(){
               info = new AlertDialog.Builder(VolumeButtonCheck.this);
                info.setTitle("Testing Volume Keys");

                // Setting Dialog Message
                info.setMessage("Please follow the instructions during test"+"\n"+"press continue,then press Volume Up key");

                info.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {



                    }
                });
                info.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.cancel();

                    }
                });

                info.show();


    }
    public void testVoldownButton(){
        info = new AlertDialog.Builder(VolumeButtonCheck.this);
        info.setTitle("Testing Volume Keys");

        // Setting Dialog Message
        info.setMessage("Please press continue then Volume down key");

        info.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {



            }
        });


        info.show();


    }


    private void moveTonext() {
        Intent intent =   new Intent(VolumeButtonCheck.this, GoTOCanVasActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }







}
