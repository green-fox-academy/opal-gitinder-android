package com.greenfox.opal.gitinder.service;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;

import java.io.IOException;

/**
 * Created by Nagy Dóra on 2017.06.12..
 */

public class OnTokenAcquired implements AccountManagerCallback<Bundle> {

    @Override
    public void run(AccountManagerFuture<Bundle> result) {
        Bundle bundle = null;
        try {
            bundle = result.getResult();
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        }

        String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
    }
}
