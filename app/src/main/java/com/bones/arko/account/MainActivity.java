package com.bones.arko.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View mProgressView;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressView = findViewById(R.id.progress_bar);

        // Bind logout button to action
        Button mLogoutButton = (Button) findViewById(R.id.logout_button);
        mLogoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                mProgressView.setVisibility(View.VISIBLE);
                logout();
            }
        });
    }

    /**
     * Logout
     * Remove Account from AccountManager
     */
    private void logout() {
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(getString(R.string.account_type));

        for (Account account : accounts) {
            if (account.name.equals(getString(R.string.account_name))) {
                // Use deprecated function to keep compatibility with SDK19 (Android KitKat)
                accountManager.removeAccount(
                    account,
                    new AccountManagerCallback<Boolean>() {
                        @Override
                        public void run(AccountManagerFuture<Boolean> future) {
                            try {
                                if (future.getResult()) {
                                    Log.d(TAG, "Account Removed");
                                    goLandActivity();
                                }
                            } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                                Log.d(TAG, "Account failed to remove");
                            }
                        }
                    },
                    null
                );
            }
        }
    }

    /**
     * Start Land Activity
     */
    private void goLandActivity() {
        Intent intent = new Intent(this, LandActivity.class);
        startActivity(intent);
    }

}
