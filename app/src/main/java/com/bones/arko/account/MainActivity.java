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
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AccountManager accountManager;

    private View mProgressView;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Extract Account Manager
        this.accountManager = AccountManager.get(this);

        showUserInfo();

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

    private Account getAccount() {
        Account account = null;
        Account[] accounts = accountManager.getAccountsByType(getString(R.string.account_type));

        for (Account item : accounts) {
            if (item.name.equals(getString(R.string.account_name))) {
                account = item;
            }
        }

        return account;
    }

    /**
     * Start Land Activity
     */
    private void goLandActivity() {
        Intent intent = new Intent(this, LandActivity.class);
        startActivity(intent);
    }

    /**
     * Logout
     * Remove Account from AccountManager
     */
    private void logout() {
        Account account = getAccount();

        if (null != account) {
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

    /**
     * Display user info from Account in UserInfo TextView
     */
    private void showUserInfo() {
        Account account = getAccount();

        if (null != account) {
            TextView mUserInfoTextArea = (TextView) findViewById(R.id.user_info_text_view);

            String info = accountManager.getUserData(account, "user")
                + ":"
                + accountManager.getUserData(account, "pass");

            mUserInfoTextArea.setText(info);
        }
    }

}
