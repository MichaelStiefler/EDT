package com.casioeurope.mis.edt;

import android.accounts.Account;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AccManager {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;
    private static String TAG = "EDT (PowerManager)";

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrMethod + " " + sb.toString() + (entrance?" +":" -"));
    }

    public static boolean removeAllAccounts(Context context) {
        logMethodEntranceExit(true);
        Log.d(TAG, "removeAllAccounts()");
        try {
            android.accounts.AccountManager accountManager = android.accounts.AccountManager.get(context);
            Account[] availableAccounts = accountManager.getAccounts();
            for (Account availableAccount : availableAccounts) {
                removeAccount(context, availableAccount);
            }
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAllAccounts():");
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
        return false;
    }

    public static boolean removeAllGoogleAccounts(Context context) {
        logMethodEntranceExit(true);
        Log.d(TAG, "removeAllGoogleAccounts()");
        try {
            android.accounts.AccountManager accountManager = android.accounts.AccountManager.get(context);
            Account[] availableAccounts = accountManager.getAccountsByType("com.google");
            for (Account availableAccount : availableAccounts) {
                removeAccount(context, availableAccount);
            }
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAllGoogleAccounts():");
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
        return false;
    }

    public static boolean removeAccount(Context context, Account account) {
        logMethodEntranceExit(true);
        Log.d(TAG, "removeAccount(" + account.name + "(Type: " + account.type + "))");
        try {
            Log.d(TAG, "Removing Account " + account.name);
            android.accounts.AccountManager.get(context).removeAccountExplicitly(account);
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAccount():");
            e.printStackTrace();
        }
        try {
            Log.d(TAG, "Trying to remove Account " + account.name + " alternatively.");
            AccountManagerFuture<Bundle> booleanAccountManagerFuture = android.accounts.AccountManager.get(context).removeAccount(account, null, null, null);
            Bundle bundle = booleanAccountManagerFuture.getResult(10, TimeUnit.SECONDS);
            logMethodEntranceExit(false);
            return bundle.getBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT);
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAccount():");
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
        return false;
    }

    public static Account[] getGoogleAccounts(Context context) {
        logMethodEntranceExit(true);
        Log.d(TAG, "removeAllGoogleAccounts()");
        try {
            android.accounts.AccountManager accountManager = android.accounts.AccountManager.get(context);
            logMethodEntranceExit(false);
            return accountManager.getAccountsByType("com.google");
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAllGoogleAccounts():");
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
        return null;
    }

}
