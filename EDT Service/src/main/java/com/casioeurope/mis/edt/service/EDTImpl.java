package com.casioeurope.mis.edt.service;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.casioeurope.mis.edt.type.APN;
import com.casioeurope.mis.edt.type.APNParcelable;
import com.casioeurope.mis.edt.IEDT;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.ObjectParcelable;
import com.casioeurope.mis.edt.type.ReadWriteFileParamsParcelable;
import com.casioeurope.mis.edt.type.WifiConfigurationParcelable;
import com.casioeurope.mis.edt.service.barcodescanner.ScanLib;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"FieldMayBeFinal", "RedundantSuppression", "deprecation", "DefaultLocale"})
public class EDTImpl extends IEDT.Stub {

    private Context context;
    private Handler handler = new Handler();

    private static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static String TAG = "EDT (Implementation)";

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

    private static final BigInteger METHODS_SUPPORTED_EDT = new BigInteger("11111111111111111111111111111111111111111111111111111111111111111111", 2);
    private static String[] methodNamesEdt = {
            "addNetwork",
            "allowUnknownSources",
            "clearCacheForPackage",
            "clearClipboard",
            "clearDataForPackage",
            "clearPassword",
            "connectNetwork",
            "connectNetworkId",
            "copyFile",
            "createNewApn",
            "deleteFile",
            "disableApplication",
            "disableBatteryOptimization",
            "enableAdb",
            "enableApplication",
            "enableBackgroundData",
            "enableBatteryOptimization",
            "enableBluetooth",
            "enableCameras",
            "enableClipboard",
            "enableDeveloperMode",
            "enableDeviceAdmin",
            "enableGps",
            "enableMassStorage",
            "enableNfc",
            "enableRoaming",
            "enableWifi",
            "enableWwan",
            "factoryReset",
            "getAllApnList",
            "getApn",
            "getApnId",
            "getCurrentAndSetNewScanSettings",
            "getCurrentScanSettings",
            "getGoogleAccounts",
            "getKeyboardNames",
            "initializeKeyStore",
            "installApk",
            "installCertificate",
            "lockDevice",
            "mountSDCard",
            "moveFile",
            "readFile",
            "reboot",
            "recovery",
            "rememberPasswords",
            "removeAccount",
            "removeAllAccounts",
            "removeAllGoogleAccounts",
            "removeNetwork",
            "removeNetworkId",
            "resetPassword",
            "saveFormData",
            "setDateTime",
            "setDefaultHomePage",
            "setKeyboard",
            "setNewScanSettings",
            "setPreferredApn",
            "setScreenLockTimeout",
            "setTimeZone",
            "shutdown",
            "testMessage",
            "uninstallPackage",
            "updateApn",
            "updateNetwork",
            "verifyApn",
            "writeFile"};

    private static final BigInteger METHODS_SUPPORTED_REFLECTION = new BigInteger("11111111111111111111111111111111111111111111111", 2);
    private static String[] methodNamesReflection = {
            "getBoolean",
            "getBoolean",
            "getByte",
            "getByte",
            "getChar",
            "getChar",
            "getDouble",
            "getDouble",
            "getFloat",
            "getFloat",
            "getInt",
            "getInt",
            "getLong",
            "getLong",
            "getShort",
            "getShort",
            "getString",
            "getString",
            "getType",
            "getType",
            "getValue",
            "getValue",
            "invokeMethod",
            "invokeMethod",
            "invokeMethod",
            "invokeMethod",
            "setBoolean",
            "setBoolean",
            "setByte",
            "setByte",
            "setChar",
            "setChar",
            "setDefaultLauncher",
            "setDouble",
            "setDouble",
            "setFloat",
            "setFloat",
            "setInt",
            "setInt",
            "setLong",
            "setLong",
            "setShort",
            "setShort",
            "setString",
            "setString",
            "setValue",
            "setValue"};

