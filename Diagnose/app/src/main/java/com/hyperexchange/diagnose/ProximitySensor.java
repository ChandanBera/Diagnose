package com.hyperexchange.diagnose;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProximitySensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    TextView object,proxy_stat;
    ImageView pass_proxy;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_sensor);
        object = (TextView)findViewById(R.id.object) ;
        proxy_stat = (TextView)findViewById(R.id.proxy_stat) ;
        pass_proxy = (ImageView) findViewById(R.id.pass_proxy) ;
        next = (Button)findViewById(R.id.next_prox) ;


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTonext();
            }
        });
    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {
            object.setText("Object :In Range");
            proxy_stat.setText("Proximity Sensor");
            object.setTextColor(Color.BLUE);


        } else {
            object.setText("Object :Out of Range");
            object.setTextColor(Color.MAGENTA);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void moveTonext() {
        Intent intent =   new Intent(ProximitySensor.this, Pedometer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
