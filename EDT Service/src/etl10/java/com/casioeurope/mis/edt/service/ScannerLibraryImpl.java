package com.casioeurope.mis.edt.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.RemoteException;

import com.casioeurope.mis.edt.IScannerLibrary;
import com.casioeurope.mis.edt.constant.ScannerLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.ScanResultParcelable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "RedundantThrows", "RedundantSuppression", "SpellCheckingInspection"})
public class ScannerLibraryImpl extends IScannerLibrary.Stub {
    private static final class CONSTANT {
        private static final String BARCODEOUTPUT = "android.intent.action.BARCODEOUTPUT";
        private static final String BARCODERESULT = "android.intent.action.SCANRESULT";
        private static final String BARCODESCAN = "android.intent.action.BARCODESCAN";
        private static final String BARCODESTARTSCAN = "android.intent.action.BARCODESTARTSCAN";
        private static final String BARCODESTOPSCAN = "android.intent.action.BARCODESTOPSCAN";
        private static final String BEEP = "android.intent.action.BEEP";
        private static final String CONTINUOUSSCAN = "android.intent.action.CONTINUOUSSCAN";
        private static final String DEFAULT = "android.intent.action.DEFAULT";
        private static final String ENABLESYMBOLOGIES = "android.intent.action.ENABLESYMBOLOGIES";
        private static final String FAILUREBEEP = "android.intent.action.FAILUREBEEP";
        private static final String LAUNCH_SCANSETTING = "android.intent.action.LAUNCH_SCANSETTING";
        private static final String LIGHT = "android.intent.action.LIGHT";
        private static final String NOTSCANBYTRIGGERKEY = "android.intent.action.NOTSCANBYTRIGGERKEY";
        private static final String SCANBYTRIGGERKEY = "android.intent.action.SCANBYTRIGGERKEY";
        private static final String TERMINATOR = "android.intent.action.TERMINATOR";
        private static final String TIMEOUT = "android.intent.action.TIMEOUT";
        private static final String VIBRATE = "android.intent.action.VIBRATE";
        private static final String BYTEDATA_BYTE = "bytedata";
        private static final String DECODETIME_INT = "decodetime";
        private static final String ENABLESYMBOLOGIESEXTRAENABLE = "enable";
        private static final String ENABLESYMBOLOGIESEXTRATYPE = "symbologies";
        private static final String VALUE_STRING = "value";
        private static final String LENGTH_INT = "length";
        private static final int OUTPUT_BROADCAST = 1;
        private static final int OUTPUT_CLIPBOARD = 3;
        private static final int OUTPUT_FOCUS_EDITBOX = 0;
        private static final int OUTPUT_KEYEVENT = 2;
        private static final int TERMINATOR_ENTER = 1;
        private static final int TERMINATOR_LF = 3;
        private static final int TERMINATOR_NONE = 0;
        private static final int TERMINATOR_TAB = 2;
        private static final String TYPE_INT = "type";
        private static final boolean DISABLE = false;
        private static final boolean ENABLE = true;
    }

    private static final class SYMBOLOGY {
        private static final int ALL_BARCODE = 99;
        private static final int SYM_AZTEC = 0;
        private static final int SYM_CODABAR = 1;
        private static final int SYM_CODE128 = 3;
        private static final int SYM_CODE93 = 6;
        private static final int SYM_COMPOSITE = 7;
        private static final int SYM_DATAMATRIX = 8;
        private static final int SYM_EAN13 = 10;
        private static final int SYM_EAN8 = 9;
        private static final int SYM_GS1_128 = 47;
        private static final int SYM_HANXIN = 48;
        private static final int SYM_INT25 = 11;
        private static final int SYM_MAXICODE = 12;
        private static final int SYM_MICROPDF = 13;
        private static final int SYM_MSI = 31;
        private static final int SYM_PDF417 = 15;
        private static final int SYM_RSS = 18;
        private static final int SYM_UPCA = 19;
        private static final int SYM_UPCE0 = 20;
        private static final int SYM_UPCE1 = 21;
    }

    private static final int[] COMMON_SYMBOLOGIES = {
            ScannerLibraryConstant.SYMBOLOGY.ALL,
            ScannerLibraryConstant.SYMBOLOGY.AZTEC,
            ScannerLibraryConstant.SYMBOLOGY.CODABAR,
            ScannerLibraryConstant.SYMBOLOGY.CODE128,
            ScannerLibraryConstant.SYMBOLOGY.CODE93,
            ScannerLibraryConstant.SYMBOLOGY.COMPOSITE,
            ScannerLibraryConstant.SYMBOLOGY.DATAMATRIX,
            ScannerLibraryConstant.SYMBOLOGY.EAN13,
            ScannerLibraryConstant.SYMBOLOGY.EAN8,
            ScannerLibraryConstant.SYMBOLOGY.GS1_128,
            ScannerLibraryConstant.SYMBOLOGY.HANXIN,
            ScannerLibraryConstant.SYMBOLOGY.ITF,
            ScannerLibraryConstant.SYMBOLOGY.MAXICODE,
            ScannerLibraryConstant.SYMBOLOGY.MICRO_PDF,
            ScannerLibraryConstant.SYMBOLOGY.MSI,
            ScannerLibraryConstant.SYMBOLOGY.PDF417,
            ScannerLibraryConstant.SYMBOLOGY.GS1_DATABAR,
            ScannerLibraryConstant.SYMBOLOGY.UPCA,
            ScannerLibraryConstant.SYMBOLOGY.UPCE
    };

