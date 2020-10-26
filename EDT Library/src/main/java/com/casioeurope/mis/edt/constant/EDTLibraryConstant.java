package com.casioeurope.mis.edt.constant;

import com.casioeurope.mis.edt.EDTLibrary;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> EDT Library Constants<br/>
 * Constants to be used in the {@link EDTLibrary} class.<br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class EDTLibraryConstant {
    /**
     * Methods of the {@link EDTLibrary} class, used e.g. to check availability of said methods using {@link EDTLibrary#isMethodSupported(BigInteger)} method.
     */
    public static class METHOD {
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#addNetwork} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ADDNETWORK                                  = new BigInteger("000000000000000000000000000000000000000000000000000000000000000001", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#allowUnknownSources} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ALLOWUNKNOWNSOURCES                         = new BigInteger("000000000000000000000000000000000000000000000000000000000000000010", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#clearCacheForPackage} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLEARCACHEFORPACKAGE                        = new BigInteger("000000000000000000000000000000000000000000000000000000000000000100", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#clearClipboard} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLEARCLIPBOARD                              = new BigInteger("000000000000000000000000000000000000000000000000000000000000001000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#clearDataForPackage} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLEARDATAFORPACKAGE                         = new BigInteger("000000000000000000000000000000000000000000000000000000000000010000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#clearPassword} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLEARPASSWORD                               = new BigInteger("000000000000000000000000000000000000000000000000000000000000100000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#connectNetwork} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CONNECTNETWORK                              = new BigInteger("000000000000000000000000000000000000000000000000000000000001000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#connectNetworkId} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CONNECTNETWORKID                            = new BigInteger("000000000000000000000000000000000000000000000000000000000010000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#copyFile} method is supported on the currently active device
         */
        public static final BigInteger METHOD_COPYFILE                                    = new BigInteger("000000000000000000000000000000000000000000000000000000000100000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#createNewApn} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CREATENEWAPN                                = new BigInteger("000000000000000000000000000000000000000000000000000000001000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#deleteFile} method is supported on the currently active device
         */
        public static final BigInteger METHOD_DELETEFILE                                  = new BigInteger("000000000000000000000000000000000000000000000000000000010000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableAdb} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEADB                                   = new BigInteger("000000000000000000000000000000000000000000000000000000100000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableApplication} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEAPPLICATION                           = new BigInteger("000000000000000000000000000000000000000000000000000001000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableBackgroundData} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEBACKGROUNDDATA                        = new BigInteger("000000000000000000000000000000000000000000000000000010000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableBatteryOptimization} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEBATTERYOPTIMIZATION                   = new BigInteger("000000000000000000000000000000000000000000000000000100000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableBluetooth} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEBLUETOOTH                             = new BigInteger("000000000000000000000000000000000000000000000000001000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableCameras} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLECAMERAS                               = new BigInteger("000000000000000000000000000000000000000000000000010000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableClipboard} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLECLIPBOARD                             = new BigInteger("000000000000000000000000000000000000000000000000100000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableDeveloperMode} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEDEVELOPERMODE                         = new BigInteger("000000000000000000000000000000000000000000000001000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableDeviceAdmin} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEDEVICEADMIN                           = new BigInteger("000000000000000000000000000000000000000000000010000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableGps} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEGPS                                   = new BigInteger("000000000000000000000000000000000000000000000100000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableMassStorage} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEMASSSTORAGE                           = new BigInteger("000000000000000000000000000000000000000000001000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableNfc} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLENFC                                   = new BigInteger("000000000000000000000000000000000000000000010000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableRoaming} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEROAMING                               = new BigInteger("000000000000000000000000000000000000000000100000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableWifi} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEWIFI                                  = new BigInteger("000000000000000000000000000000000000000001000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#enableWwan} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ENABLEWWAN                                  = new BigInteger("000000000000000000000000000000000000000010000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#factoryReset} method is supported on the currently active device
         */
        public static final BigInteger METHOD_FACTORYRESET                                = new BigInteger("000000000000000000000000000000000000000100000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#getAllApnList} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETALLAPNLIST                               = new BigInteger("000000000000000000000000000000000000001000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#getApn} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETAPN                                      = new BigInteger("000000000000000000000000000000000000010000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#getApnId} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETAPNID                                    = new BigInteger("000000000000000000000000000000000000100000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#getCurrentAndSetNewScanSettings} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETCURRENTANDSETNEWSCANSETTINGS             = new BigInteger("000000000000000000000000000000000001000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#getCurrentScanSettings} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETCURRENTSCANSETTINGS                      = new BigInteger("000000000000000000000000000000000010000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#getGoogleAccounts} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETGOOGLEACCOUNTS                           = new BigInteger("000000000000000000000000000000000100000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#getKeyboardNames} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETKEYBOARDNAMES                            = new BigInteger("000000000000000000000000000000001000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#initializeKeyStore} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INITIALIZEKEYSTORE                          = new BigInteger("000000000000000000000000000000010000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#installApk} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INSTALLAPK                                  = new BigInteger("000000000000000000000000000000100000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#installCertificate} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INSTALLCERTIFICATE                          = new BigInteger("000000000000000000000000000000010000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#lockDevice} method is supported on the currently active device
         */
        public static final BigInteger METHOD_LOCKDEVICE                                  = new BigInteger("000000000000000000000000000010000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#mountSDCard} method is supported on the currently active device
         */
        public static final BigInteger METHOD_MOUNTSDCARD                                 = new BigInteger("000000000000000000000000000100000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#moveFile} method is supported on the currently active device
         */
        public static final BigInteger METHOD_MOVEFILE                                    = new BigInteger("000000000000000000000000001000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#readFile} method is supported on the currently active device
         */
        public static final BigInteger METHOD_READFILE                                    = new BigInteger("000000000000000000000000010000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#reboot} method is supported on the currently active device
         */
        public static final BigInteger METHOD_REBOOT                                      = new BigInteger("000000000000000000000000100000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#recovery} method is supported on the currently active device
         */
        public static final BigInteger METHOD_RECOVERY                                    = new BigInteger("000000000000000000000001000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#rememberPasswords} method is supported on the currently active device
         */
        public static final BigInteger METHOD_REMEMBERPASSWORDS                           = new BigInteger("000000000000000000000010000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#removeAccount} method is supported on the currently active device
         */
        public static final BigInteger METHOD_REMOVEACCOUNT                               = new BigInteger("000000000000000000000100000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#removeAllAccounts} method is supported on the currently active device
         */
        public static final BigInteger METHOD_REMOVEALLACCOUNTS                           = new BigInteger("000000000000000000001000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#removeAllGoogleAccounts} method is supported on the currently active device
         */
        public static final BigInteger METHOD_REMOVEALLGOOGLEACCOUNTS                     = new BigInteger("000000000000000000010000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#removeNetwork} method is supported on the currently active device
         */
        public static final BigInteger METHOD_REMOVENETWORK                               = new BigInteger("000000000000000000100000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#removeNetworkId} method is supported on the currently active device
         */
        public static final BigInteger METHOD_REMOVENETWORKID                             = new BigInteger("000000000000000001000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#resetPassword} method is supported on the currently active device
         */
        public static final BigInteger METHOD_RESETPASSWORD                               = new BigInteger("000000000000000010000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#saveFormData} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SAVEFORMDATA                                = new BigInteger("000000000000000100000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setDateTime} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDATETIME                                 = new BigInteger("000000000000001000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setDefaultHomePage} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDEFAULTHOMEPAGE                          = new BigInteger("000000000000010000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setDefaultLauncher} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDEFAULTLAUNCHER                          = new BigInteger("000000000000100000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setKeyboard} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETKEYBOARD                                 = new BigInteger("000000000001000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setNewScanSettings} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETNEWSCANSETTINGS                          = new BigInteger("000000000010000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setPreferredApn} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETPREFERREDAPN                             = new BigInteger("000000000100000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setScreenLockTimeout} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSCREENLOCKTIMEOUT                        = new BigInteger("000000001000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#setTimeZone} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETTIMEZONE                                 = new BigInteger("000000010000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#shutdown} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SHUTDOWN                                    = new BigInteger("000000100000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#testMessage} method is supported on the currently active device
         */
        public static final BigInteger METHOD_TESTMESSAGE                                 = new BigInteger("000001000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#uninstallPackage} method is supported on the currently active device
         */
        public static final BigInteger METHOD_UNINSTALLPACKAGE                            = new BigInteger("000010000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#updateApn} method is supported on the currently active device
         */
        public static final BigInteger METHOD_UPDATEAPN                                   = new BigInteger("000100000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#updateNetwork} method is supported on the currently active device
         */
        public static final BigInteger METHOD_UPDATENETWORK                               = new BigInteger("001000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#verifyApn} method is supported on the currently active device
         */
        public static final BigInteger METHOD_VERIFYAPN                                   = new BigInteger("010000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link EDTLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link EDTLibrary#writeFile} method is supported on the currently active device
         */
        public static final BigInteger METHOD_WRITEFILE                                   = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000", 2);
    }
}
