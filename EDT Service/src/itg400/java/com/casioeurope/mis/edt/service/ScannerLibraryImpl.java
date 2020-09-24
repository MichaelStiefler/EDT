package com.casioeurope.mis.edt.service;

import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;

import com.casioeurope.mis.edt.IScannerLibrary;
import com.casioeurope.mis.edt.constant.ScannerLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.ScanResultParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantThrows", "RedundantSuppression", "SpellCheckingInspection"})
public class ScannerLibraryImpl extends IScannerLibrary.Stub {
    private static final BigInteger METHODS_SUPPORTED = new BigInteger("1111111111111110011100111011111111111111111111111111111111111111111111", 2);
    private static String[] methodNames = {"openScanner",
            "closeScanner",
            "isScannerOpen",
            "setDefaultAll",
            "getAPIVersion",
            "getModuleVersion",
            "getScanResult",
            "setNotificationLED",
            "getNotificationLED",
            "setNotificationVibrator",
            "getNotificationVibrator",
            "setNotificationSound",
            "getNotificationSound",
            "setLightMode",
            "getLightMode",
            "turnAimerOn",
            "turnIlluminationOn",
            "getImageDataSize",
            "captureImage",
            "getStreamDataSize",
            "getStreamDataSize2",
            "initializeStream",
            "startStream",
            "readStream",
            "stopStream",
            "deinitializeStream",
            "setSymbologyEnable",
            "getSymbologyEnable",
            "getSymbologyMaxDefault",
            "getSymbologyMinDefault",
            "setSymbologyMax",
            "getSymbologyMax",
            "setSymbologyMin",
            "getSymbologyMin",
            "setSymbologyCheckCount",
            "getSymbologyCheckCount",
            "setSymbologyProperty",
            "getSymbologyProperty",
            "setOutputType",
            "getOutputType",
            "setSuffix",
            "getSuffix",
            "setInverseMode",
            "getInverseMode",
            "setTriggerKeyEnable",
            "getTriggerKeyEnable",
            "setTriggerKeyMode",
            "getTriggerKeyMode",
            "setNumberOfBarcodes",
            "getNumberOfBarcodes",
            "setDelimiter",
            "getDelimiter",
            "setTriggerKeyTimeout",
            "getTriggerKeyTimeout",
            "setTriggerKeyOn",
            "setScannerAPO",
            "getScannerAPO",
            "setCenteringWindow",
            "getCenteringWindow",
            "setDetectionAreaSize",
            "getDetectionAreaSize",
            "setLaserSwingWidth",
            "getLaserSwingWidth",
            "setLaserHighlightMode",
            "getLaserHighlightMode",
            "setInternalParameter",
            "setInternalParameter2",
            "setInternalParameter3",
            "getInternalParameter",
            "getInternalParameter2"};
    private static volatile jp.casio.ht.devicelibrary.ScannerLibrary jpInstance;

    public ScannerLibraryImpl(Context context) {
    }

    private static jp.casio.ht.devicelibrary.ScannerLibrary getJpInstance() {
        if (jpInstance == null) {
            synchronized (ScannerLibraryImpl.class) {
                if (jpInstance == null) {
                    jpInstance = new jp.casio.ht.devicelibrary.ScannerLibrary();
                }
            }
        }
        return jpInstance;
    }

    public int openScanner(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().openScanner();
    }

    public int closeScanner(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().closeScanner();
    }

    public boolean isScannerOpen(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().isScannerOpen();
    }

    public int setDefaultAll(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setDefaultAll();
    }

    public String getAPIVersion(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getAPIVersion();
    }

    public String getModuleVersion(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getModuleVersion();
    }

    private jp.casio.ht.devicelibrary.ScannerLibrary.ScanResult jpScanResult(ScanResultParcelable theScanResult) {
        jp.casio.ht.devicelibrary.ScannerLibrary.ScanResult jpScanResult = new jp.casio.ht.devicelibrary.ScannerLibrary.ScanResult();
        jpScanResult.symbologyID = theScanResult.symbologyID;
        jpScanResult.length = theScanResult.length;
        jpScanResult.time = theScanResult.time;
        jpScanResult.value = theScanResult.value;
        jpScanResult.aimID = theScanResult.aimID;
        jpScanResult.aimModifier = theScanResult.aimModifier;
        jpScanResult.symbologyName = theScanResult.symbologyName;
        return jpScanResult;
    }

    private void copyScanResult(jp.casio.ht.devicelibrary.ScannerLibrary.ScanResult source, ScanResultParcelable target) {
        target.symbologyID = source.symbologyID;
        target.length = source.length;
        target.time = source.time;
        target.value = source.value;
        target.aimID = source.aimID;
        target.aimModifier = source.aimModifier;
        target.symbologyName = source.symbologyName;
    }

    public int getScanResult(ScanResultParcelable scanResultParcelable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        jp.casio.ht.devicelibrary.ScannerLibrary.ScanResult jpScanResult = jpScanResult(scanResultParcelable);
        int retVal = getJpInstance().getScanResult(jpScanResult);
        this.copyScanResult(jpScanResult, scanResultParcelable);
        return retVal;
    }

    public int setNotificationLED(int led, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setNotificationLED(led);
    }

    public int getNotificationLED(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getNotificationLED();
    }

    public int setNotificationVibrator(int vibrator, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setNotificationVibrator(vibrator);
    }

    public int getNotificationVibrator(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getNotificationVibrator();
    }