    private static final int[] ETL10_SYMBOLOGIES = {
            SYMBOLOGY.ALL_BARCODE,
            SYMBOLOGY.SYM_AZTEC,
            SYMBOLOGY.SYM_CODABAR,
            SYMBOLOGY.SYM_CODE128,
            SYMBOLOGY.SYM_CODE93,
            SYMBOLOGY.SYM_COMPOSITE,
            SYMBOLOGY.SYM_DATAMATRIX,
            SYMBOLOGY.SYM_EAN13,
            SYMBOLOGY.SYM_EAN8,
            SYMBOLOGY.SYM_GS1_128,
            SYMBOLOGY.SYM_HANXIN,
            SYMBOLOGY.SYM_INT25,
            SYMBOLOGY.SYM_MAXICODE,
            SYMBOLOGY.SYM_MICROPDF,
            SYMBOLOGY.SYM_MSI,
            SYMBOLOGY.SYM_PDF417,
            SYMBOLOGY.SYM_RSS,
            SYMBOLOGY.SYM_UPCA,
            SYMBOLOGY.SYM_UPCE0
    };

    private static final int[] COMMON_OUTPUTTYPES = {
            ScannerLibraryConstant.OUTPUT.OUTPUT_FOCUS_EDITBOX,
            ScannerLibraryConstant.OUTPUT.BROADCAST,
            ScannerLibraryConstant.OUTPUT.KEY,
            ScannerLibraryConstant.OUTPUT.CLIP
    };

    private static final int[] ETL10_OUTPUTTYPES = {
            CONSTANT.OUTPUT_FOCUS_EDITBOX,
            CONSTANT.OUTPUT_BROADCAST,
            CONSTANT.OUTPUT_KEYEVENT,
            CONSTANT.OUTPUT_CLIPBOARD
    };

    private static final int[] COMMON_SUFFIXES = {
            ScannerLibraryConstant.SUFFIX.NONE,
            ScannerLibraryConstant.SUFFIX.ENTER,
            ScannerLibraryConstant.SUFFIX.TAB,
            ScannerLibraryConstant.SUFFIX.LF
    };

