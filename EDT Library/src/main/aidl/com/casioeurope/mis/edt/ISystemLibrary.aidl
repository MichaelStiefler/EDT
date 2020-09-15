package com.casioeurope.mis.edt;

interface ISystemLibrary {
     String getCASIOSerial();
     String getModelName();
     boolean getNavigationBarState();
     void setNavigationBarState(boolean state);
}
