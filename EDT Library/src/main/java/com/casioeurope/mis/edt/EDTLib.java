package com.casioeurope.mis.edt;

import android.accounts.Account;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.RemoteException;

import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The <b>CASIO Enterprise Developer Tools Library</b> Main Class<br/><br/>
 * This Class holds all static methods required for a Develope to easily access methods and properties of the device where system access rights are required.<br/><br/>
 */
public class EDTLib {

    private static EDTLib instance;

    private EDTLib() {
    }

    /**
     * Add a new network description to the set of configured networks. The {@link android.net.wifi.WifiConfiguration#networkId networkId} field of the supplied configuration object is ignored.
     *
     * @param wifiConfiguration {@link android.net.wifi.WifiConfiguration WifiConfiguration}: The set of variables that describe the configuration, contained in a WifiConfiguration object
     * @return boolean whether or not the new network could be added
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
     * @param allow {@link java.lang.Boolean boolean}: true allows unknown sources, false disallows them
     * @return boolean whether or not the Setting could be applied
     */
    @SuppressWarnings("unused")
    public static boolean allowUnknownSources(boolean allow) {
        try {
            return getInstance().edtService().allowUnknownSources(allow);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Clears the device password (PIN)
     *
     * @return boolean whether or not the Password could be cleared
     */
    @SuppressWarnings("unused")
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
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection"})
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
     */
    @SuppressWarnings("unused")
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
     */
    @SuppressWarnings({"unused", "JavadocReference"})
    public static Path copyFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) {
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
     * Removes the specified file
     *
     * @param filePath {@link java.lang.String String}: Path (including name) of the file to be removed
     * @return boolean whether or not the file has been removed successfully
     */
    @SuppressWarnings("unused")
    public static boolean deleteFile(String filePath) {
        try {
            return getInstance().edtService().deleteFile(filePath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies whether or not Background Data use is permitted
     *
     * @param enable {@link java.lang.Boolean boolean}: true enables Background Data, false disables it
     * @return boolean whether or not the setting was applied successfully
     */
    @SuppressWarnings("unused")
    public static boolean enableBackgroundData(boolean enable) {
        try {
            return getInstance().edtService().enableBackgroundData(enable);
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
     * @param enabled {@link java.lang.Boolean boolean}: true enables Bluetooth, false disables it
     * @return boolean whether or not Bluetooth could be enabled/disabled
     */
    @SuppressWarnings("unused")
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
     * @param enable {@link java.lang.Boolean boolean}: true enables the Camera usage, false disables it
     * @return boolean whether or not the setting was applied successfully
     */
    @SuppressWarnings("unused")
    public static boolean enableCameras(boolean enable) {
        try {
            return getInstance().edtService().enableCameras(enable);
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
     * @param enabled {@link java.lang.Boolean boolean}: true enables Developer Mode, false disables it
     * @return boolean whether or not Developer Mode could be enabled/disabled
     */
    @SuppressWarnings({"unused"})
    public static boolean enableDeveloperMode(boolean enabled) {
        try {
            return getInstance().edtService().enableDeveloperMode(enabled);
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
     * @param enabled {@link java.lang.Boolean boolean}: true enables GPS, false disables it
     * @return boolean whether or not GPS could be enabled/disabled
     */
    @SuppressWarnings("unused")
    public static boolean enableGps(boolean enabled) {
        try {
            return getInstance().edtService().enableGps(enabled);
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
     * @param enabled {@link java.lang.Boolean boolean}: true enables NFC, false disables it
     * @return boolean whether or not NFC could be enabled/disabled
     */
    @SuppressWarnings("unused")
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
     * @param enable {@link java.lang.Boolean boolean}: true enables Data Roaming false disables it
     * @return boolean whether or not the setting was applied successfully
     */
    @SuppressWarnings("unused")
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
     * @param enabled {@link java.lang.Boolean boolean}: true enables Wifi, false disables it
     * @return boolean whether or not Wifi could be enabled/disabled
     */
    @SuppressWarnings("unused")
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
     * @param enabled {@link java.lang.Boolean boolean}: true enables WWAN, false disables it
     * @return boolean whether or not WWAN could be enabled/disabled
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection"})
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
     * @return boolean whether or not the Factory Reset could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot and wipe the device.
     * <p><b>CAUTION: This wipes all data off the device!<br/>
     * CAUTION: If there's a Google Account active on the device at factory reset time, you will need to login to that account after reboot (Google Factory Reset Protection)!</b></p>
     */
    @SuppressWarnings("unused")
    public static boolean factoryReset() {
        try {
            return getInstance().edtService().factoryReset();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Reads current Barcode Scanner settings and applies new Barcode Scanner settings to/from files according to the specified file path
     *
     * <p>NOTE: The Filename for the old Barcode Scanner setting will get a suffix "_old" applied.<br/>
     * See {@link #getCurrentScanSettings(String settingsFilePath)} and {@link #setNewScanSettings(String settingsFilePath)} for further details regarding file name and file format specifications</p>
     *
     * @param settingsFilePath {@link java.lang.String String}: Path to the file holding the Scanner settings
     * @return boolean whether or not the Barcode Scanner settings could be written to (a) file(s) and the new settings could applied to the Barcode Scanner successfully
     */
    @SuppressWarnings("unused")
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
     */
    @SuppressWarnings("unused")
    public static boolean getCurrentScanSettings(String settingsFilePath) {
        try {
            return getInstance().edtService().getCurrentScanSettings(settingsFilePath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static EDTLib getInstance() {
        if (EDTLib.instance == null) {
            EDTLib.instance = new EDTLib();
        }
        return EDTLib.instance;
    }

    /**
     * Locks the Device (i.e. device needs to be unlocked to be used further on)
     *
     * <p>May or may not turn off the display, depending on device settings</p>
     *
     * @return boolean whether or not the Device could be locked
     */
    @SuppressWarnings("unused")
    public static boolean lockDevice() {
        try {
            return getInstance().edtService().lockDevice();
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
     */
    @SuppressWarnings({"unused", "JavadocReference"})
    public static Path moveFile(Path sourceFilePath, Path destinationFilePath, CopyOption... options) {
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
     * ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.fromPath(Paths.get("/sdcard/Download/devinfo.html"))
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
     * @return boolean whether or not the file data has been read successfully
     * @apiNote the {@link ReadWriteFileParams#getData() data buffer} of {@link ReadWriteFileParams readWriteFileParams} is optional.<br/>
     * If this method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to hold the data being read.<br/>
     * If this method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will dynamically allocate a buffer holding the data being read.
     * @see ReadWriteFileParams ReadWriteFileParams for further details.
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the Reboot could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot, quite like when the user keeps pressing the power key and chooses to "Reboot".
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the Recovery Reboot could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently reboot into recovery mode, quite like when the user keeps pressing the power key and chooses to "Reboot" - but using this method it will not reboot the Operating System, but reboot into recovery mode instead.
     */
    @SuppressWarnings("unused")
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
     * @param enable {@link java.lang.Boolean boolean}: true enables remembering Login Passwords, false disables it
     * @return boolean whether or not the setting was applied successfully
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the account removal was successful
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the account removal was successful
     * @apiNote This method usually fails, because certain accounts cannot be removed.<br/>
     * Those that can be removed will be removed nevertheless.
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the account removal was successful
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the existing network could be removed
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection"})
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
     * @return boolean whether or not the existing network could be removed
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the new Password could be set
     */
    @SuppressWarnings("unused")
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
     * @param enable {@link java.lang.Boolean boolean}: true enables saving Form Input Data, false disables it
     * @return boolean whether or not the setting was applied successfully
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the new Date/Time could be set
     */
    @SuppressWarnings("unused")
    public static boolean setDateTime(Date date) {
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
     * @return boolean whether or not the default Browser Homepage could be set successfully
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the Default Launcher was set successfully
     */
    @SuppressWarnings("unused")
    public static boolean setDefaultLauncher(String packageName) {
        try {
            return getInstance().edtService().setDefaultLauncher(packageName);
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
     * @return boolean whether or not the new settings could applied to the Barcode Scanner
     */
    @SuppressWarnings("unused")
    public static boolean setNewScanSettings(String settingsFilePath) {
        try {
            return getInstance().edtService().setNewScanSettings(settingsFilePath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Specifies a new Screen Off Timeout in Milliseconds
     *
     * @param milliseconds {@link java.lang.Integer int}: The new Screen Off Timeout Value in Milliseconds
     * @return boolean whether or not the Screen Timeout was set successfully
     */
    @SuppressWarnings("unused")
    public static boolean setScreenOffTimeout(int milliseconds) {
        try {
            return getInstance().edtService().setScreenOffTimeout(milliseconds);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets a new Device Time Zone
     *
     * @param timeZone {@link android.icu.util.TimeZone TimeZone}: The new Time Zone to be set
     * @return boolean whether or not the new Time Zone could be set
     */
    @SuppressWarnings("unused")
    public static boolean setTimeZone(TimeZone timeZone) {
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
     * @return boolean whether or not the Shutdown could be performed
     * @apiNote There will be no warning or any confirmation dialog whatsoever when you call this method.<br/>
     * The device will simply, irrevocably, silently power off, quite like when the user keeps pressing the power key and chooses to "Power off".
     */
    @SuppressWarnings("unused")
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
     * @return boolean whether or not the Message could be shown
     */
    @SuppressWarnings("unused")
    public static boolean testMessage(String message) {
        try {
            return getInstance().edtService().testMessage(message);
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
     * @return boolean whether or not the existing network could be updated
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
     * ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.fromPath(Paths.get("/sdcard/Download/devinfo.html"))
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
     * @return boolean whether or not the file data has been written successfully
     * @apiNote the {@link ReadWriteFileParams#getData() data buffer} of {@link ReadWriteFileParams readWriteFileParams} is mandatory.<br/>
     * If this method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to write the specified amount of data.<br/>
     * If this method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will fail.
     * @see ReadWriteFileParams ReadWriteFileParams for further details.
     */
    @SuppressWarnings("unused")
    public static boolean writeFile(ReadWriteFileParams readWriteFileParams) {
        try {
            return getInstance().edtService().readFile(new ReadWriteFileParamsParcelable(readWriteFileParams));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private IEDT edtService() {
        return EDTServiceConnection.getInstance().getEDTService();
    }
}
