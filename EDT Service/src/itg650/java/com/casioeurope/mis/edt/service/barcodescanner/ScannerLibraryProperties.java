package com.casioeurope.mis.edt.service.barcodescanner;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

import jp.casio.ht.devicelibrary.ScannerLibrary;

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
    private final ArrayList<Symbology> symbologies;

    ScannerLibraryProperties() {
        this.notificationLED = ScannerLibrary.CONSTANT.NOTIFICATION.LED_ON;
        this.notificationVibrator = ScannerLibrary.CONSTANT.NOTIFICATION.VIBRATOR_ALL_OFF;
        this.notificationSound = ScannerLibrary.CONSTANT.NOTIFICATION.SOUND_ALL_ON;
        this.lightMode = ScannerLibrary.CONSTANT.LIGHT_MODE.ILLUMINATION_ON;
        this.outputType = ScannerLibrary.CONSTANT.OUTPUT.CLIP;
        this.suffix = ScannerLibrary.CONSTANT.SUFFIX.NONE;
        this.inverseMode = ScannerLibrary.CONSTANT.INVERSE.DISABLE;
        this.triggerKeyEnable = ScannerLibrary.CONSTANT.TRIGGERKEY.ENABLE;
        this.triggerKeyMode = ScannerLibrary.CONSTANT.TRIGGER_MODE.NORMAL;
        this.numberOfBarcodes = 4;
        this.delimiter = 0x1f;
        this.triggerKeyTimeout = 10000;
        this.scannerAPOTime = 60;
        this.centeringWindow = ScannerLibrary.CONSTANT.CENTERING_WINDOW_MODE.DISABLE;
        this.detectionAreaSize = 5;
        this.laserSwingWidth = ScannerLibrary.CONSTANT.SWING_WIDTH.MAX;
        this.laserHighlightMode = ScannerLibrary.CONSTANT.LASER_HIGHLIGHT_MODE.DISABLE;
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
