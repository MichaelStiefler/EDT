package com.casioeurope.mis.cdl;

interface ISystemLibrary {
     String getCASIOSerial();
     String getModelName();
     boolean getNavigationBarState();
     void setNavigationBarState(boolean state);
}
