package com.casioeurope.mis.edt;

import android.accounts.Account;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.RemoteException;

import com.casioeurope.mis.edt.types.APN;
import com.casioeurope.mis.edt.types.APNParcelable;
import com.casioeurope.mis.edt.types.ReadWriteFileParams;
import com.casioeurope.mis.edt.types.ReadWriteFileParamsParcelable;
import com.casioeurope.mis.edt.types.WifiConfigurationParcelable;

import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The <b>CASIO Enterprise Developer Tools Library</b> Main Class<br/><br/>
 * This Class holds all static methods required for a Developer to easily access methods and properties of the device where system access rights are required.<br/><br/>
 *
 * @version 1.02
 * @since 1.00
 */
public class EDTLib {

    private static EDTLib instance;

    private EDTLib() {
    }

    private static EDTLib getInstance() {
        if (EDTLib.instance == null) {
            EDTLib.instance = new EDTLib();
        }
        return EDTLib.instance;
    }

    private IEDT edtService() {
        return EDTServiceConnection.getInstance().getEDTService();
    }


    /**
     * Add a new network description to the set of configured networks. The {@link android.net.wifi.WifiConfiguration#networkId networkId} field of the supplied configuration object is ignored.
     *
     * @param wifiConfiguration {@link android.net.wifi.WifiConfiguration WifiConfiguration}: The set of variables that describe the configuration, contained in a WifiConfiguration object
     * @return boolean whether or not the new network could be added
     * @since 1.00
     */
    @SuppressWarnings({"deprecation", "unused", "RedundantSuppression"})
    public static boolean addNetwork(android.net.wifi.WifiConfiguration wifiConfiguration) {
        try {
            WifiConfigurationParcelable wifiConfigurationParcelable = new WifiConfigurationParcelable(wifiConfiguration);
            return getInstance().edtService().addNetwork(wifiConfigurationParcelable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies whether applications from "unknown sources" (not from Google Play)
     * can be installed on the device
     *
     * @param allow {@code boolean}: true allows unknown sources, false disallows them
     * @return boolean whether or not the Setting could be applied
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean allowUnknownSources(boolean allow) {
        try {
            return getInstance().edtService().allowUnknownSources(allow);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Clears the Cache of an Application given by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the App which's cache shall be cleared.
     * @return {@code boolean} whether or not the app cache could be cleared successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean clearCacheForPackage(String packageName) {
        try {
            return getInstance().edtService().clearCacheForPackage(packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Clears the device's Clipboard
     *
     * @return boolean whether or not the Clipboard could be cleared
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean clearClipboard() {
        try {
            return getInstance().edtService().clearClipboard();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Clears the Data of an Application given by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the App which's data shall be cleared.
     * @return {@code boolean} whether or not the app data could be cleared successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean clearDataForPackage(String packageName) {
        try {
            return getInstance().edtService().clearDataForPackage(packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Clears the device password (PIN)
     *
     * @return boolean whether or not the Password could be cleared
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean clearPassword() {
        try {
            return getInstance().edtService().clearPassword();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Connects to an existing network according to it's {@link android.net.wifi.WifiConfiguration#SSID SSID}.
     *
     * @param ssid {@link java.lang.String String}: The {@link android.net.wifi.WifiConfiguration#SSID SSID} of the network to connect to
     * @return boolean whether or not the connection could be established
     * @since 1.00
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection", "RedundantSuppression"})
    public static boolean connectNetwork(String ssid) {
        try {
            return getInstance().edtService().connectNetwork(ssid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Connects to an existing network according to it's {@link android.net.wifi.WifiConfiguration#networkId networkId}.
     *
     * @param networkId {@link java.lang.Integer int}: The {@link android.net.wifi.WifiConfiguration#networkId networkId} of the network to connect to
     * @return boolean whether or not the connection could be established
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean connectNetworkId(int networkId) {
        try {
            return getInstance().edtService().connectNetworkId(networkId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Copies a file from one location to another, with a variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} and/or {@link java.nio.file.LinkOption LinkOptions} applied
     *
     * @param sourceFilePath      {@link java.nio.file.Path Path}:      Path to copy the file from
     * @param destinationFilePath {@link java.nio.file.Path Path}: Path to copy the file to
     * @param options             variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} and/or {@link java.nio.file.LinkOption LinkOptions} to apply
     * @return {@link java.nio.file.Path Path} to the target file or {@link javax.lang.model.type.NullType null} if operation failed
     * @apiNote This method follows the specification of the Android API {@link java.nio.file.Files#copy(Path, Path, CopyOption...) copy} method.<br/>
     * It's purpose is to give user applications the ability to copy files which are out of reach for non-system apps.
     * <p> Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code null}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "JavadocReference", "RedundantSuppression"})
    public static Path copyFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            return null;
        }
        try {
            List<String> copyOptionStrings = options == null ? null : Arrays.stream(options).map(Object::toString).collect(Collectors.toList());
            String retVal = getInstance().edtService().copyFile(sourceFilePath.toString(), destinationFilePath.toString(), copyOptionStrings);
            return retVal == null ? null : Paths.get(retVal);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new Access Point Name (APN) configuration for a carrier data connection and optionally sets it as the new default configuration.
     *
     * @param apn          {@link APN APN}:         The Access Point Name (APN) configuration for a carrier data connection.
     * @param setAsDefault {@code boolean}: Whether or not the APN configuration should become the new default configuration.
     * @return boolean whether or not the new Access Point Name (APN) configuration has been created successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean createNewApn(APN apn, boolean setAsDefault) {
        try {
            return getInstance().edtService().createNewApn(new APNParcelable(apn), setAsDefault);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes the specified file
     *
     * @param filePath {@link java.lang.String String}: Path (including name) of the file to be removed
     * @return boolean whether or not the file has been removed successfully
     * @apiNote Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean deleteFile(String filePath) {
        try {
            return getInstance().edtService().deleteFile(filePath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enable / disables the ADB connectivity via USB
     *
     * @param enable {@code boolean}: {@code true} enables ADB connectivity via USB, {@code false} disables it
     * @return boolean whether or not the ADB connectivity via USB was enabled/disabled successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableAdb(boolean enable) {
        try {
            return getInstance().edtService().enableAdb(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables / Disables a System Application given by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the System App which shall be enabled/disabled.
     * @param enable      {@code boolean}: True enables the System App, false disables it.
     * @return {@code boolean} whether or not the app could be enabled/disabled successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableApplication(String packageName, boolean enable) {
        try {
            return enable ? getInstance().edtService().enableApplication(packageName) : getInstance().edtService().disableApplication(packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies whether or not Background Data use is permitted
     *
     * @param enable {@code boolean}: true enables Background Data, false disables it
     * @return boolean whether or not the setting was applied successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableBackgroundData(boolean enable) {
        try {
            return getInstance().edtService().enableBackgroundData(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables / Disables a Battery Optimization ("Doze Mode") for an Application given by it's package name.<br/>
     * This adds/removes the given application to/from the system's whitelist of Apps which are not subject of battery optimization.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the System App which shall be added/removed to/from the doze mode whitelist.
     * @param enable      {@code boolean}: True adds the app to the doze mode whitelist, false removes it from the doze mode whitelist.
     * @return {@code boolean} whether or not the app could be added/removed to/from the doze mode whitelist successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableBatteryOptimization(String packageName, boolean enable) {
        try {
            return enable ? getInstance().edtService().enableBatteryOptimization(packageName) : getInstance().edtService().disableBatteryOptimization(packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables/Disables Bluetooth on demand
     *
     * <p>If the requested state equals the current state of Bluetooth, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables Bluetooth, false disables it
     * @return boolean whether or not Bluetooth could be enabled/disabled
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableBluetooth(boolean enabled) {
        try {
            return getInstance().edtService().enableBluetooth(enabled);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies whether or not the Device Cameras can be used
     *
     * @param enable {@code boolean}: true enables the Camera usage, false disables it
     * @return boolean whether or not the setting was applied successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableCameras(boolean enable) {
        try {
            return getInstance().edtService().enableCameras(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enable / disables the Clipboard
     *
     * @param enable {@code boolean}: {@code true} enables the Clipboard, {@code false} disables it
     * @return boolean whether or not the Clipboard was enabled/disabled successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableClipboard(boolean enable) {
        try {
            return getInstance().edtService().enableClipboard(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables/Disables Developer Mode on demand
     *
     * <p>If the requested state equals the current state of Developer Mode, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables Developer Mode, false disables it
     * @return boolean whether or not Developer Mode could be enabled/disabled
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableDeveloperMode(boolean enabled) {
        try {
            return getInstance().edtService().enableDeveloperMode(enabled);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables/Disables Device Admin Mode for specific apps/packages on demand
     *
     * <p>Note that you must know both the package name of the app you want to enable/disable device admin mode upon, as well as the Class within that application that implements the DeviceAdminReceiver.<br/>
     * This is not the same as the Activity class name required to launch the application and would likely need to be acquired from the application developer.</p>
     *
     * @param packageName {@link String String}: The package name of the app in question where you want to enable/disable device admin mode upon.
     * @param className   {@link String String}: The class name of the Class within that application that implements the DeviceAdminReceiver.
     * @param makeAdmin   {@code boolean}: true enables device admin mode on the application, false disables it.
     * @return boolean whether or not Device Admin Mode could be enabled/disabled on the application in question.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableDeviceAdmin(String packageName, String className, boolean makeAdmin) {
        try {
            return getInstance().edtService().enableDeviceAdmin(packageName, className, makeAdmin);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables/Disables GPS on demand
     *
     * <p>If the requested state equals the current state of GPS, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables GPS, false disables it
     * @return boolean whether or not GPS could be enabled/disabled
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableGps(boolean enabled) {
        try {
            return getInstance().edtService().enableGps(enabled);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enable / disables the "USB Mass Storage" aka "File Transfer" mode
     *
     * @param enable {@code boolean}: {@code true} enables the "USB Mass Storage" mode, {@code false} disables it
     * @return boolean whether or not the "USB Mass Storage" mode was enabled/disabled successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableMassStorage(boolean enable) {
        try {
            return getInstance().edtService().enableMassStorage(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables/Disables NFC on demand
     *
     * <p>If the requested state equals the current state of NFC, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables NFC, false disables it
     * @return boolean whether or not NFC could be enabled/disabled
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableNfc(boolean enabled) {
        try {
            return getInstance().edtService().enableNfc(enabled);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies whether or not Data Roaming is available
     *
     * @param enable {@code boolean}: true enables Data Roaming false disables it
     * @return boolean whether or not the setting was applied successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableRoaming(boolean enable) {
        try {
            return getInstance().edtService().enableRoaming(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables/Disables Wifi on demand
     *
     * <p>If the requested state equals the current state of Wifi, the method returns true without performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables Wifi, false disables it
     * @return boolean whether or not Wifi could be enabled/disabled
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableWifi(boolean enabled) {
        try {
            return getInstance().edtService().enableWifi(enabled);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enables/Disables WWAN (3G/LTE/...) on demand
     *
     * <p>If the requested state equals the current state of WWAN, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables WWAN, false disables it
     * @return boolean whether or not WWAN could be enabled/disabled
     * @apiNote Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection", "RedundantSuppression"})
    public static boolean enableWwan(boolean enabled) {
        try {
            return getInstance().edtService().enableWwan(enabled);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Performs a factory reset of the device
     *
     * @param removeAccounts {@code boolean}: Specifies whether or not to remove all Google Accounts prior to performing the Factory Reset.<br/>
     *                       If {@code removeAccounts} is set to "true", all Google Accounts will be removed from the device before the Factory Reset is being performed, meaning that the device will <i>not</i> enter <a href="https://developer.android.com/work/dpc/security">FRP</a> after performing the reset.<br/>
     *                       If {@code removeAccounts} is set to "true" and it's <i>not</i> possible to remove all Google Accounts prior to performing the Factory Reset, the method will return {@code false} and the device will <i>not</i> perform the Factory Reset.<br/>
     *                       This is to prevent the device from accidentally falling into <a href="https://developer.android.com/work/dpc/security">FRP</a> mode.
     *                       If {@code removeAccounts} is set to "false", existing Google Accounts will remain untouched and the device will simply perform the Factory Reset. This means that if aGoogle Accounts was present at the time of calling {@code factoryReset(false)}, the device will fall into <a href="https://developer.android.com/work/dpc/security">FRP</a> mode after reboot has finished.
     * @return boolean whether or not the Factory Reset could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot and wipe the device.
     * <p><b>CAUTION: This wipes all data off the device!<br/>
     * CAUTION: If there's a Google Account active on the device at factory reset time, you will need to login to that account after reboot (Google Factory Reset Protection)!</b></p>
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean factoryReset(boolean removeAccounts) {
        try {
            return getInstance().edtService().factoryReset(removeAccounts);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the {@link java.lang.reflect.Array Array} of existing {@link APN Access Point Name (APN)} configurations.
     *
     * @return {@link APN APN[]} {@link java.lang.reflect.Array Array} of available Access Point Name (APN) configurations.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static APN[] getAllApnList() {
        try {
            APNParcelable[] apnParcelables = getInstance().edtService().getAllApnList();
            if (apnParcelables == null) return null;
            //noinspection SpellCheckingInspection
            APN[] apns = new APN[apnParcelables.length];
            int i = 0;
            for (APNParcelable apnParcelable : apnParcelables) apns[i++] = apnParcelable.getAPN();
            return apns;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the {@link APN Access Point Name (APN) configuration} for a carrier data connection by it's {@link APN#getName() name}.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for a carrier data connection.<br/>
     * @return {@link APN APN} the Access Point Name (APN) configuration for a carrier data connection.
     * If no matching configuration for the {@link APN#getName() name} could be found, the method returns {@code null}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static APN getApn(String name) {
        try {
            return getInstance().edtService().getApn(name).getAPN();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the {@link APN#getId() id} field of an existing Access Point Name (APN) configuration for a carrier data connection.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for a carrier data connection.<br/>
     * @return {@code int} the {@link APN#getId() id} field of an existing Access Point Name (APN) configuration for a carrier data connection.
     * If no matching configuration for the {@link APN#getName() name} could be found, the method returns {@link APN#INVALID_APN INVALID_APN (-1)}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static int getApnId(String name) {
        try {
            return getInstance().edtService().getApnId(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return APN.INVALID_APN;
    }

    /**
     * Reads current Barcode Scanner settings and applies new Barcode Scanner settings to/from files according to the specified file path
     *
     * <p>NOTE: The Filename for the old Barcode Scanner setting will get a suffix "_old" applied.<br/>
     * See {@link #getCurrentScanSettings(String settingsFilePath)} and {@link #setNewScanSettings(String settingsFilePath)} for further details regarding file name and file format specifications</p>
     *
     * @param settingsFilePath {@link java.lang.String String}: Path to the file holding the Scanner settings
     * @return boolean whether or not the Barcode Scanner settings could be written to (a) file(s) and the new settings could applied to the Barcode Scanner successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean getCurrentAndSetNewScanSettings(String settingsFilePath) {
        try {
            return getInstance().edtService().getCurrentAndSetNewScanSettings(settingsFilePath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Write current Barcode Scanner Settings to file
     *
     * <p>NOTE: The file may or may not contain a file extension.<br/>
     * If the file contains an extension (case insensitive), the generated settings file will have the following format:<br/>
     * * XML if the file extension is not ".json"<br/>
     * * JSON if the file extension is ".json"<br/>
     * If the file contains no extension, two settings files with be created with ".xml" and ".json" extension and<br/>
     * appropriate format accordingly.<br/>
     * The Filename will get a suffix "_cur" applied</p>
     *
     * @param settingsFilePath {@link java.lang.String String}: Path to the file holding the Scanner settings
     * @return boolean whether or not the current Barcode Scanner settings could be written to (a) file(s) successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean getCurrentScanSettings(String settingsFilePath) {
        try {
            return getInstance().edtService().getCurrentScanSettings(settingsFilePath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get an {@link java.lang.reflect.Array Array} of existing Google {@link Account Accounts} currently configured for this device.
     *
     * @return {@link java.lang.reflect.Array Array} of {@link Account Account}: The {@link java.lang.reflect.Array Array} of existing Google {@link Account Accounts} currently configured for this device.<br/>
     * In case of failure, this method returns {@code null}.<br/>
     * If no Google {@link Account Accounts} are configured at the time of calling this method, the return value will be an empty {@link java.lang.reflect.Array Array}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Account[] getGoogleAccounts() {
        try {
            return getInstance().edtService().getGoogleAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a {@link List List} of {@link String Names} of available Keyboards on this device.
     *
     * @return {@link List List} of {@link String String} The Package Names of the available Keyboards on this device.
     * In case of an error, the method returns {@code null}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static List<String> getKeyboardNames() {
        try {
            return getInstance().edtService().getKeyboardNames();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Initialize the given Certificate {@link java.security.KeyStore KeyStore} with the given Password.
     *
     * @param storeName {@link java.lang.String String}: Friendly name of the Certificate {@link java.security.KeyStore KeyStore} to be initialized.<br/>
     *                  Can be {@code null} or an empty {@link java.lang.String String}, in which case the default {@link java.security.KeyStore KeyStore} will be used.
     * @param password  {@link java.lang.String String}: Password to be used for the {@link java.security.KeyStore KeyStore}.
     * @return {@code boolean} whether or not the Certificate {@link java.security.KeyStore KeyStore} could be initialized successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean initializeKeyStore(String storeName, String password) {
        try {
            return getInstance().edtService().initializeKeyStore(storeName, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Installs an app by it's apk file name.
     *
     * @param apkFilename {@link java.lang.String String}: File name of the .apk file to be installed.
     * @param update      {@code boolean}: Whether or not to update the app if it's already installed.<br/>
     *                    If you specify "true" but the app is not installed yet, this parameter will have no effect.<br/>
     *                    If you specify "false" but the app is already installed, this method will return false and the installation will fail.
     * @return {@code boolean} whether or not the .apk file could be installed successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean installApk(String apkFilename, boolean update) {
        try {
            return getInstance().edtService().installApk(apkFilename, update);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Installs a CA Certificate from a File.
     *
     * @param friendlyName {@link java.lang.String String}: Friendly name of the Certificate to be installed.
     * @param fileName     {@link java.lang.String String}: File Path and Name of the Certificate File.
     * @return {@code boolean} whether or not the Certificate could be installed successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean installCACertificate(String friendlyName, String fileName) {
        try {
            return getInstance().edtService().installCACertificate(friendlyName, fileName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Locks the Device (i.e. device needs to be unlocked to be used further on)
     *
     * <p>May or may not turn off the display, depending on device settings</p>
     *
     * @return {@code boolean} whether or not the Device could be locked
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean lockDevice() {
        try {
            return getInstance().edtService().lockDevice();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mounts/Unmounts the external SD Card
     *
     * @param mount {@code boolean}: true mounts the external SD card, false unmounts it
     * @return boolean whether or not the external SD card could (un)mounted successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean mountSDCard(boolean mount) {
        try {
            return getInstance().edtService().mountSDCard(mount);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Moves a file from one location to another, with a variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} applied
     *
     * @param sourceFilePath      {@link java.nio.file.Path Path}:      Path to move the file from
     * @param destinationFilePath {@link java.nio.file.Path Path}: Path to move the file to
     * @param options             variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} to apply
     * @return {@link java.nio.file.Path Path} to the target file or {@link javax.lang.model.type.NullType null} if operation failed
     * @apiNote This method follows the specification of the Android API {@link java.nio.file.Files#move(Path, Path, CopyOption...) move} method.<br/>
     * It's purpose is to give user applications the ability to move files which are out of reach for non-system apps.
     * <p> Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code null}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "JavadocReference", "RedundantSuppression"})
    public static Path moveFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            return null;
        }
        try {
            List<String> copyOptionStrings = options == null ? null : Arrays.stream(options).map(Object::toString).collect(Collectors.toList());
            String retVal = getInstance().edtService().moveFile(sourceFilePath.toString(), destinationFilePath.toString(), copyOptionStrings);
            return retVal == null ? null : Paths.get(retVal);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads Data from a File
     *
     * <p>The {@link ReadWriteFileParams readWriteFileParams} parameter holds a set of parameters used to read the data from the given file.</p>
     *
     * <p>Since this method uses a variable set of parameters with overlapping parameter types, overloading methods or using variable argument lists isn't feasible.<br/>
     * Instead, the {@link ReadWriteFileParams readWriteFileParams} holds a set of all required parameters for this method.</p>
     *
     * <p>Use {@link ReadWriteFileParams.Builder} to create a new instance of the {@link ReadWriteFileParams readWriteFileParams} parameter.</p>
     *
     * <p>The example below shows how you might create a new {@link ReadWriteFileParams} object instance for use with this method:
     *
     * <pre><code>
     * // Create a ReadWriteFileParams object instance with a data buffer of 4K,
     * // for read operation starting after 1 byte, accessing the buffer from index 2 onwards, reading a hundred bytes.
     * byte[] testData = new byte[4096];
     * ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.setPath(Paths.get("/sdcard/Download/devinfo.html"))
     *       .setData(testData)
     *       .setFileOffset(1)
     *       .setDataOffset(2)
     *       .setLength(100)
     *       .setOptions(StandardOpenOption.READ)
     *       .build();
     * EDTLib.readFile(readWriteFileParams);
     * </code></pre>
     *
     * @param readWriteFileParams {@link ReadWriteFileParams ReadWriteFileParams}: Parameters specifying which file to read from, with optional data buffer, offsets, length and File Options.<br>
     * @return {@code boolean} whether or not the file data has been read successfully
     * @apiNote the {@link ReadWriteFileParams#getData() data buffer} of {@link ReadWriteFileParams readWriteFileParams} is optional.<br/>
     * If this method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to hold the data being read.<br/>
     * If this method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will dynamically allocate a buffer holding the data being read.
     * <p> Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @see ReadWriteFileParams ReadWriteFileParams for further details.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean readFile(ReadWriteFileParams readWriteFileParams) {
        try {
            return getInstance().edtService().readFile(new ReadWriteFileParamsParcelable(readWriteFileParams));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Performs a device reboot
     *
     * @return {@code boolean} whether or not the Reboot could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot, quite like when the user keeps pressing the power key and chooses to "Reboot".
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean reboot() {
        try {
            return getInstance().edtService().reboot();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Performs a device reboot into recovery mode
     *
     * @return {@code boolean} whether or not the Recovery Reboot could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot into recovery mode, quite like when the user keeps pressing the power key and chooses to "Reboot" - but using this method it will not reboot the Operating System, but reboot into recovery mode instead.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean recovery() {
        try {
            return getInstance().edtService().recovery();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies whether or not the Browser may remember Login Passwords
     *
     * @param enable {@code boolean}: true enables remembering Login Passwords, false disables it
     * @return {@code boolean} whether or not the setting was applied successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean rememberPasswords(boolean enable) {
        try {
            return getInstance().edtService().rememberPasswords(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes (attempts to remove) a certain account from the device
     *
     * @param account {@link android.accounts.Account Account}: Account to be removed
     * @return {@code boolean} whether or not the account removal was successful
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean removeAccount(Account account) {
        try {
            return getInstance().edtService().removeAccount(account);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes (attempts to remove) all configured accounts of all kind from the device<br/>
     *
     * @return {@code boolean} whether or not the account removal was successful
     * @apiNote This method usually fails, because certain accounts cannot be removed.<br/>
     * Those that can be removed will be removed nevertheless.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean removeAllAccounts() {
        try {
            return getInstance().edtService().removeAllAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes (attempts to remove) all configured Google accounts from the device
     *
     * @return {@code boolean} whether or not the account removal was successful
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean removeAllGoogleAccounts() {
        try {
            return getInstance().edtService().removeAllGoogleAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes an existing network according to it's {@link android.net.wifi.WifiConfiguration#SSID SSID}.
     *
     * @param ssid {@link java.lang.String String}: The {@link android.net.wifi.WifiConfiguration#SSID SSID} of the network to be removed
     * @return {@code boolean} whether or not the existing network could be removed
     * @since 1.00
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection", "RedundantSuppression"})
    public static boolean removeNetwork(String ssid) {
        try {
            return getInstance().edtService().removeNetwork(ssid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes an existing network according to it's {@link android.net.wifi.WifiConfiguration#networkId networkId}.
     *
     * @param networkId {@link java.lang.Integer int}: The {@link android.net.wifi.WifiConfiguration#networkId networkId} of the network to be removed
     * @return {@code boolean} whether or not the existing network could be removed
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean removeNetworkId(int networkId) {
        try {
            return getInstance().edtService().removeNetworkId(networkId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Resets the device password (PIN) to a new value
     *
     * @param newPassword {@link java.lang.String String}: The new Device Password to be set
     * @return {@code boolean} whether or not the new Password could be set
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean resetPassword(String newPassword) {
        try {
            return getInstance().edtService().resetPassword(newPassword);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies whether or not the Browser may save Form Input Data
     *
     * @param enable {@code boolean}: true enables saving Form Input Data, false disables it
     * @return {@code boolean} whether or not the setting was applied successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean saveFormData(boolean enable) {
        try {
            return getInstance().edtService().saveFormData(enable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets a new Device Date and Time
     *
     * @param date {@link java.util.Date Date}: The Date and Time to be set
     * @return {@code boolean} whether or not the new Date/Time could be set
     * @apiNote Requires Android N (Android 7) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setDateTime(Date date) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) { // requires Android N or later
            return false;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // Convert Calendar to integer values because AIDL can handle limited data types only
            return getInstance().edtService().setDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets the default Browser Homepage
     *
     * @param homePage {@link java.lang.String String}: URL of the new Homepage
     * @return {@code boolean} whether or not the default Browser Homepage could be set successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setDefaultHomePage(String homePage) {
        try {
            return getInstance().edtService().setDefaultHomePage(homePage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies a new Default Launcher
     *
     * @param packageName {@link java.lang.String String}: Package Name of the new Default Launcher
     * @return {@code boolean} whether or not the Default Launcher was set successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setDefaultLauncher(String packageName) {
        try {
            return getInstance().edtService().setDefaultLauncher(packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets the default and current Keyboard on this device.
     *
     * @param keyboardName {@link java.lang.String String}: The Package Name of the Keyboard to be used.<br/>
     *                     Use {@link #getKeyboardNames} to fetch a {@link List List} of available Keyboard Names.
     * @return {@code boolean} whether or not the Keyboard was set successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setKeyboard(String keyboardName) {
        try {
            return getInstance().edtService().setKeyboard(keyboardName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Apply new Barcode Scanner Settings from file.
     *
     * <p>NOTE: The file may or may not contain a file extension.<br/>
     * If the file contains an extension (case insensitive), the settings will be parsed from the following format:<br/>
     * * XML if the file extension is ".xml"<br/>
     * * JSON if the file extension is ".json"<br/>
     * * Dynamically XML or JSON (XML is attempted first, in case of failure another attempt is made to parse JSON format) if the file extension is neither ".xml" nor ".json"<br/>
     * If the file contains no extension, two settings will dynamically be parsed in XML or JSON format (XML is attempted first, in case of failure another attempt is made to parse JSON format)</p>
     *
     * @param settingsFilePath {@link java.lang.String String}: Path to the file holding the Scanner settings
     * @return {@code boolean} whether or not the new settings could applied to the Barcode Scanner
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setNewScanSettings(String settingsFilePath) {
        try {
            return getInstance().edtService().setNewScanSettings(settingsFilePath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets a new preferred Access Point Name (APN) configuration for a carrier data connections.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for becoming the new preferred carrier data connection.<br/>
     * @return {@code boolean} whether or not the Access Point Name (APN) configuration in question could be set as the new preferred APN configuration.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setPreferredApn(String name) {
        try {
            return getInstance().edtService().setPreferredApn(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies a new Screen Lock Timeout in Milliseconds
     *
     * @param milliseconds {@link java.lang.Integer int}: The new Screen Lock Timeout Value in Milliseconds
     * @return {@code boolean} whether or not the Screen Lock Timeout was set successfully
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setScreenLockTimeout(int milliseconds) {
        try {
            return getInstance().edtService().setScreenLockTimeout(milliseconds);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets a new Device Time Zone
     *
     * @param timeZone {@link android.icu.util.TimeZone TimeZone}: The new Time Zone to be set
     * @return {@code boolean} whether or not the new Time Zone could be set
     * @apiNote Requires Android N (Android 7) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean setTimeZone(TimeZone timeZone) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) { // requires Android N or later
            return false;
        }
        try {
            // Convert TimeZone to String because AIDL can handle limited data types only
            return getInstance().edtService().setTimeZone(timeZone.getDisplayName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Performs a device shutdown
     *
     * @return {@code boolean} whether or not the Shutdown could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently power off, quite like when the user keeps pressing the power key and chooses to "Power off".
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean shutdown() {
        try {
            return getInstance().edtService().shutdown();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Displays a Toast Message containing the specified String.
     *
     * <p>This method is supposed to be used for testing purpose only, for instance in order to check whether the service binding from a sample application is working or not.</p>
     *
     * @param message {@link java.lang.String String}: The Message to be displayed
     * @return {@code boolean} whether or not the Message could be shown
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean testMessage(String message) {
        try {
            return getInstance().edtService().testMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing Access Point Name (APN) configuration for a carrier data connection.
     *
     * @param apn {@link APN APN}:         The Access Point Name (APN) configuration for a carrier data connection, holding the new data for this configuration.<br/>
     *            The "id" field holds the ID of the existing APN configuration to be updated.<br/>
     *            Use {@link EDTLib#getApnId getApnId} to fetch the ID from a given name if required.
     * @return {@code boolean} whether or not the new Access Point Name (APN) configuration has been applied successfully.<br/>
     * If no matching configuration for the ID could be found, the method returns {@code false}.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean updateApn(APN apn) {
        try {
            return getInstance().edtService().updateApn(new APNParcelable(apn));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update an existing network description of the set of configured networks. The {@link android.net.wifi.WifiConfiguration#networkId networkId} field must be set to the ID of the existing network being updated.
     *
     * @param wifiConfiguration {@link android.net.wifi.WifiConfiguration WifiConfiguration}: The set of variables that describe the configuration, contained in a WifiConfiguration object.
     *                          It may be sparse, so that only the items that are being changed are non-null.
     * @return {@code boolean} whether or not the existing network could be updated
     * @since 1.00
     */
    @SuppressWarnings({"deprecation", "unused", "RedundantSuppression"})
    public static boolean updateNetwork(android.net.wifi.WifiConfiguration wifiConfiguration) {
        try {
            WifiConfigurationParcelable wifiConfigurationParcelable = new WifiConfigurationParcelable(wifiConfiguration);
            return getInstance().edtService().updateNetwork(wifiConfigurationParcelable);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Uninstalls an app by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the App to be uninstalled.
     * @return {@code boolean} whether or not the app could be uninstalled successfully.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean uninstallPackage(String packageName) {
        try {
            return getInstance().edtService().uninstallPackage(packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing Access Point Name (APN) configuration for a carrier data connection.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for a carrier data connection.<br/>
     * @return {@code boolean} whether or not the Access Point Name (APN) configuration in question refers to a valid APN configuration.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean verifyApn(String name) {
        try {
            return getInstance().edtService().verifyApn(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Writes Data to a File
     *
     * <p>The {@link ReadWriteFileParams readWriteFileParams} parameter holds a set of parameters used to write the data to the given file.</p>
     *
     * <p>Since this method uses a variable set of parameters with overlapping parameter types, overloading methods or using variable argument lists isn't feasible.<br/>
     * Instead, the {@link ReadWriteFileParams readWriteFileParams} holds a set of all required parameters for this method.</p>
     *
     * <p>Use {@link ReadWriteFileParams.Builder} to create a new instance of the {@link ReadWriteFileParams readWriteFileParams} parameter.</p>
     *
     * <p>The example below shows how you might create a new {@link ReadWriteFileParams} object instance for use with this method:
     *
     * <pre><code>
     * // Create a ReadWriteFileParams object instance with a data buffer of 4K,
     * // for write operation starting after 1 byte, accessing the buffer from index 2 onwards, writing a hundred bytes.
     * byte[] testData = new byte[4096];
     * ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.setPath(Paths.get("/sdcard/Download/devinfo.html"))
     *       .setData(testData)
     *       .setFileOffset(1)
     *       .setDataOffset(2)
     *       .setLength(100)
     *       .setOptions(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
     *       .build();
     * EDTLib.writeFile(readWriteFileParams);
     * </code></pre>
     *
     * @param readWriteFileParams {@link ReadWriteFileParams ReadWriteFileParams}: Parameters specifying which file to write to, with mandatory data buffer and optional offsets, length and File Options.<br>
     * @return {@code boolean} whether or not the file data has been written successfully
     * @apiNote the {@link ReadWriteFileParams#getData() data buffer} of {@link ReadWriteFileParams readWriteFileParams} is mandatory.<br/>
     * If this method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to write the specified amount of data.<br/>
     * If this method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will fail.
     * <p>Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @see ReadWriteFileParams ReadWriteFileParams for further details.
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean writeFile(ReadWriteFileParams readWriteFileParams) {
        try {
            return getInstance().edtService().readFile(new ReadWriteFileParamsParcelable(readWriteFileParams));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

}
