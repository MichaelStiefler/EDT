package com.casioeurope.mis.edt.barcodescanner;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class ScannerLibraryProperties {
    @Attribute
    private int notificationLED;
    @Attribute
    private int notificationVibrator;
    @Attribute
    private int notificationSound;
    @Attribute
    private int lightMode;
    @Attribute
    private int outputType;
    @Attribute
    private int suffix;
    @Attribute
    private int inverseMode;
    @Attribute
    private int triggerKeyEnable;
    @Attribute
    private int triggerKeyMode;
    @Attribute
    private int numberOfBarcodes;
    @Attribute
    private int delimiter;
    @Attribute
    private int triggerKeyTimeout;
    @Attribute
    private int scannerAPOTime;
    @Attribute
    private int centeringWindow;
    @Attribute
    private int detectionAreaSize;
    @Attribute
    private int laserSwingWidth;
    @Attribute
    private int laserHighlightMode;

    @ElementList
    private ArrayList<Symbology> symbologies;

    ScannerLibraryProperties() {
        this.notificationLED = 0;
        this.notificationVibrator = 0;
        this.notificationSound = 0;
        this.lightMode = 0;
        this.outputType = 0;
        this.suffix = 0;
        this.inverseMode = 0;
        this.triggerKeyEnable = 0;
        this.triggerKeyMode = 0;
        this.numberOfBarcodes = 4;
        this.delimiter = 0x1f;
        this.triggerKeyTimeout = 10000;
        this.scannerAPOTime = 60;
        this.centeringWindow = 0;
        this.detectionAreaSize = 5;
        this.laserSwingWidth = 0;
        this.laserHighlightMode = 0;
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

    int getLightMode() {
        return lightMode;
    }

    void setLightMode(int lightMode) {
        this.lightMode = lightMode;
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

    int getInverseMode() {
        return inverseMode;
    }

    void setInverseMode(int inverseMode) {
        this.inverseMode = inverseMode;
    }

    int getTriggerKeyEnable() {
        return triggerKeyEnable;
    }

    void setTriggerKeyEnable(int triggerKeyEnable) {
        this.triggerKeyEnable = triggerKeyEnable;
    }

    int getTriggerKeyMode() {
        return triggerKeyMode;
    }

    void setTriggerKeyMode(int triggerKeyMode) {
        this.triggerKeyMode = triggerKeyMode;
    }

    int getNumberOfBarcodes() {
        return numberOfBarcodes;
    }

    void setNumberOfBarcodes(int numberOfBarcodes) {
        this.numberOfBarcodes = numberOfBarcodes;
    }

    int getDelimiter() {
        return delimiter;
    }

    void setDelimiter(int delimiter) {
        this.delimiter = delimiter;
    }

    int getTriggerKeyTimeout() {
        return triggerKeyTimeout;
    }

    void setTriggerKeyTimeout(int triggerKeyTimeout) {
        this.triggerKeyTimeout = triggerKeyTimeout;
    }

    int getScannerAPOTime() {
        return scannerAPOTime;
    }

    void setScannerAPOTime(int scannerAPOTime) {
        this.scannerAPOTime = scannerAPOTime;
    }

    int getCenteringWindow() {
        return centeringWindow;
    }

    void setCenteringWindow(int centeringWindow) {
        this.centeringWindow = centeringWindow;
    }

    int getDetectionAreaSize() {
        return detectionAreaSize;
    }

    void setDetectionAreaSize(int detectionAreaSize) {
        this.detectionAreaSize = detectionAreaSize;
    }

    int getLaserSwingWidth() {
        return laserSwingWidth;
    }

    void setLaserSwingWidth(int laserSwingWidth) {
        this.laserSwingWidth = laserSwingWidth;
    }

    int getLaserHighlightMode() {
        return laserHighlightMode;
    }

    void setLaserHighlightMode(int laserHighlightMode) {
        this.laserHighlightMode = laserHighlightMode;
    }

    List<Symbology> getSymbologies() {
        return symbologies;
    }

}
