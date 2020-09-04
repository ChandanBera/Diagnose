package com.hyperexchange.diagnose;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.bohush.geometricprogressview.GeometricProgressView;
import net.bohush.geometricprogressview.TYPE;

import java.io.File;

public class Memory extends AppCompatActivity {
    TextView tot_free,tot_full,int_free,tot_int,sd_free,sd_tot;
    Button mem_pass;
    ProgressBar simpleProgressBar,simpleProgressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        tot_free = (TextView) findViewById(R.id.tot_free);
        tot_full = (TextView) findViewById(R.id.tot_full);
        int_free = (TextView) findViewById(R.id.int_free);
        tot_int = (TextView) findViewById(R.id.tot_int);
        sd_free = (TextView) findViewById(R.id.sd_free);
        sd_tot = (TextView) findViewById(R.id.sd_tot);
        mem_pass = (Button) findViewById(R.id.mem_pass);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        simpleProgressBar2 = (ProgressBar) findViewById(R.id.simpleProgressBar2);




        long freeAvailable=freeRamMemorySize();
        long totalmem=totalRamMemorySize();
        long totalMemUsed= totalmem-freeAvailable;
        Boolean isSdcardAvailAble=externalMemoryAvailable();
        long internalMem_free=getAvailableInternalMemorySize()/1024;
        long internalmem_total=getTotalInternalMemorySize()/1024;
        long usedIntMem= internalmem_total-internalMem_free;
        long sd_mem_available=getAvailableExternalMemorySize()/1024;
        long sd_total_mem=getTotalExternalMemorySize()/1024;
        simpleProgressBar.setProgress(Integer.parseInt(String.valueOf(totalMemUsed)));
        simpleProgressBar.setMax(Integer.parseInt(String.valueOf(totalmem)));
        simpleProgressBar2.setProgress(Integer.parseInt(String.valueOf(usedIntMem)));
        simpleProgressBar2.setMax(Integer.parseInt(String.valueOf(internalmem_total)));
        if(isSdcardAvailAble){
            sd_free.setText(String.valueOf(sd_mem_available));
            sd_tot.setText(String.valueOf(sd_total_mem));
        } else {
            sd_free.setText("N/A");
            sd_tot.setText("N/A");
        }
        tot_full.setText(String.valueOf(totalmem));
        tot_free.setText(String.valueOf(freeAvailable));
        tot_int.setText(String.valueOf(internalmem_total));
        int_free.setText(String.valueOf(internalMem_free));

        mem_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNetCheck();
            }
        });




    }


    private long freeRamMemorySize()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        return availableMegs;
    }
    private long totalRamMemorySize()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.totalMem / 1048576L;
        return availableMegs;
    }
    public static boolean externalMemoryAvailable()
    {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
    public static long getAvailableInternalMemorySize()
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
    public static long getTotalInternalMemorySize()
    { File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }
    public static long getAvailableExternalMemorySize()
    {
        if (externalMemoryAvailable())
        {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        }
        else { return 0; }
    }
    public static long getTotalExternalMemorySize()
    {
        if (externalMemoryAvailable())
        {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        }
        else { return 0; }
    }

    private void moveToNetCheck() {
        Intent intent =   new Intent(Memory.this, NetworkCheckActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
