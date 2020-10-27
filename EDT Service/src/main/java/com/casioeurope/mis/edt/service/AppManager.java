package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppManager {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    @SuppressWarnings("FieldCanBeLocal")
    private static final String TAG = "EDT (AppManager)";

    @SuppressLint({"SdCardPath", "ObsoleteSdkInt"})
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

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    public static boolean installApk(Context context, String apkFilename, boolean update) {
        logMethodEntranceExit(true, String.format("installPackage(%s, %s, %b)", context.getPackageName(), apkFilename, update));
        File localApk = new File(apkFilename);
        PackageManager packageManager = context.getPackageManager();
        PackageInstaller packageInstaller = packageManager.getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        sessionParams.setAppPackageName(Objects.requireNonNull(packageManager.getPackageArchiveInfo(localApk.getAbsolutePath(), 0)).packageName);
        try {
            int sessionId = packageInstaller.createSession(sessionParams);
            PackageInstaller.Session session = packageInstaller.openSession(sessionId);
            try(OutputStream out = session.openWrite(localApk.getName(), 0, -1);
                FileInputStream fin = new FileInputStream(localApk)) {
                out.write(IOUtil.toByteArray(fin));
                session.fsync(out);
            }
            session.commit(PendingIntent.getBroadcast(context, sessionId,
                    new Intent(Intent.ACTION_MAIN), 0).getIntentSender());
            logMethodEntranceExit(false, String.format("installPackage(%s, %s, %b) = true", context.getPackageName(), apkFilename, update));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        logMethodEntranceExit(false, String.format("installPackage(%s, %s, %b) = false", context.getPackageName(), apkFilename, update));
        return false;
    }

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

    public static boolean uninstallPackage(Context context, String packageName) {
        logMethodEntranceExit(true, String.format("uninstallPackage(%s, %s)", context.getPackageName(), packageName));
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        sessionParams.setAppPackageName(packageName);
        try {
            packageInstaller.uninstall(packageName, PendingIntent.getBroadcast(context, packageInstaller.createSession(sessionParams),
                    new Intent(Intent.ACTION_MAIN), 0).getIntentSender());
            logMethodEntranceExit(false, String.format("uninstallPackage(%s, %s) = true", context.getPackageName(), packageName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("uninstallPackage(%s, %s) = false", context.getPackageName(), packageName));
        return false;
    }
}