    private static final int[] ETL10_TERMINATORS = {
            CONSTANT.TERMINATOR_NONE,
            CONSTANT.TERMINATOR_ENTER,
            CONSTANT.TERMINATOR_TAB,
            CONSTANT.TERMINATOR_LF
    };

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("1100000101010000000000000010000000000010100010000000101000000000000000", 2);
    private static final String[] methodNames = {"openScanner",
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

    public ScannerLibraryImpl(Context context) {
        this.context = context;
    }

    private final Context context;

    public int openScanner(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        Intent intent = new Intent(CONSTANT.BARCODESCAN);
        intent.putExtra(CONSTANT.BARCODESCAN, true);
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int closeScanner(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        Intent intent = new Intent(CONSTANT.BARCODESCAN);
        intent.putExtra(CONSTANT.BARCODESCAN, false);
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public boolean isScannerOpen(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return false;
    }

    public int setDefaultAll(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public String getAPIVersion(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return null;
    }

    public String getModuleVersion(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return null;
    }


    public int getScanResult(ScanResultParcelable scanResultParcelable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setNotificationLED(int led, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        Intent intent = new Intent(CONSTANT.LIGHT);
        intent.putExtra(CONSTANT.LIGHT, (led == ScannerLibraryConstant.NOTIFICATION.LED_ON));
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getNotificationLED(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setNotificationVibrator(int vibrator, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        Intent intent = new Intent(CONSTANT.VIBRATE);
        intent.putExtra(CONSTANT.VIBRATE, (vibrator != ScannerLibraryConstant.NOTIFICATION.VIBRATOR_ALL_OFF));
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getNotificationVibrator(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setNotificationSound(int sound, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        Intent intent = new Intent(CONSTANT.BEEP);
        intent.putExtra(CONSTANT.BEEP, ((sound & ScannerLibraryConstant.NOTIFICATION.SOUND_SUCCESS_ON) != 0));
        this.context.sendBroadcast(intent);
        Intent intent2 = new Intent(CONSTANT.FAILUREBEEP);
        intent2.putExtra(CONSTANT.FAILUREBEEP, ((sound & ScannerLibraryConstant.NOTIFICATION.SOUND_FAIL_ON) != 0));
        this.context.sendBroadcast(intent2);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getNotificationSound(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setLightMode(int lightMode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getLightMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
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
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int captureImage(byte[] buffer, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getStreamDataSize(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
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
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int readStream(byte[] buffer, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int stopStream(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int deinitializeStream(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setSymbologyEnable(int symbologyID, int enable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        int symbologyIndex = Arrays.stream(COMMON_SYMBOLOGIES).boxed().collect(Collectors.toList()).indexOf(symbologyID);
        if (symbologyIndex < 0) return ScannerLibraryConstant.RETURN.ERROR_PARAMETER;
        symbologyID = ETL10_SYMBOLOGIES[symbologyIndex];
        Intent intent = new Intent(CONSTANT.ENABLESYMBOLOGIES);
        intent.putExtra(CONSTANT.ENABLESYMBOLOGIESEXTRATYPE, symbologyID);
        intent.putExtra(CONSTANT.ENABLESYMBOLOGIESEXTRAENABLE, (enable == ScannerLibraryConstant.SYMBOLOGY_PARAMETER.ENABLE));
        this.context.sendBroadcast(intent);
        //noinspection ConstantConditions
        if (symbologyID == SYMBOLOGY.SYM_UPCE0) {
            Intent intent2 = new Intent(CONSTANT.ENABLESYMBOLOGIES);
            intent2.putExtra(CONSTANT.ENABLESYMBOLOGIESEXTRATYPE, SYMBOLOGY.SYM_UPCE1);
            intent2.putExtra(CONSTANT.ENABLESYMBOLOGIESEXTRAENABLE, (enable == ScannerLibraryConstant.SYMBOLOGY_PARAMETER.ENABLE));
            this.context.sendBroadcast(intent2);
        }
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getSymbologyEnable(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getSymbologyMaxDefault(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getSymbologyMinDefault(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setSymbologyMax(int symbologyID, int max, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getSymbologyMax(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setSymbologyMin(int symbologyID, int min, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getSymbologyMin(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setSymbologyCheckCount(int symbologyID, int checkCount, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getSymbologyCheckCount(int symbologyID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getSymbologyProperty(int symbologyID, int propertyNo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setOutputType(int outputType, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        int outputIndex = Arrays.stream(COMMON_OUTPUTTYPES).boxed().collect(Collectors.toList()).indexOf(outputType);
        if (outputIndex < 0) return ScannerLibraryConstant.RETURN.ERROR_PARAMETER;
        outputType = ETL10_OUTPUTTYPES[outputIndex];
        Intent intent = new Intent(CONSTANT.BARCODEOUTPUT);
        intent.putExtra(CONSTANT.BARCODEOUTPUT, outputType);
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getOutputType(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setSuffix(int suffix, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        int suffixIndex = Arrays.stream(COMMON_SUFFIXES).boxed().collect(Collectors.toList()).indexOf(suffix);
        if (suffixIndex < 0) return ScannerLibraryConstant.RETURN.ERROR_PARAMETER;
        suffix = ETL10_TERMINATORS[suffixIndex];
        Intent intent = new Intent(CONSTANT.TERMINATOR);
        intent.putExtra(CONSTANT.TERMINATOR, suffix);
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getSuffix(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setInverseMode(int inverseMode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getInverseMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setTriggerKeyEnable(int triggerKeyEnable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        this.context.sendBroadcast(triggerKeyEnable==ScannerLibraryConstant.TRIGGERKEY.ENABLE?new Intent(CONSTANT.SCANBYTRIGGERKEY):new Intent(CONSTANT.NOTSCANBYTRIGGERKEY));
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getTriggerKeyEnable(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setTriggerKeyMode(int triggerKeyMode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getTriggerKeyMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setNumberOfBarcodes(int numberOfBarcodes, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getNumberOfBarcodes(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setDelimiter(int delimiter, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getDelimiter(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setTriggerKeyTimeout(int triggerKeyTimeout, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        if (triggerKeyTimeout<1000 || triggerKeyTimeout>10000) return ScannerLibraryConstant.RETURN.ERROR_PARAMETER;
        triggerKeyTimeout /= 1000;
        Intent intent = new Intent(CONSTANT.TIMEOUT);
        intent.putExtra(CONSTANT.TIMEOUT, triggerKeyTimeout);
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int getTriggerKeyTimeout(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setTriggerKeyOn(int triggerKeyOn, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        Intent intent = new Intent(triggerKeyOn==ScannerLibraryConstant.CONTROL.TRIGGER_ON?CONSTANT.BARCODESTARTSCAN:CONSTANT.BARCODESTOPSCAN);
        this.context.sendBroadcast(intent);
        return ScannerLibraryConstant.RETURN.SUCCESS;
    }

    public int setScannerAPO(int scannerAPOTime, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getScannerAPO(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setCenteringWindow(int centeringWindow, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getCenteringWindow(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setDetectionAreaSize(int detectionAreaSize, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getDetectionAreaSize(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setLaserSwingWidth(int laserSwingWidth, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getLaserSwingWidth(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setLaserHighlightMode(int enable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getLaserHighlightMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setInternalParameter(byte[] command, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setInternalParameter2(int tag, int value, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setInternalParameter3(int number, int[] tags, int[] values, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getInternalParameter(int tag, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getInternalParameter2(int[] tags, int[] values, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return ScannerLibraryConstant.RETURN.ERROR_UNSUPPORTED;
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
