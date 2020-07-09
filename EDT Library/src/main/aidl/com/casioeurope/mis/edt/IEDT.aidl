// IEDT.aidl
package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.IWifiConfigurationParcelable;
import com.casioeurope.mis.edt.IReadWriteFileParamsParcelable;

interface IEDT {
    boolean testMessage(String message);
    boolean shutdown();
    boolean reboot();
    boolean recovery();
    boolean clearPassword();
    boolean resetPassword(String newPassword);
    boolean lockDevice();
    boolean factoryReset();
    boolean allowUnknownSources(boolean allow);
    boolean setDateTime(int year, int month, int day, int hour, int minute, int second);
    boolean setTimeZone(String timeZone);
    boolean removeAllAccounts();
    boolean removeAllGoogleAccounts();
    boolean removeAccount(in Account account);
    boolean enableWifi(boolean enabled);
    String copyFile(String sourceFilePath, String destinationFilePath, in List<String> options);
    String moveFile(String sourceFilePath, String destinationFilePath, in List<String> options);
    boolean deleteFile(String filePath);
    boolean readFile(in ReadWriteFileParamsParcelable readWriteFileParams);
    boolean writeFile(in ReadWriteFileParamsParcelable readWriteFileParams);
    boolean getCurrentScanSettings(String settingsFilePath);
    boolean setNewScanSettings(String settingsFilePath);
    boolean getCurrentAndSetNewScanSettings(String settingsFilePath);
    boolean setDefaultHomePage(String homePage);
    boolean rememberPasswords(boolean enable);
    boolean saveFormData(boolean enable);
    boolean enableCameras(boolean enable);
    boolean enableRoaming(boolean enable);
    boolean enableBackgroundData(boolean enable);
    boolean setScreenOffTimeout(int milliseconds);
    boolean setDefaultLauncher(String packageName);
    boolean addNetwork(in WifiConfigurationParcelable wifiConfiguration);
    boolean updateNetwork(in WifiConfigurationParcelable wifiConfiguration);
    boolean removeNetwork(String ssid);
    boolean removeNetworkId(int networkId);
    boolean connectNetwork(String ssid);
    boolean connectNetworkId(int networkId);
    boolean enableBluetooth(boolean enable);
    boolean enableNfc(boolean enable);
    boolean enableGps(boolean enable);
    boolean enableWwan(boolean enable);
    boolean enableDeveloperMode(boolean enable);
}
