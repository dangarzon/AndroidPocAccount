package com.bones.arko.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
                Account account = createAccount();

                Boolean stored = storeAccount(account);

                if (stored) {
                    goMain();
                }
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
     * Start Main Activity
     */
    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Store Account into AccountManager
     *
     * @param account Account to store
     * @return true if the Account was added to AccountManager
     */
    private boolean storeAccount(Account account) {
        AccountManager accountManager = AccountManager.get(this);

        boolean success = accountManager.addAccountExplicitly(
            account,
            getString(R.string.account_password),
            null
        );

        if (success) {
            Log.d(TAG, "Account created!!");
        } else {
            Log.d(TAG, "Account not created!!");
        }

        return success;
    }

}
