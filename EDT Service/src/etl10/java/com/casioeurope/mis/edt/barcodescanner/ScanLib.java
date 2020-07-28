package com.casioeurope.mis.edt.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanLib {

    private static final String TAG_SCANSETTINGS = "[CSS]";

    public static boolean getCurrentScanSettings(String settingsFilePath, Context context) {
        String fileNameCurrentJson = FileUtils.removeExtension(settingsFilePath) + "_cur.json";
        String fileNameCurrentXml = FileUtils.removeExtension(settingsFilePath) + "_cur.xml";
        boolean getJson = true, getXml = true;
        if (settingsFilePath.toLowerCase().endsWith(".json")) {
            getXml = false;
        } else if (settingsFilePath.toLowerCase().endsWith(".xml")) {
            getJson = false;
        }
        boolean retVal = false;
        if (getXml) retVal |= getSettingsXml(fileNameCurrentXml, context);
        if (getJson) retVal |= getSettingsJson(fileNameCurrentJson, context);
        return retVal;
    }

    public static boolean setNewScanSettings(String settingsFilePath) {
        String fileNameNewJson = "";
        String fileNameNewXml = "";
        boolean setJson = true, setXml = true;
        if (settingsFilePath.toLowerCase().endsWith(".json")) {
            setXml = false;
            fileNameNewJson = settingsFilePath;
        } else if (settingsFilePath.toLowerCase().endsWith(".xml")) {
            setJson = false;
            fileNameNewXml = settingsFilePath;
        } else {
            if (FileUtils.getExtension(settingsFilePath).length() == 0) {
                fileNameNewJson = settingsFilePath + ".json";
                fileNameNewXml = settingsFilePath + ".xml";
            } else {
                fileNameNewJson = settingsFilePath;
                fileNameNewXml = settingsFilePath;
            }
        }
        if (setJson && setXml) {
            if (!setSettingsJson(fileNameNewJson))
                 return setSettingsXml(fileNameNewXml);
            else return true;
        } else if (setJson) {
            return setSettingsJson(fileNameNewJson);
        } else if (setXml) {
            return setSettingsXml(fileNameNewXml);
        }
        else return false;
    }

    public static boolean getCurrentAndSetNewScanSettings(String settingsFilePath, Context context) {
        boolean retVal = getCurrentScanSettings(settingsFilePath, context);
        retVal &= setNewScanSettings(settingsFilePath);
        return retVal;
    }

    private static boolean getSettingsJson(String jsonFilePath, Context context) {
        return false;
    }

    private static boolean getSettingsXml(String xmlFilePath, Context context) {
        return false;
    }

    @SuppressWarnings("deprecation")
    private static void scanFile(String path, Context context) {
        Uri contentUri = Uri.fromFile(new File(path));
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    private static boolean setSettingsJson(String jsonFilePath) {
        return false;
    }

    private static boolean setSettingsXml(String xmlFilePath) {
        return false;
    }

    static int[] getValidSymbologies() {
        List<Integer> validSymbologyIDs = new ArrayList<>();
        return toArray(validSymbologyIDs);
    }

    private static int[] toArray(List<Integer> list) {
        int[] ret = new int[ list.size() ];
        int i = 0;
        for (Integer integer : list) ret[i++] = integer;
        return ret;
    }

}
