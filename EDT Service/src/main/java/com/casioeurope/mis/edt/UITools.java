package com.casioeurope.mis.edt;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UITools {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;
    private static String TAG = "EDT (UI Tools)";
    private static Handler clipboardHandler;

    private static Handler nullHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message inputMessage) {
        }
    };


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

    @SuppressWarnings("unused")
    public static boolean enableClipboard(Context context, boolean enable) {
        logMethodEntranceExit(true);
        try {
            ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            Field handlerField = clipboardManager.getClass().getDeclaredField("mHandler");
            handlerField.setAccessible(true);
            Handler handler = (Handler) handlerField.get(clipboardManager);
            if (!enable) {
                if (handler != null && handler != nullHandler) clipboardHandler = handler;
                handlerField.set(clipboardManager, nullHandler);
            } else {
                if (handler == nullHandler && clipboardHandler != null) {
                    handlerField.set(clipboardManager, clipboardHandler);
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean clearClipboard(Context context) {
        logMethodEntranceExit(true);
        try {
            ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("", ""));
            return true;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return false;
    }

    @SuppressWarnings("unused")
    public static List<String> getKeyboardNames(Context context) {
        logMethodEntranceExit(true);
        List<String> keyboardNames = new ArrayList<String>();
        try {
            List<InputMethodInfo> InputMethods = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).getInputMethodList();
            int numOfKeEyboards = InputMethods.size();
            for (int i = 0; i < numOfKeEyboards; i++)
            {
                String fullKeyboardName = ((InputMethodInfo) InputMethods.get(i)).toString();
                String keyboard_package = fullKeyboardName.substring(fullKeyboardName.indexOf("{") + 1, fullKeyboardName.indexOf("/"));
                try
                {
                    // by package name getting app name
                    String inputKeyboardName = context.getPackageManager().getApplicationInfo(keyboard_package, 0).loadLabel(context.getPackageManager()).toString();
                    keyboardNames.add(inputKeyboardName);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return keyboardNames;
    }

    @SuppressWarnings("unused")
    public static boolean setKeyboard(Context context, String keyboardName) {
        logMethodEntranceExit(true);
        try {
            boolean retVal = true;
            retVal &= Settings.Secure.putString(context.getContentResolver(), Settings.Secure.ENABLED_INPUT_METHODS, keyboardName);
            retVal &= Settings.Secure.putString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, keyboardName);
            return retVal;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return false;
    }
}
