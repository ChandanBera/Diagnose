package com.hyperexchange.diagnose;

import android.content.Context;
import android.content.Intent;
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

public class LightSensor extends AppCompatActivity implements SensorEventListener {
    private TextView max_range,curr_light,light_stat;
    private ImageView pic_light;
    private Button con_light;
    private SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
        max_range = (TextView)findViewById(R.id.max_range);
        curr_light = (TextView)findViewById(R.id.curr_light);
        light_stat = (TextView)findViewById(R.id.light_stat);
        pic_light = (ImageView) findViewById(R.id.pic_light);
        con_light = (Button) findViewById(R.id.con_light);

        sensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor= sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null){
            Toast.makeText(LightSensor.this,"No Light Sensor Found! ",Toast.LENGTH_LONG).show();
        }else{ float max =  lightSensor.getMaximumRange();
            //Get Maximum Value From Light sensor
            max_range.setText(String.valueOf(max));
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

        con_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTonext();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the lightSensor
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            float currentLight = event.values[0];
            if(currentLight<1){curr_light.setText( "no Light");}
            else if(currentLight<5){curr_light.setText(" Dim:" + String.valueOf(currentLight)+"lux");}
            else if(currentLight<10){curr_light.setText(" Normal:" + String.valueOf(currentLight)+"lux");}
            else if(currentLight<100){curr_light.setText(" Bright(Room):" + String.valueOf(currentLight)+"lux");}
            else curr_light.setText("Bright(Sun):" + String.valueOf(currentLight));
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void moveTonext() {
        Intent intent =   new Intent(LightSensor.this, AccCover.class);
        //intent.putExtra("touch",true);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
