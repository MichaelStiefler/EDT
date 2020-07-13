package com.casioeurope.mis.edt;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import java.util.Arrays;

public class APNTools {
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (APN)";
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;

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

        Log.v(TAG, nameOfCurrMethod + " " + sb.toString() + (entrance ? " +" : " -"));
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
        int apnid = APN.INVALID_APN;

        try {
            if (apn != null) {
                Log.v(TAG, "APNutils.createNewApn. Reading APN list.");
                Uri APN_URI = APN_TABLE_URI;
                ContentResolver resolver = context.getContentResolver();

                Log.v(TAG, "APNutils.createNewApn. Creating new registry based on parameters.");
                ContentValues values = prepareValues(apn);

                Log.v(TAG, "APNutils.createNewApn. Inserting new APN.");
                Cursor c = null;
                Uri newRow = resolver.insert(APN_URI, values);

                if(newRow != null) {
                    Log.v(TAG, "APNutils.createNewApn. Getting new ID.");
                    c = resolver.query(newRow, null, null, null, null);

                    int tableIndex = c.getColumnIndex("_id");
                    c.moveToFirst();
                    apnid = c.getShort(tableIndex);
                } else
                    Log.w(TAG, "APNutils.createNewApn. New APN was not found. Inserting failed?");

                if(c != null){
                    c.close();
                }

                if (apnid > APN.INVALID_APN && setAsDefaultAPN) {
                    Log.v(TAG, "APNutils.createNewApn. Setting new APN as default.");
                    ContentValues v = new ContentValues(1);
                    v.put("apn_id", apnid);

                    context.getContentResolver().update(APN_PREFER_URI, v, null, null);
                }
            } else
                Log.w(TAG, "APNutils.createNewApn. Invalid apn (null).");
        } catch (Exception e) {
            Log.e(TAG, "createNewApn: error", e);
        }

        Log.v(TAG, "APNutils.createNewApn. Returning ID " + String.valueOf(apnid));
        logMethodEntranceExit(false);
        return apnid;
    }

    public static boolean updateApn(Context context, APN apn) {
        logMethodEntranceExit(true);

        if (apn != null) {
            try {
                Log.v(TAG, "APNutils.updateApn. Reading APN list.");
                Uri APN_URI = APN_TABLE_URI;
                ContentResolver resolver = context.getContentResolver();

                Log.v(TAG, "APNutils.updateApn. Creating new registry based on parameters.");
                ContentValues values = prepareValues(apn);

                Log.v(TAG, "APNutils.updateApn. Inserting new APN.");
                int result = resolver.update(APN_URI, values, "_id = " + String.valueOf(apn.getId()), null);

                if (result != APN.INVALID_APN) {
                    Log.v(TAG, "APNutils.updateApn. APN updated.");
                    logMethodEntranceExit(false);
                    return true;
                } else {
                    Log.w(TAG, "APNutils.updateApn. Invalid ID (" + String.valueOf(apn.getId()) + ").");
                    logMethodEntranceExit(false);
                    return false;
                }
            } catch (Exception e) {
                Log.e(TAG, "APNUtils.updateApn error: ", e);
                logMethodEntranceExit(false);
                return false;
            }
        } else {
            Log.w(TAG, "APNutils.updateApn. Invalid apn (null).");

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

        Log.v(TAG, "APNutils.getApn. Looking for APN " + apn);
        String columns[] = new String[] { "_ID", "NAME" };
        String where = "name = ?";
        String wargs[] = new String[] { apn };
        String sortOrder = null;
        Cursor cur = context.getContentResolver().query(APN_TABLE_URI, columns, where, wargs, sortOrder);

        if (cur != null) {
            int tableIndex = cur.getColumnIndex("_id");

            if (cur.moveToFirst()) {
                Log.v(TAG, "APNutils.getApn. APN found.");
                result = cur.getShort(tableIndex);
            }

            cur.close();
        }

        if (result == APN.INVALID_APN)
            Log.w(TAG, "APNutils.getApn. APN not found.");

        logMethodEntranceExit(false);
        return result;
    }

    public static APNParcelable getApn(Context context, String name) {
        logMethodEntranceExit(true);
        APN apn = Arrays.stream(getAllApnList(context)).filter(a -> a.getName().equals(name)).findFirst().orElse(null);
        logMethodEntranceExit(false);
        return apn==null?null:new APNParcelable(apn);
    }


    public static boolean setPreferredApn(Context context, String apn) {
        logMethodEntranceExit(true);
        boolean changed = false;

        Log.v(TAG, "APNutils.setPreferredApn. Looking for APN " + apn);
        String columns[] = new String[] { "_ID", "NAME" };
        String where = "name = ?";
        String wargs[] = new String[] { apn };
        String sortOrder = null;
        Cursor cur = context.getContentResolver().query(APN_TABLE_URI, columns, where, wargs, sortOrder);

        if (cur != null) {
            if (cur.moveToFirst()) {
                Log.v(TAG, "APNutils.setPreferredApn. APN found. Setting as default.");
                ContentValues values = new ContentValues(1);
                values.put("apn_id", cur.getLong(0));

                if (context.getContentResolver().update(APN_PREFER_URI, values, null, null) == 1) {
                    Log.v(TAG, "APNutils.setPreferredApn. APN marked as default.");
                    changed = true;
                }
            }

            cur.close();
        }

        if (!changed)
            Log.w(TAG, "APNutils.setPreferredApn. APN not found or could not be marked as default.");

        logMethodEntranceExit(false);
        return changed;
    }

    public static APNParcelable[] getAllApnList(Context context) {

        logMethodEntranceExit(true);
        APNParcelable[] result = null;

        Uri contentUri = APN_TABLE_URI;

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri,
                    new String[]{"name", "_ID", "apn", "mcc", "mnc", "numeric", "user", "password", "server", "proxy",
                            "port", "mmsproxy", "mmsport", "mmsc", "type", "current", "authtype "}, null, null, null);
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
        } finally {
            if (cursor != null)
                cursor.close();
        }

        logMethodEntranceExit(false);
        return result;
    }
}
