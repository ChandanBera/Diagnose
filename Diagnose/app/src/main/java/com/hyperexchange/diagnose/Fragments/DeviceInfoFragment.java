package com.hyperexchange.diagnose.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.hyperexchange.diagnose.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceInfoFragment extends Fragment {
    View mView;
    ProgressBar progressBar;
    TextView battery_per,charging_stat;
    Intent batteryStatus,b,c;
    PowerConnectionReceiver powerConnectionReceiver;
    public DeviceInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_device_info, container, false);
        progressBar=(ProgressBar) mView.findViewById(R.id.progressBar_batt);
        battery_per = (TextView) mView.findViewById(R.id.battery_per);
        charging_stat=(TextView)mView.findViewById(R.id.charginStat);

       // batteryStatus=getActivity().getIntent();

         batteryStatus= getActivity().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
       b= getActivity().registerReceiver(this.bBatInfoReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
       c= getActivity().registerReceiver(this.mBatInfoReceiver,new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
         powerConnectionReceiver = new PowerConnectionReceiver( );

        //chargingStatus();
        return mView;
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
    private BroadcastReceiver bBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {

            //chargingStatus();
            int chargePlug = b.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            if(usbCharge){
                charging_stat.setText("via USB");
            }
        }
    };

    public void chargingStatus(){
        // Are we charging / charged?
        String finalStr="";
        int status = b.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = b.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Log.d("", "chargingStatus: "+usbCharge);
       Toast.makeText(getActivity(),""+usbCharge,Toast.LENGTH_SHORT).show();
        if(isCharging){
            finalStr+="charging ";
            if(usbCharge){
                finalStr+="via USB";
            } else if(acCharge){
                finalStr+="via Acc";
            }

        } else{
            finalStr+="Not charging";
        }

        charging_stat.setText(finalStr);


        // How are we charging?

    }


    public class PowerConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

           chargingStatus();
        }
    }

    @Override
    public void onStart() {
      //  getActivity().registerReceiver(powerConnectionReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
       // getActivity().registerReceiver(powerConnectionReceiver,new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        //Intent.ACTION_POWER_DISCONNECTED
        super.onStart();
    }
    @Override
    public void onStop() {
        getActivity().unregisterReceiver(powerConnectionReceiver);
        super.onStop();
    }

}
