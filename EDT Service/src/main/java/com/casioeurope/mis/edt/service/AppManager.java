package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppManager {
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (AppManager)";
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;

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

    public static boolean installApk(Context context, String apkFilename, boolean update) {
        logMethodEntranceExit(true, String.format("installPackage(%s, %s, %b)", context.getPackageName(), apkFilename, update));
        File localApk = new File(apkFilename);
        String[] Commands = update ? new String[]{"pm", "install", "-r", localApk.getAbsolutePath()} : new String[]{"pm", "install", localApk.getAbsolutePath()};
        try {
            Runtime.getRuntime().exec(Commands);
            logMethodEntranceExit(false, String.format("installPackage(%s, %s, %b) = true", context.getPackageName(), apkFilename, update));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("installPackage(%s, %s, %b) = false", context.getPackageName(), apkFilename, update));
        return true;
    }

    public static boolean uninstallPackage(Context context, String packageName, boolean keepData) {
        logMethodEntranceExit(true, String.format("uninstallPackage(%s, %s, %b)", context.getPackageName(), packageName, keepData));
        String[] Commands = keepData ? new String[]{"pm", "uninstall", "-k", packageName} : new String[]{"pm", "uninstall", packageName};
        try {
            Runtime.getRuntime().exec(Commands);
            logMethodEntranceExit(false, String.format("uninstallPackage(%s, %s, %b) = true", context.getPackageName(), packageName, keepData));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("uninstallPackage(%s, %s, %b) = false", context.getPackageName(), packageName, keepData));
        return true;
    }

    public static boolean clearDataForPackage(Context context, String packageName) {
        logMethodEntranceExit(true, String.format("clearDataForPackage(%s, %s)", context.getPackageName(), packageName));
        String[] Commands = {"pm", "clear", packageName};
        try {
            Runtime.getRuntime().exec(Commands);
            logMethodEntranceExit(false, String.format("clearDataForPackage(%s, %s) = true", context.getPackageName(), packageName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("clearDataForPackage(%s, %s) = false", context.getPackageName(), packageName));
        return true;
    }

    private static void removeCache(String folderName) {
        File androidDataFolder = new File(folderName);
        if (androidDataFolder.exists()) {
            File[] filesList = androidDataFolder.listFiles();
            if (filesList != null) {
                for (File file : filesList) {
                    if (file.isDirectory()) {
                        file = new File(file.getAbsolutePath() + "/cache/");
                        if (file.exists()) {
                            try {
                                FileUtils.deleteDirectory(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SdCardPath")
    public static boolean clearCacheForPackage(Context context, String packageName) {
        logMethodEntranceExit(true, String.format("clearCacheForPackage(%s, %s)", context.getPackageName(), packageName));
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) { // requires Android N or later
            logMethodEntranceExit(false, String.format("clearDataForPackage(%s, %s) = false", context.getPackageName(), packageName));
            return false;
        }
        try {
            StorageManager storage = context.getSystemService(StorageManager.class);
            List<StorageVolume> volumes = storage.getStorageVolumes();
            @SuppressWarnings("JavaReflectionMemberAccess") Method getPath = StorageVolume.class.getMethod("getPath");
            List<String> storageVolumePaths = volumes.stream().map(v -> {
                try {
                    return (String) getPath.invoke(v);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

            for (String storageVolumePath : storageVolumePaths) {
                removeCache(String.format("%s/Android/data/%s/", storageVolumePath, packageName));
                removeCache(String.format("%s/data/data/%s/", storageVolumePath, packageName));
                removeCache(String.format("%s/data/%s/", storageVolumePath, packageName));
            }
            logMethodEntranceExit(false, String.format("clearDataForPackage(%s, %s) = true", context.getPackageName(), packageName));
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("clearDataForPackage(%s, %s) = false", context.getPackageName(), packageName));
        return true;
    }

    public static boolean enableApplication(Context context, String packageName) {
        logMethodEntranceExit(true, String.format("enableApplication(%s, %s)", context.getPackageName(), packageName));
        String[] Commands = {"pm", "enable", packageName};
        try {
            Runtime.getRuntime().exec(Commands);
            logMethodEntranceExit(false, String.format("enableApplication(%s, %s) = true", context.getPackageName(), packageName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("enableApplication(%s, %s) = false", context.getPackageName(), packageName));
        return true;
    }

    public static boolean disableApplication(Context context, String packageName) {
        logMethodEntranceExit(true, String.format("disableApplication(%s, %s)", context.getPackageName(), packageName));
        String[] Commands = {"pm", "disable", packageName};
        try {
            Runtime.getRuntime().exec(Commands);
            logMethodEntranceExit(false, String.format("disableApplication(%s, %s) = true", context.getPackageName(), packageName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("disableApplication(%s, %s) = false", context.getPackageName(), packageName));
        return true;
    }

    public static boolean disableBatteryOptimization(Context context, String packageName) {
        logMethodEntranceExit(true, String.format("disableBatteryOptimization(%s, %s)", context.getPackageName(), packageName));
        try {
            @SuppressLint("PrivateApi") Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClass.getMethod("getService", String.class);
            Object mIDeviceIdleController = getService.invoke(serviceManagerClass, "deviceidle");
            if (mIDeviceIdleController == null) {
                logMethodEntranceExit(false, "ServiceManager.getService(Context.DEVICE_IDLE_CONTROLLER) returned null!");
                return false;
            }
            @SuppressLint("PrivateApi") Class<?> iDeviceIdleControllerClass = Class.forName("android.os.IDeviceIdleController");
            Method addPowerSaveWhitelistApp = iDeviceIdleControllerClass.getMethod("addPowerSaveWhitelistApp", String.class);
            addPowerSaveWhitelistApp.invoke(mIDeviceIdleController, packageName);
            logMethodEntranceExit(false, String.format("disableBatteryOptimization(%s, %s) = true", context.getPackageName(), packageName));
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("disableBatteryOptimization(%s, %s) = false", context.getPackageName(), packageName));
        return true;
    }

    public static boolean enableBatteryOptimization(Context context, String packageName) {
        logMethodEntranceExit(true, String.format("enableBatteryOptimization(%s, %s)", context.getPackageName(), packageName));
        try {
            @SuppressLint("PrivateApi") Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClass.getMethod("getService", String.class);
            Object mIDeviceIdleController = getService.invoke(serviceManagerClass, "deviceidle");
            if (mIDeviceIdleController == null) {
                logMethodEntranceExit(false, "ServiceManager.getService(Context.DEVICE_IDLE_CONTROLLER) returned null!");
                return false;
            }
            @SuppressLint("PrivateApi") Class<?> iDeviceIdleControllerClass = Class.forName("android.os.IDeviceIdleController");
            Method removePowerSaveWhitelistApp = iDeviceIdleControllerClass.getMethod("removePowerSaveWhitelistApp", String.class);
            removePowerSaveWhitelistApp.invoke(mIDeviceIdleController, packageName);
            logMethodEntranceExit(false, String.format("enableBatteryOptimization(%s, %s) = true", context.getPackageName(), packageName));
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("enableBatteryOptimization(%s, %s) = false", context.getPackageName(), packageName));
        return true;
    }

}
