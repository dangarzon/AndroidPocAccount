package com.bones.arko.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind logout button to action
        Button mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String user = getString(R.string.username);
                        String pass = getString(R.string.password);

                        String token = remoteAuth(user, pass);

                        if (null != token) {
                            Account account = createAccount();

                            Bundle bundle = fillInfo(user, pass, token);

                            Boolean stored = storeAccount(account, bundle);

                            if (stored) {
                                goMain();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * Create an Account
     * The account name and type are auto configured
     *
     * @return newly created Account
     */
    private Account createAccount() {
        return new Account(
            getString(R.string.account_name),
            getString(R.string.account_type)
        );
    }

    /**
     * Create Bundle with provided info
     *
     * @param user user value for user key
     * @param pass pass value for pass key
     * @param token pass value for token key
     * @return filled bundle
     */
    private Bundle fillInfo(String user, String pass, String token) {
        Bundle bundle = new Bundle();

        bundle.putString("user", user);
        bundle.putString("pass", pass);
        bundle.putString("token", token);

        return bundle;
    }

    /**
     * Start Main Activity
     */
    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Auth with remove service with given credentials
     *
     * @param user username
     * @param pass password
     * @return auth token
     */
    private String remoteAuth(String user, String pass) {
        String token = null;

        try {
            String params = "?grant_type=password&username=" + user + "&password=" + pass;
            URL url = new URL(getString(R.string.identity_uri) + params);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                "Authorization",
                "Basic " + getString(R.string.identity_token)
            );
            connection.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
            );

            int statusCode = connection.getResponseCode();

            if (statusCode == 200) {
                InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String response = bufferedReader.readLine();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    token = (String) jsonObject.get("access_token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * Store Account into AccountManager
     *
     * @param account Account to store
     * @return true if the Account was added to AccountManager
     */
    private boolean storeAccount(Account account, Bundle bundle) {
        AccountManager accountManager = AccountManager.get(this);

        boolean success = accountManager.addAccountExplicitly(
            account,
            getString(R.string.app_name),
            bundle
        );

        if (success) {
            Log.d(TAG, "Account created!!");
        } else {
            Log.d(TAG, "Account not created!!");
        }

        return success;
    }

}
