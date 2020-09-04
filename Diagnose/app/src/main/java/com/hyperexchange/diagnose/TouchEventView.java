package com.hyperexchange.diagnose;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.graphics.Point;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hyperexchange.diagnose.R;
import com.hyperexchange.diagnose.SingleTouch;
import com.hyperexchange.diagnose.VolumeButtonCheck;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by amit on 8/5/17.
 */

public class TouchEventView extends View {
   // private Point point = new Point();
    private Path path = new Path();
    Paint paint = new Paint();
    ProgressDialog progressDialog;
    AlertDialog.Builder info;

    public TouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
    }
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawPath(path,paint);

    }
    @Override
    public  boolean onTouchEvent(MotionEvent event){
        float xPos= event.getX();
        float yPos= event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xPos,yPos);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos,yPos);
                break;
            case MotionEvent.ACTION_UP:
                info = new AlertDialog.Builder(getContext());
                info.setTitle("Success");

                // Setting Dialog Message
                info.setMessage("It seems Your touch screen is working fine.please press continue and back button");

                info.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.cancel();


                    }
                });

                info.show();

                return true;
              // break;
            default:
                return  false;
        }
        invalidate();
        return true;

    }

}
