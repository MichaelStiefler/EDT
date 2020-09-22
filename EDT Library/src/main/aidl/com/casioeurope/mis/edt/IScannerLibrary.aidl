package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.type.IParcelableTypes;

interface IScannerLibrary {
    boolean isMethodSupported(String methodBigInteger);
    boolean isMethodNameSupported(String methodName);
    int openScanner(out BooleanParcelable unsupported);
    int closeScanner(out BooleanParcelable unsupported);
    boolean isScannerOpen(out BooleanParcelable unsupported);
    int setDefaultAll(out BooleanParcelable unsupported);
    String getAPIVersion(out BooleanParcelable unsupported);
    String getModuleVersion(out BooleanParcelable unsupported);
    int getScanResult(out ScanResultParcelable scanResultParcelable, out BooleanParcelable unsupported);
    int setNotificationLED(int led, out BooleanParcelable unsupported);
    int getNotificationLED(out BooleanParcelable unsupported);
    int setNotificationVibrator(int vibrator, out BooleanParcelable unsupported);
    int getNotificationVibrator(out BooleanParcelable unsupported);
    int setNotificationSound(int sound, out BooleanParcelable unsupported);
    int getNotificationSound(out BooleanParcelable unsupported);
    int setLightMode(int lightMode, out BooleanParcelable unsupported);
    int getLightMode(out BooleanParcelable unsupported);
    int turnAimerOn(int aimerOn, out BooleanParcelable unsupported);
    int turnIlluminationOn(int illuminationOn, out BooleanParcelable unsupported);
    int getImageDataSize(out BooleanParcelable unsupported);
    int captureImage(out byte[] buffer, out BooleanParcelable unsupported);
    int getStreamDataSize(out BooleanParcelable unsupported);
    int getStreamDataSize2(in Rect rectangle, int resolution, out BooleanParcelable unsupported);
    int initializeStream(in Rect rectangle, int resolution, out BooleanParcelable unsupported);
    int startStream(out BooleanParcelable unsupported);
    int readStream(out byte[] buffer, out BooleanParcelable unsupported);
    int stopStream(out BooleanParcelable unsupported);
    int deinitializeStream(out BooleanParcelable unsupported);
    int setSymbologyEnable(int symbologyID, int enable, out BooleanParcelable unsupported);
    int getSymbologyEnable(int symbologyID, out BooleanParcelable unsupported);
    int getSymbologyMaxDefault(int symbologyID, out BooleanParcelable unsupported);
    int getSymbologyMinDefault(int symbologyID, out BooleanParcelable unsupported);
    int setSymbologyMax(int symbologyID, int max, out BooleanParcelable unsupported);
    int getSymbologyMax(int symbologyID, out BooleanParcelable unsupported);
    int setSymbologyMin(int symbologyID, int min, out BooleanParcelable unsupported);
    int getSymbologyMin(int symbologyID, out BooleanParcelable unsupported);
    int setSymbologyCheckCount(int symbologyID, int checkCount, out BooleanParcelable unsupported);
    int getSymbologyCheckCount(int symbologyID, out BooleanParcelable unsupported);
    int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting, out BooleanParcelable unsupported);
    int getSymbologyProperty(int symbologyID, int propertyNo, out BooleanParcelable unsupported);
    int setOutputType(int outputType, out BooleanParcelable unsupported);
    int getOutputType(out BooleanParcelable unsupported);
    int setSuffix(int suffix, out BooleanParcelable unsupported);
    int getSuffix(out BooleanParcelable unsupported);
    int setInverseMode(int inverseMode, out BooleanParcelable unsupported);
    int getInverseMode(out BooleanParcelable unsupported);
    int setTriggerKeyEnable(int triggerKeyEnable, out BooleanParcelable unsupported);
    int getTriggerKeyEnable(out BooleanParcelable unsupported);
    int setTriggerKeyMode(int triggerKeyMode, out BooleanParcelable unsupported);
    int getTriggerKeyMode(out BooleanParcelable unsupported);
    int setNumberOfBarcodes(int numberOfBarcodes, out BooleanParcelable unsupported);
    int getNumberOfBarcodes(out BooleanParcelable unsupported);
    int setDelimiter(int delimiter, out BooleanParcelable unsupported);
    int getDelimiter(out BooleanParcelable unsupported);
    int setTriggerKeyTimeout(int triggerKeyTimeout, out BooleanParcelable unsupported);
    int getTriggerKeyTimeout(out BooleanParcelable unsupported);
    int setTriggerKeyOn(int triggerKeyOn, out BooleanParcelable unsupported);
    int setScannerAPO(int scannerAPOTime, out BooleanParcelable unsupported);
    int getScannerAPO(out BooleanParcelable unsupported);
    int setCenteringWindow(int centeringWindow, out BooleanParcelable unsupported);
    int getCenteringWindow(out BooleanParcelable unsupported);
    int setDetectionAreaSize(int detectionAreaSize, out BooleanParcelable unsupported);
    int getDetectionAreaSize(out BooleanParcelable unsupported);
    int setLaserSwingWidth(int laserSwingWidth, out BooleanParcelable unsupported);
    int getLaserSwingWidth(out BooleanParcelable unsupported);
    int setLaserHighlightMode(int enable, out BooleanParcelable unsupported);
    int getLaserHighlightMode(out BooleanParcelable unsupported);
    int setInternalParameter(in byte[] command, out BooleanParcelable unsupported);
    int setInternalParameter2(int tag, int value, out BooleanParcelable unsupported);
    int setInternalParameter3(int number, in int[] tags, in int[] values, out BooleanParcelable unsupported);
    int getInternalParameter(int tag, out BooleanParcelable unsupported);
    int getInternalParameter2(out int[] tags, out int[] values, out BooleanParcelable unsupported);
}
