package com.hyperexchange.diagnose;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HeadSetJackCheck extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MusicIntentReceiver myReceiver;
    ProgressDialog progressDialog;
    TextView ins;
    Button nextHeadset;
    boolean checked=false;
    ImageView pic_success,pic_headset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_set_jack_check);
        ins=(TextView)findViewById(R.id.headPhoneUnstructions);
        pic_success=(ImageView)findViewById(R.id.pic_success);
        pic_headset=(ImageView)findViewById(R.id.pic_headset);
        myReceiver = new MusicIntentReceiver();
    }


    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pic_headset.setImageResource(R.drawable.headphone);
                                ins.setText("Please connect your headset");
                               // Toast.makeText(HeadSetJackCheck.this,"unplugged",Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 1:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                checked=true;
                                pic_headset.setImageResource(R.drawable.headphone_green);
                                if (checked){
                                    pic_success.setVisibility(View.VISIBLE);
                                }

                                ins.setText("HeadPhone is connected");
                              //  Toast.makeText(HeadSetJackCheck.this,"plugged",Toast.LENGTH_SHORT).show();
                            }
                        });

                        Log.d(TAG, "Headset is plugged");
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }

    @Override
    public void onPause() {
        unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        super.onResume();


    }
}
