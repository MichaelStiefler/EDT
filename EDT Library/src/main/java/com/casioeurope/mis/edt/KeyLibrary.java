package com.casioeurope.mis.edt;

import android.os.RemoteException;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibrary {

    /**
     *
     */
    public static class CONSTANT {

        /**
         *
         */
        public static class KEYCODE {
            /**
             *
             */
            public static final int KEYCODE_BLANK = 0x70000001;
            /**
             *
             */
            public static final int KEYCODE_TRIGGER_BACK = 1013;
            /**
             *
             */
            public static final int KEYCODE_TRIGGER_CENTER = 1010;
            /**
             *
             */
            public static final int KEYCODE_TRIGGER_LEFT = 1012;
            /**
             *
             */
            public static final int KEYCODE_TRIGGER_RIGHT = 1011;
        }

        /**
         *
         */
        public static class KEYID {
            /**
             *
             */
            public static final int APPSWITCH = 3;
            /**
             *
             */
            public static final int BACK = 4;
            /**
             *
             */
            public static final int BACKTRIGGER = 27;
            /**
             *
             */
            public static final int CENTERTRIGGER = 26;
            /**
             *
             */
            public static final int CLEAR = 28;
            /**
             *
             */
            public static final int DOWN = 32;
            /**
             *
             */
            public static final int ENTER = 29;
            /**
             *
             */
            public static final int F1 = 18;
            /**
             *
             */
            public static final int F2 = 19;
            /**
             *
             */
            public static final int F3 = 20;
            /**
             *
             */
            public static final int F4 = 21;
            /**
             *
             */
            public static final int F5 = 22;
            /**
             *
             */
            public static final int F6 = 23;
            /**
             *
             */
            public static final int F7 = 24;
            /**
             *
             */
            public static final int F8 = 25;
            /**
             *
             */
            public static final int FUNCTION = 5;
            /**
             *
             */
            public static final int KEY0 = 17;
            /**
             *
             */
            public static final int KEY1 = 8;
            /**
             *
             */
            public static final int KEY2 = 9;
            /**
             *
             */
            public static final int KEY3 = 10;
            /**
             *
             */
            public static final int KEY4 = 11;
            /**
             *
             */
            public static final int KEY5 = 12;
            /**
             *
             */
            public static final int KEY6 = 13;
            /**
             *
             */
            public static final int KEY7 = 14;
            /**
             *
             */
            public static final int KEY8 = 15;
            /**
             *
             */
            public static final int KEY9 = 16;
            /**
             *
             */
            public static final int LEFT = 33;
            /**
             *
             */
            public static final int LEFTTRIGGER = 2;
            /**
             *
             */
            public static final int MINUS = 36;
            /**
             *
             */
            public static final int PERIOD = 30;
            /**
             *
             */
            public static final int RIGHT = 34;
            /**
             *
             */
            public static final int RIGHTTRIGGER = 1;
            /**
             *
             */
            public static final int SPACE = 35;
            /**
             *
             */
            public static final int UP = 31;
            /**
             *
             */
            public static final int VOLUMEDOWN = 7;
            /**
             *
             */
            public static final int VOLUMEUP = 6;
        }

        /**
         *
         */
        public static class RETURN {
            /**
             *
             */
            public static final int ERROR_FUNCTION = -2;
            /**
             *
             */
            public static final int ERROR_NOTSUPPORTED = -1;
            /**
             *
             */
            public static final int SUCCESS = 0;
        }
    }

    private static KeyLibrary instance;

    private KeyLibrary() {
    }

    private static KeyLibrary getInstance() {
        if (KeyLibrary.instance == null) {
            KeyLibrary.instance = new KeyLibrary();
        }
        return KeyLibrary.instance;
    }

    private IKeyLibrary edtServiceKeyLibrary() {
        return EDTServiceConnection.getInstance().getKeyLibrary();
    }

    /**
     *
     * @param nID
     * @return
     * @throws RemoteException
     */
    public static int getUserKeyCode(int nID) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().getUserKeyCode(nID);
    }

    /**
     *
     * @param nID
     * @return
     * @throws RemoteException
     */
    public static int setDefaultKeyCode(int nID) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().setDefaultKeyCode(nID);
    }

    /**
     *
     * @param nID
     * @param KeyCode
     * @return
     * @throws RemoteException
     */
    private static int setFnUserKeyCode(int nID, int KeyCode) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().setFnUserKeyCode(nID, KeyCode);
    }

    /**
     *
     * @param nID
     * @return
     * @throws RemoteException
     */
    public static int getFnUserKeyCode(int nID) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().getFnUserKeyCode(nID);
    }

    /**
     *
     * @param nID
     * @return
     * @throws RemoteException
     */
    public static int setFnDefaultKeyCode(int nID) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().setFnDefaultKeyCode(nID);
    }

    /**
     *
     * @param nID
     * @param appInfo
     * @return
     * @throws RemoteException
     */
    public static int setLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().setLaunchApplication(nID, appInfo);
    }

    /**
     *
     * @param nID
     * @param appInfo
     * @return
     * @throws RemoteException
     */
    public static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().getLaunchApplication(nID, appInfo);
    }

    /**
     *
     * @param nID
     * @return
     * @throws RemoteException
     */
    public static int clearLaunchApplication(int nID) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().clearLaunchApplication(nID);
    }

    /**
     *
     * @param nID
     * @param appInfo
     * @return
     * @throws RemoteException
     */
    public static int setFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().setFnLaunchApplication(nID, appInfo);
    }

    /**
     *
     * @param nID
     * @param appInfo
     * @return
     * @throws RemoteException
     */
    public static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().getFnLaunchApplication(nID, appInfo);
    }

    /**
     *
     * @param nID
     * @return
     * @throws RemoteException
     */
    public static int clearFnLaunchApplication(int nID) throws RemoteException {
        return getInstance().edtServiceKeyLibrary().clearFnLaunchApplication(nID);
    }
}
