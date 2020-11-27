package com.casioeurope.mis.edt;

import android.accounts.Account;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.RemoteException;
import android.util.Log;

import com.casioeurope.mis.edt.type.APN;
import com.casioeurope.mis.edt.type.APNParcelable;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.LibraryCallback;
import com.casioeurope.mis.edt.type.ReadWriteFileParams;
import com.casioeurope.mis.edt.type.ReadWriteFileParamsParcelable;
import com.casioeurope.mis.edt.type.WifiConfigurationParcelable;

import java.math.BigInteger;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Main Library<br/><br/>
 *
 * <p>
 * This Class holds all static methods required for a Developer to easily access methods and properties of the device where system access rights are required.<br/><br/>
 *
 * @apiNote The EDT Library is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly advised not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link EDTLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link EDTLibrary#reboot() EDTLibrary.reboot()} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>ScannerLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         EDTLibrary.reboot();
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>EDTLibrary.onLibraryReady(() -&gt; { EDTLibrary.reboot(); });</pre>
 *          <br/>Or even further to:<br/>
 * <pre>EDTLibrary.onLibraryReady(EDTLibrary::reboot);</pre>
 *
 * @version 2.00
 * @since 1.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "deprecation", "JavadocReference", "SpellCheckingInspection"})
public class EDTLibrary {

    private static EDTLibrary instance;

    private EDTLibrary() {
    }

    private static EDTLibrary getInstance() {
        if (EDTLibrary.instance == null) {
            EDTLibrary.instance = new EDTLibrary();
        }
        return EDTLibrary.instance;
    }

    private IEDT edtService() {
        return EDTServiceConnection.getInstance().getEDTService();
    }

    private static void checkMethodUnsupported(BooleanParcelable unsupported) throws UnsupportedOperationException {
        if (unsupported == null) return;
        if (!unsupported.getValue()) return;
        String nameOfCurrentMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrentMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrentMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        checkMethodUnsupported(nameOfCurrentMethod, unsupported);
    }

