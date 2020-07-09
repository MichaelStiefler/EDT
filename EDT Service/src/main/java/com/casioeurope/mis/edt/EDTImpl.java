package com.casioeurope.mis.edt;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.AlarmManager;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.casioeurope.mis.edt.Wifi.Wifi;
import com.casioeurope.mis.edt.barcodescanner.ScanLib;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EDTImpl extends IEDT.Stub {

    private Context context;
    private Handler handler = new Handler();

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;
    private static String TAG = "EDT_TOOLS (Implementation)";

    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameofCurrMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameofCurrMethod.startsWith("access$")) { // Inner Class called this method!
            nameofCurrMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameofCurrMethod + " " + sb.toString() + (entrance?" +":" -"));
    }

    private class DisplayToast implements Runnable {

        String toastMessage;

        public DisplayToast(String toast){
            toastMessage = toast;
        }

        public void run(){
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public EDTImpl(Context context) {
        this.context = context;
    }

    /**
     * Displays a Toast Message containing the specified String.
     * To be used for testing purpose only.
     *
     * @param message the Message to be displayed
     * @return        whether or not the Message could be shown
     */
    @Override
    public boolean testMessage(String message) {
        Log.d(TAG, String.format("testMessage(%s)", message));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools Test Message!");
            return false;
        }
        handler.post(new DisplayToast(message));
        return true;
    }

    @Override
    public boolean shutdown() {
        Log.d(TAG, "shutdown()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools shutdown!");
            return false;
        }
        return PowerManager.shutdown(this.context);
     }

    @Override
    public boolean reboot() {
        Log.d(TAG, "reboot()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools reboot!");
            return false;
        }
        return PowerManager.reboot(this.context);
    }

    @Override
    public boolean recovery() {
        Log.d(TAG, "recovery()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools recovery!");
            return false;
        }
        return PowerManager.recovery(this.context);
    }

    @Override
    public boolean clearPassword() {
        Log.d(TAG, "clearPassword()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearPassword!");
            return false;
        }
        return Security.clearPassword(this.context);
    }

    @Override
    public boolean resetPassword(String newPassword) {
        Log.d(TAG, "passwordReset()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools passwordReset!");
            return false;
        }
        return Security.resetPassword(newPassword, this.context);
    }

    @Override
    public boolean lockDevice() {
        Log.d(TAG, "lockDevice()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools lockDevice!");
            return false;
        }
        return PowerManager.lockDevice(this.context);
    }

    @Override
    public boolean factoryReset() {
        Log.d(TAG, "factoryReset()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools factoryReset!");
            return false;
        }
        return PowerManager.factoryReset(this.context);
    }

    @Override
    public boolean allowUnknownSources(boolean allow) {
        Log.d(TAG, "allowUnknownSources(" + allow + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools allowUnknownSources!");
            return false;
        }
        return Security.allowUnknownSources(allow, this.context);
    }

    @Override
    public boolean setDateTime(int year, int month, int day, int hour, int minute, int second) {
        Log.d(TAG, "setDateTime(" + year + "," + month + "," + day + "," + hour + "," + minute + "," + second + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setDateTime!");
            return false;
        }
        try {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, hour, minute, second);
            AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
            assert am != null;
            am.setTime(c.getTimeInMillis());
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in setDateTime():");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean setTimeZone(String timeZone) {
        Log.d(TAG, "setTimeZone(" + timeZone + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setTimeZone!");
            return false;
        }
        try {
            AlarmManager am = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
            assert am != null;
            am.setTimeZone(timeZone);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in setTimeZone():");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeAllAccounts() {
        Log.d(TAG, "removeAllAccounts()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAllAccounts!");
            return false;
        }
        try {
            AccountManager accountManager = AccountManager.get(this.context);
            Account[] availableAccounts = accountManager.getAccounts();
            for (Account availableAccount : availableAccounts) {
                removeAccount(availableAccount);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAllAccounts():");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeAllGoogleAccounts() {
        Log.d(TAG, "removeAllGoogleAccounts()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAllGoogleAccounts!");
            return false;
        }
        try {
            AccountManager accountManager = AccountManager.get(this.context);
            Account[] availableAccounts = accountManager.getAccountsByType("com.google");
            for (Account availableAccount : availableAccounts) {
                removeAccount(availableAccount);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAllGoogleAccounts():");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeAccount(Account account) {
        Log.d(TAG, "removeAccount(" + account.name + "(Type: " + account.type + "))");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAccount!");
            return false;
        }
        try {
            Log.d(TAG, "Removing Google Account " + account.name);
            AccountManager.get(this.context).removeAccountExplicitly(account);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAccount():");
            e.printStackTrace();
        }
        try {
            Log.d(TAG, "Trying to remove Google Account " + account.name + " alternatively.");
            AccountManagerFuture<Bundle> booleanAccountManagerFuture = AccountManager.get(this.context).removeAccount(account, null, null, null);
            Bundle bundle = booleanAccountManagerFuture.getResult(10, TimeUnit.SECONDS);
            return bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);
            // return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeAccount():");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean enableWifi(boolean enable) {
        Log.d(TAG, "enableWifi(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableWifi!");
            return false;
        }
        return Wifi.enableWifi(enable, this.context);
    }

    @Override
    public String copyFile(String sourceFilePath, String destinationFilePath, List<String> options) {
        Log.d(TAG, String.format("copyFile(%s, %s, %s)", sourceFilePath, destinationFilePath, options==null?"null":options.toString()));
        try {
            CopyOption[] copyOptions = null;
            if (options != null) {
                copyOptions = new CopyOption[options.size()];
                List<String> standardCopyOptionStrings = Arrays.stream(StandardCopyOption.values()).map(Enum::toString).collect(Collectors.toList());
                List<String> linkOptionStrings = Arrays.stream(LinkOption.values()).map(Enum::toString).collect(Collectors.toList());
                for (int i = 0; i < options.size(); i++) {
                    try {
                        if (standardCopyOptionStrings.contains(options.get(i)))
                            copyOptions[i] = StandardCopyOption.valueOf(options.get(i));
                        else if (linkOptionStrings.contains(options.get(i)))
                            copyOptions[i] = LinkOption.valueOf(options.get(i));
                        else
                            Log.d(TAG, String.format("CopyOption named \"%s\" unknown, will be ignored!", options.get(i)));
                    } catch (Exception e) {
                        Log.d(TAG, "Error in copyFile():");
                        e.printStackTrace();
                    }
                }
            }
            return Files.copy(Paths.get(sourceFilePath), Paths.get(destinationFilePath), copyOptions).toString();
        } catch (IOException e) {
            Log.d(TAG, "Error in copyFile():");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String moveFile(String sourceFilePath, String destinationFilePath, List<String> options) {
        Log.d(TAG, String.format("moveFile(%s, %s, %s)", sourceFilePath, destinationFilePath, options==null?"null":options.toString()));
        try {
            CopyOption[] copyOptions = null;
            if (options != null) {
                copyOptions = new CopyOption[options.size()];
                List<String> standardCopyOptionStrings = Arrays.stream(StandardCopyOption.values()).map(Enum::toString).collect(Collectors.toList());
                List<String> linkOptionStrings = Arrays.stream(LinkOption.values()).map(Enum::toString).collect(Collectors.toList());
                for (int i = 0; i < options.size(); i++) {
                    try {
                        if (standardCopyOptionStrings.contains(options.get(i)))
                            copyOptions[i] = StandardCopyOption.valueOf(options.get(i));
                        else if (linkOptionStrings.contains(options.get(i)))
                            copyOptions[i] = LinkOption.valueOf(options.get(i));
                        else
                            Log.d(TAG, String.format("CopyOption named \"%s\" unknown, will be ignored!", options.get(i)));
                    } catch (Exception e) {
                        Log.d(TAG, "Error in moveFile():");
                        e.printStackTrace();
                    }
                }
            }
            return Files.move(Paths.get(sourceFilePath), Paths.get(destinationFilePath), copyOptions).toString();
        } catch (IOException e) {
            Log.d(TAG, "Error in moveFile():");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteFile(String filePath) {
        Log.d(TAG, "deleteFile(" + filePath + ")");
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            Log.d(TAG, "Error in deleteIfExists():");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean readFile(ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        Log.d(TAG, "readFile(ReadWriteFileParams)");

        return FileTools.readFile(readWriteFileParamsParcelable);
    }

    @Override
    public boolean writeFile(ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        Log.d(TAG, "readFile(ReadWriteFileParams)");

        return FileTools.writeFile(readWriteFileParamsParcelable);
    }

    @Override
    public boolean getCurrentScanSettings(String settingsFilePath) {
        Log.d(TAG, "getCurrentScanSettings(" + settingsFilePath + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getCurrentScanSettings!");
            return false;
        }
        return ScanLib.getCurrentScanSettings(settingsFilePath, context);
    }
    @Override
    public boolean setNewScanSettings(String settingsFilePath) {
        Log.d(TAG, "setNewScanSettings(" + settingsFilePath + ")");
        return ScanLib.setNewScanSettings(settingsFilePath);
    }
    @Override
    public boolean getCurrentAndSetNewScanSettings(String settingsFilePath) {
        Log.d(TAG, "getCurrentAndSetNewScanSettings(" + settingsFilePath + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getCurrentAndSetNewScanSettings!");
            return false;
        }
        return ScanLib.getCurrentAndSetNewScanSettings(settingsFilePath, context);
    }

    @Override
    public boolean setDefaultHomePage(String homePage) {
        Log.d(TAG, "setDefaultHomePage(" + homePage + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setDefaultHomePage!");
            return false;
        }
        return Browser.setDefaultHomePage(homePage, context);
    }
    @Override
    public boolean rememberPasswords(boolean enable) {
        Log.d(TAG, "rememberPasswords(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools rememberPasswords!");
            return false;
        }
        return Browser.rememberPasswords(enable, context);
    }
    @Override
    public boolean saveFormData(boolean enable) {
        Log.d(TAG, "saveFormData(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools saveFormData!");
            return false;
        }
        return Browser.saveFormData(enable, context);
    }

    @Override
    public boolean enableCameras(boolean enable) {
        Log.d(TAG, "enableCameras(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableCameras!");
            return false;
        }
        return Camera.enableCameras(enable, context);
    }

    @Override
    public boolean enableRoaming(boolean enable) {
        Log.d(TAG, "enableRoaming(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableRoaming!");
            return false;
        }
        return Settings.Global.putInt(context.getContentResolver(), Settings.Global.DATA_ROAMING, enable?1:0);
    }

    @Override
    public boolean enableBackgroundData(boolean enable) {
        Log.d(TAG, "enableBackgroundData(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableBackgroundData!");
            return Cellular.enableBackgroundData(enable, context);
        }
        return false;
    }

    @Override
    public boolean setScreenOffTimeout(int milliseconds) {
        Log.d(TAG, "setScreenOffTimeout(" + milliseconds + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setScreenOffTimeout!");
            return false;
        }
        return Settings.System.putInt(this.context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, milliseconds);
    }

    @Override
    public boolean setDefaultLauncher(String packageName) {
        Log.d(TAG, "setDefaultLauncher(" + packageName + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setDefaultLauncher!");
            return false;
        }
        return Launcher.setDefaultLauncher(packageName, this.context);
    }

    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public boolean addNetwork(WifiConfigurationParcelable wifiConfiguration) {
        Log.d(TAG, "addNetwork(" + wifiConfiguration.SSID + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools addNetwork!");
            return false;
        }
        return Wifi.addNetwork(wifiConfiguration, this.context);
    }

    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public boolean updateNetwork(WifiConfigurationParcelable wifiConfiguration) {
        if (wifiConfiguration == null || (wifiConfiguration.SSID == null || wifiConfiguration.SSID.isEmpty()) && wifiConfiguration.networkId < 1) return false;
        Log.d(TAG, "updateNetwork()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools updateNetwork!");
            return false;
        }
        return Wifi.updateNetwork(wifiConfiguration, this.context);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public boolean removeNetwork(String ssid) {
        Log.d(TAG, "removeNetwork(" + ssid + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeNetwork!");
            return false;
        }
        return Wifi.removeNetwork(ssid, this.context);
    }

    public boolean removeNetworkId(int networkId) {
        Log.d(TAG, "removeNetworkId(" + networkId + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeNetworkId!");
            return false;
        }
        return Wifi.removeNetwork(networkId, this.context);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public boolean connectNetwork(String ssid) {
        Log.d(TAG, "connectNetwork(" + ssid + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools connectNetwork!");
            return false;
        }
        return Wifi.connectNetwork(ssid, this.context);
    }

    public boolean connectNetworkId(int networkId) {
        Log.d(TAG, "connectNetworkId(" + networkId + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools connectNetworkId!");
            return false;
        }
        return Wifi.connectNetwork(networkId, this.context);
    }

    @Override
    public boolean enableBluetooth(boolean enable) {
        Log.d(TAG, "enableBluetooth(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableBluetooth!");
            return false;
        }
        return WirelessManager.enableBluetooth(enable);
    }

    @Override
    public boolean enableNfc(boolean enable) {
        Log.d(TAG, "enableNfc(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableNfc!");
            return false;
        }
        return WirelessManager.enableNfc(enable, this.context);
    }

    @Override
    public boolean enableGps(boolean enable) {
        Log.d(TAG, "enableGps(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableGps!");
            return false;
        }
        return WirelessManager.enableGps(enable, this.context);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public boolean enableWwan(boolean enable) {
        Log.d(TAG, "enableWwan(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableWwan!");
            return false;
        }
        return WirelessManager.enableWwan(enable, this.context);
    }

    @Override
    public boolean enableDeveloperMode(boolean enable) {
        Log.d(TAG, "enableDeveloperMode(" + enable + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableDeveloperMode!");
            return false;
        }
        return Security.enableDeveloperMode(enable, this.context);
    }


}
