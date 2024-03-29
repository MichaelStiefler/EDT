package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.casioeurope.mis.edt.BuildConfig;
import com.casioeurope.mis.edt.type.ReadWriteFileParamsParcelable;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.OpenOption;
import java.util.Arrays;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;

public class FileTools {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static final String TAG = "EDT (FileTools)";

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrentMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrentMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrentMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrentMethod + " " + sb.toString() + (entrance ? " +" : " -"));
    }

    @SuppressLint("ObsoleteSdkInt")
    public static boolean readFile(ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        logMethodEntranceExit(true);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            logMethodEntranceExit(false);
            return false;
        }
        Log.d(TAG, String.format("readFile(%s)", readWriteFileParamsParcelable.getPath().toString()));
        InputStream is = null;
        try {
            int fileOffset = readWriteFileParamsParcelable.getFileOffset();
            if (fileOffset < 0) fileOffset = 0;
            int dataOffset = readWriteFileParamsParcelable.getDataOffset();
            if (dataOffset < 0) dataOffset = 0;
            int numBytesToProcess = readWriteFileParamsParcelable.getLength();
            is = newInputStream(readWriteFileParamsParcelable.getPath(), readWriteFileParamsParcelable.getOptions().toArray(new OpenOption[0]));
            byte[] streamData = IOUtils.toByteArray(is);
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

    @SuppressLint("ObsoleteSdkInt")
    public static boolean writeFile(ReadWriteFileParamsParcelable readWriteFileParamsParcelable) {
        logMethodEntranceExit(true);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            logMethodEntranceExit(false);
            return false;
        }
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
            context.sendBroadcast(new Intent(mount ? Intent.ACTION_MEDIA_MOUNTED : Intent.ACTION_MEDIA_UNMOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            return true;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return false;
    }

    public static boolean logcatToFile(String fileName, String buffers) {
        try {
//            Log.d(TAG, String.format("logcatToFile(%s, %s)", fileName, buffers));
            File file = new File(fileName);
//            Log.d(TAG, String.format("file = %s", file.getAbsolutePath()));
            if (file.exists()) {
//                Log.d(TAG, "file exists...");
                if (!file.delete()) return false;
//                Log.d(TAG, "deleted.");
            }

            String cmd = new StringBuilder("logcat ").append(buffersCmd(buffers)).append("-d -f ").append(file.getAbsolutePath()).toString();
            //String cmd = "logcat -b " + buffers + " -d -f " + file.getAbsolutePath();
//            Log.d(TAG, String.format("cmd = %s", cmd));
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
//            Log.d(TAG, String.format("p.exitValue() = %d", p.exitValue()));
            return p.exitValue() == 0;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return false;
    }

    public static boolean logcatClear(String buffers) {
        try {
            String cmd = new StringBuilder("logcat ").append(buffersCmd(buffers)).append("-c").toString();
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            return p.exitValue() == 0;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return false;
    }

    private static String buffersCmd(String buffers) {
        StringBuilder cmdBuilder = new StringBuilder();
        if (buffers != null) {
            String[] buffersArray = buffers.trim().split("\\s*,\\s*");
            if (!(buffersArray.length == 1 && (buffersArray[0].length()<1 || buffersArray[0].equalsIgnoreCase("default")))) {
                for (String buffer:buffersArray)
                    if (buffer.equalsIgnoreCase("default"))
                        cmdBuilder.append("-b main -b system -b crash ");
                    else
                        cmdBuilder.append("-b ").append(buffer).append(" ");
            }
        }
        return cmdBuilder.toString();
    }

}
