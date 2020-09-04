package com.hyperexchange.diagnose;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CameraPreview extends AppCompatActivity implements SurfaceHolder.Callback {
    TextView camStatus;
    ImageView camera_health;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Camera mcamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        camStatus =(TextView)findViewById(R.id.cam_sta);
        // front_cam=(ImageView)findViewById(R.id.pic_front);

        camera_health=(ImageView)findViewById(R.id.stat_cam_pass);

        surfaceView = (SurfaceView)findViewById(R.id.pic_rear);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //refreshCamera();
        //setUpCam();
    }

    public void refreshCamera() {

        if (surfaceHolder.getSurface() == null) {

            // preview surface does not exist

            return;

        }

        try {

            mcamera.stopPreview();

        } catch (Exception e) {
           e.printStackTrace();
        }

        try {

            mcamera.setPreviewDisplay(surfaceHolder);

            mcamera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUpCam(){


        if (Camera.getNumberOfCameras() >= 2) {
            mcamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            Log.d("", "setUpCam:+++++++ "+Camera.getNumberOfCameras());

        }

        try {

            mcamera.setDisplayOrientation(90);
            mcamera.setPreviewDisplay(surfaceHolder);
            mcamera.startPreview();
            camStatus.setText("Front camera: Working\n Rear Camera: Working");
            camStatus.setVisibility(View.VISIBLE);
            camera_health.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            mcamera.release();
        }



    }




    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {

            mcamera = Camera.open();

        } catch (RuntimeException e) {


            System.err.println(e);


            return;
        }


        try {
            setUpCam();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//        Thread t= new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    mcamera = Camera.open();
//
//                } catch (RuntimeException e) {
//
//
//                    System.err.println(e);
//
//
//                    return;
//                }
//
//
//                try {
//                    setUpCam();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        t.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mcamera.stopPreview();
        mcamera.release();
        mcamera = null;

    }
}