    public int setNotificationSound(int sound, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setNotificationSound(sound);
    }

    public int getNotificationSound(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getNotificationSound();
    }

    public int setLightMode(int lightMode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setLightMode(lightMode);
    }

    public int getLightMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getLightMode();
    }

    public int turnAimerOn(int aimerOn, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int turnIlluminationOn(int illuminationOn, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getImageDataSize(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getImageDataSize();
    }

    public int captureImage(byte[] buffer, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().captureImage(buffer);
    }

    public int getStreamDataSize(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getStreamDataSize();
    }

    public int getStreamDataSize2(Rect rectangle, int resolution, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int initializeStream(Rect rectangle, int resolution, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int startStream(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().startStream();
    }

    public int readStream(byte[] buffer, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().readStream(buffer);
    }

    public int stopStream(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().stopStream();
    }

    public int deinitializeStream(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setSymbologyEnable(int symbologyID, int enable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setSymbologyEnable(symbologyID, enable);
    }

    public int getSymbologyEnable(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSymbologyEnable(symbologyID);
    }

    public int getSymbologyMaxDefault(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSymbologyMaxDefault(symbologyID);
    }

    public int getSymbologyMinDefault(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSymbologyMinDefault(symbologyID);
    }

    public int setSymbologyMax(int symbologyID, int max, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setSymbologyMax(symbologyID, max);
    }

    public int getSymbologyMax(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSymbologyMax(symbologyID);
    }

    public int setSymbologyMin(int symbologyID, int min, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setSymbologyMin(symbologyID, min);
    }

    public int getSymbologyMin(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSymbologyMin(symbologyID);
    }

    public int setSymbologyCheckCount(int symbologyID, int checkCount, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setSymbologyCheckCount(symbologyID, checkCount);
    }

    public int getSymbologyCheckCount(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSymbologyCheckCount(symbologyID);
    }

    public int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setSymbologyProperty(symbologyID, propertyNo, propertySetting);
    }

    public int getSymbologyProperty(int symbologyID, int propertyNo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSymbologyProperty(symbologyID, propertyNo);
    }

    public int setOutputType(int outputType, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setOutputType(outputType);
    }

    public int getOutputType(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getOutputType();
    }

    public int setSuffix(int suffix, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setSuffix(suffix);
    }

    public int getSuffix(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getSuffix();
    }

    public int setInverseMode(int inverseMode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setInverseMode(inverseMode);
    }

    public int getInverseMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getInverseMode();
    }

    public int setTriggerKeyEnable(int triggerKeyEnable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setTriggerKeyEnable(triggerKeyEnable);
    }

    public int getTriggerKeyEnable(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getTriggerKeyEnable();
    }

    public int setTriggerKeyMode(int triggerKeyMode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setTriggerKeyMode(triggerKeyMode);
    }

    public int getTriggerKeyMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getTriggerKeyMode();
    }

    public int setNumberOfBarcodes(int numberOfBarcodes, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setNumberOfBarcodes(numberOfBarcodes);
    }

    public int getNumberOfBarcodes(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getNumberOfBarcodes();
    }

    public int setDelimiter(int delimiter, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setDelimiter(delimiter);
    }

    public int getDelimiter(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getDelimiter();
    }

    public int setTriggerKeyTimeout(int triggerKeyTimeout, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setTriggerKeyTimeout(triggerKeyTimeout);
    }

    public int getTriggerKeyTimeout(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getTriggerKeyTimeout();
    }

    public int setTriggerKeyOn(int triggerKeyOn, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setTriggerKeyOn(triggerKeyOn);
    }

    public int setScannerAPO(int scannerAPOTime, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setScannerAPO(scannerAPOTime);
    }

    public int getScannerAPO(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getScannerAPO();
    }

    public int setCenteringWindow(int centeringWindow, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setCenteringWindow(centeringWindow);
    }

    public int getCenteringWindow(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getCenteringWindow();
    }

    public int setDetectionAreaSize(int detectionAreaSize, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setDetectionAreaSize(detectionAreaSize);
    }

    public int getDetectionAreaSize(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getDetectionAreaSize();
    }

    public int setLaserSwingWidth(int laserSwingWidth, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setLaserSwingWidth(laserSwingWidth);
    }

    public int getLaserSwingWidth(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getLaserSwingWidth();
    }

    public int setLaserHighlightMode(int enable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setLaserHighlightMode(enable);
    }

    public int getLaserHighlightMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getLaserHighlightMode();
    }

    public int setInternalParameter(byte[] command, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setInternalParameter(command);
    }

    public int setInternalParameter2(int tag, int value, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setInternalParameter(tag, value);
    }

    public int setInternalParameter3(int number, int[] tags, int[] values, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setInternalParameter(number, tags, values);
    }

    public int getInternalParameter(int tag, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getInternalParameter(tag);
    }

    public int getInternalParameter2(int[] tags, int[] values, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getInternalParameter(tags, values);
    }

    public boolean isMethodNameSupported(String methodName) {
        int methodIndex = Arrays.asList(methodNames).indexOf(methodName);
        return methodIndex >= 0 && isMethodSupported(BigInteger.ONE.shiftLeft(methodIndex).toString());
    }

    public boolean isMethodSupported(String methodBigInteger) {
        try {
            return !new BigInteger(methodBigInteger).and(METHODS_SUPPORTED).equals(BigInteger.ZERO);
        } catch (Exception e) {
            return false;
        }
    }
}
