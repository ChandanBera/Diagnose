package com.hyperexchange.diagnose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CheckMultiTouch extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_multi_touch);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTonext();

    }

    private void moveTonext() {
        Intent intent =   new Intent(CheckMultiTouch.this, MultiTouchCover.class);
        intent.putExtra("touch",true);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }


}
