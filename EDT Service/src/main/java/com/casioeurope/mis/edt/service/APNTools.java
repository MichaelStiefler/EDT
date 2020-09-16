package com.casioeurope.mis.edt.service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import com.casioeurope.mis.edt.types.APN;
import com.casioeurope.mis.edt.types.APNParcelable;

import java.util.Arrays;
import java.util.Objects;

public class APNTools {
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (APN)";
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrentMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrentMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrentMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrentMethod + " " + sb.toString() + (entrance ? " +" : " -"));
    }

    private static final Uri APN_TABLE_URI = Telephony.Carriers.CONTENT_URI;
    private static final Uri APN_PREFER_URI = Uri.parse(Telephony.Carriers.CONTENT_URI.toString() + "/preferapn");

    private static ContentValues prepareValues(APN apn) {
        logMethodEntranceExit(true);
        ContentValues values = new ContentValues();

        if (!apn.getName().trim().equals(""))
            values.put("name", apn.getName());

        if (!apn.getApn().trim().equals(""))
            values.put("apn", apn.getApn());

        if (!apn.getMcc().trim().equals(""))
            values.put("mcc", apn.getMcc());

        if (!apn.getMnc().trim().equals(""))
            values.put("mnc", apn.getMnc());

        if (!apn.getMcc().trim().equals("") && !apn.getMnc().trim().equals(""))
            values.put("numeric", apn.getMcc() + apn.getMnc());

        if (!apn.getUser().trim().equals(""))
            values.put("user", apn.getUser());

        if (!apn.getPassword().trim().equals(""))
            values.put("password", apn.getPassword());

        if (!apn.getServer().trim().equals(""))
            values.put("server", apn.getServer());

        if (!apn.getProxy().trim().equals(""))
            values.put("proxy", apn.getProxy());

        if (!apn.getPort().trim().equals(""))
            values.put("port", apn.getPort());

        if (!apn.getMmsProxy().trim().equals(""))
            values.put("mmsproxy", apn.getMmsProxy());

        if (!apn.getMmsPort().trim().equals(""))
            values.put("mmsport", apn.getMmsPort());

        if (!apn.getMmsc().trim().equals(""))
            values.put("mmsc", apn.getMmsc());

        if (!apn.getType().trim().equals(""))
            values.put("type", apn.getType());

        if (!apn.getCurrent().trim().equals(""))
            values.put("current", apn.getCurrent());

        values.put("authtype", apn.getAuthType());

        logMethodEntranceExit(false);
        return values;
    }

    public static int createNewApn(Context context, APN apn, boolean setAsDefaultAPN) {
        logMethodEntranceExit(true);
        int apnId = APN.INVALID_APN;

        try {
            if (apn != null) {
                Log.v(TAG, "APNTools.createNewApn. Reading APN list.");
                ContentResolver resolver = context.getContentResolver();

                Log.v(TAG, "APNTools.createNewApn. Creating new registry based on parameters.");
                ContentValues values = prepareValues(apn);

                Log.v(TAG, "APNTools.createNewApn. Inserting new APN.");
                Cursor c = null;
                Uri newRow = resolver.insert(APN_TABLE_URI, values);

                if (newRow != null) {
                    Log.v(TAG, "APNTools.createNewApn. Getting new ID.");
                    c = resolver.query(newRow, null, null, null, null);

                    int tableIndex = Objects.requireNonNull(c).getColumnIndex("_id");
                    c.moveToFirst();
                    apnId = c.getShort(tableIndex);
                } else
                    Log.w(TAG, "APNTools.createNewApn. New APN was not found. Inserting failed?");

                if (c != null) {
                    c.close();
                }

                if (apnId > APN.INVALID_APN && setAsDefaultAPN) {
                    Log.v(TAG, "APNTools.createNewApn. Setting new APN as default.");
                    ContentValues v = new ContentValues(1);
                    v.put("apn_id", apnId);

                    context.getContentResolver().update(APN_PREFER_URI, v, null, null);
                }
            } else
                Log.w(TAG, "APNTools.createNewApn. Invalid apn (null).");
        } catch (Exception e) {
            Log.e(TAG, "createNewApn: error", e);
        }

        Log.v(TAG, "APNTools.createNewApn. Returning ID " + apnId);
        logMethodEntranceExit(false);
        return apnId;
    }

    public static boolean updateApn(Context context, APN apn) {
        logMethodEntranceExit(true);

        if (apn != null) {
            try {
                Log.v(TAG, "APNTools.updateApn Reading APN list.");
                ContentResolver resolver = context.getContentResolver();

                Log.v(TAG, "APNTools.updateApn Creating new registry based on parameters.");
                ContentValues values = prepareValues(apn);

                Log.v(TAG, "APNTools.updateApn Inserting new APN.");
                int result = resolver.update(APN_TABLE_URI, values, "_id = " + apn.getId(), null);

                if (result != APN.INVALID_APN) {
                    Log.v(TAG, "APNTools.updateApn APN updated.");
                    logMethodEntranceExit(false);
                    return true;
                } else {
                    Log.w(TAG, "APNTools.updateApn Invalid ID (" + apn.getId() + ").");
                    logMethodEntranceExit(false);
                    return false;
                }
            } catch (Exception e) {
                Log.e(TAG, "APNTools.updateApn error: ", e);
                logMethodEntranceExit(false);
                return false;
            }
        } else {
            Log.w(TAG, "APNTools.updateApn Invalid apn (null).");

            logMethodEntranceExit(false);
            return false;
        }
    }

    public static boolean verifyApn(Context context, String apn) {
        logMethodEntranceExit(true);
        logMethodEntranceExit(false);
        return getApnId(context, apn) > APN.INVALID_APN;
    }

    public static int getApnId(Context context, String apn) {
        logMethodEntranceExit(true);
        int result = APN.INVALID_APN;

        Log.v(TAG, "APNTools.getApn Looking for APN " + apn);
        String[] columns = new String[]{"_ID", "NAME"};
        String where = "name = ?";
        String[] selectionArgs = new String[]{apn};
        Cursor cur = context.getContentResolver().query(APN_TABLE_URI, columns, where, selectionArgs, null);

        if (cur != null) {
            int tableIndex = cur.getColumnIndex("_id");

            if (cur.moveToFirst()) {
                Log.v(TAG, "APNTools.getApn APN found.");
                result = cur.getShort(tableIndex);
            }

            cur.close();
        }

        if (result == APN.INVALID_APN)
            Log.w(TAG, "APNTools.getApn APN not found.");

        logMethodEntranceExit(false);
        return result;
    }

    public static APNParcelable getApn(Context context, String name) {
        logMethodEntranceExit(true);
        APN apn = Arrays.stream(Objects.requireNonNull(getAllApnList(context))).filter(a -> a.getName().equals(name)).findFirst().orElse(null);
        logMethodEntranceExit(false);
        return apn == null ? null : new APNParcelable(apn);
    }


    public static boolean setPreferredApn(Context context, String apn) {
        logMethodEntranceExit(true);
        boolean changed = false;

        Log.v(TAG, "APNTools.setPreferredApn. Looking for APN " + apn);
        String[] columns = new String[]{"_ID", "NAME"};
        String where = "name = ?";
        String[] selectionArgs = new String[]{apn};
        Cursor cur = context.getContentResolver().query(APN_TABLE_URI, columns, where, selectionArgs, null);

        if (cur != null) {
            if (cur.moveToFirst()) {
                Log.v(TAG, "APNTools.setPreferredApn. APN found. Setting as default.");
                ContentValues values = new ContentValues(1);
                values.put("apn_id", cur.getLong(0));

                if (context.getContentResolver().update(APN_PREFER_URI, values, null, null) == 1) {
                    Log.v(TAG, "APNTools.setPreferredApn. APN marked as default.");
                    changed = true;
                }
            }

            cur.close();
        }

        if (!changed)
            Log.w(TAG, "APNTools.setPreferredApn. APN not found or could not be marked as default.");

        logMethodEntranceExit(false);
        return changed;
    }

    public static APNParcelable[] getAllApnList(Context context) {

        logMethodEntranceExit(true);
        APNParcelable[] result = null;

        try (Cursor cursor = context.getContentResolver().query(APN_TABLE_URI,
                new String[]{"name", "_ID", "apn", "mcc", "mnc", "numeric", "user", "password", "server", "proxy",
                        "port", "mmsproxy", "mmsport", "mmsc", "type", "current", "authtype "}, null, null, null)) {
            if (cursor != null) {
                result = new APNParcelable[cursor.getCount()];
                int i = 0;

                while (cursor.moveToNext()) {
                    result[i++] = new APNParcelable(APN.setName(cursor.getString(0))
                            .setId(cursor.getInt(1))
                            .setApn(cursor.getString(2))
                            .setMcc(cursor.getString(3))
                            .setMnc(cursor.getString(4))
                            .setUser(cursor.getString(6))
                            .setPassword(cursor.getString(7))
                            .setServer(cursor.getString(8))
                            .setProxy(cursor.getString(9))
                            .setPort(cursor.getString(10))
                            .setMmsProxy(cursor.getString(11))
                            .setMmsPort(cursor.getString(12))
                            .setMmsc(cursor.getString(13))
                            .setType(cursor.getString(14))
                            .setCurrent(cursor.getString(15))
                            .setAuthType(cursor.getInt(16))
                            .build());
                }
            }
        } catch (Exception ex) {
            //Handle exceptions here
            logMethodEntranceExit(false);
            return null;
        }

        logMethodEntranceExit(false);
        return result;
    }
}
