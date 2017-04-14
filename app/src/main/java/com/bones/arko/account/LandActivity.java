package com.bones.arko.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

        if (hasAccount()) {
            goActivity(MainActivity.class);
        } else {
            goActivity(LoginActivity.class);
        }
    }

    /**
     * Check if User has Account
     *
     * @return true if the Account exists
     */
    @NonNull
    private Boolean hasAccount() {
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(getString(R.string.account_type));

        Boolean match = false;
        for (Account account : accounts) {
            if (account.name.equals(getString(R.string.account_name))) {
                match = true;
            }
        }
        return match;
    }

    /**
     * Go to Activity
     *
     * @param aClass class of the Activity to go to
     */
    private void goActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

}
