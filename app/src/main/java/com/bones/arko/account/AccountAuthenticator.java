package com.bones.arko.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/**
 * Account Authenticator
 */
class AccountAuthenticator extends AbstractAccountAuthenticator {

    AccountAuthenticator(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bundle editProperties(
        AccountAuthenticatorResponse response,
        String accountType
    ) {
        return null;
    }

    @Override
    public Bundle addAccount(
        AccountAuthenticatorResponse response,
        String accountType,
        String authTokenType,
        String[] requiredFeatures,
        Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bundle confirmCredentials(
        AccountAuthenticatorResponse response,
        Account account,
        Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bundle getAuthToken(
        AccountAuthenticatorResponse response,
        Account account,
        String authTokenType,
        Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bundle updateCredentials(
        AccountAuthenticatorResponse response,
        Account account,
        String authTokenType,
        Bundle options
    ) throws NetworkErrorException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bundle hasFeatures(
        AccountAuthenticatorResponse response,
        Account account,
        String[] features
    ) throws NetworkErrorException {
        return null;
    }

}
