package jp.co.casio.edt.service.barcodescanner.etl10;

import android.content.Context;
import android.content.Intent;

public class ScannerLibrary {
    Context context;

    @SuppressWarnings({"unused", "SpellCheckingInspection", "RedundantSuppression"})
    public static final class CONSTANT {
        public static final String BARCODEOUTPUT = "android.intent.action.BARCODEOUTPUT";
        public static final String BARCODERESULT = "android.intent.action.SCANRESULT";
        public static final String BARCODESCAN = "android.intent.action.BARCODESCAN";
        public static final String BARCODESTARTSCAN = "android.intent.action.BARCODESTARTSCAN";
        public static final String BARCODESTOPSCAN = "android.intent.action.BARCODESTOPSCAN";
        public static final String BEEP = "android.intent.action.BEEP";
        public static final String CONTINUOUSSCAN = "android.intent.action.CONTINUOUSSCAN";
        public static final String DEFAULT = "android.intent.action.DEFAULT";
        public static final String ENABLESYMBOLOGIES = "android.intent.action.ENABLESYMBOLOGIES";
        public static final String FAILUREBEEP = "android.intent.action.FAILUREBEEP";
        public static final String LAUNCH_SCANSETTING = "android.intent.action.LAUNCH_SCANSETTING";
        public static final String LIGHT = "android.intent.action.LIGHT";
        public static final String NOTSCANBYTRIGGERKEY = "android.intent.action.NOTSCANBYTRIGGERKEY";
        public static final String SCANBYTRIGGERKEY = "android.intent.action.SCANBYTRIGGERKEY";
        public static final String TERMINATOR = "android.intent.action.TERMINATOR";
        public static final String TIMEOUT = "android.intent.action.TIMEOUT";
        public static final String VIBRATE = "android.intent.action.VIBRATE";
        public static final String BYTEDATA_BYTE = "bytedata";
        public static final String DECODETIME_INT = "decodetime";
        public static final String ENABLESYMBOLOGIESEXTRAENABLE = "enable";
        public static final String ENABLESYMBOLOGIESEXTRATYPE = "symbologies";
        public static final String VALUE_STRING = "value";
        public static final String LENGTH_INT = "length";
        public static final int OUTPUT_BROADCAST = 1;
        public static final int OUTPUT_CLIPBOARD = 3;
        public static final int OUTPUT_FOCUS_EDITBOX = 0;
        public static final int OUTPUT_KEYEVENT = 2;
        public static final int TERMINATOR_ENTER = 1;
        public static final int TERMINATOR_LF = 3;
        public static final int TERMINATOR_NONE = 0;
        public static final int TERMINATOR_TAB = 2;
        public static final int TRIGGERTIMEOUT_10s = 10;
        public static final int TRIGGERTIMEOUT_1s = 1;
        public static final int TRIGGERTIMEOUT_2s = 2;
        public static final int TRIGGERTIMEOUT_3s = 3;
        public static final int TRIGGERTIMEOUT_4s = 4;
        public static final int TRIGGERTIMEOUT_5s = 5;
        public static final int TRIGGERTIMEOUT_6s = 6;
        public static final int TRIGGERTIMEOUT_7s = 7;
        public static final int TRIGGERTIMEOUT_8s = 8;
        public static final int TRIGGERTIMEOUT_9s = 9;
        public static final String TYPE_INT = "type";
        public static final boolean DISABLE = false;
        public static final boolean ENABLE = true;

        @SuppressWarnings("unused")
        public static final class SYMBOLOGY {
            public static final int ALL_BARCODE = 99;
            public static final int SYM_AZTEC = 0;
            public static final int SYM_CODABAR = 1;
            public static final int SYM_CODE128 = 3;
            public static final int SYM_CODE93 = 6;
            public static final int SYM_COMPOSITE = 7;
            public static final int SYM_DATAMATRIX = 8;
            public static final int SYM_EAN13 = 10;
            public static final int SYM_EAN8 = 9;
            public static final int SYM_GS1_128 = 47;
            public static final int SYM_HANXIN = 48;
            public static final int SYM_INT25 = 11;
            public static final int SYM_MAXICODE = 12;
            public static final int SYM_MICROPDF = 13;
            public static final int SYM_MSI = 31;
            public static final int SYM_PDF417 = 15;
            public static final int SYM_RSS = 18;
            public static final int SYM_UPCA = 19;
            public static final int SYM_UPCE0 = 20;
            public static final int SYM_UPCE1 = 21;
        }