    public boolean isMethodNameSupportedEdt(String methodName) {
        int methodIndex = Arrays.asList(methodNamesEdt).indexOf(methodName);
        return methodIndex >= 0 && isMethodSupportedEdt(BigInteger.ONE.shiftLeft(methodIndex).toString());
    }

    public boolean isMethodSupportedEdt(String methodBigInteger) {
        try {
            return !new BigInteger(methodBigInteger).and(METHODS_SUPPORTED_EDT).equals(BigInteger.ZERO);
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isMethodNameSupportedReflection(String methodName) {
        int methodIndex = Arrays.asList(methodNamesReflection).indexOf(methodName);
        return methodIndex >= 0 && isMethodSupportedReflection(BigInteger.ONE.shiftLeft(methodIndex).toString());
    }

    public boolean isMethodSupportedReflection(String methodBigInteger) {
        try {
            return !new BigInteger(methodBigInteger).and(METHODS_SUPPORTED_REFLECTION).equals(BigInteger.ZERO);
        } catch (Exception e) {
            return false;
        }
    }

    private class DisplayToast implements Runnable {

        String toastMessage;

        public DisplayToast(String toast) {
            toastMessage = toast;
        }

        public void run() {
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
     * @return whether or not the Message could be shown
     */
    @Override
    public boolean testMessage(BooleanParcelable unsupported, String message) {
        logMethodEntranceExit(true, String.format("testMessage(%s)", message));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools Test Message!");
            return false;
        }
        handler.post(new DisplayToast(message));
        return true;
    }

    @Override
    public boolean shutdown(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "shutdown()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools shutdown!");
            return false;
        }
        return PowerManager.shutdown(this.context);
    }

    @Override
    public boolean reboot(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "reboot()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools reboot!");
            return false;
        }
        return PowerManager.reboot(this.context);
    }

    @Override
    public boolean recovery(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "recovery()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools recovery!");
            return false;
        }
        return PowerManager.recovery(this.context);
    }

    @Override
    public boolean clearPassword(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "clearPassword()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearPassword!");
            return false;
        }
        return Security.clearPassword(this.context);
    }

    @Override
    public boolean resetPassword(BooleanParcelable unsupported, String newPassword) {
        logMethodEntranceExit(true, String.format("resetPassword(%s)", newPassword));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools passwordReset!");
            return false;
        }
        return Security.resetPassword(newPassword, this.context);
    }

    @Override
    public boolean lockDevice(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "lockDevice()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools lockDevice!");
            return false;
        }
        return PowerManager.lockDevice(this.context);
    }

    @Override
    public boolean factoryReset(BooleanParcelable unsupported, boolean removeAccounts) {
        logMethodEntranceExit(true, String.format("factoryReset(%b)", removeAccounts));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools factoryReset!");
            return false;
        }
        return PowerManager.factoryReset(this.context, removeAccounts);
    }

    @Override
    public boolean allowUnknownSources(BooleanParcelable unsupported, boolean allow) {
        logMethodEntranceExit(true, String.format("allowUnknownSources(%b)", allow));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools allowUnknownSources!");
            return false;
        }
        return Security.allowUnknownSources(allow, this.context);
    }

    @Override
    public boolean setDateTime(BooleanParcelable unsupported, int year, int month, int day, int hour, int minute, int second) {
        logMethodEntranceExit(true, String.format("setDateTime(%d, %d, %d, %d, %d, %d)", year, month, day, hour, minute, second));
        unsupported.setValue(false);
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
    public boolean setTimeZone(BooleanParcelable unsupported, String timeZone) {
        logMethodEntranceExit(true, String.format("setTimeZone(%s)", timeZone));
        unsupported.setValue(false);
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
    public boolean removeAllAccounts(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "removeAllAccounts()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAllAccounts!");
            return false;
        }
        return AccManager.removeAllAccounts(this.context);
    }

    @Override
    public boolean removeAllGoogleAccounts(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "removeAllGoogleAccounts()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAllGoogleAccounts!");
            return false;
        }
        return AccManager.removeAllGoogleAccounts(this.context);
    }

    @Override
    public boolean removeAccount(BooleanParcelable unsupported, Account account) {
        logMethodEntranceExit(true, String.format("removeAccount(Account name:%s type:%s)", account.name, account.type));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAccount!");
            return false;
        }
        return AccManager.removeAccount(this.context, account);
    }

    @Override
    public boolean enableWifi(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableWifi(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableWifi!");
            return false;
        }
        return Wifi.enableWifi(enable, this.context);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public String copyFile(BooleanParcelable unsupported, String sourceFilePath, String destinationFilePath, List<String> options) {
        logMethodEntranceExit(true, String.format("copyFile(%s, %s, %s)", sourceFilePath, destinationFilePath, options == null ? "null" : options.toString()));
        unsupported.setValue(false);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            logMethodEntranceExit(false, String.format("copyFile(%s, %s, %s) = null", sourceFilePath, destinationFilePath, options == null ? "null" : options.toString()));
            return null;
        }
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

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public String moveFile(BooleanParcelable unsupported, String sourceFilePath, String destinationFilePath, List<String> options) {
        logMethodEntranceExit(true, String.format("moveFile(%s, %s, %s)", sourceFilePath, destinationFilePath, options == null ? "null" : options.toString()));
        unsupported.setValue(false);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            logMethodEntranceExit(false, String.format("moveFile(%s, %s, %s) = null", sourceFilePath, destinationFilePath, options == null ? "null" : options.toString()));
            return null;
        }
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

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public boolean deleteFile(BooleanParcelable unsupported, String filePath) {
        logMethodEntranceExit(true, String.format("deleteFile(%s)", filePath));
        unsupported.setValue(false);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            logMethodEntranceExit(false, "deleteFile(" + filePath + ") = false");
            return false;
        }
        try {
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            Log.d(TAG, "Error in deleteIfExists():");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean readFile(BooleanParcelable unsupported, ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        logMethodEntranceExit(true, "readFile(ReadWriteFileParams)");
        unsupported.setValue(false);
        return FileTools.readFile(readWriteFileParamsParcelable);
    }

    @Override
    public boolean writeFile(BooleanParcelable unsupported, ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        logMethodEntranceExit(true, "writeFile(ReadWriteFileParams)");
        unsupported.setValue(false);
        return FileTools.writeFile(readWriteFileParamsParcelable);
    }

    @Override
    public boolean getCurrentScanSettings(BooleanParcelable unsupported, String settingsFilePath) {
        logMethodEntranceExit(true, String.format("getCurrentScanSettings(%s)", settingsFilePath));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getCurrentScanSettings!");
            return false;
        }
        return ScanLib.getCurrentScanSettings(settingsFilePath, this.context);
    }

    @Override
    public boolean setNewScanSettings(BooleanParcelable unsupported, String settingsFilePath) {
        logMethodEntranceExit(true, String.format("setNewScanSettings(%s)", settingsFilePath));
        unsupported.setValue(false);
        return ScanLib.setNewScanSettings(settingsFilePath, this.context);
    }

    @Override
    public boolean getCurrentAndSetNewScanSettings(BooleanParcelable unsupported, String settingsFilePath) {
        logMethodEntranceExit(true, String.format("getCurrentAndSetNewScanSettings(%s)", settingsFilePath));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getCurrentAndSetNewScanSettings!");
            return false;
        }
        return ScanLib.getCurrentAndSetNewScanSettings(settingsFilePath, context);
    }

    @Override
    public boolean setDefaultHomePage(BooleanParcelable unsupported, String homePage) {
        logMethodEntranceExit(true, String.format("setDefaultHomePage(%s)", homePage));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setDefaultHomePage!");
            return false;
        }
        return Browser.setDefaultHomePage(homePage, context);
    }

    @Override
    public boolean rememberPasswords(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("rememberPasswords(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools rememberPasswords!");
            return false;
        }
        return Browser.rememberPasswords(enable, context);
    }

    @Override
    public boolean saveFormData(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("saveFormData(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools saveFormData!");
            return false;
        }
        return Browser.saveFormData(enable, context);
    }

    @Override
    public boolean enableCameras(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableCameras(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableCameras!");
            return false;
        }
        return Camera.enableCameras(enable, context);
    }

    @Override
    public boolean enableRoaming(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableRoaming(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableRoaming!");
            return false;
        }
        return Settings.Global.putInt(context.getContentResolver(), Settings.Global.DATA_ROAMING, enable ? 1 : 0);
    }

    @Override
    public boolean enableBackgroundData(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableBackgroundData(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableBackgroundData!");
            return Cellular.enableBackgroundData(enable, context);
        }
        return false;
    }

    @Override
    public boolean setScreenLockTimeout(BooleanParcelable unsupported, int milliseconds) {
        logMethodEntranceExit(true, String.format("setScreenLockTimeout(%d)", milliseconds));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setScreenLockTimeout!");
            return false;
        }
        return PowerManager.setScreenLockTimeout(this.context, milliseconds);
    }

    @Override
    public boolean setDefaultLauncher(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("setDefaultLauncher(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setDefaultLauncher!");
            return false;
        }
        return Launcher.setDefaultLauncher(packageName, this.context);
    }

    @Override
    public boolean addNetwork(BooleanParcelable unsupported, WifiConfigurationParcelable wifiConfiguration) {
        logMethodEntranceExit(true, String.format("addNetwork(%s)", wifiConfiguration.SSID));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools addNetwork!");
            return false;
        }
        return Wifi.addNetwork(wifiConfiguration, this.context);
    }

    @Override
    public boolean updateNetwork(BooleanParcelable unsupported, WifiConfigurationParcelable wifiConfiguration) {
        if (wifiConfiguration == null || (wifiConfiguration.SSID == null || wifiConfiguration.SSID.isEmpty()) && wifiConfiguration.networkId < 1)
            return false;
        logMethodEntranceExit(true, String.format("updateNetwork(%s)", wifiConfiguration.SSID));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools updateNetwork!");
            return false;
        }
        return Wifi.updateNetwork(wifiConfiguration, this.context);
    }

    @Override
    public boolean removeNetwork(BooleanParcelable unsupported, String ssid) {
        logMethodEntranceExit(true, String.format("removeNetwork(%s)", ssid));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeNetwork!");
            return false;
        }
        return Wifi.removeNetwork(ssid, this.context);
    }

    @Override
    public boolean removeNetworkId(BooleanParcelable unsupported, int networkId) {
        logMethodEntranceExit(true, String.format("removeNetworkId(%d)", networkId));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeNetworkId!");
            return false;
        }
        return Wifi.removeNetwork(networkId, this.context);
    }

    @Override
    public boolean connectNetwork(BooleanParcelable unsupported, String ssid) {
        logMethodEntranceExit(true, String.format("connectNetwork(%s)", ssid));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools connectNetwork!");
            return false;
        }
        return Wifi.connectNetwork(ssid, this.context);
    }

    @Override
    public boolean connectNetworkId(BooleanParcelable unsupported, int networkId) {
        logMethodEntranceExit(true, String.format("connectNetworkId(%d)", networkId));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools connectNetworkId!");
            return false;
        }
        return Wifi.connectNetwork(networkId, this.context);
    }

    @Override
    public boolean enableBluetooth(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableBluetooth(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableBluetooth!");
            return false;
        }
        return WirelessManager.enableBluetooth(enable);
    }

    @Override
    public boolean enableNfc(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableNfc(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableNfc!");
            return false;
        }
        return WirelessManager.enableNfc(enable, this.context);
    }

    @Override
    public boolean enableGps(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableGps(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableGps!");
            return false;
        }
        return WirelessManager.enableGps(enable, this.context);
    }

    @Override
    public boolean enableWwan(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableWwan(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableWwan!");
            return false;
        }
        return WirelessManager.enableWwan(enable, this.context);
    }

    @Override
    public boolean enableDeveloperMode(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableDeveloperMode(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableDeveloperMode!");
            return false;
        }
        return Security.enableDeveloperMode(enable, this.context);
    }

    @Override
    public boolean createNewApn(BooleanParcelable unsupported, APNParcelable apn, boolean setAsDefault) {
        logMethodEntranceExit(true, String.format("createNewApn(%s, %b)", apn.getName(), setAsDefault));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools createNewApn!");
            return false;
        }
        return APNTools.createNewApn(this.context, apn, setAsDefault) != APN.INVALID_APN;
    }

    @Override
    public boolean updateApn(BooleanParcelable unsupported, APNParcelable apn) {
        logMethodEntranceExit(true, String.format("updateApn(%s)", apn.getName()));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools updateApn!");
            return false;
        }
        return APNTools.updateApn(this.context, apn);
    }

    @Override
    public boolean verifyApn(BooleanParcelable unsupported, String name) {
        logMethodEntranceExit(true, String.format("verifyApn(%s)", name));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools verifyApn!");
            return false;
        }
        return APNTools.verifyApn(this.context, name);
    }

    @Override
    public int getApnId(BooleanParcelable unsupported, String name) {
        logMethodEntranceExit(true, String.format("getApnId(%s)", name));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getApnId!");
            return APN.INVALID_APN;
        }
        return APNTools.getApnId(this.context, name);
    }

    @Override
    public APNParcelable getApn(BooleanParcelable unsupported, String name) {
        logMethodEntranceExit(true, String.format("getApn(%s)", name));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getApn!");
            return null;
        }
        return APNTools.getApn(this.context, name);
    }

    @Override
    public boolean setPreferredApn(BooleanParcelable unsupported, String name) {
        logMethodEntranceExit(true, String.format("setPreferredApn(%s)", name));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setPreferredApn!");
            return false;
        }
        return APNTools.setPreferredApn(this.context, name);
    }

    @Override
    public APNParcelable[] getAllApnList(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "getAllApnList()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getAllApnList!");
            return null;
        }
        return APNTools.getAllApnList(this.context);
    }

    @Override
    public Account[] getGoogleAccounts(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "getGoogleAccounts()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getGoogleAccounts!");
            return null;
        }
        return AccManager.getGoogleAccounts(this.context);
    }

    @Override
    public boolean initializeKeyStore(BooleanParcelable unsupported, String storeName, String password) {
        logMethodEntranceExit(true, String.format("initializeKeyStore(%s, %s)", storeName, password));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools initializeKeyStore!");
            return false;
        }
        return CertificateManager.initializeKeyStore(storeName, password);
    }

    @Override
    public boolean installCertificate(BooleanParcelable unsupported, String friendlyName, String fileName) {
        logMethodEntranceExit(true, String.format("installCertificate(%s, %s)", friendlyName, fileName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools installCACertificate!");
            return false;
        }
        return CertificateManager.installCACertificate(friendlyName, fileName, this.context);
    }

    @Override
    public boolean mountSDCard(BooleanParcelable unsupported, boolean mount) {
        logMethodEntranceExit(true, String.format("mountSDCard(%b)", mount));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools mountSDCard!");
            return false;
        }
        return FileTools.mountSDCard(this.context, mount);
    }

    @Override
    public boolean enableAdb(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableAdb(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableAdb!");
            return false;
        }
        return USBTools.enableAdb(this.context, enable);
    }

    @Override
    public boolean enableMassStorage(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableMassStorage(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableMassStorage!");
            return false;
        }
        return USBTools.enableMassStorage(this.context, enable);
    }

    @Override
    public List<String> getKeyboardNames(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "getKeyboardNames()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getKeyboardNames!");
            return null;
        }
        return UITools.getKeyboardNames(this.context);
    }

    @Override
    public boolean setKeyboard(BooleanParcelable unsupported, String keyboardName) {
        logMethodEntranceExit(true, String.format("setKeyboard(%s)", keyboardName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setKeyboard(%!");
            return false;
        }
        return UITools.setKeyboard(this.context, keyboardName);
    }

    @Override
    public boolean clearClipboard(BooleanParcelable unsupported) {
        logMethodEntranceExit(true, "clearClipboard()");
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearClipboard!");
            return false;
        }
        return UITools.clearClipboard(this.context);
    }

    @Override
    public boolean enableClipboard(BooleanParcelable unsupported, boolean enable) {
        logMethodEntranceExit(true, String.format("enableClipboard(%b)", enable));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableClipboard!");
            return false;
        }
        return UITools.enableClipboard(this.context, enable);
    }

    @Override
    public ObjectParcelable invokeMethod(BooleanParcelable unsupported, ObjectParcelable obj, String methodName) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericInvokeMethod(obj, methodName, new ObjectParcelable[]{});
    }

    @Override
    public ObjectParcelable invokeMethodWithParams(BooleanParcelable unsupported, ObjectParcelable obj, String methodName, String[] classParamNames, ObjectParcelable[] params) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericInvokeMethod(obj, methodName, classParamNames, params);
    }

    @Override
    public ObjectParcelable invokeMethodStatic(BooleanParcelable unsupported, String declaringClassName, String methodName) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericInvokeMethod(declaringClassName, methodName, new ObjectParcelable[]{});
    }

    @Override
    public ObjectParcelable invokeMethodStaticWithParams(BooleanParcelable unsupported, String declaringClassName, String methodName, String[] classParamNames, ObjectParcelable[] params) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericInvokeMethod(declaringClassName, methodName, classParamNames, params);
    }

    @Override
    public ObjectParcelable getValue(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericGetFieldValue(obj, fieldName);
    }

    @Override
    public ObjectParcelable getValueStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public boolean getBoolean(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doBooleanGetFieldValue(obj, fieldName);
    }

    @Override
    public boolean getBooleanStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doBooleanGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public byte getByte(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doByteGetFieldValue(obj, fieldName);
    }

    @Override
    public byte getByteStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doByteGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public char getChar(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doCharGetFieldValue(obj, fieldName);
    }

    @Override
    public char getCharStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doCharGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public double getDouble(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doDoubleGetFieldValue(obj, fieldName);
    }

    @Override
    public double getDoubleStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doDoubleGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public float getFloat(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doFloatGetFieldValue(obj, fieldName);
    }

    @Override
    public float getFloatStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doFloatGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public int getInt(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doIntGetFieldValue(obj, fieldName);
    }

    @Override
    public int getIntStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doIntGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public long getLong(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doLongGetFieldValue(obj, fieldName);
    }

    @Override
    public long getLongStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doLongGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public int getShort(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doShortGetFieldValue(obj, fieldName);
    }

    @Override
    public int getShortStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doShortGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public String getType(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericGetFieldType(obj, fieldName);
    }

    @Override
    public String getTypeStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doGenericGetFieldType(declaringClassName, fieldName);
    }

    @Override
    public String getString(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doGetString(obj, fieldName);
    }

    @Override
    public String getStringStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName) {
        unsupported.setValue(false);
        return ReflectionManager.doGetString(declaringClassName, fieldName);
    }

    @Override
    public void setValue(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, ObjectParcelable value) {
        unsupported.setValue(false);
        ReflectionManager.doGenericSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setValueStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, ObjectParcelable value) {
        unsupported.setValue(false);
        ReflectionManager.doGenericSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setBoolean(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, boolean value) {
        unsupported.setValue(false);
        ReflectionManager.doBooleanSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setBooleanStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, boolean value) {
        unsupported.setValue(false);
        ReflectionManager.doBooleanSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setByte(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, byte value) {
        unsupported.setValue(false);
        ReflectionManager.doByteSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setByteStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, byte value) {
        unsupported.setValue(false);
        ReflectionManager.doByteSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setChar(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, char value) {
        unsupported.setValue(false);
        ReflectionManager.doCharSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setCharStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, char value) {
        unsupported.setValue(false);
        ReflectionManager.doCharSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setDouble(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, double value) {
        unsupported.setValue(false);
        ReflectionManager.doDoubleSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setDoubleStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, double value) {
        unsupported.setValue(false);
        ReflectionManager.doDoubleSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setFloat(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, float value) {
        unsupported.setValue(false);
        ReflectionManager.doFloatSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setFloatStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, float value) {
        unsupported.setValue(false);
        ReflectionManager.doFloatSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setInt(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, int value) {
        unsupported.setValue(false);
        ReflectionManager.doIntSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setIntStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, int value) {
        unsupported.setValue(false);
       ReflectionManager.doIntSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setLong(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, long value) {
        unsupported.setValue(false);
        ReflectionManager.doLongSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setLongStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, long value) {
        unsupported.setValue(false);
        ReflectionManager.doLongSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setShort(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, int value) {
        unsupported.setValue(false);
        ReflectionManager.doShortSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setShortStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, int value) {
        unsupported.setValue(false);
        ReflectionManager.doShortSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setString(BooleanParcelable unsupported, ObjectParcelable obj, String fieldName, String value) {
        unsupported.setValue(false);
        ReflectionManager.doGenericSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setStringStatic(BooleanParcelable unsupported, String declaringClassName, String fieldName, String value) {
        unsupported.setValue(false);
        ReflectionManager.doGenericSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public boolean enableDeviceAdmin(BooleanParcelable unsupported, String packageName, String className, boolean makeAdmin) {
        logMethodEntranceExit(true, String.format("enableDeviceAdmin(%s, %s, %b)", packageName, className, makeAdmin));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableDeviceAdmin!");
            return false;
        }
        return Security.enableDeviceAdmin(this.context, packageName, className, makeAdmin);
    }

    @Override
    public boolean installApk(BooleanParcelable unsupported, String apkFilename, boolean update) {
        logMethodEntranceExit(true, String.format("installApk(%s, %b)", apkFilename, update));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools installApk!");
            return false;
        }
        return AppManager.installApk(this.context, apkFilename, update);
    }

    @Override
    public boolean uninstallPackage(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("uninstallPackage(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools uninstallPackage!");
            return false;
        }
        return AppManager.uninstallPackage(this.context, packageName);
    }

    @Override
    public boolean clearDataForPackage(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("clearDataForPackage(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearDataForPackage!");
            return false;
        }
        return AppManager.clearDataForPackage(this.context, packageName);
    }

    @Override
    public boolean clearCacheForPackage(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("clearCacheForPackage(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearCacheForPackage!");
            return false;
        }
        return AppManager.clearCacheForPackage(this.context, packageName);
    }

    @Override
    public boolean enableApplication(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("enableApplication(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableApplication!");
            return false;
        }
        return AppManager.enableApplication(this.context, packageName);
    }

    @Override
    public boolean disableApplication(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("disableApplication(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools disableApplication!");
            return false;
        }
        return AppManager.disableApplication(this.context, packageName);
    }

    @Override
    public boolean enableBatteryOptimization(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("enableBatteryOptimization(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableBatteryOptimization!");
            return false;
        }
        return AppManager.enableBatteryOptimization(this.context, packageName);
    }

    @Override
    public boolean disableBatteryOptimization(BooleanParcelable unsupported, String packageName) {
        logMethodEntranceExit(true, String.format("disableBatteryOptimization(%s)", packageName));
        unsupported.setValue(false);
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools disableBatteryOptimization!");
            return false;
        }
        return AppManager.disableBatteryOptimization(this.context, packageName);
    }
}