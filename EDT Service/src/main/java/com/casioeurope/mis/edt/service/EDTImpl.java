package com.casioeurope.mis.edt.service;

import android.accounts.Account;
import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.casioeurope.mis.edt.types.APN;
import com.casioeurope.mis.edt.types.APNParcelable;
import com.casioeurope.mis.edt.IEDT;
import com.casioeurope.mis.edt.types.ObjectParcelable;
import com.casioeurope.mis.edt.types.ReadWriteFileParamsParcelable;
import com.casioeurope.mis.edt.types.WifiConfigurationParcelable;
import com.casioeurope.mis.edt.service.barcodescanner.ScanLib;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class EDTImpl extends IEDT.Stub {

    private Context context;
    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    private Handler handler = new Handler();

    private static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static String TAG = "EDT (Implementation)";

    @SuppressWarnings({"unused", "SpellCheckingInspection", "RedundantSuppression"})
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
    public boolean factoryReset(boolean removeAccounts) {
        Log.d(TAG, "factoryReset()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools factoryReset!");
            return false;
        }
        return PowerManager.factoryReset(this.context, removeAccounts);
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
        return AccManager.removeAllAccounts(this.context);
    }

    @Override
    public boolean removeAllGoogleAccounts() {
        Log.d(TAG, "removeAllGoogleAccounts()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAllGoogleAccounts!");
            return false;
        }
        return AccManager.removeAllGoogleAccounts(this.context);
    }

    @Override
    public boolean removeAccount(Account account) {
        Log.d(TAG, "removeAccount(" + account.name + "(Type: " + account.type + "))");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools removeAccount!");
            return false;
        }
        return AccManager.removeAccount(this.context, account);
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
        logMethodEntranceExit(true, String.format("copyFile(%s, %s, %s)", sourceFilePath, destinationFilePath, options == null ? "null" : options.toString()));
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

    @Override
    public String moveFile(String sourceFilePath, String destinationFilePath, List<String> options) {
        logMethodEntranceExit(true, String.format("moveFile(%s, %s, %s)", sourceFilePath, destinationFilePath, options == null ? "null" : options.toString()));
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

    @Override
    public boolean deleteFile(String filePath) {
        logMethodEntranceExit(true, "deleteFile(" + filePath + ")");
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
        return ScanLib.getCurrentScanSettings(settingsFilePath, this.context);
    }

    @Override
    public boolean setNewScanSettings(String settingsFilePath) {
        Log.d(TAG, "setNewScanSettings(" + settingsFilePath + ")");
        return ScanLib.setNewScanSettings(settingsFilePath, this.context);
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
        return Settings.Global.putInt(context.getContentResolver(), Settings.Global.DATA_ROAMING, enable ? 1 : 0);
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
    public boolean setScreenLockTimeout(int milliseconds) {
        Log.d(TAG, "setScreenLockTimeout(" + milliseconds + ")");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setScreenLockTimeout!");
            return false;
        }
        return PowerManager.setScreenLockTimeout(this.context, milliseconds);
        //return Settings.System.putInt(this.context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, milliseconds);
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
        if (wifiConfiguration == null || (wifiConfiguration.SSID == null || wifiConfiguration.SSID.isEmpty()) && wifiConfiguration.networkId < 1)
            return false;
        Log.d(TAG, "updateNetwork()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools updateNetwork!");
            return false;
        }
        return Wifi.updateNetwork(wifiConfiguration, this.context);
    }

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
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

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
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

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
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

    @Override
    public boolean createNewApn(APNParcelable apn, boolean setAsDefault) {
        Log.d(TAG, String.format("createNewApn(%s, %b)", apn.getName(), setAsDefault));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools createNewApn!");
            return false;
        }
        return APNTools.createNewApn(this.context, apn, setAsDefault) != APN.INVALID_APN;
    }

    @Override
    public boolean updateApn(APNParcelable apn) {
        Log.d(TAG, String.format("updateApn(%s)", apn.getName()));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools updateApn!");
            return false;
        }
        return APNTools.updateApn(this.context, apn);
    }

    @Override
    public boolean verifyApn(String name) {
        Log.d(TAG, String.format("verifyApn(%s)", name));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools verifyApn!");
            return false;
        }
        return APNTools.verifyApn(this.context, name);
    }

    @Override
    public int getApnId(String name) {
        Log.d(TAG, String.format("getApnId(%s)", name));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getApnId!");
            return APN.INVALID_APN;
        }
        return APNTools.getApnId(this.context, name);
    }

    @Override
    public APNParcelable getApn(String name) {
        Log.d(TAG, String.format("getApn(%s)", name));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getApn!");
            return null;
        }
        return APNTools.getApn(this.context, name);
    }

    @Override
    public boolean setPreferredApn(String name) {
        Log.d(TAG, String.format("setPreferredApn(%s)", name));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setPreferredApn!");
            return false;
        }
        return APNTools.setPreferredApn(this.context, name);
    }

    @Override
    public APNParcelable[] getAllApnList() {
        Log.d(TAG, "getAllApnList()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getAllApnList!");
            return null;
        }
        return APNTools.getAllApnList(this.context);
    }

    @Override
    public Account[] getGoogleAccounts() {
        Log.d(TAG, "getGoogleAccounts()");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getGoogleAccounts!");
            return null;
        }
        return AccManager.getGoogleAccounts(this.context);
    }

    @Override
    public boolean initializeKeyStore(String storeName, String password) {
        Log.d(TAG, String.format("initializeKeyStore(%s, %s)", storeName, password));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools initializeKeyStore!");
            return false;
        }
        return CertificateManager.initializeKeyStore(storeName, password);
    }

    @Override
    public boolean installCACertificate(String friendlyName, String fileName) {
        Log.d(TAG, String.format("installCACertificate(%s, %s)", friendlyName, fileName));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools installCACertificate!");
            return false;
        }
        return CertificateManager.installCACertificate(friendlyName, fileName, this.context);
    }

    @Override
    public boolean mountSDCard(boolean mount) {
        Log.d(TAG, String.format("mountSDCard(%b)", mount));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools mountSDCard!");
            return false;
        }
        return FileTools.mountSDCard(this.context, mount);
    }

    @Override
    public boolean enableAdb(boolean enable) {
        Log.d(TAG, String.format("enableAdb(%b)", enable));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableAdb!");
            return false;
        }
        return USBTools.enableAdb(this.context, enable);
    }

    @Override
    public boolean enableMassStorage(boolean enable) {
        Log.d(TAG, String.format("enableMassStorage(%b)", enable));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableMassStorage!");
            return false;
        }
        return USBTools.enableMassStorage(this.context, enable);
    }

    @Override
    public List<String> getKeyboardNames() {
        Log.d(TAG, "getKeyboardNames");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools getKeyboardNames!");
            return null;
        }
        return UITools.getKeyboardNames(this.context);
    }

    @Override
    public boolean setKeyboard(String keyboardName) {
        Log.d(TAG, String.format("setKeyboard(%s)", keyboardName));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools setKeyboard(%!");
            return false;
        }
        return UITools.setKeyboard(this.context, keyboardName);
    }

    @Override
    public boolean clearClipboard() {
        Log.d(TAG, "clearClipboard");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearClipboard!");
            return false;
        }
        return UITools.clearClipboard(this.context);
    }

    @Override
    public boolean enableClipboard(boolean enable) {
        Log.d(TAG, String.format("enableClipboard(%b)", enable));
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableClipboard!");
            return false;
        }
        return UITools.enableClipboard(this.context, enable);
    }

    @Override
    public ObjectParcelable invokeMethod(ObjectParcelable obj, String methodName) {
        return ReflectionManager.doGenericInvokeMethod(obj, methodName, new ObjectParcelable[]{});
    }

    @Override
    public ObjectParcelable invokeMethodWithParams(ObjectParcelable obj, String methodName, String[] classParamNames, ObjectParcelable[] params) {
        return ReflectionManager.doGenericInvokeMethod(obj, methodName, classParamNames, params);
    }

    @Override
    public ObjectParcelable invokeMethodStatic(String declaringClassName, String methodName) {
        return ReflectionManager.doGenericInvokeMethod(declaringClassName, methodName, new ObjectParcelable[]{});
    }

    @Override
    public ObjectParcelable invokeMethodStaticWithParams(String declaringClassName, String methodName, String[] classParamNames, ObjectParcelable[] params) {
        return ReflectionManager.doGenericInvokeMethod(declaringClassName, methodName, classParamNames, params);
    }

    @Override
    public ObjectParcelable getValue(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doGenericGetFieldValue(obj, fieldName);
    }

    @Override
    public ObjectParcelable getValueStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doGenericGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public boolean getBoolean(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doBooleanGetFieldValue(obj, fieldName);
    }

    @Override
    public boolean getBooleanStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doBooleanGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public byte getByte(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doByteGetFieldValue(obj, fieldName);
    }

    @Override
    public byte getByteStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doByteGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public char getChar(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doCharGetFieldValue(obj, fieldName);
    }

    @Override
    public char getCharStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doCharGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public double getDouble(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doDoubleGetFieldValue(obj, fieldName);
    }

    @Override
    public double getDoubleStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doDoubleGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public float getFloat(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doFloatGetFieldValue(obj, fieldName);
    }

    @Override
    public float getFloatStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doFloatGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public int getInt(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doIntGetFieldValue(obj, fieldName);
    }

    @Override
    public int getIntStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doIntGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public long getLong(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doLongGetFieldValue(obj, fieldName);
    }

    @Override
    public long getLongStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doLongGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public int getShort(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doShortGetFieldValue(obj, fieldName);
    }

    @Override
    public int getShortStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doShortGetFieldValue(declaringClassName, fieldName);
    }

    @Override
    public String getType(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doGenericGetFieldType(obj, fieldName);
    }

    @Override
    public String getTypeStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doGenericGetFieldType(declaringClassName, fieldName);
    }

    @Override
    public String getString(ObjectParcelable obj, String fieldName) {
        return ReflectionManager.doGetString(obj, fieldName);
    }

    @Override
    public String getStringStatic(String declaringClassName, String fieldName) {
        return ReflectionManager.doGetString(declaringClassName, fieldName);
    }

    @Override
    public void setValue(ObjectParcelable obj, String fieldName, ObjectParcelable value) {
        ReflectionManager.doGenericSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setValueStatic(String declaringClassName, String fieldName, ObjectParcelable value) {
        ReflectionManager.doGenericSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setBoolean(ObjectParcelable obj, String fieldName, boolean value) {
        ReflectionManager.doBooleanSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setBooleanStatic(String declaringClassName, String fieldName, boolean value) {
        ReflectionManager.doBooleanSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setByte(ObjectParcelable obj, String fieldName, byte value) {
        ReflectionManager.doByteSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setByteStatic(String declaringClassName, String fieldName, byte value) {
        ReflectionManager.doByteSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setChar(ObjectParcelable obj, String fieldName, char value) {
        ReflectionManager.doCharSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setCharStatic(String declaringClassName, String fieldName, char value) {
        ReflectionManager.doCharSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setDouble(ObjectParcelable obj, String fieldName, double value) {
        ReflectionManager.doDoubleSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setDoubleStatic(String declaringClassName, String fieldName, double value) {
        ReflectionManager.doDoubleSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setFloat(ObjectParcelable obj, String fieldName, float value) {
        ReflectionManager.doFloatSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setFloatStatic(String declaringClassName, String fieldName, float value) {
        ReflectionManager.doFloatSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setInt(ObjectParcelable obj, String fieldName, int value) {
        ReflectionManager.doIntSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setIntStatic(String declaringClassName, String fieldName, int value) {
        ReflectionManager.doIntSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setLong(ObjectParcelable obj, String fieldName, long value) {
        ReflectionManager.doLongSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setLongStatic(String declaringClassName, String fieldName, long value) {
        ReflectionManager.doLongSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setShort(ObjectParcelable obj, String fieldName, int value) {
        ReflectionManager.doShortSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setShortStatic(String declaringClassName, String fieldName, int value) {
        ReflectionManager.doShortSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public void setString(ObjectParcelable obj, String fieldName, String value) {
        ReflectionManager.doGenericSetFieldValue(obj, fieldName, value);
    }

    @Override
    public void setStringStatic(String declaringClassName, String fieldName, String value) {
        ReflectionManager.doGenericSetFieldValue(declaringClassName, fieldName, value);
    }

    @Override
    public boolean enableDeviceAdmin(String packageName, String className, boolean makeAdmin) {
        Log.d(TAG, "enableDeviceAdmin");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableDeviceAdmin!");
            return false;
        }
        return Security.enableDeviceAdmin(this.context, packageName, className, makeAdmin);
    }

    @Override
    public boolean installApk(String apkFilename, boolean update) {
        Log.d(TAG, "installApk");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools installApk!");
            return false;
        }
        return AppManager.installApk(this.context, apkFilename, update);
    }

    @Override
    public boolean uninstallPackage(String packageName) {
        Log.d(TAG, "uninstallPackage");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools uninstallPackage!");
            return false;
        }
        return AppManager.uninstallPackage(this.context, packageName);
    }

    @Override
    public boolean clearDataForPackage(String packageName) {
        Log.d(TAG, "clearDataForPackage");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearDataForPackage!");
            return false;
        }
        return AppManager.clearDataForPackage(this.context, packageName);
    }

    @Override
    public boolean clearCacheForPackage(String packageName) {
        Log.d(TAG, "clearCacheForPackage");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools clearCacheForPackage!");
            return false;
        }
        return AppManager.clearCacheForPackage(this.context, packageName);
    }

    @Override
    public boolean enableApplication(String packageName) {
        Log.d(TAG, "enableApplication");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableApplication!");
            return false;
        }
        return AppManager.enableApplication(this.context, packageName);
    }

    @Override
    public boolean disableApplication(String packageName) {
        Log.d(TAG, "disableApplication");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools disableApplication!");
            return false;
        }
        return AppManager.disableApplication(this.context, packageName);
    }

    @Override
    public boolean enableBatteryOptimization(String packageName) {
        Log.d(TAG, "enableBatteryOptimization");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools enableBatteryOptimization!");
            return false;
        }
        return AppManager.enableBatteryOptimization(this.context, packageName);
    }

    @Override
    public boolean disableBatteryOptimization(String packageName) {
        Log.d(TAG, "disableBatteryOptimization");
        if (this.context == null) {
            Log.d(TAG, "No Context specified for EDT Tools disableBatteryOptimization!");
            return false;
        }
        return AppManager.disableBatteryOptimization(this.context, packageName);
    }
}