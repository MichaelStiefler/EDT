package com.casioeurope.mis.edt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class EDTServiceConnection implements ServiceConnection {

    private IEDT edtService;

    protected IEDT getEDTService() {
        return this.edtService;
    }


    protected boolean bind(Context context) {
        context.bindService(new Intent("com.casioeurope.mis.edt.EDTService").setPackage("com.casioeurope.mis.edt")
                ,this,context.BIND_AUTO_CREATE);
        return true;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        edtService = IEDT.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private static EDTServiceConnection instance;

    protected static EDTServiceConnection getInstance () {
        if (EDTServiceConnection.instance == null) {
            EDTServiceConnection.instance = new EDTServiceConnection();
        }
        return EDTServiceConnection.instance;
    }

}
