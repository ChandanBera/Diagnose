package com.hyperexchange.diagnose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleTouch extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_touch);
       // setContentView(new TouchEventView(this,null));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTonext();


    }
    private void moveTonext() {
        Intent intent =   new Intent(SingleTouch.this, GoTOCanVasActivity.class);
        intent.putExtra("touch",true);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
