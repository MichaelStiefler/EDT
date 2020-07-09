package com.casioeurope.mis.edt.test;

import android.app.Activity;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.casioeurope.mis.edt.EDTLib;
import com.casioeurope.mis.edt.ReadWriteFileParams;
import com.casioeurope.mis.edt.test.databinding.ActivityMainBinding;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {

    private ActivityMainBinding activityMainBinding;

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = false;
    private static String TAG = "EDT_TOOLS (Test MainActivity)";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onClick(View v) {
        try {
            if (v==activityMainBinding.buttonReboot) {
                Log.d(TAG, "Calling Reboot from Service!");
                Log.d(TAG, String.format("Reboot Result = %b", EDTLib.reboot()));
            } else if (v==activityMainBinding.buttonRebootFactory) {
                Log.d(TAG, "Calling Factory Reset from Service!");
                Log.d(TAG, String.format("Factory Reset Result = %b", EDTLib.factoryReset()));
            } else if (v==activityMainBinding.buttonRebootRecovery) {
                Log.d(TAG, "Calling Recovery Reboot from Service!");
                Log.d(TAG, String.format("Recovery Reboot Result = %b", EDTLib.recovery()));
            } else if (v==activityMainBinding.buttonShutdown) {
                Log.d(TAG, "Calling Shutdown from Service!");
                Log.d(TAG, String.format("Shutdown Result = %b", EDTLib.shutdown()));
            } else if (v==activityMainBinding.buttonClearPassword) {
                Log.d(TAG, "Calling Clear Password from Service!");
                Log.d(TAG, String.format("Clear Password Result = %b", EDTLib.clearPassword()));
            } else if (v==activityMainBinding.buttonResetPassword) {
                Log.d(TAG, "Calling Reset Password from Service!");
                Log.d(TAG, String.format("Reset Password Result = %b", EDTLib.resetPassword("1234")));
            } else if (v==activityMainBinding.buttonLockDevice) {
                Log.d(TAG, "Calling Lock Device from Service!");
                Log.d(TAG, String.format("Lock Device Result = %b", EDTLib.lockDevice()));
            } else if (v==activityMainBinding.buttonAllowUnknownSources) {
                Log.d(TAG, "Calling Allow Unknown Sources(true) from Service!");
                Log.d(TAG, String.format("Allow Unknown Sources(true) Result = %b", EDTLib.allowUnknownSources(true)));
            } else if (v==activityMainBinding.buttonDisallowUnknownSources) {
                Log.d(TAG, "Calling Allow Unknown Sources(false) from Service!");
                Log.d(TAG, String.format("Allow Unknown Sources(false) Result = %b", EDTLib.allowUnknownSources(false)));
            } else if (v==activityMainBinding.buttonSetDateTime) {
                Calendar c = Calendar.getInstance();
                Log.d(TAG, "Calling Set Date/Time from Service!");
                Log.d(TAG, String.format("Date/Time Result = %b", EDTLib.setDateTime(new Date())));
            } else if (v==activityMainBinding.buttonSetTimeZone) {
                Log.d(TAG, "Calling Set TimeZone from Service!");
                Log.d(TAG, String.format("Set TimeZone Result = %b", EDTLib.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"))));
            } else if (v==activityMainBinding.buttonRemoveAllGoogleAccounts) {
                Log.d(TAG, "Calling Remove all Google Accounts from Service!");
                Log.d(TAG, String.format("Remove all Google Accounts Result = %b", EDTLib.removeAllGoogleAccounts()));
            } else if (v==activityMainBinding.buttonRemoveAllAccounts) {
                Log.d(TAG, "Calling Remove all Accounts from Service!");
                Log.d(TAG, String.format("Remove all Accounts Result = %b", EDTLib.removeAllAccounts()));
            } else if (v==activityMainBinding.buttonEnableWifi) {
                Log.d(TAG, "Calling Enable Wifi from Service!");
                Log.d(TAG, String.format("Enable Wifi Result = %b", EDTLib.enableWifi(true)));
            } else if (v==activityMainBinding.buttonDisableWifi) {
                Log.d(TAG, "Calling Disable Wifi from Service!");
                Log.d(TAG, String.format("Disable Wifi Result = %b", EDTLib.enableWifi(false)));
            } else if (v==activityMainBinding.buttonAddWifi) {
                Log.d(TAG, "Calling Add Wifi Account from Service!");
 //               WifiConfigurationParcelable conf = new WifiConfigurationParcelable();
                android.net.wifi.WifiConfiguration conf = new android.net.wifi.WifiConfiguration();
                conf.SSID = "\"MIS Test\"";
                conf.hiddenSSID = true;
                conf.preSharedKey = "\"UmfegUmfe\"";
                //Log.d(TAG, String.format("Add Wifi AccountResult = %b", edtToolsService.addNetwork(conf)));
                Log.d(TAG, String.format("Add Wifi AccountResult = %b", EDTLib.addNetwork(conf)));
            } else if (v==activityMainBinding.buttonRemoveWifi) {
                Log.d(TAG, "Calling Remove Wifi Account from Service!");
                Log.d(TAG, String.format("Remove Wifi Account Result = %b", EDTLib.removeNetwork("MIS Test")));
            } else if (v==activityMainBinding.buttonEnableBluetooth) {
                Log.d(TAG, "Calling Enable Bluetooth from Service!");
                Log.d(TAG, String.format("Enable Bluetooth Result = %b", EDTLib.enableBluetooth(true)));
            } else if (v==activityMainBinding.buttonDisableBluetooth) {
                Log.d(TAG, "Calling Disable Bluetooth from Service!");
                Log.d(TAG, String.format("Disable Bluetooth Result = %b", EDTLib.enableBluetooth(false)));
            } else if (v==activityMainBinding.buttonEnableNfc) {
                Log.d(TAG, "Calling Enable NFC from Service!");
                Log.d(TAG, String.format("Enable NFC Result = %b", EDTLib.enableNfc(true)));
            } else if (v==activityMainBinding.buttonDisableNfc) {
                Log.d(TAG, "Calling Disable NFC from Service!");
                Log.d(TAG, String.format("Disable NFC Result = %b", EDTLib.enableNfc(false)));
            } else if (v==activityMainBinding.buttonEnableGps) {
                Log.d(TAG, "Calling Enable GPS from Service!");
                Log.d(TAG, String.format("Enable GPS Result = %b", EDTLib.enableGps(true)));
            } else if (v==activityMainBinding.buttonDisableGps) {
                Log.d(TAG, "Calling Disable GPS from Service!");
                Log.d(TAG, String.format("Disable GPS Result = %b", EDTLib.enableGps(false)));
            } else if (v==activityMainBinding.buttonEnableWwan) {
                Log.d(TAG, "Calling Enable WWAN from Service!");
                Log.d(TAG, String.format("Enable WWAN Result = %b", EDTLib.enableWwan(true)));
            } else if (v==activityMainBinding.buttonDisableWwan) {
                Log.d(TAG, "Calling Disable WWAN from Service!");
                Log.d(TAG, String.format("Disable WWAN Result = %b", EDTLib.enableWwan(false)));
            } else if (v==activityMainBinding.buttonEnableDeveloperMode) {
                Log.d(TAG, "Calling Enable Developer Mode from Service!");
                Log.d(TAG, String.format("Enable Developer Mode Result = %b", EDTLib.enableDeveloperMode(true)));
            } else if (v==activityMainBinding.buttonDisableDeveloperMode) {
                Log.d(TAG, "Calling Disable Developer Mode from Service!");
                Log.d(TAG, String.format("Disable Developer Mode Result = %b", EDTLib.enableDeveloperMode(false)));
            } else if (v==activityMainBinding.buttonCopyFile) {
                Log.d(TAG, "Calling Copy File from Service!");
                Log.d(TAG, String.format("Copy File Result = %b", EDTLib.copyFile(Paths.get("/sdcard/Download/devinfo.html"), Paths.get("/sdcard/Download/devinfo1.html"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS)));
            } else if (v==activityMainBinding.buttonMoveFile) {
                Log.d(TAG, "Calling Move File from Service!");
                Log.d(TAG, String.format("Move File Result = %b", EDTLib.moveFile(Paths.get("/sdcard/Download/devinfo1.html"), Paths.get("/sdcard/Download/devinfo2.html"))));
            } else if (v==activityMainBinding.buttonReadFile) {
                Log.d(TAG, "Calling Read File from Service!");
                byte[] testData = new byte[4096];
                ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.path(Paths.get("/sdcard/Download/devinfo.html")).data(testData).fileOffset(1).dataOffset(2).length(100).options(StandardOpenOption.READ).build();
                Log.d(TAG, String.format("Read File Result = %b", EDTLib.readFile(readWriteFileParams)));
            } else if (v==activityMainBinding.buttonTestMessage) {
                Log.d(TAG, "Calling Test Message from Service!");
                //Log.d(TAG, String.format("Test Message Result = %b", edtToolsService.testMessage("EDT Tools Test Message!")));
                Log.d(TAG, String.format("Test Message Result = %b", EDTLib.testMessage("EDT Tools Test Message 2!")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