    private static void checkMethodUnsupported(String methodName, BooleanParcelable unsupported) throws UnsupportedOperationException {
        if (unsupported == null) return;
        if (!unsupported.getValue()) return;
        throw new UnsupportedOperationException("Method \"" + methodName + "\" is not supported on this device type!");
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     */
    public static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
        if (getInstance().edtService()== null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        try {
            return getInstance().edtService().isMethodSupportedEdt(method.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link String String} methodName parameter is supported on the currently active device
     *
     * @param methodName {@link String String}: Name of the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     */
    public static boolean isMethodSupported(String methodName) throws IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        try {
            return getInstance().edtService().isMethodNameSupportedEdt(methodName);
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Add a new network description to the set of configured networks. The {@link android.net.wifi.WifiConfiguration#networkId networkId} field of the supplied configuration object is ignored.
     *
     * @param wifiConfiguration {@link android.net.wifi.WifiConfiguration WifiConfiguration}: The set of variables that describe the configuration, contained in a WifiConfiguration object
     * @return boolean whether or not the new network could be added
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean addNetwork(android.net.wifi.WifiConfiguration wifiConfiguration) throws RemoteException, UnsupportedOperationException {
        return Implementation.addNetwork(wifiConfiguration);
    }

    /**
     * Specifies whether applications from "unknown sources" (not from Google Play)
     * can be installed on the device
     *
     * @param allow {@code boolean}: true allows unknown sources, false disallows them
     * @return boolean whether or not the Setting could be applied
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean allowUnknownSources(boolean allow) throws RemoteException, UnsupportedOperationException {
        return Implementation.allowUnknownSources(allow);
    }

    /**
     * Clears the Cache of an Application given by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the App which's cache shall be cleared.
     * @return {@code boolean} whether or not the app cache could be cleared successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean clearCacheForPackage(String packageName) throws RemoteException, UnsupportedOperationException {
        return Implementation.clearCacheForPackage(packageName);
    }

    /**
     * Clears the device's Clipboard
     *
     * @return boolean whether or not the Clipboard could be cleared
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean clearClipboard() throws RemoteException, UnsupportedOperationException {
        return Implementation.clearClipboard();
    }

    /**
     * Clears the Data of an Application given by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the App which's data shall be cleared.
     * @return {@code boolean} whether or not the app data could be cleared successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean clearDataForPackage(String packageName) throws RemoteException, UnsupportedOperationException {
        return Implementation.clearCacheForPackage(packageName);
    }

    /**
     * Clears the device password (PIN)
     *
     * @return boolean whether or not the Password could be cleared
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean clearPassword() throws RemoteException, UnsupportedOperationException {
        return Implementation.clearPassword();
    }

    /**
     * Connects to an existing network according to it's {@link android.net.wifi.WifiConfiguration#SSID SSID}.
     *
     * @param ssid {@link java.lang.String String}: The {@link android.net.wifi.WifiConfiguration#SSID SSID} of the network to connect to
     * @return boolean whether or not the connection could be established
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean connectNetwork(String ssid) throws RemoteException, UnsupportedOperationException {
        return Implementation.connectNetwork(ssid);
    }

    /**
     * Connects to an existing network according to it's {@link android.net.wifi.WifiConfiguration#networkId networkId}.
     *
     * @param networkId {@link java.lang.Integer int}: The {@link android.net.wifi.WifiConfiguration#networkId networkId} of the network to connect to
     * @return boolean whether or not the connection could be established
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean connectNetworkId(int networkId) throws RemoteException, UnsupportedOperationException {
        return Implementation.connectNetworkId(networkId);
    }

    /**
     * Copies a file from one location to another, with a variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} and/or {@link java.nio.file.LinkOption LinkOptions} applied
     *
     * @param sourceFilePath      {@link java.nio.file.Path Path}:      Path to copy the file from
     * @param destinationFilePath {@link java.nio.file.Path Path}: Path to copy the file to
     * @param options             variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} and/or {@link java.nio.file.LinkOption LinkOptions} to apply
     * @return {@link java.nio.file.Path Path} to the target file or {@code null} if operation failed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @apiNote This method follows the specification of the Android API {@link java.nio.file.Files#copy(Path, Path, CopyOption...) copy} method.<br/>
     * It's purpose is to give user applications the ability to copy files which are out of reach for non-system apps.
     * <p> Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code null}.
     * @since 1.00
     */
    public static Path copyFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) throws RemoteException, UnsupportedOperationException {
        return Implementation.copyFile(sourceFilePath, destinationFilePath, options);
    }

    /**
     * Creates a new Access Point Name (APN) configuration for a carrier data connection and optionally sets it as the new default configuration.
     *
     * @param apn          {@link APN APN}:         The Access Point Name (APN) configuration for a carrier data connection.
     * @param setAsDefault {@code boolean}: Whether or not the APN configuration should become the new default configuration.
     * @return boolean whether or not the new Access Point Name (APN) configuration has been created successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean createNewApn(APN apn, boolean setAsDefault) throws RemoteException, UnsupportedOperationException {
        return Implementation.createNewApn(apn, setAsDefault);
    }

    /**
     * Removes the specified file
     *
     * @param filePath {@link java.lang.String String}: Path (including name) of the file to be removed
     * @return boolean whether or not the file has been removed successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    public static boolean deleteFile(String filePath) throws RemoteException, UnsupportedOperationException {
        return Implementation.deleteFile(filePath);
    }

    /**
     * Enable / disables the ADB connectivity via USB
     *
     * @param enable {@code boolean}: {@code true} enables ADB connectivity via USB, {@code false} disables it
     * @return boolean whether or not the ADB connectivity via USB was enabled/disabled successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableAdb(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableAdb(enable);
    }

    /**
     * Enables / Disables a System Application given by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the System App which shall be enabled/disabled.
     * @param enable      {@code boolean}: True enables the System App, false disables it.
     * @return {@code boolean} whether or not the app could be enabled/disabled successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableApplication(String packageName, boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableApplication(packageName, enable);
    }

    /**
     * Specifies whether or not Background Data use is permitted
     *
     * @param enable {@code boolean}: true enables Background Data, false disables it
     * @return boolean whether or not the setting was applied successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableBackgroundData(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableBackgroundData(enable);
    }

    /**
     * Enables / Disables a Battery Optimization ("Doze Mode") for an Application given by it's package name.<br/>
     * This adds/removes the given application to/from the system's whitelist of Apps which are not subject of battery optimization.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the System App which shall be added/removed to/from the doze mode whitelist.
     * @param enable      {@code boolean}: True adds the app to the doze mode whitelist, false removes it from the doze mode whitelist.
     * @return {@code boolean} whether or not the app could be added/removed to/from the doze mode whitelist successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableBatteryOptimization(String packageName, boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableBatteryOptimization(packageName, enable);
    }

    /**
     * Enables/Disables Bluetooth on demand
     *
     * <p>If the requested state equals the current state of Bluetooth, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables Bluetooth, false disables it
     * @return boolean whether or not Bluetooth could be enabled/disabled
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableBluetooth(boolean enabled) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableBluetooth(enabled);
    }

    /**
     * Specifies whether or not the Device Cameras can be used
     *
     * @param enable {@code boolean}: true enables the Camera usage, false disables it
     * @return boolean whether or not the setting was applied successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableCameras(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableCameras(enable);
    }

    /**
     * Enable / disables the Clipboard
     *
     * @param enable {@code boolean}: {@code true} enables the Clipboard, {@code false} disables it
     * @return boolean whether or not the Clipboard was enabled/disabled successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableClipboard(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableClipboard(enable);
    }

    /**
     * Enables/Disables Developer Mode on demand
     *
     * <p>If the requested state equals the current state of Developer Mode, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables Developer Mode, false disables it
     * @return boolean whether or not Developer Mode could be enabled/disabled
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableDeveloperMode(boolean enabled) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableDeveloperMode(enabled);
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
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableDeviceAdmin(String packageName, String className, boolean makeAdmin) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableDeviceAdmin(packageName, className, makeAdmin);
    }

    /**
     * Enables/Disables GPS on demand
     *
     * <p>If the requested state equals the current state of GPS, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables GPS, false disables it
     * @return boolean whether or not GPS could be enabled/disabled
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableGps(boolean enabled) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableGps(enabled);
    }

    /**
     * Enable / disables the "USB Mass Storage" aka "File Transfer" mode
     *
     * @param enable {@code boolean}: {@code true} enables the "USB Mass Storage" mode, {@code false} disables it
     * @return boolean whether or not the "USB Mass Storage" mode was enabled/disabled successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableMassStorage(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableMassStorage(enable);
    }

    /**
     * Enables/Disables NFC on demand
     *
     * <p>If the requested state equals the current state of NFC, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables NFC, false disables it
     * @return boolean whether or not NFC could be enabled/disabled
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableNfc(boolean enabled) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableNfc(enabled);
    }

    /**
     * Specifies whether or not Data Roaming is available
     *
     * @param enable {@code boolean}: true enables Data Roaming false disables it
     * @return boolean whether or not the setting was applied successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableRoaming(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableRoaming(enable);
    }

    /**
     * Enables/Disables Wifi on demand
     *
     * <p>If the requested state equals the current state of Wifi, the method returns true without performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables Wifi, false disables it
     * @return boolean whether or not Wifi could be enabled/disabled
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean enableWifi(boolean enabled) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableWifi(enabled);
    }

    /**
     * Enables/Disables WWAN (3G/LTE/...) on demand
     *
     * <p>If the requested state equals the current state of WWAN, the method returns true without
     * performing any action.</p>
     *
     * @param enabled {@code boolean}: true enables WWAN, false disables it
     * @return boolean whether or not WWAN could be enabled/disabled
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    public static boolean enableWwan(boolean enabled) throws RemoteException, UnsupportedOperationException {
        return Implementation.enableWwan(enabled);
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
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot and wipe the device.
     * <p><b>CAUTION: This wipes all data off the device!<br/>
     * CAUTION: If there's a Google Account active on the device at factory reset time, you will need to login to that account after reboot (Google Factory Reset Protection)!</b></p>
     * @since 1.00
     */
    public static boolean factoryReset(boolean removeAccounts) throws RemoteException, UnsupportedOperationException {
        return Implementation.factoryReset(removeAccounts);
    }

    /**
     * Gets the {@link java.lang.reflect.Array Array} of existing {@link APN Access Point Name (APN)} configurations.
     *
     * @return {@link APN APN[]} {@link java.lang.reflect.Array Array} of available Access Point Name (APN) configurations.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @since 1.00
     */
    public static APN[] getAllApnList() throws RemoteException, UnsupportedOperationException {
        return Implementation.getAllApnList();
    }

    /**
     * Gets the {@link APN Access Point Name (APN) configuration} for a carrier data connection by it's {@link APN#getName() name}.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for a carrier data connection.<br/>
     * @return {@link APN APN} the Access Point Name (APN) configuration for a carrier data connection.
     * If no matching configuration for the {@link APN#getName() name} could be found, the method returns {@code null}.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @since 1.00
     */
    public static APN getApn(String name) throws RemoteException, UnsupportedOperationException {
        return Implementation.getApn(name);
    }

    /**
     * Gets the {@link APN#getId() id} field of an existing Access Point Name (APN) configuration for a carrier data connection.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for a carrier data connection.<br/>
     * @return {@code int} the {@link APN#getId() id} field of an existing Access Point Name (APN) configuration for a carrier data connection.
     * If no matching configuration for the {@link APN#getName() name} could be found, the method returns {@link APN#INVALID_APN INVALID_APN (-1)}.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @since 1.00
     */
    public static int getApnId(String name) throws RemoteException, UnsupportedOperationException {
        return Implementation.getApnId(name);
    }

    /**
     * Reads current Barcode Scanner settings and applies new Barcode Scanner settings to/from files according to the specified file path
     *
     * <p>NOTE: The Filename for the old Barcode Scanner setting will get a suffix "_old" applied.<br/>
     * See {@link #getCurrentScanSettings(String settingsFilePath)} and {@link #setNewScanSettings(String settingsFilePath)} for further details regarding file name and file format specifications</p>
     *
     * @param settingsFilePath {@link java.lang.String String}: Path to the file holding the Scanner settings
     * @return boolean whether or not the Barcode Scanner settings could be written to (a) file(s) and the new settings could applied to the Barcode Scanner successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean getCurrentAndSetNewScanSettings(String settingsFilePath) throws RemoteException, UnsupportedOperationException {
        return Implementation.getCurrentAndSetNewScanSettings(settingsFilePath);
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
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean getCurrentScanSettings(String settingsFilePath) throws RemoteException, UnsupportedOperationException {
        return Implementation.getCurrentScanSettings(settingsFilePath);
    }

    /**
     * Get an {@link java.lang.reflect.Array Array} of existing Google {@link Account Accounts} currently configured for this device.
     *
     * @return {@link java.lang.reflect.Array Array} of {@link Account Account}: The {@link java.lang.reflect.Array Array} of existing Google {@link Account Accounts} currently configured for this device.<br/>
     * In case of failure, this method returns {@code null}.<br/>
     * If no Google {@link Account Accounts} are configured at the time of calling this method, the return value will be an empty {@link java.lang.reflect.Array Array}.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @since 1.00
     */
    public static Account[] getGoogleAccounts() throws RemoteException, UnsupportedOperationException {
        return Implementation.getGoogleAccounts();
    }

    /**
     * Gets a {@link List List} of {@link String Names} of available Keyboards on this device.
     *
     * @return {@link List List} of {@link String String} The Package Names of the available Keyboards on this device.
     * In case of an error, the method returns {@code null}.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @since 1.00
     */
    public static List<String> getKeyboardNames() throws RemoteException, UnsupportedOperationException {
        return Implementation.getKeyboardNames();
    }

    /**
     * Initialize the given Certificate {@link java.security.KeyStore KeyStore} with the given Password.
     *
     * @param storeName {@link java.lang.String String}: Friendly name of the Certificate {@link java.security.KeyStore KeyStore} to be initialized.<br/>
     *                  Can be {@code null} or an empty {@link java.lang.String String}, in which case the default {@link java.security.KeyStore KeyStore} will be used.
     * @param password  {@link java.lang.String String}: Password to be used for the {@link java.security.KeyStore KeyStore}.
     * @return {@code boolean} whether or not the Certificate {@link java.security.KeyStore KeyStore} could be initialized successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean initializeKeyStore(String storeName, String password) throws RemoteException, UnsupportedOperationException {
        return Implementation.initializeKeyStore(storeName, password);
    }

    /**
     * Installs an app by it's apk file name.
     *
     * @param apkFilename {@link java.lang.String String}: File name of the .apk file to be installed.
     * @param update      {@code boolean}: Whether or not to update the app if it's already installed.<br/>
     *                    If you specify "true" but the app is not installed yet, this parameter will have no effect.<br/>
     *                    If you specify "false" but the app is already installed, this method will return false and the installation will fail.
     * @return {@code boolean} whether or not the .apk file could be installed successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean installApk(String apkFilename, boolean update) throws RemoteException, UnsupportedOperationException {
        return Implementation.installApk(apkFilename, update);
    }

    /**
     * Installs a Certificate from a File.
     *
     * @param friendlyName {@link java.lang.String String}: Friendly name of the Certificate to be installed.
     * @param fileName     {@link java.lang.String String}: File Path and Name of the Certificate File.
     * @return {@code boolean} whether or not the Certificate could be installed successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 2.00
     */
    public static boolean installCertificate(String friendlyName, String fileName) throws RemoteException, UnsupportedOperationException {
        return Implementation.installCertificate(friendlyName, fileName);
    }

    /**
     * Locks the Device (i.e. device needs to be unlocked to be used further on)
     *
     * <p>May or may not turn off the display, depending on device settings</p>
     *
     * @return {@code boolean} whether or not the Device could be locked
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean lockDevice() throws RemoteException, UnsupportedOperationException {
        return Implementation.lockDevice();
    }

    /**
     * Mounts/Unmounts the external SD Card
     *
     * @param mount {@code boolean}: true mounts the external SD card, false unmounts it
     * @return boolean whether or not the external SD card could (un)mounted successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean mountSDCard(boolean mount) throws RemoteException, UnsupportedOperationException {
        return Implementation.mountSDCard(mount);
    }

    /**
     * Moves a file from one location to another, with a variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} applied
     *
     * @param sourceFilePath      {@link java.nio.file.Path Path}:      Path to move the file from
     * @param destinationFilePath {@link java.nio.file.Path Path}: Path to move the file to
     * @param options             variable number of {@link java.nio.file.StandardCopyOption StandardCopyOptions} to apply
     * @return {@link java.nio.file.Path Path} to the target file or {@code null} if operation failed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @apiNote This method follows the specification of the Android API {@link java.nio.file.Files#move(Path, Path, CopyOption...) move} method.<br/>
     * It's purpose is to give user applications the ability to move files which are out of reach for non-system apps.
     * <p> Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code null}.
     * @since 1.00
     */
    public static Path moveFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) throws RemoteException, UnsupportedOperationException {
        return Implementation.moveFile(sourceFilePath, destinationFilePath, options);
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
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @apiNote the {@link ReadWriteFileParams#getData() data buffer} of {@link ReadWriteFileParams readWriteFileParams} is optional.<br/>
     * If this method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to hold the data being read.<br/>
     * If this method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will dynamically allocate a buffer holding the data being read.
     * <p> Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @see ReadWriteFileParams ReadWriteFileParams for further details.
     * @since 1.00
     */
    public static boolean readFile(ReadWriteFileParams readWriteFileParams) throws RemoteException, UnsupportedOperationException {
        return Implementation.readFile(readWriteFileParams);
    }

    /**
     * Performs a device reboot
     *
     * @return {@code boolean} whether or not the Reboot could be performed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot, quite like when the user keeps pressing the power key and chooses to "Reboot".
     * @since 1.00
     */
    public static boolean reboot() throws RemoteException, UnsupportedOperationException {
        return Implementation.reboot();
    }

    /**
     * Performs a device reboot into recovery mode
     *
     * @return {@code boolean} whether or not the Recovery Reboot could be performed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot into recovery mode, quite like when the user keeps pressing the power key and chooses to "Reboot" - but using this method it will not reboot the Operating System, but reboot into recovery mode instead.
     * @since 1.00
     */
    public static boolean recovery() throws RemoteException, UnsupportedOperationException {
        return Implementation.recovery();
    }

    /**
     * Specifies whether or not the Browser may remember Login Passwords
     *
     * @param enable {@code boolean}: true enables remembering Login Passwords, false disables it
     * @return {@code boolean} whether or not the setting was applied successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean rememberPasswords(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.rememberPasswords(enable);
    }

    /**
     * Removes (attempts to remove) a certain account from the device
     *
     * @param account {@link android.accounts.Account Account}: Account to be removed
     * @return {@code boolean} whether or not the account removal was successful
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean removeAccount(Account account) throws RemoteException, UnsupportedOperationException {
        return Implementation.removeAccount(account);
    }

    /**
     * Removes (attempts to remove) all configured accounts of all kind from the device<br/>
     *
     * @return {@code boolean} whether or not the account removal was successful
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote This method usually fails, because certain accounts cannot be removed.<br/>
     * Those that can be removed will be removed nevertheless.
     * @since 1.00
     */
    public static boolean removeAllAccounts() throws RemoteException, UnsupportedOperationException {
        return Implementation.removeAllAccounts();
    }

    /**
     * Removes (attempts to remove) all configured Google accounts from the device
     *
     * @return {@code boolean} whether or not the account removal was successful
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean removeAllGoogleAccounts() throws RemoteException, UnsupportedOperationException {
        return Implementation.removeAllGoogleAccounts();
    }

    /**
     * Removes an existing network according to it's {@link android.net.wifi.WifiConfiguration#SSID SSID}.
     *
     * @param ssid {@link java.lang.String String}: The {@link android.net.wifi.WifiConfiguration#SSID SSID} of the network to be removed
     * @return {@code boolean} whether or not the existing network could be removed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean removeNetwork(String ssid) throws RemoteException, UnsupportedOperationException {
        return Implementation.removeNetwork(ssid);
    }

    /**
     * Removes an existing network according to it's {@link android.net.wifi.WifiConfiguration#networkId networkId}.
     *
     * @param networkId {@link java.lang.Integer int}: The {@link android.net.wifi.WifiConfiguration#networkId networkId} of the network to be removed
     * @return {@code boolean} whether or not the existing network could be removed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean removeNetworkId(int networkId) throws RemoteException, UnsupportedOperationException {
        return Implementation.removeNetworkId(networkId);
    }

    /**
     * Resets the device password (PIN) to a new value
     *
     * @param newPassword {@link java.lang.String String}: The new Device Password to be set
     * @return {@code boolean} whether or not the new Password could be set
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean resetPassword(String newPassword) throws RemoteException, UnsupportedOperationException {
        return Implementation.resetPassword(newPassword);
    }

    /**
     * Specifies whether or not the Browser may save Form Input Data
     *
     * @param enable {@code boolean}: true enables saving Form Input Data, false disables it
     * @return {@code boolean} whether or not the setting was applied successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean saveFormData(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.saveFormData(enable);
    }

    /**
     * Sets a new Device Date and Time
     *
     * @param date {@link java.util.Date Date}: The Date and Time to be set
     * @return {@code boolean} whether or not the new Date/Time could be set
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @apiNote Requires Android N (Android 7) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    public static boolean setDateTime(Date date) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDateTime(date);
    }

    /**
     * Sets the default Browser Homepage
     *
     * @param homePage {@link java.lang.String String}: URL of the new Homepage
     * @return {@code boolean} whether or not the default Browser Homepage could be set successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean setDefaultHomePage(String homePage) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDefaultHomePage(homePage);
    }

    /**
     * Specifies a new Default Launcher
     *
     * @param packageName {@link java.lang.String String}: Package Name of the new Default Launcher
     * @return {@code boolean} whether or not the Default Launcher was set successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean setDefaultLauncher(String packageName) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDefaultLauncher(packageName);
    }

    /**
     * Sets the default and current Keyboard on this device.
     *
     * @param keyboardName {@link java.lang.String String}: The Package Name of the Keyboard to be used.<br/>
     *                     Use {@link #getKeyboardNames} to fetch a {@link List List} of available Keyboard Names.
     * @return {@code boolean} whether or not the Keyboard was set successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean setKeyboard(String keyboardName) throws RemoteException, UnsupportedOperationException {
        return Implementation.setKeyboard(keyboardName);
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
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean setNewScanSettings(String settingsFilePath) throws RemoteException, UnsupportedOperationException {
        return Implementation.setNewScanSettings(settingsFilePath);
    }

    /**
     * Sets a new preferred Access Point Name (APN) configuration for a carrier data connections.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for becoming the new preferred carrier data connection.<br/>
     * @return {@code boolean} whether or not the Access Point Name (APN) configuration in question could be set as the new preferred APN configuration.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean setPreferredApn(String name) throws RemoteException, UnsupportedOperationException {
        return Implementation.setPreferredApn(name);
    }

    /**
     * Specifies a new Screen Lock Timeout in Milliseconds
     *
     * @param milliseconds {@link java.lang.Integer int}: The new Screen Lock Timeout Value in Milliseconds
     * @return {@code boolean} whether or not the Screen Lock Timeout was set successfully
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean setScreenLockTimeout(int milliseconds) throws RemoteException, UnsupportedOperationException {
        return Implementation.setScreenLockTimeout(milliseconds);
    }

    /**
     * Sets a new Device Time Zone from a specified android.icu.util.TimeZone
     *
     * @param timeZone {@link android.icu.util.TimeZone android.icu.util.TimeZone}: The new Time Zone to be set
     * @return {@code boolean} whether or not the new Time Zone could be set
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Requires Android N (Android 7) or later.<br/>
     * Devices running Android M (Android 6) or earlier should use {@link #setTimeZone(java.util.TimeZone) setTimeZone(java.util.TimeZone timeZone)} instead.
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @since 1.00
     */
    public static boolean setTimeZone(android.icu.util.TimeZone timeZone) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTimeZone(timeZone);
    }

    /**
     * Sets a new Device Time Zone from a specified java.util.TimeZone
     *
     * @param timeZone {@link java.util.TimeZone java.util.TimeZone}: The new Time Zone to be set
     * @return {@code boolean} whether or not the new Time Zone could be set
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote This Method is meant to be used on devices running Requires Android M (Android 6) or earlier.<br/>
     * Devices running Android N (Android 7) or later should use {@link #setTimeZone(android.icu.util.TimeZone) setTimeZone(android.icu.util.TimeZone timeZone)} instead.
     * @since 2.02
     */
    public static boolean setTimeZone(java.util.TimeZone timeZone) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTimeZone(timeZone);
    }

    /**
     * Sets a new Device Time Zone from a specified TimeZone ID
     *
     * @param timeZoneID {@link java.lang.String String}: The ID of the new Time Zone to be set.<br/>
     *                                                  Use {@link android.icu.util.TimeZone#getAvailableIDs() android.icu.util.TimeZone.getAvailableIDs()} or {@link java.util.TimeZone#getAvailableIDs() java.util.TimeZone.getAvailableIDs()} to get a list of available TimeZone IDs on your device.
     * @return {@code boolean} whether or not the new Time Zone could be set
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 2.02
     */
    public static boolean setTimeZone(String timeZoneID) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTimeZone(timeZoneID);
    }

    /**
     * Performs a device shutdown
     *
     * @return {@code boolean} whether or not the Shutdown could be performed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently power off, quite like when the user keeps pressing the power key and chooses to "Power off".
     * @since 1.00
     */
    public static boolean shutdown() throws RemoteException, UnsupportedOperationException {
        return Implementation.shutdown();
    }

    /**
     * Displays a Toast Message containing the specified String.
     *
     * <p>This method is supposed to be used for testing purpose only, for instance in order to check whether the service binding from a sample application is working or not.</p>
     *
     * @param message {@link java.lang.String String}: The Message to be displayed
     * @return {@code boolean} whether or not the Message could be shown
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean testMessage(String message) throws RemoteException, UnsupportedOperationException {
        return Implementation.testMessage(message);
    }

    /**
     * Updates an existing Access Point Name (APN) configuration for a carrier data connection.
     *
     * @param apn {@link APN APN}:         The Access Point Name (APN) configuration for a carrier data connection, holding the new data for this configuration.<br/>
     *            The "id" field holds the ID of the existing APN configuration to be updated.<br/>
     *            Use {@link EDTLibrary#getApnId getApnId} to fetch the ID from a given name if required.
     * @return {@code boolean} whether or not the new Access Point Name (APN) configuration has been applied successfully.<br/>
     * If no matching configuration for the ID could be found, the method returns {@code false}.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean updateApn(APN apn) throws RemoteException, UnsupportedOperationException {
        return Implementation.updateApn(apn);
    }

    /**
     * Update an existing network description of the set of configured networks. The {@link android.net.wifi.WifiConfiguration#networkId networkId} field must be set to the ID of the existing network being updated.
     *
     * @param wifiConfiguration {@link android.net.wifi.WifiConfiguration WifiConfiguration}: The set of variables that describe the configuration, contained in a WifiConfiguration object.
     *                          It may be sparse, so that only the items that are being changed are non-null.
     * @return {@code boolean} whether or not the existing network could be updated
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean updateNetwork(android.net.wifi.WifiConfiguration wifiConfiguration) throws RemoteException, UnsupportedOperationException {
        return Implementation.updateNetwork(wifiConfiguration);
    }

    /**
     * Uninstalls an app by it's package name.
     *
     * @param packageName {@link java.lang.String String}: Package Name of the App to be uninstalled.
     * @return {@code boolean} whether or not the app could be uninstalled successfully.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean uninstallPackage(String packageName) throws RemoteException, UnsupportedOperationException {
        return Implementation.uninstallPackage(packageName);
    }

    /**
     * Updates an existing Access Point Name (APN) configuration for a carrier data connection.
     *
     * @param name {@link String String}:         The {@link APN#getName() name} field of the Access Point Name (APN) configuration in question for a carrier data connection.<br/>
     * @return {@code boolean} whether or not the Access Point Name (APN) configuration in question refers to a valid APN configuration.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @since 1.00
     */
    public static boolean verifyApn(String name) throws RemoteException, UnsupportedOperationException {
        return Implementation.verifyApn(name);
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
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EDTLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EDTLibrary this class} for further details.
     * @apiNote the {@link ReadWriteFileParams#getData() data buffer} of {@link ReadWriteFileParams readWriteFileParams} is mandatory.<br/>
     * If this method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to write the specified amount of data.<br/>
     * If this method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will fail.
     * <p>Requires Android O (Android 8) or later.<br/>
     * If you call this method on a device running an earlier version of Android, this method will return {@code false}.
     * @see ReadWriteFileParams ReadWriteFileParams for further details.
     * @since 1.00
     */
    public static boolean writeFile(ReadWriteFileParams readWriteFileParams) throws RemoteException, UnsupportedOperationException {
        return Implementation.writeFile(readWriteFileParams);
    }

    /**
     * Add a new Callback to the Queue of Callbacks to be processed once the EDT Service becomes available
     *
     * @param callback {@link LibraryCallback LibraryCallback}: Instance of the {@link LibraryCallback LibraryCallback} Interface which holds the {@link LibraryCallback#onLibraryReady() onLibraryReady()} Method which will get called once the regarding library becomes available
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void onLibraryReady(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        EDTServiceConnection.getInstance().addEdtCallback(callback);
    }

    @SuppressWarnings("SpellCheckingInspection")
    private static final class Implementation {
        private static boolean addNetwork(android.net.wifi.WifiConfiguration wifiConfiguration) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            WifiConfigurationParcelable wifiConfigurationParcelable = new WifiConfigurationParcelable(wifiConfiguration);
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().addNetwork(unsupported, wifiConfigurationParcelable);
                    checkMethodUnsupported("openScanner", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().edtService().addNetwork(unsupported, wifiConfigurationParcelable);
            checkMethodUnsupported("addNetwork", unsupported);
            return retVal;
        }
        private static boolean allowUnknownSources(boolean allow) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().allowUnknownSources(unsupported, allow);
                    checkMethodUnsupported("allowUnknownSources", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().allowUnknownSources(unsupported, allow);
            checkMethodUnsupported("allowUnknownSources", unsupported);
            return retVal;
        }
        private static boolean clearCacheForPackage(String packageName) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().clearCacheForPackage(unsupported, packageName);
                            checkMethodUnsupported("clearCacheForPackage", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().clearCacheForPackage(unsupported, packageName);
            checkMethodUnsupported("clearCacheForPackage", unsupported);
            return retVal;
        }
        private static boolean clearClipboard() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().clearClipboard(unsupported);
                            checkMethodUnsupported("clearClipboard", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().clearClipboard(unsupported);
            checkMethodUnsupported("clearClipboard", unsupported);
            return retVal;
        }
        private static boolean clearDataForPackage(String packageName) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().clearDataForPackage(unsupported, packageName);
                            checkMethodUnsupported("clearDataForPackage", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().clearDataForPackage(unsupported, packageName);
            checkMethodUnsupported("clearDataForPackage", unsupported);
            return retVal;
        }
        private static boolean clearPassword() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().clearPassword(unsupported);
                            checkMethodUnsupported("clearPassword", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().clearPassword(unsupported);
            checkMethodUnsupported("clearPassword", unsupported);
            return retVal;
        }
        private static boolean connectNetwork(String ssid) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().connectNetwork(unsupported, ssid);
                            checkMethodUnsupported("connectNetwork", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().connectNetwork(unsupported, ssid);
            checkMethodUnsupported("connectNetwork", unsupported);
            return retVal;
        }
        private static boolean connectNetworkId(int networkId) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().connectNetworkId(unsupported, networkId);
                            checkMethodUnsupported("connectNetworkId", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().connectNetworkId(unsupported, networkId);
            checkMethodUnsupported("connectNetworkId", unsupported);
            return retVal;
        }
        private static Path copyFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                throw new UnsupportedOperationException("Requires Android O or later!");
            }
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            List<String> copyOptionStrings = options == null ? null : Arrays.stream(options).map(Object::toString).collect(Collectors.toList());
            String sRetVal = getInstance().edtService().copyFile(unsupported, sourceFilePath.toString(), destinationFilePath.toString(), copyOptionStrings);
            Path retVal = sRetVal == null ? null : Paths.get(sRetVal);
            checkMethodUnsupported("copyFile", unsupported);
            return retVal;
        }
        private static boolean createNewApn(APN apn, boolean setAsDefault) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().createNewApn(unsupported, new APNParcelable(apn), setAsDefault);
                            checkMethodUnsupported("createNewApn", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().createNewApn(unsupported, new APNParcelable(apn), setAsDefault);
            checkMethodUnsupported("createNewApn", unsupported);
            return retVal;
        }
        private static boolean deleteFile(String filePath) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().deleteFile(unsupported, filePath);
                            checkMethodUnsupported("deleteFile", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().deleteFile(unsupported, filePath);
            checkMethodUnsupported("deleteFile", unsupported);
            return retVal;
        }
        private static boolean enableAdb(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableAdb(unsupported, enable);
                            checkMethodUnsupported("enableAdb", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableAdb(unsupported, enable);
            checkMethodUnsupported("enableAdb", unsupported);
            return retVal;
        }
        private static boolean enableApplication(String packageName, boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    if (enable) {
                        getInstance().edtService().enableApplication(unsupported, packageName);
                    } else {
                        getInstance().edtService().disableApplication(unsupported, packageName);
                    }
                    checkMethodUnsupported("enableApplication", unsupported);
                });
                return true;
            }
            boolean retVal = enable ? getInstance().edtService().enableApplication(unsupported, packageName) : getInstance().edtService().disableApplication(unsupported, packageName);
            checkMethodUnsupported("enableApplication", unsupported);
            return retVal;
        }
        private static boolean enableBackgroundData(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableBackgroundData(unsupported, enable);
                            checkMethodUnsupported("enableBackgroundData", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableBackgroundData(unsupported, enable);
            checkMethodUnsupported("enableBackgroundData", unsupported);
            return retVal;
        }
        private static boolean enableBatteryOptimization(String packageName, boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    if (enable) {
                        getInstance().edtService().enableBatteryOptimization(unsupported, packageName);
                    } else {
                        getInstance().edtService().disableBatteryOptimization(unsupported, packageName);
                    }
                    checkMethodUnsupported("enableBatteryOptimization", unsupported);
                });
                return true;
            }
            boolean retVal = enable ? getInstance().edtService().enableBatteryOptimization(unsupported, packageName) : getInstance().edtService().disableBatteryOptimization(unsupported, packageName);
            checkMethodUnsupported("enableBatteryOptimization", unsupported);
            return retVal;
        }
        private static boolean enableBluetooth(boolean enabled) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableBluetooth(unsupported, enabled);
                            checkMethodUnsupported("enableBluetooth", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableBluetooth(unsupported, enabled);
            checkMethodUnsupported("enableBluetooth", unsupported);
            return retVal;
        }
        private static boolean enableCameras(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableCameras(unsupported, enable);
                            checkMethodUnsupported("enableCameras", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableCameras(unsupported, enable);
            checkMethodUnsupported("enableCameras", unsupported);
            return retVal;
        }
        private static boolean enableClipboard(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableClipboard(unsupported, enable);
                            checkMethodUnsupported("enableClipboard", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableClipboard(unsupported, enable);
            checkMethodUnsupported("enableClipboard", unsupported);
            return retVal;
        }
        private static boolean enableDeveloperMode(boolean enabled) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableDeveloperMode(unsupported, enabled);
                            checkMethodUnsupported("enableDeveloperMode", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableDeveloperMode(unsupported, enabled);
            checkMethodUnsupported("enableDeveloperMode", unsupported);
            return retVal;
        }
        private static boolean enableDeviceAdmin(String packageName, String className, boolean makeAdmin) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableDeviceAdmin(unsupported, packageName, className, makeAdmin);
                            checkMethodUnsupported("enableDeviceAdmin", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableDeviceAdmin(unsupported, packageName, className, makeAdmin);
            checkMethodUnsupported("enableDeviceAdmin", unsupported);
            return retVal;
        }
        private static boolean enableGps(boolean enabled) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableGps(unsupported, enabled);
                            checkMethodUnsupported("enableGps", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableGps(unsupported, enabled);
            checkMethodUnsupported("enableGps", unsupported);
            return retVal;
        }
        private static boolean enableMassStorage(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableMassStorage(unsupported, enable);
                            checkMethodUnsupported("enableMassStorage", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableMassStorage(unsupported, enable);
            checkMethodUnsupported("enableMassStorage", unsupported);
            return retVal;
        }
        private static boolean enableNfc(boolean enabled) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableNfc(unsupported, enabled);
                            checkMethodUnsupported("enableNfc", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableNfc(unsupported, enabled);
            checkMethodUnsupported("enableNfc", unsupported);
            return retVal;
        }
        private static boolean enableRoaming(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableRoaming(unsupported, enable);
                            checkMethodUnsupported("enableRoaming", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableRoaming(unsupported, enable);
            checkMethodUnsupported("enableRoaming", unsupported);
            return retVal;
        }
        private static boolean enableWifi(boolean enabled) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableWifi(unsupported, enabled);
                            checkMethodUnsupported("enableWifi", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableWifi(unsupported, enabled);
            checkMethodUnsupported("enableWifi", unsupported);
            return retVal;
        }
        private static boolean enableWwan(boolean enabled) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().enableWwan(unsupported, enabled);
                            checkMethodUnsupported("enableWwan", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().enableWwan(unsupported, enabled);
            checkMethodUnsupported("enableWwan", unsupported);
            return retVal;
        }
        private static boolean factoryReset(boolean removeAccounts) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().factoryReset(unsupported, removeAccounts);
                            checkMethodUnsupported("factoryReset", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().factoryReset(unsupported, removeAccounts);
            checkMethodUnsupported("factoryReset", unsupported);
            return retVal;
        }
        private static APN[] getAllApnList() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            APNParcelable[] apnParcelables = getInstance().edtService().getAllApnList(unsupported);
            if (apnParcelables == null) return null;
            APN[] apns = new APN[apnParcelables.length];
            int i = 0;
            for (APNParcelable apnParcelable : apnParcelables) apns[i++] = apnParcelable.getAPN();
            checkMethodUnsupported("getAllApnList", unsupported);
            return apns;
        }
        private static APN getApn(String name) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            APN retVal = getInstance().edtService().getApn(unsupported, name).getAPN();
            checkMethodUnsupported("getApn", unsupported);
            return retVal;
        }
        private static int getApnId(String name) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtService().getApnId(unsupported, name);
            checkMethodUnsupported("getApnId", unsupported);
            return retVal;
        }
        private static boolean getCurrentAndSetNewScanSettings(String settingsFilePath) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().getCurrentAndSetNewScanSettings(unsupported, settingsFilePath);
                            checkMethodUnsupported("getCurrentAndSetNewScanSettings", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().getCurrentAndSetNewScanSettings(unsupported, settingsFilePath);
            checkMethodUnsupported("getCurrentAndSetNewScanSettings", unsupported);
            return retVal;
        }
        private static boolean getCurrentScanSettings(String settingsFilePath) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().getCurrentScanSettings(unsupported, settingsFilePath);
                            checkMethodUnsupported("getCurrentScanSettings", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().getCurrentScanSettings(unsupported, settingsFilePath);
            checkMethodUnsupported("getCurrentScanSettings", unsupported);
            return retVal;
        }
        private static Account[] getGoogleAccounts() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            Account[] retVal = getInstance().edtService().getGoogleAccounts(unsupported);
            checkMethodUnsupported("getGoogleAccounts", unsupported);
            return retVal;
        }
        private static List<String> getKeyboardNames() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            List<String> retVal = getInstance().edtService().getKeyboardNames(unsupported);
            checkMethodUnsupported("getKeyboardNames", unsupported);
            return retVal;
        }
        private static boolean initializeKeyStore(String storeName, String password) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().initializeKeyStore(unsupported, storeName, password);
                            checkMethodUnsupported("initializeKeyStore", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().initializeKeyStore(unsupported, storeName, password);
            checkMethodUnsupported("initializeKeyStore", unsupported);
            return retVal;
        }
        private static boolean installApk(String apkFilename, boolean update) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().installApk(unsupported, apkFilename, update);
                            checkMethodUnsupported("installApk", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().installApk(unsupported, apkFilename, update);
            checkMethodUnsupported("installApk", unsupported);
            return retVal;
        }
        private static boolean installCertificate(String friendlyName, String fileName) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().installCertificate(unsupported, friendlyName, fileName);
                            checkMethodUnsupported("installCertificate", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().installCertificate(unsupported, friendlyName, fileName);
            checkMethodUnsupported("installCertificate", unsupported);
            return retVal;
        }
        private static boolean lockDevice() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().lockDevice(unsupported);
                            checkMethodUnsupported("lockDevice", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().lockDevice(unsupported);
            checkMethodUnsupported("lockDevice", unsupported);
            return retVal;
        }
        private static boolean mountSDCard(boolean mount) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().mountSDCard(unsupported, mount);
                            checkMethodUnsupported("mountSDCard", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().mountSDCard(unsupported, mount);
            checkMethodUnsupported("mountSDCard", unsupported);
            return retVal;
        }
        private static Path moveFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                throw new UnsupportedOperationException("Requires Android O or later!");
            }
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            List<String> copyOptionStrings = options == null ? null : Arrays.stream(options).map(Object::toString).collect(Collectors.toList());
            String sRetVal = getInstance().edtService().moveFile(unsupported, sourceFilePath.toString(), destinationFilePath.toString(), copyOptionStrings);
            Path retVal = sRetVal == null ? null : Paths.get(sRetVal);
            checkMethodUnsupported("moveFile", unsupported);
            return retVal;
        }
        private static boolean readFile(ReadWriteFileParams readWriteFileParams) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal = getInstance().edtService().readFile(unsupported, new ReadWriteFileParamsParcelable(readWriteFileParams));
            checkMethodUnsupported("readFile", unsupported);
            return retVal;
        }
        private static boolean reboot() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().reboot(unsupported);
                            checkMethodUnsupported("reboot", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().reboot(unsupported);
            checkMethodUnsupported("reboot", unsupported);
            return retVal;
        }
        private static boolean recovery() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().recovery(unsupported);
                            checkMethodUnsupported("recovery", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().recovery(unsupported);
            checkMethodUnsupported("recovery", unsupported);
            return retVal;
        }
        private static boolean rememberPasswords(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().rememberPasswords(unsupported, enable);
                            checkMethodUnsupported("rememberPasswords", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().rememberPasswords(unsupported, enable);
            checkMethodUnsupported("rememberPasswords", unsupported);
            return retVal;
        }
        private static boolean removeAccount(Account account) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().removeAccount(unsupported, account);
                            checkMethodUnsupported("removeAccount", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().removeAccount(unsupported, account);
            checkMethodUnsupported("removeAccount", unsupported);
            return retVal;
        }
        private static boolean removeAllAccounts() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().removeAllAccounts(unsupported);
                            checkMethodUnsupported("removeAllAccounts", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().removeAllAccounts(unsupported);
            checkMethodUnsupported("removeAllAccounts", unsupported);
            return retVal;
        }
        private static boolean removeAllGoogleAccounts() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().removeAllGoogleAccounts(unsupported);
                            checkMethodUnsupported("removeAllGoogleAccounts", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().removeAllGoogleAccounts(unsupported);
            checkMethodUnsupported("removeAllGoogleAccounts", unsupported);
            return retVal;
        }
        private static boolean removeNetwork(String ssid) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().removeNetwork(unsupported, ssid);
                            checkMethodUnsupported("removeNetwork", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().removeNetwork(unsupported, ssid);
            checkMethodUnsupported("removeNetwork", unsupported);
            return retVal;
        }
        private static boolean removeNetworkId(int networkId) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().removeNetworkId(unsupported, networkId);
                            checkMethodUnsupported("removeNetworkId", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().removeNetworkId(unsupported, networkId);
            checkMethodUnsupported("removeNetworkId", unsupported);
            return retVal;
        }
        private static boolean resetPassword(String newPassword) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().resetPassword(unsupported, newPassword);
                            checkMethodUnsupported("resetPassword", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().resetPassword(unsupported, newPassword);
            checkMethodUnsupported("resetPassword", unsupported);
            return retVal;
        }
        private static boolean saveFormData(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().saveFormData(unsupported, enable);
                            checkMethodUnsupported("saveFormData", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().saveFormData(unsupported, enable);
            checkMethodUnsupported("saveFormData", unsupported);
            return retVal;
        }
        private static boolean setDateTime(Date date) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) { // requires Android N or later
                throw new UnsupportedOperationException("Requires Android N or later!");
            }
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // Convert Calendar to integer values because AIDL can handle limited data types only
            boolean retVal = getInstance().edtService().setDateTime(unsupported, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
            checkMethodUnsupported("setDateTime", unsupported);
            return retVal;
        }
        private static boolean setDefaultHomePage(String homePage) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().setDefaultHomePage(unsupported, homePage);
                            checkMethodUnsupported("setDefaultHomePage", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().setDefaultHomePage(unsupported, homePage);
            checkMethodUnsupported("setDefaultHomePage", unsupported);
            return retVal;
        }
        private static boolean setDefaultLauncher(String packageName) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().setDefaultLauncher(unsupported, packageName);
                            checkMethodUnsupported("setDefaultLauncher", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().setDefaultLauncher(unsupported, packageName);
            checkMethodUnsupported("setDefaultLauncher", unsupported);
            return retVal;
        }
        private static boolean setKeyboard(String keyboardName) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().setKeyboard(unsupported, keyboardName);
                            checkMethodUnsupported("setKeyboard", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().setKeyboard(unsupported, keyboardName);
            checkMethodUnsupported("setKeyboard", unsupported);
            return retVal;
        }
        private static boolean setNewScanSettings(String settingsFilePath) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().setNewScanSettings(unsupported, settingsFilePath);
                            checkMethodUnsupported("setNewScanSettings", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().setNewScanSettings(unsupported, settingsFilePath);
            checkMethodUnsupported("setNewScanSettings", unsupported);
            return retVal;
        }
        private static boolean setPreferredApn(String name) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().setPreferredApn(unsupported, name);
                            checkMethodUnsupported("setPreferredApn", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().setPreferredApn(unsupported, name);
            checkMethodUnsupported("setPreferredApn", unsupported);
            return retVal;
        }
        private static boolean setScreenLockTimeout(int milliseconds) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().setScreenLockTimeout(unsupported, milliseconds);
                            checkMethodUnsupported("setScreenLockTimeout", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().setScreenLockTimeout(unsupported, milliseconds);
            checkMethodUnsupported("setScreenLockTimeout", unsupported);
            return retVal;
        }
        private static boolean setTimeZone(android.icu.util.TimeZone timeZone) throws RemoteException, UnsupportedOperationException {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) { // requires Android N or later
                throw new UnsupportedOperationException("Requires Android N or later!");
            }
            return setTimeZone(timeZone.getID());
        }
        private static boolean setTimeZone(java.util.TimeZone timeZone) throws RemoteException, UnsupportedOperationException {
            return setTimeZone(timeZone.getID());
        }
        private static boolean setTimeZone(String timeZoneID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().setTimeZone(unsupported, timeZoneID);
                    checkMethodUnsupported("setTimeZone", unsupported);
                });
                return true;
            }
            // Convert TimeZone to String because AIDL can handle limited data types only
            boolean retVal = getInstance().edtService().setTimeZone(unsupported, timeZoneID);
            checkMethodUnsupported("setTimeZone", unsupported);
            return retVal;
        }
        private static boolean shutdown() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().shutdown(unsupported);
                            checkMethodUnsupported("shutdown", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().shutdown(unsupported);
            checkMethodUnsupported("shutdown", unsupported);
            return retVal;
        }
        private static boolean testMessage(String message) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().testMessage(unsupported, message);
                            checkMethodUnsupported("testMessage", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().testMessage(unsupported, message);
            checkMethodUnsupported("testMessage", unsupported);
            return retVal;
        }
        private static boolean updateApn(APN apn) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().updateApn(unsupported, new APNParcelable(apn));
                            checkMethodUnsupported("updateApn", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().updateApn(unsupported, new APNParcelable(apn));
            checkMethodUnsupported("updateApn", unsupported);
            return retVal;
        }
        private static boolean updateNetwork(android.net.wifi.WifiConfiguration wifiConfiguration) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            WifiConfigurationParcelable wifiConfigurationParcelable = new WifiConfigurationParcelable(wifiConfiguration);
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().updateNetwork(unsupported, wifiConfigurationParcelable);
                            checkMethodUnsupported("updateNetwork", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().updateNetwork(unsupported, wifiConfigurationParcelable);
            checkMethodUnsupported("updateNetwork", unsupported);
            return retVal;
        }
        private static boolean uninstallPackage(String packageName) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().uninstallPackage(unsupported, packageName);
                            checkMethodUnsupported("uninstallPackage", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().uninstallPackage(unsupported, packageName);
            checkMethodUnsupported("uninstallPackage", unsupported);
            return retVal;
        }
        private static boolean verifyApn(String name) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtService() == null) {
                onLibraryReady(() -> {
                    getInstance().edtService().verifyApn(unsupported, name);
                            checkMethodUnsupported("verifyApn", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().edtService().verifyApn(unsupported, name);
            checkMethodUnsupported("verifyApn", unsupported);
            return retVal;
        }
        private static boolean writeFile(ReadWriteFileParams readWriteFileParams) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal = getInstance().edtService().readFile(unsupported, new ReadWriteFileParamsParcelable(readWriteFileParams));
            checkMethodUnsupported("writeFile", unsupported);
            return retVal;
        }

    }


}
