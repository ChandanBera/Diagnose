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

public class GoTOCanVasActivity extends AppCompatActivity {
    AlertDialog.Builder info;
    Intent intent;
    TextView status;
    ImageView done;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_tocan_vas);
        status =(TextView)findViewById(R.id.touch_stat);
        done=(ImageView)findViewById(R.id.pass_st);
        nextButton=(Button)findViewById(R.id.next_mt) ;


        intent=getIntent();
        boolean check=intent.getBooleanExtra("touch",false);
        if(check){
            status.setText("Single Touch Mode :Working");
            status.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
        }
        else{
            info = new AlertDialog.Builder(GoTOCanVasActivity.this);
            info.setTitle("Instructions");

            // Setting Dialog Message
            info.setMessage("You are redirecting to canvas \n Please Draw Line with your finger");

            info.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    moveTonext();


                }
            });
            info.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    dialog.cancel();

                }
            });

            info.show();


        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMultiTouch();
            }
        });




    }
    private void moveTonext() {
        Intent intent =   new Intent(GoTOCanVasActivity.this, SingleTouch.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
    private void moveMultiTouch() {
        Intent intent =   new Intent(GoTOCanVasActivity.this, MultiTouchCover.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
