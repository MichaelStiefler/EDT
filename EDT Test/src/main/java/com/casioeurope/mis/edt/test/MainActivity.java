package com.casioeurope.mis.edt.test;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.casioeurope.mis.edt.APN;
import com.casioeurope.mis.edt.EDTLib;
import com.casioeurope.mis.edt.ReadWriteFileParams;
import com.casioeurope.mis.edt.test.databinding.ActivityMainBinding;

import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {

    private ActivityMainBinding activityMainBinding;

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static String TAG = "EDT (Test MainActivity)";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logMethodEntranceExit(true);
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        logMethodEntranceExit(false);

        //com.casioeurope.mis.edt.

//        StringBuilder sb = new StringBuilder(" \n");
//        final Method[] methods = getPackageManager().getClass().getDeclaredMethods();
//        for (final Method method : methods) {
//            sb.append(method.getName());
//            sb.append("\n");
//            if (sb.length() > 1000) {
//                Log.v(TAG, sb.toString());
//                sb = new StringBuilder();
//            }
//        }
    }

    @SuppressLint("SdCardPath")
    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    @Override
    public void onClick(View v) {
        logMethodEntranceExit(true);
        try {
            if (v == activityMainBinding.buttonReboot) {
                Log.d(TAG, "Calling Reboot from Service!");
                String result = String.format("Reboot Result = %b", EDTLib.reboot());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRebootFactory) {
                Log.d(TAG, "Calling Factory Reset from Service!");
                String result = String.format("Factory Reset Result = %b", EDTLib.factoryReset(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRebootRecovery) {
                Log.d(TAG, "Calling Recovery Reboot from Service!");
                String result = String.format("Recovery Reboot Result = %b", EDTLib.recovery());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonShutdown) {
                Log.d(TAG, "Calling Shutdown from Service!");
                String result = String.format("Shutdown Result = %b", EDTLib.shutdown());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonClearPassword) {
                Log.d(TAG, "Calling Clear Password from Service!");
                String result = String.format("Clear Password Result = %b", EDTLib.clearPassword());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonResetPassword) {
                Log.d(TAG, "Calling Reset Password from Service!");
                String result = String.format("Reset Password Result = %b", EDTLib.resetPassword("1234"));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonLockDevice) {
                Log.d(TAG, "Calling Lock Device from Service!");
                String result = String.format("Lock Device Result = %b", EDTLib.lockDevice());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonAllowUnknownSources) {
                Log.d(TAG, "Calling Allow Unknown Sources(true) from Service!");
                String result = String.format("Allow Unknown Sources(true) Result = %b", EDTLib.allowUnknownSources(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisallowUnknownSources) {
                Log.d(TAG, "Calling Allow Unknown Sources(false) from Service!");
                String result = String.format("Allow Unknown Sources(false) Result = %b", EDTLib.allowUnknownSources(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonSetDateTime) {
                Log.d(TAG, "Calling Set Date/Time from Service!");
                String result = String.format("Date/Time Result = %b", EDTLib.setDateTime(new Date()));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonSetTimeZone) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) { // requires Android N or later
                    EDTLib.testMessage("setting the Time Zone requires Android N (7) or later!");
                } else {
                    Log.d(TAG, "Calling Set TimeZone from Service!");
                    String result = String.format("Set TimeZone Result = %b", EDTLib.setTimeZone(TimeZone.getTimeZone("Europe/Berlin")));
                    EDTLib.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.buttonRemoveAllGoogleAccounts) {
                Log.d(TAG, "Calling Remove all Google Accounts from Service!");
                String result = String.format("Remove all Google Accounts Result = %b", EDTLib.removeAllGoogleAccounts());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRemoveAllAccounts) {
                Log.d(TAG, "Calling Remove all Accounts from Service!");
                String result = String.format("Remove all Accounts Result = %b", EDTLib.removeAllAccounts());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableWifi) {
                Log.d(TAG, "Calling Enable Wifi from Service!");
                String result = String.format("Enable Wifi Result = %b", EDTLib.enableWifi(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableWifi) {
                Log.d(TAG, "Calling Disable Wifi from Service!");
                String result = String.format("Disable Wifi Result = %b", EDTLib.enableWifi(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonAddWifi) {
                Log.d(TAG, "Calling Add Wifi Account from Service!");
                //               WifiConfigurationParcelable conf = new WifiConfigurationParcelable();
                android.net.wifi.WifiConfiguration conf = new android.net.wifi.WifiConfiguration();
                conf.SSID = "\"MIS Test\"";
                conf.hiddenSSID = true;
                //noinspection SpellCheckingInspection
                conf.preSharedKey = "\"UmfegUmfe\"";
                //String result = String.format("Add Wifi AccountResult = %b", edtToolsService.addNetwork(conf));
                boolean retVal = EDTLib.addNetwork(conf);
                if (retVal) retVal = EDTLib.connectNetwork("MIS Test");
                String result = String.format("Add Wifi AccountResult = %b", retVal);
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRemoveWifi) {
                Log.d(TAG, "Calling Remove Wifi Account from Service!");
                String result = String.format("Remove Wifi Account Result = %b", EDTLib.removeNetwork("MIS Test"));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableBluetooth) {
                Log.d(TAG, "Calling Enable Bluetooth from Service!");
                String result = String.format("Enable Bluetooth Result = %b", EDTLib.enableBluetooth(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableBluetooth) {
                Log.d(TAG, "Calling Disable Bluetooth from Service!");
                String result = String.format("Disable Bluetooth Result = %b", EDTLib.enableBluetooth(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableNfc) {
                Log.d(TAG, "Calling Enable NFC from Service!");
                String result = String.format("Enable NFC Result = %b", EDTLib.enableNfc(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableNfc) {
                Log.d(TAG, "Calling Disable NFC from Service!");
                String result = String.format("Disable NFC Result = %b", EDTLib.enableNfc(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableGps) {
                Log.d(TAG, "Calling Enable GPS from Service!");
                String result = String.format("Enable GPS Result = %b", EDTLib.enableGps(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableGps) {
                Log.d(TAG, "Calling Disable GPS from Service!");
                String result = String.format("Disable GPS Result = %b", EDTLib.enableGps(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableWwan) {
                Log.d(TAG, "Calling Enable WWAN from Service!");
                String result = String.format("Enable WWAN Result = %b", EDTLib.enableWwan(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableWwan) {
                Log.d(TAG, "Calling Disable WWAN from Service!");
                String result = String.format("Disable WWAN Result = %b", EDTLib.enableWwan(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableDeveloperMode) {
                Log.d(TAG, "Calling Enable Developer Mode from Service!");
                String result = String.format("Enable Developer Mode Result = %b", EDTLib.enableDeveloperMode(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableDeveloperMode) {
                Log.d(TAG, "Calling Disable Developer Mode from Service!");
                String result = String.format("Disable Developer Mode Result = %b", EDTLib.enableDeveloperMode(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonCopyFile) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                    EDTLib.testMessage("copying Files requires Android O (8) or later!");
                } else {
                    Log.d(TAG, "Calling Copy File from Service!");
                    String result = String.format("Copy File Result = %b", EDTLib.copyFile(Paths.get("/sdcard/Download/devinfo.html"), Paths.get("/sdcard/Download/devinfo1.html"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS));
                    EDTLib.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.buttonMoveFile) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                    EDTLib.testMessage("moving Files requires Android O (8) or later!");
                } else {
                    Log.d(TAG, "Calling Move File from Service!");
                    String result = String.format("Move File Result = %b", EDTLib.moveFile(Paths.get("/sdcard/Download/devinfo1.html"), Paths.get("/sdcard/Download/devinfo2.html")));
                    EDTLib.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.buttonReadFile) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                    EDTLib.testMessage("reading Files requires Android O (8) or later!");
                } else {
                    Log.d(TAG, "Calling Read File from Service!");
                    byte[] testData = new byte[4096];
                    ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.setPath(Paths.get("/sdcard/Download/devinfo.html")).setData(testData).setFileOffset(1).setDataOffset(2).setLength(100).setOptions(StandardOpenOption.READ).build();
                    String result = String.format("Read File Result = %b", EDTLib.readFile(readWriteFileParams));
                    EDTLib.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.getApnList) {
                Log.d(TAG, "Calling Get All APN List from Service!");
                APN[] apnArray = EDTLib.getAllApnList();
                @SuppressLint("DefaultLocale") String result = String.format("Get All APN List Result = %d", apnArray == null ? -1 : apnArray.length);
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.getGoogleAccounts) {
                Log.d(TAG, "Calling Get Google Accounts from Service!");
                Account[] accounts = EDTLib.getGoogleAccounts();
                @SuppressLint("DefaultLocale") String result = String.format("Get Google Accounts Result = %d", accounts == null ? -1 : accounts.length);
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.removeGoogleAccounts) {
                Log.d(TAG, "Calling Remove Google Accounts from Service!");
                String result = String.format("Remove Google Accounts Result = %b", EDTLib.removeAllAccounts());
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.installCertificate) {
                Log.d(TAG, "Calling Install CA Certificate from Service!");
                String result = String.format("Install testca.cer Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/testca.cer"));
                result += "\n" + String.format("Install admin.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/admin.der"));
                result += "\n" + String.format("Install admin.pem Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/admin.pem"));
                result += "\n" + String.format("Install admin.pfx Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/admin.pfx"));
                result += "\n" + String.format("Install mistest.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.der"));
                result += "\n" + String.format("Install mistest.pem Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.pem"));
                result += "\n" + String.format("Install mistest.pfx Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.pfx"));
                result += "\n" + String.format("Install zeroshell.example.com.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/zeroshell.example.com.der"));
                result += "\n" + String.format("Install zeroshell.example.com.pem Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/zeroshell.example.com.pem"));
                result += "\n" + String.format("Install zeroshell.example.com.pfx Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/zeroshell.example.com.pfx"));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.mountSdCard) {
                Log.d(TAG, "Calling Mount SD Card from Service!");
                String result = String.format("Mount SD Card Result = %b", EDTLib.mountSDCard(true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.unmountSdCard) {
                Log.d(TAG, "Calling Unmount SD Card from Service!");
                String result = String.format("Unmount SD Card Result = %b", EDTLib.mountSDCard(false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.screenLockTimeout) {
                Log.d(TAG, "Calling Set Screen Lock Timeout from Service!");
                String result = String.format("Set Screen Lock Timeout Result = %b", EDTLib.setScreenLockTimeout(Integer.MAX_VALUE));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonInstallApp) {
                Log.d(TAG, "Calling Install App from Service!");
                String result = String.format("Install App Result = %b", EDTLib.installApk("/sdcard/OSUpdateService.apk", false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonUninstallApp) {
                Log.d(TAG, "Calling Uninstall App from Service!");
                String result = String.format("Uninstall App Result = %b", EDTLib.uninstallPackage("jp.casio.ht.osupdateservice", false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonClearData) {
                Log.d(TAG, "Calling Clear Data from Service!");
                String result = String.format("Clear Data Result = %b", EDTLib.clearDataForPackage("com.android.chrome"));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonClearCache) {
                Log.d(TAG, "Calling Clear Cache from Service!");
                String result = String.format("Clear Cache Result = %b", EDTLib.clearCacheForPackage("com.android.chrome"));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableApp) {
                Log.d(TAG, "Calling Enable App from Service!");
                String result = String.format("Enable App Result = %b", EDTLib.enableApplication("com.android.chrome", true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableApp) {
                Log.d(TAG, "Calling Disable App from Service!");
                String result = String.format("Disable App Result = %b", EDTLib.enableApplication("com.android.chrome", false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableDoze) {
                Log.d(TAG, "Calling Enable Doze Mode from Service!");
                String result = String.format("Enable Doze Mode Result = %b", EDTLib.enableBatteryOptimization("com.android.chrome", true));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableDoze) {
                Log.d(TAG, "Calling Disable Doze Mode from Service!");
                String result = String.format("Disable Doze Mode Result = %b", EDTLib.enableBatteryOptimization("com.android.chrome", false));
                EDTLib.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonTestMessage) {
                Log.d(TAG, "Calling Test Message from Service!");
                Log.d(TAG, String.format("Test Message Result = %b", EDTLib.testMessage("EDT Tools Test Message!")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
    }
}
