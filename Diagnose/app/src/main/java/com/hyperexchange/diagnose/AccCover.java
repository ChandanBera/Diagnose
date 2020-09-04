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

public class AccCover extends AppCompatActivity {
    TextView acc_stat,acc_status;
    Button acc_next;
    ImageView done;
    AlertDialog.Builder info;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_cover);
        acc_stat = (TextView)findViewById(R.id.next_acc);
        acc_status = (TextView)findViewById(R.id.acc_status);

        acc_next=(Button)findViewById(R.id.next_acc) ;
        done=(ImageView)findViewById(R.id.pass_acc);
        intent=getIntent();
        boolean check=intent.getBooleanExtra("status",false);
        if(check){
            acc_status.setText("Staus : Working");
            acc_status.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
        }
        else{

            info = new AlertDialog.Builder(AccCover.this);
            info.setTitle("Checking Accelerometer");

            // Setting Dialog Message
            info.setMessage("Please press continue to show the result");

            info.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    moveTonext();


                }
            });


            info.show();
        }
        acc_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToProxy();
            }
        });



    }
    private void moveTonext() {
        Intent intent =   new Intent(AccCover.this, Accelerometer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }

    private void moveToProxy() {
        Intent intent =   new Intent(AccCover.this, ProximitySensor.class);//HomeActivity
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }

}
