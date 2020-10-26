package com.casioeurope.mis.edt.test;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.casioeurope.mis.edt.EDTLibrary;
import com.casioeurope.mis.edt.SystemLibrary;
import com.casioeurope.mis.edt.type.APN;
import com.casioeurope.mis.edt.type.ReadWriteFileParams;
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
                String result = String.format("Reboot Result = %b", EDTLibrary.reboot());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRebootFactory) {
                Log.d(TAG, "Calling Factory Reset from Service!");
                String result = String.format("Factory Reset Result = %b", EDTLibrary.factoryReset(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRebootRecovery) {
                Log.d(TAG, "Calling Recovery Reboot from Service!");
                String result = String.format("Recovery Reboot Result = %b", EDTLibrary.recovery());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonShutdown) {
                Log.d(TAG, "Calling Shutdown from Service!");
                String result = String.format("Shutdown Result = %b", EDTLibrary.shutdown());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonClearPassword) {
                Log.d(TAG, "Calling Clear Password from Service!");
                String result = String.format("Clear Password Result = %b", EDTLibrary.clearPassword());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonResetPassword) {
                Log.d(TAG, "Calling Reset Password from Service!");
                String result = String.format("Reset Password Result = %b", EDTLibrary.resetPassword("1234"));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonLockDevice) {
                Log.d(TAG, "Calling Lock Device from Service!");
                String result = String.format("Lock Device Result = %b", EDTLibrary.lockDevice());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonAllowUnknownSources) {
                Log.d(TAG, "Calling Allow Unknown Sources(true) from Service!");
                String result = String.format("Allow Unknown Sources(true) Result = %b", EDTLibrary.allowUnknownSources(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisallowUnknownSources) {
                Log.d(TAG, "Calling Allow Unknown Sources(false) from Service!");
                String result = String.format("Allow Unknown Sources(false) Result = %b", EDTLibrary.allowUnknownSources(false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonSetDateTime) {
                Log.d(TAG, "Calling Set Date/Time from Service!");
                String result = String.format("Date/Time Result = %b", EDTLibrary.setDateTime(new Date()));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonSetTimeZone) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) { // requires Android N or later
                    EDTLibrary.testMessage("setting the Time Zone requires Android N (7) or later!");
                } else {
                    Log.d(TAG, "Calling Set TimeZone from Service!");
                    String result = String.format("Set TimeZone Result = %b", EDTLibrary.setTimeZone(TimeZone.getTimeZone("Europe/Berlin")));
                    EDTLibrary.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.buttonRemoveAllGoogleAccounts) {
                Log.d(TAG, "Calling Remove all Google Accounts from Service!");
                String result = String.format("Remove all Google Accounts Result = %b", EDTLibrary.removeAllGoogleAccounts());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRemoveAllAccounts) {
                Log.d(TAG, "Calling Remove all Accounts from Service!");
                String result = String.format("Remove all Accounts Result = %b", EDTLibrary.removeAllAccounts());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableWifi) {
                Log.d(TAG, "Calling Enable Wifi from Service!");
                String result = String.format("Enable Wifi Result = %b", EDTLibrary.enableWifi(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableWifi) {
                Log.d(TAG, "Calling Disable Wifi from Service!");
                String result = String.format("Disable Wifi Result = %b", EDTLibrary.enableWifi(false));
                EDTLibrary.testMessage(result);
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
                boolean retVal = EDTLibrary.addNetwork(conf);
                if (retVal) retVal = EDTLibrary.connectNetwork("MIS Test");
                String result = String.format("Add Wifi AccountResult = %b", retVal);
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonRemoveWifi) {
                Log.d(TAG, "Calling Remove Wifi Account from Service!");
                String result = String.format("Remove Wifi Account Result = %b", EDTLibrary.removeNetwork("MIS Test"));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableBluetooth) {
                Log.d(TAG, "Calling Enable Bluetooth from Service!");
                String result = String.format("Enable Bluetooth Result = %b", EDTLibrary.enableBluetooth(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableBluetooth) {
                Log.d(TAG, "Calling Disable Bluetooth from Service!");
                String result = String.format("Disable Bluetooth Result = %b", EDTLibrary.enableBluetooth(false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableNfc) {
                Log.d(TAG, "Calling Enable NFC from Service!");
                String result = String.format("Enable NFC Result = %b", EDTLibrary.enableNfc(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableNfc) {
                Log.d(TAG, "Calling Disable NFC from Service!");
                String result = String.format("Disable NFC Result = %b", EDTLibrary.enableNfc(false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableGps) {
                Log.d(TAG, "Calling Enable GPS from Service!");
                String result = String.format("Enable GPS Result = %b", EDTLibrary.enableGps(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableGps) {
                Log.d(TAG, "Calling Disable GPS from Service!");
                String result = String.format("Disable GPS Result = %b", EDTLibrary.enableGps(false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableWwan) {
                Log.d(TAG, "Calling Enable WWAN from Service!");
                String result = String.format("Enable WWAN Result = %b", EDTLibrary.enableWwan(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableWwan) {
                Log.d(TAG, "Calling Disable WWAN from Service!");
                String result = String.format("Disable WWAN Result = %b", EDTLibrary.enableWwan(false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableDeveloperMode) {
                Log.d(TAG, "Calling Enable Developer Mode from Service!");
                String result = String.format("Enable Developer Mode Result = %b", EDTLibrary.enableDeveloperMode(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableDeveloperMode) {
                Log.d(TAG, "Calling Disable Developer Mode from Service!");
                String result = String.format("Disable Developer Mode Result = %b", EDTLibrary.enableDeveloperMode(false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonCopyFile) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                    EDTLibrary.testMessage("copying Files requires Android O (8) or later!");
                } else {
                    Log.d(TAG, "Calling Copy File from Service!");
                    String result = String.format("Copy File Result = %b", EDTLibrary.copyFile(Paths.get("/sdcard/Download/devinfo.html"), Paths.get("/sdcard/Download/devinfo1.html"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS));
                    EDTLibrary.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.buttonMoveFile) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                    EDTLibrary.testMessage("moving Files requires Android O (8) or later!");
                } else {
                    Log.d(TAG, "Calling Move File from Service!");
                    String result = String.format("Move File Result = %b", EDTLibrary.moveFile(Paths.get("/sdcard/Download/devinfo1.html"), Paths.get("/sdcard/Download/devinfo2.html")));
                    EDTLibrary.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.buttonReadFile) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                    EDTLibrary.testMessage("reading Files requires Android O (8) or later!");
                } else {
                    Log.d(TAG, "Calling Read File from Service!");
                    byte[] testData = new byte[4096];
                    ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.setPath(Paths.get("/sdcard/Download/devinfo.html")).setData(testData).setFileOffset(1).setDataOffset(2).setLength(100).setOptions(StandardOpenOption.READ).build();
                    String result = String.format("Read File Result = %b", EDTLibrary.readFile(readWriteFileParams));
                    EDTLibrary.testMessage(result);
                    Log.d(TAG, result);
                }
            } else if (v == activityMainBinding.getApnList) {
                Log.d(TAG, "Calling Get All APN List from Service!");
                APN[] apnArray = EDTLibrary.getAllApnList();
                @SuppressLint("DefaultLocale") String result = String.format("Get All APN List Result = %d", apnArray == null ? -1 : apnArray.length);
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.getGoogleAccounts) {
                Log.d(TAG, "Calling Get Google Accounts from Service!");
                Account[] accounts = EDTLibrary.getGoogleAccounts();
                @SuppressLint("DefaultLocale") String result = String.format("Get Google Accounts Result = %d", accounts == null ? -1 : accounts.length);
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.removeGoogleAccounts) {
                Log.d(TAG, "Calling Remove Google Accounts from Service!");
                String result = String.format("Remove Google Accounts Result = %b", EDTLibrary.removeAllAccounts());
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.installCertificate) {
                Log.d(TAG, "Calling Install CA Certificate from Service!");
                String result = String.format("Install casio-europe.pem Result = %b", EDTLibrary.installCertificate("TEST CA", "/sdcard/certs/casio-europe.pem"));
//                result += "\n" + String.format("Install admin.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.p7b"));
//                result += "\n" + String.format("Install admin.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.crt"));
//                String result = String.format("Install testca.cer Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/testca.cer"));
//                result += "\n" + String.format("Install admin.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/admin.der"));
//                result += "\n" + String.format("Install admin.pem Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/admin.pem"));
//                result += "\n" + String.format("Install admin.pfx Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/admin.pfx"));
//                result += "\n" + String.format("Install mistest.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.der"));
//                result += "\n" + String.format("Install mistest.pem Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.pem"));
//                result += "\n" + String.format("Install mistest.pfx Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/mistest.pfx"));
//                result += "\n" + String.format("Install zeroshell.example.com.der Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/zeroshell.example.com.der"));
//                result += "\n" + String.format("Install zeroshell.example.com.pem Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/zeroshell.example.com.pem"));
//                result += "\n" + String.format("Install zeroshell.example.com.pfx Result = %b", EDTLib.installCACertificate("TEST CA", "/sdcard/certs/zeroshell.example.com.pfx"));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.mountSdCard) {
                Log.d(TAG, "Calling Mount SD Card from Service!");
                String result = String.format("Mount SD Card Result = %b", EDTLibrary.mountSDCard(true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.unmountSdCard) {
                Log.d(TAG, "Calling Unmount SD Card from Service!");
                String result = String.format("Unmount SD Card Result = %b", EDTLibrary.mountSDCard(false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.screenLockTimeout) {
                Log.d(TAG, "Calling Set Screen Lock Timeout from Service!");
                String result = String.format("Set Screen Lock Timeout Result = %b", EDTLibrary.setScreenLockTimeout(Integer.MAX_VALUE));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonInstallApp) {
                Log.d(TAG, "Calling Install App from Service!");
//                String result = String.format("Install App Result = %b", EDTLib.installApk("/sdcard/OSUpdateService.apk", false));
               // String result = String.format("Install App Result = %b", EDTLib.installApk("/sdcard/ScanDemo_ET-L10.apk", false));
                String result = String.format("Install App Result = %b", EDTLibrary.installApk("/sdcard/NFC Tools PRO 8.0.apk", false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonUninstallApp) {
                Log.d(TAG, "Calling Uninstall App from Service!");
//                String result = String.format("Uninstall App Result = %b", EDTLib.uninstallPackage("casio.de.scandemo_et_l10", false));
                String result = String.format("Uninstall App Result = %b", EDTLibrary.uninstallPackage("com.wakdev.nfctools.pro"));
                Runtime.getRuntime().exec("pm uninstall 'casio.de.scandemo_et_l10'");
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonClearData) {
                Log.d(TAG, "Calling Clear Data from Service!");
                String result = String.format("Clear Data Result = %b", EDTLibrary.clearDataForPackage("com.android.chrome"));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonClearCache) {
                Log.d(TAG, "Calling Clear Cache from Service!");
                String result = String.format("Clear Cache Result = %b", EDTLibrary.clearCacheForPackage("com.android.chrome"));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableApp) {
                Log.d(TAG, "Calling Enable App from Service!");
                String result = String.format("Enable App Result = %b", EDTLibrary.enableApplication("com.android.chrome", true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableApp) {
                Log.d(TAG, "Calling Disable App from Service!");
                String result = String.format("Disable App Result = %b", EDTLibrary.enableApplication("com.android.chrome", false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonEnableDoze) {
                Log.d(TAG, "Calling Enable Doze Mode from Service!");
                String result = String.format("Enable Doze Mode Result = %b", EDTLibrary.enableBatteryOptimization("com.android.chrome", true));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonDisableDoze) {
                Log.d(TAG, "Calling Disable Doze Mode from Service!");
                String result = String.format("Disable Doze Mode Result = %b", EDTLibrary.enableBatteryOptimization("com.android.chrome", false));
                EDTLibrary.testMessage(result);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonShowSerialNumber) {
                Log.d(TAG, "Calling getCASIOSerial from System Library!");
                String result = String.format("Serial Number = %s\r\nModel Name=%s", SystemLibrary.getCASIOSerial(), SystemLibrary.getModelName());
                EDTLibrary.testMessage(result);
                SystemLibrary.setNavigationBarState(true);
                Log.d(TAG, result);
            } else if (v == activityMainBinding.buttonTestMessage) {
                Log.d(TAG, "Calling Test Message from Service!");
                Log.d(TAG, String.format("Test Message Result = %b", EDTLibrary.testMessage("EDT Tools Test Message!")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
    }
}
