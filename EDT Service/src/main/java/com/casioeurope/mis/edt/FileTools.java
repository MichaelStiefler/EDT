package com.casioeurope.mis.edt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.OpenOption;
import java.util.Arrays;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;

public class FileTools {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;
    private static String TAG = "EDT (FileTools)";

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrMethod + " " + sb.toString() + (entrance?" +":" -"));
    }

    public static boolean readFile(ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        logMethodEntranceExit(true);
        Log.d(TAG, String.format("readFile(%s)", readWriteFileParamsParcelable.getPath().toString()));
        InputStream is = null;
        try {
            int fileOffset = readWriteFileParamsParcelable.getFileOffset();
            if (fileOffset < 0) fileOffset = 0;
            int dataOffset = readWriteFileParamsParcelable.getDataOffset();
            if (dataOffset < 0) dataOffset = 0;
            int numBytesToProcess = readWriteFileParamsParcelable.getLength();
            is = newInputStream(readWriteFileParamsParcelable.getPath(), readWriteFileParamsParcelable.getOptions().toArray(new OpenOption[0]));
            byte[] streamData = IOUtil.toByteArray(is);
            if (streamData.length - fileOffset < numBytesToProcess) {
                Log.d(TAG, String.format("readFile(%s) failed: Insufficient amount of data in file. Available: %d, requested: %d, file offset: %d.", readWriteFileParamsParcelable.getPath().toString(), streamData.length - fileOffset, numBytesToProcess, fileOffset));
                return false;
            }
            if (numBytesToProcess < 1) numBytesToProcess = streamData.length - fileOffset;
            if (readWriteFileParamsParcelable.getData() != null) {
                if (readWriteFileParamsParcelable.getData().length < numBytesToProcess + dataOffset) {
                    Log.d(TAG, String.format("readFile(%s) failed: Insufficient buffer size. data buffer size: %d, required: %d, data offset: %d.", readWriteFileParamsParcelable.getPath().toString(), readWriteFileParamsParcelable.getData().length, numBytesToProcess + dataOffset, dataOffset));
                    return false;
                }
            } else {
                readWriteFileParamsParcelable.newData(numBytesToProcess + dataOffset);
            }
            System.arraycopy(streamData, fileOffset, readWriteFileParamsParcelable.getData(), dataOffset, numBytesToProcess);
            return true;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
            logMethodEntranceExit(false);
        }
        return false;
    }

    public static boolean writeFile(ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        logMethodEntranceExit(true);
        Log.d(TAG, String.format("writeFile(%s)", readWriteFileParamsParcelable.getPath().toString()));
        OutputStream os = null;
        if (readWriteFileParamsParcelable.getData() == null) {
            Log.d(TAG, String.format("writeFile(%s) failed: No data provided to write to file.", readWriteFileParamsParcelable.getPath().toString()));
            logMethodEntranceExit(false);
            return false;
        }
        try {
            int fileOffset = readWriteFileParamsParcelable.getFileOffset();
            if (fileOffset < 0) fileOffset = 0;
            int dataOffset = readWriteFileParamsParcelable.getDataOffset();
            if (dataOffset < 0) dataOffset = 0;
            int numBytesToProcess = readWriteFileParamsParcelable.getLength();
            if (readWriteFileParamsParcelable.getData().length < dataOffset + numBytesToProcess) {
                Log.d(TAG, String.format("readFile(%s) failed: Insufficient amount of data. data buffer size: %d, required: %d, data offset: %d.", readWriteFileParamsParcelable.getPath().toString(), readWriteFileParamsParcelable.getData().length, numBytesToProcess + dataOffset, dataOffset));
                return false;
            }
            os = newOutputStream(readWriteFileParamsParcelable.getPath(), readWriteFileParamsParcelable.getOptions().toArray(new OpenOption[0]));
            while (fileOffset-- > 0) os.write(0);
            os.write(readWriteFileParamsParcelable.getData(), dataOffset, numBytesToProcess);
            return true;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
            logMethodEntranceExit(false);
        }
        return false;
    }

    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public static boolean mountSDCard(Context context, boolean mount) {
        try {
            context.sendBroadcast(new Intent(mount?Intent.ACTION_MEDIA_MOUNTED:Intent.ACTION_MEDIA_UNMOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            return true;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return false;
    }
}
