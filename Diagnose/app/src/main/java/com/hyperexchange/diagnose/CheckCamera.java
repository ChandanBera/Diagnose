package com.hyperexchange.diagnose;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class CheckCamera extends AppCompatActivity implements SurfaceHolder.Callback {
    Button rearCam,frontCam;
    TextView camStatus;
    ImageView front_cam,camera_health;
    private Uri imageUri=null;
    SurfaceView surfaceView,rear_cam;
    protected static final int CAMERA_REQUEST = 0;
    static Camera camera = null;
    Boolean frontCamera=true;
    SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_camera);
        frontCam=(Button)findViewById(R.id.butt_front);
        surfaceView = (SurfaceView)findViewById(R.id.pic_front);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        frontCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moveToNext();
            }
        });



    }

    public void setUpCam(){




        try {
            int camId=Camera.getNumberOfCameras();
            releaseCameraAndPreview();
//            if (camId == 0) {
//                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
//            }
//            else {
//                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
//            }
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);

            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            camera.release();
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }


//        try {
//            camera.setDisplayOrientation(90);
//            camera.setPreviewDisplay(surfaceHolder);
//            camera.startPreview();
//        } catch (Exception e) {
//            camera.release();
//        }



    }




    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {

            camera = Camera.open();

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
        try {

            camera = Camera.open();
            setUpCam();

        } catch (RuntimeException e) {


            System.err.println(e);


            return;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();

    }

    private void moveToNext() {
        Intent intent =   new Intent(CheckCamera.this, CameraPreview.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
    private void releaseCameraAndPreview() {

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
