package com.bones.arko.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AccountManager accountManager;

    private View mProgressView;

    private TextView mResponseTextView;

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
        mResponseTextView = (TextView) findViewById(R.id.response_text_view);

        // Bind token ok button to action
        Button mTokenOkButton = (Button) findViewById(R.id.call_token_ok_button);
        mTokenOkButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                String token = getToken();

                processCall(
                    getString(R.string.uri_secured),
                    token
                );
            }
        });

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
     * Call url with given token
     *
     * @param uri uri to call
     * @param token authorization bearer token
     * @return response status code
     */
    private Integer doCall(String uri, String token) {
        Integer statusCode = null;

        try {
            URL url = new URL(uri);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty(
                "Authorization",
                "Bearer " + token
            );

            statusCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return statusCode;
    }

    /**
     * Get User Account
     *
     * @return user account
     */
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
     * Get user token
     *
     * @return token
     */
    @Nullable
    private String getToken() {
        Account account = getAccount();

        return (null != account)
            ? accountManager.getUserData(account, "token")
            : null;
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
     * Do call and update Response TextView
     *
     * @param url url to call
     * @param token auth token
     */
    private void processCall(final String url, final String token) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Integer status = doCall(url, token);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResponseTextView.setText(status.toString());
                    }
                });
            }
        });
    }

    /**
     * Display user info from Account in UserInfo TextView
     */
    private void showUserInfo() {
        Account account = getAccount();

        if (null != account) {
            // user:pass
            TextView mUserInfoTextArea = (TextView) findViewById(R.id.user_info_text_view);

            String info = accountManager.getUserData(account, "user")
                + ":"
                + accountManager.getUserData(account, "pass");

            mUserInfoTextArea.setText(info);

            // token
            TextView mTokenTextArea = (TextView) findViewById(R.id.token_text_view);

            String token = accountManager.getUserData(account, "token");

            mTokenTextArea.setText(token);
        }
    }

}
