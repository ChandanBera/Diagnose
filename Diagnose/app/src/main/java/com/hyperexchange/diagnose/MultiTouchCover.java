package com.hyperexchange.diagnose;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MultiTouchCover extends AppCompatActivity {
    TextView mt_status;
    ImageView mt_pass;
    Intent intent;
    Button nextButton;
    AlertDialog.Builder info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_touch_cover);
        mt_status = (TextView) findViewById(R.id.mttouch_stat);
        mt_pass = (ImageView) findViewById(R.id.pass_mt);
        nextButton = (Button) findViewById(R.id.multi_con);

        intent = getIntent();
        boolean check = intent.getBooleanExtra("touch", false);
        if (check) {
            mt_status.setText("Multi Touch Mode :Working");
            mt_pass.setVisibility(View.VISIBLE);
            mt_status.setVisibility(View.VISIBLE);
        } else {
            info = new AlertDialog.Builder(MultiTouchCover.this);
            info.setTitle("Instructions");

            // Setting Dialog Message
            info.setMessage("You are redirecting to canvas \n Please Draw Line using multiple fingers");

            info.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    moveTonext();


                }
            });
            info.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });

            info.show();

        }
                    nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveTo();
                }
            });
    }


    private void moveTonext() {
        Intent intent =   new Intent(MultiTouchCover.this, CheckMultiTouch.class);
        //intent.putExtra("touch",true);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // moveTonext();


    }

    private void moveTo() {
        Intent intent =   new Intent(MultiTouchCover.this, LightSensor.class);
        //intent.putExtra("touch",true);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
