package com.bones.arko.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Account account = createAccount();

        storeAccount(account);
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
