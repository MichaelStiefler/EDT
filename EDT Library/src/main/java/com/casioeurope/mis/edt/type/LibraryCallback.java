package com.casioeurope.mis.edt.type;

import android.os.RemoteException;

/**
 * Callback Interface to be used to postpone Library Method calls until the regarding library becomes available
 *
 * @apiNote Each of the Libraries in EDT is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly adviced not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@ScannerLibrary.onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling MyLibrary.myMethod() directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>MyLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         MyLibrary.myMethod();
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>MyLibrary.onLibraryReady(() -> { MyLibrary.myMethod(); });</pre>
 *          <br/>Or even further to:<br/>
 * <pre>MyLibrary.onLibraryReady(MyLibrary::myMethod);</pre>
 *
 */
@SuppressWarnings({"unused", "RedundantSuppression", "JavadocReference"})
public interface LibraryCallback {
    /**
     * Callback Method which will get called once the regarding library becomes available
     *
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public void onLibraryReady() throws RemoteException, UnsupportedOperationException;
}
