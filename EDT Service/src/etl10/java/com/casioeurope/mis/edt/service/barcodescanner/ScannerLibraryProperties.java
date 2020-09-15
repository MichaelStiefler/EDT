package com.casioeurope.mis.edt.service.barcodescanner;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "RedundantSuppression"})
@Root
public class ScannerLibraryProperties {
    @Attribute
    private int notificationLED;
    @Attribute
    private int notificationVibrator;
    @Attribute
    private int notificationSound;
    @Attribute
    private int failureSound;
    @Attribute
    private int outputType;
    @Attribute
    private int suffix;
    @Attribute
    private int triggerKeyEnable;
    @Attribute
    private int triggerKeyTimeout;

    @ElementList
    private ArrayList<Symbology> symbologies;

    ScannerLibraryProperties() {
        this.notificationLED = 0;
        this.notificationVibrator = 0;
        this.notificationSound = 0;
        this.failureSound = 0;
        this.outputType = 0;
        this.suffix = 0;
        this.triggerKeyEnable = 0;
        this.triggerKeyTimeout = 10000;
        this.symbologies = new ArrayList<>();
    }

    int getNotificationLED() {
        return notificationLED;
    }

    void setNotificationLED(int notificationLED) {
        this.notificationLED = notificationLED;
    }

    int getNotificationVibrator() {
        return notificationVibrator;
    }

    void setNotificationVibrator(int notificationVibrator) {
        this.notificationVibrator = notificationVibrator;
    }

    int getNotificationSound() {
        return notificationSound;
    }

    void setNotificationSound(int notificationSound) {
        this.notificationSound = notificationSound;
    }

    int getFailureSound() {
        return failureSound;
    }

    void setFailureSound(int failureSound) {
        this.failureSound = failureSound;
    }

    int getOutputType() {
        return outputType;
    }

    void setOutputType(int outputType) {
        this.outputType = outputType;
    }

    public int getSuffix() {
        return suffix;
    }

    public void setSuffix(int suffix) {
        this.suffix = suffix;
    }

    int getTriggerKeyEnable() {
        return triggerKeyEnable;
    }

    void setTriggerKeyEnable(int triggerKeyEnable) {
        this.triggerKeyEnable = triggerKeyEnable;
    }

    int getTriggerKeyTimeout() {
        return triggerKeyTimeout;
    }

    void setTriggerKeyTimeout(int triggerKeyTimeout) {
        this.triggerKeyTimeout = triggerKeyTimeout;
    }

    List<Symbology> getSymbologies() {
        return symbologies;
    }

}
