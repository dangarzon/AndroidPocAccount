package com.bones.arko.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LandActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);

        // Sleep a little
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.d(TAG, "Unexpected sleep error!!");
        }

        // Go to Login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
