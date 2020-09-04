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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyperexchange.diagnose.Models.ResultData;

import java.util.Timer;
import java.util.TimerTask;

public class NetworkCheckActivity extends AppCompatActivity {
    //Context context=getApplicationContext();
    Button skipBtn, passBtn;
    TextView label_status;
    ImageView pass_net;
    ProgressDialog progressDialog;
    Timer t= new Timer();
    private int TimeCounter;
    String res="";
    ResultData resultData = new ResultData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_check);
        skipBtn = (Button) findViewById(R.id.skipBtn);
        passBtn = (Button)findViewById(R.id.net_pass);
        pass_net= (ImageView)findViewById(R.id.pass_net);
        label_status=(TextView)findViewById(R.id.label_status);
         progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Testing Network Connection Status");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final int I=2;
        TimeCounter=0;
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (TimeCounter == I) {
                            t.cancel();
                            t.purge();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    getNetStat();
                                }
                            }).start();
                            return;
                        }
                        TimeCounter++;

                    }
                });
            }
        }, 0, 1000);

        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultData.getInstance().setNeworkStatus("Good");
                moveTonext();
            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultData.getInstance().setNeworkStatus("INCOMPLETE");

                moveTonext();
            }
        });

    }
    public void getNetStat(){


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cm  	=  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                String res = "Connected-"+isConnected+"\n"+" subtype-"+activeNetwork.getSubtypeName()+"\n"+" reason-"+activeNetwork.getReason()+"\n"+" state-"+activeNetwork.getState()+"\n"+
                        " roaming-"+activeNetwork.isRoaming()+"\n"+" extrainfo-"+activeNetwork.getExtraInfo();
                Log.d("ddddd",res);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(NetworkCheckActivity.this);
                alertDialog.setTitle("Result");

                // Setting Dialog Message
                alertDialog.setMessage(res);

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        label_status.setText("Status : Good");
                        label_status.setVisibility(View.VISIBLE);
                        pass_net.setVisibility(View.VISIBLE);
                        dialog.cancel();

                    }
                });

                alertDialog.show();
            }
        });



        // Setting Dialog Title


//        // Setting Negative "NO" Button
//        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // Write your code here to invoke NO event
//                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        });

        // Showing Alert Message



    }

    private void moveTonext() {
        Intent intent =   new Intent(NetworkCheckActivity.this, VolumeButtonCheck.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