        @SuppressWarnings("unused")
        public static class RETURN {
            public static final int ERROR_NOTOPENED = -3;
            public static final int ERROR_PARAMETER = -2;
            public static final int ERROR_UNSUPPORTED = -1;
            public static final int SUCCESS = 0;
        }

        public static class SYMBOLOGY_PARAMETER {
            public static final int DISABLE = 0;
            public static final int ENABLE = 1;
        }

    }

    public ScannerLibrary(Context context) {
        this.context = context;
    }

    public void setNotificationLED(int enabled) {
        Intent intent = new Intent(CONSTANT.LIGHT);
        intent.putExtra(CONSTANT.LIGHT, (enabled == CONSTANT.SYMBOLOGY_PARAMETER.ENABLE));
        this.context.sendBroadcast(intent);
    }

    public void setNotificationVibrator(int enabled) {
        Intent intent = new Intent(CONSTANT.VIBRATE);
        intent.putExtra(CONSTANT.VIBRATE, (enabled == CONSTANT.SYMBOLOGY_PARAMETER.ENABLE));
        this.context.sendBroadcast(intent);
    }

    public void setNotificationSound(int enabled) {
        Intent intent = new Intent(CONSTANT.BEEP);
        intent.putExtra(CONSTANT.BEEP, (enabled == CONSTANT.SYMBOLOGY_PARAMETER.ENABLE));
        this.context.sendBroadcast(intent);
    }

    public void setFailureSound(int enabled) {
        Intent intent = new Intent(CONSTANT.FAILUREBEEP);
        intent.putExtra(CONSTANT.FAILUREBEEP, (enabled == CONSTANT.SYMBOLOGY_PARAMETER.ENABLE));
        this.context.sendBroadcast(intent);
    }

    public void setOutputType(int outputType) {
        if (outputType >= 0 && outputType <= 3) {
            Intent intent = new Intent(CONSTANT.BARCODEOUTPUT);
            intent.putExtra(CONSTANT.BARCODEOUTPUT, outputType);
            this.context.sendBroadcast(intent);
        }
    }

    public void setSuffix(int terminatorType) {
        if (terminatorType >= 0 && terminatorType <= 3) {
            Intent intent = new Intent(CONSTANT.TERMINATOR);
            intent.putExtra(CONSTANT.TERMINATOR, terminatorType);
            this.context.sendBroadcast(intent);
        }
    }

    public void setTriggerKeyEnable(int enable) {
        if (enable == CONSTANT.SYMBOLOGY_PARAMETER.ENABLE) {
            this.context.sendBroadcast(new Intent(CONSTANT.SCANBYTRIGGERKEY));
            return;
        }
        this.context.sendBroadcast(new Intent(CONSTANT.NOTSCANBYTRIGGERKEY));
    }

    public void setTriggerKeyTimeout(int timeOut) {
        if (timeOut >= 1 && timeOut <= 10) {
            Intent intent = new Intent(CONSTANT.TIMEOUT);
            intent.putExtra(CONSTANT.TIMEOUT, timeOut);
            this.context.sendBroadcast(intent);
        }
    }

    public void setSymbologyEnable(int symbologyID, int enabled) {
        Intent intent = new Intent(CONSTANT.ENABLESYMBOLOGIES);
        intent.putExtra(CONSTANT.ENABLESYMBOLOGIESEXTRATYPE, symbologyID);
        intent.putExtra(CONSTANT.ENABLESYMBOLOGIESEXTRAENABLE, (enabled == CONSTANT.SYMBOLOGY_PARAMETER.ENABLE));
        this.context.sendBroadcast(intent);
    }

}