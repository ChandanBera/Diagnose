package com.hyperexchange.diagnose;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    // define the display assembly compass picture
     ImageView image,im;
    private float currentDegree         = 0f;
    private SensorManager mSensorManager;
    TextView tvHeading;
    AlertDialog.Builder info;
    Button pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        im                              = (ImageView) findViewById(R.id.cmp);
        im.setVisibility(View.VISIBLE);
        tvHeading                       = (TextView) findViewById(R.id.tvHeading);
        pass = (Button)findViewById(R.id.com_pass);
        mSensorManager                  = (SensorManager) getSystemService(SENSOR_SERVICE);
        info = new AlertDialog.Builder(this);
        info.setTitle("Instructions");

        // Setting Dialog Message
        info.setMessage("Please click the pass button if you can see heading changed");

        info.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTonext();
            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        RotateAnimation ra = new RotateAnimation(currentDegree, -degree,Animation.RELATIVE_TO_SELF, 0.5f,
         Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);

//        image.startAnimation(ra);
       im.startAnimation(ra);
        currentDegree = -degree;
        ra.start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private void moveTonext() {
        Intent intent =   new Intent(CompassActivity.this, HeadSetJackCheck.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
