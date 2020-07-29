package com.casioeurope.mis.edt.service.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import jp.casio.ht.devicelibrary.ScannerLibrary;

public class ScanLib {

    private static final String TAG_SCANSETTINGS = "[CSS]";
    private static ScannerLibrary scanLib;

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
        scanLib = new ScannerLibrary();
        int[] validSymbologies = ScanLib.getValidSymbologies();
        ScannerLibraryProperties scannerLibraryProperties = ScanLib.getCommonSettings(scanLib);

        for (int validSymbology:validSymbologies) {
            scannerLibraryProperties.getSymbologies().add(ScanLib.getSymbologies(scanLib, validSymbology));
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Writer writer = new FileWriter(jsonFilePath);
            gson.toJson(scannerLibraryProperties, writer);
            writer.flush();
            writer.close();
            scanFile(jsonFilePath, context);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean getSettingsXml(String xmlFilePath, Context context) {
        scanLib = new ScannerLibrary();
        int[] validSymbologies = ScanLib.getValidSymbologies();
        ScannerLibraryProperties scannerLibraryProperties = ScanLib.getCommonSettings(scanLib);

        for (int validSymbology:validSymbologies) {
            scannerLibraryProperties.getSymbologies().add(ScanLib.getSymbologies(scanLib, validSymbology));
        }

//        Serializer serializer = new Persister(new org.simpleframework.xml.stream.Format(2, new CamelCaseStyle()));
        Serializer serializer = new Persister();
        File result = new File(xmlFilePath);
        try {
            serializer.write(scannerLibraryProperties, result);
            scanFile(xmlFilePath, context);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        scanLib = new ScannerLibrary();
        Gson gson = new Gson();

        try {
            File file = new File(jsonFilePath);
            if(!file.exists()) {
                return false;
            }
            Reader reader = new FileReader(jsonFilePath);
            ScannerLibraryProperties scannerLibraryProperties = gson.fromJson(reader, ScannerLibraryProperties.class);
            reader.close();
            ScanLib.setCommonSettings(scanLib, scannerLibraryProperties);
            for (Symbology symbology:scannerLibraryProperties.getSymbologies()) {
                ScanLib.setSymbologies(scanLib, symbology);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean setSettingsXml(String xmlFilePath) {
        scanLib = new ScannerLibrary();
        try {
            File file = new File(xmlFilePath);
            if(!file.exists()) {
                return false;
            }
            Serializer serializer = new Persister();
            ScannerLibraryProperties scannerLibraryProperties = serializer.read(ScannerLibraryProperties.class, file);
            ScanLib.setCommonSettings(scanLib, scannerLibraryProperties);
            for (Symbology symbology:scannerLibraryProperties.getSymbologies()) {
                ScanLib.setSymbologies(scanLib, symbology);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static int[] getValidSymbologies() {
        Field[] declaredFields = ScannerLibrary.CONSTANT.SYMBOLOGY.class.getDeclaredFields();
        List<Integer> validSymbologyIDs = new ArrayList<>();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    int fieldValue = field.getInt(ScannerLibrary.CONSTANT.SYMBOLOGY.class);
                    if (fieldValue != 999) validSymbologyIDs.add(fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return toArray(validSymbologyIDs);
    }

    private static int[] toArray(List<Integer> list) {
        int[] ret = new int[ list.size() ];
        int i = 0;
        for (Integer integer : list) ret[i++] = integer;
        return ret;
    }

    private static SymbologyProperty getSymbologyProperties(ScannerLibrary scanLib, int symbologyId, int propertyId) {
        int retVal = scanLib.getSymbologyProperty(symbologyId, propertyId);
        if (retVal == ScannerLibrary.CONSTANT.RETURN.ERROR_PARAMETER
                || retVal == ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED) return null;
        return new SymbologyProperty(propertyId, retVal);
    }

    private static void setSymbologyProperties(ScannerLibrary scanLib, Symbology symbology) {
        for (SymbologyProperty symbologyProperty:symbology.getProperties()) {
            int retVal = scanLib.setSymbologyProperty(symbology.getId(), symbologyProperty.getId(), symbologyProperty.getValue());
            if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
                Log.d(TAG_SCANSETTINGS, "setSymbologyProperty(" + symbology.getId() + ", " + symbologyProperty.getId() + ", " + symbologyProperty.getValue() + ") = " + retVal);
        }
    }

    static Symbology getSymbologies(ScannerLibrary scanLib, int symbologyId) {
        Symbology symbology = new Symbology(symbologyId);

        int retVal = scanLib.getSymbologyEnable(symbologyId);
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED
                && retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_PARAMETER)
            symbology.setEnabled(retVal == ScannerLibrary.CONSTANT.SYMBOLOGY_PARAMETER.ENABLE);
        else Log.d(TAG_SCANSETTINGS, "getSymbologyEnable(" + symbologyId + ") = " + retVal);

        retVal = scanLib.getSymbologyMin(symbologyId);
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED
                && retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_PARAMETER)
            symbology.setMin(retVal);
        else Log.d(TAG_SCANSETTINGS, "getSymbologyMin(" + symbologyId + ") = " + retVal);

        retVal = scanLib.getSymbologyMax(symbologyId);
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED
                && retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_PARAMETER)
            symbology.setMax(retVal);
        else Log.d(TAG_SCANSETTINGS, "getSymbologyMax(" + symbologyId + ") = " + retVal);

        retVal = scanLib.getSymbologyCheckCount(symbologyId);
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED
                && retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_PARAMETER)
            symbology.setCheckCount(retVal);
        else Log.d(TAG_SCANSETTINGS, "getSymbologyCheckCount(" + symbologyId + ") = " + retVal);

        int propertyId = 0;

        SymbologyProperty symbologyProperty;

        do {
            symbologyProperty = getSymbologyProperties(scanLib, symbologyId, propertyId++);
            if (symbologyProperty != null) symbology.getProperties().add(symbologyProperty);
        } while (symbologyProperty != null);

        return symbology;
    }

    static void setSymbologies(ScannerLibrary scanLib, Symbology symbology) {

        int retVal = scanLib.setSymbologyEnable(symbology.getId(), symbology.isEnabled()?ScannerLibrary.CONSTANT.SYMBOLOGY_PARAMETER.ENABLE:ScannerLibrary.CONSTANT.SYMBOLOGY_PARAMETER.DISABLE);
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setSymbologyEnable(" + symbology.getId() + ", " + (symbology.isEnabled()?ScannerLibrary.CONSTANT.SYMBOLOGY_PARAMETER.ENABLE:ScannerLibrary.CONSTANT.SYMBOLOGY_PARAMETER.DISABLE) + ") = " + retVal);

        retVal = scanLib.setSymbologyMin(symbology.getId(), symbology.getMin());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setSymbologyMin(" + symbology.getId() + ", " + symbology.getMin() + ") = " + retVal);

        retVal = scanLib.setSymbologyMax(symbology.getId(), symbology.getMax());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setSymbologyMax(" + symbology.getId() + ", " + symbology.getMax() + ") = " + retVal);

        retVal = scanLib.setSymbologyCheckCount(symbology.getId(), symbology.getCheckCount());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setSymbologyCheckCount(" + symbology.getId() + ", " + symbology.getCheckCount() + ") = " + retVal);

        retVal = scanLib.setSymbologyCheckCount(symbology.getId(), symbology.getCheckCount());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setSymbologyCheckCount(" + symbology.getId() + ", " + symbology.getCheckCount() + ") = " + retVal);

        setSymbologyProperties(scanLib, symbology);
    }

    static ScannerLibraryProperties getCommonSettings(ScannerLibrary scanLib) {
        ScannerLibraryProperties scannerLibraryProperties = new ScannerLibraryProperties();
        int retVal = scanLib.getNotificationLED();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setNotificationLED(retVal);
        else Log.d(TAG_SCANSETTINGS, "getNotificationLED() = " + retVal);

        retVal = scanLib.getNotificationVibrator();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setNotificationVibrator(retVal);
        else Log.d(TAG_SCANSETTINGS, "getNotificationVibrator() = " + retVal);

        retVal = scanLib.getNotificationSound();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setNotificationSound(retVal);
        else Log.d(TAG_SCANSETTINGS, "getNotificationSound() = " + retVal);

        retVal = scanLib.getLightMode();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setLightMode(retVal);
        else Log.d(TAG_SCANSETTINGS, "getLightMode() = " + retVal);

        retVal = scanLib.getOutputType();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setOutputType(retVal);
        else Log.d(TAG_SCANSETTINGS, "getOutputType() = " + retVal);

        retVal = scanLib.getSuffix();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setSuffix(retVal);
        else Log.d(TAG_SCANSETTINGS, "getSuffix() = " + retVal);

        retVal = scanLib.getInverseMode();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setInverseMode(retVal);
        else Log.d(TAG_SCANSETTINGS, "getInverseMode() = " + retVal);

        retVal = scanLib.getTriggerKeyEnable();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setTriggerKeyEnable(retVal);
        else Log.d(TAG_SCANSETTINGS, "getTriggerKeyEnable() = " + retVal);

        retVal = scanLib.getTriggerKeyMode();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setTriggerKeyMode(retVal);
        else Log.d(TAG_SCANSETTINGS, "getTriggerKeyMode() = " + retVal);

        retVal = scanLib.getNumberOfBarcodes();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setNumberOfBarcodes(retVal);
        else Log.d(TAG_SCANSETTINGS, "getNumberOfBarcodes() = " + retVal);

        retVal = scanLib.getDelimiter();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setDelimiter(retVal);
        else Log.d(TAG_SCANSETTINGS, "getDelimiter() = " + retVal);

        retVal = scanLib.getTriggerKeyTimeout();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setTriggerKeyTimeout(retVal);
        else Log.d(TAG_SCANSETTINGS, "getTriggerKeyTimeout() = " + retVal);

        retVal = scanLib.getScannerAPO();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setScannerAPOTime(retVal);
        else Log.d(TAG_SCANSETTINGS, "getScannerAPO() = " + retVal);

        retVal = scanLib.getCenteringWindow();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setCenteringWindow(retVal);
        else Log.d(TAG_SCANSETTINGS, "getCenteringWindow() = " + retVal);

        retVal = scanLib.getDetectionAreaSize();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setDetectionAreaSize(retVal);
        else Log.d(TAG_SCANSETTINGS, "getDetectionAreaSize() = " + retVal);

        retVal = scanLib.getLaserSwingWidth();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setLaserSwingWidth(retVal);
        else Log.d(TAG_SCANSETTINGS, "getLaserSwingWidth() = " + retVal);

        retVal = scanLib.getLaserHighlightMode();
        if (retVal != ScannerLibrary.CONSTANT.RETURN.ERROR_UNSUPPORTED)
            scannerLibraryProperties.setLaserHighlightMode(retVal);
        else Log.d(TAG_SCANSETTINGS, "getLaserHighlightMode() = " + retVal);

        return scannerLibraryProperties;
    }

    static void setCommonSettings(ScannerLibrary scanLib, ScannerLibraryProperties scannerLibraryProperties) {
        int retVal = scanLib.setNotificationLED(scannerLibraryProperties.getNotificationLED());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setNotificationLED(" + scannerLibraryProperties.getNotificationLED() + ") = " + retVal);

        retVal = scanLib.setNotificationVibrator(scannerLibraryProperties.getNotificationVibrator());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setNotificationVibrator(" + scannerLibraryProperties.getNotificationVibrator() + ") = " + retVal);

        retVal = scanLib.setNotificationSound(scannerLibraryProperties.getNotificationSound());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setNotificationSound(" + scannerLibraryProperties.getNotificationSound() + ") = " + retVal);

        retVal = scanLib.setLightMode(scannerLibraryProperties.getLightMode());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setLightMode(" + scannerLibraryProperties.getLightMode() + ") = " + retVal);

        retVal = scanLib.setOutputType(scannerLibraryProperties.getOutputType());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setOutputType(" + scannerLibraryProperties.getOutputType() + ") = " + retVal);

        retVal = scanLib.setSuffix(scannerLibraryProperties.getSuffix());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setSuffix(" + scannerLibraryProperties.getSuffix() + ") = " + retVal);

        retVal = scanLib.setInverseMode(scannerLibraryProperties.getInverseMode());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setInverseMode(" + scannerLibraryProperties.getInverseMode() + ") = " + retVal);

        retVal = scanLib.setTriggerKeyEnable(scannerLibraryProperties.getTriggerKeyEnable());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setTriggerKeyEnable(" + scannerLibraryProperties.getTriggerKeyEnable() + ") = " + retVal);

        retVal = scanLib.setTriggerKeyMode(scannerLibraryProperties.getTriggerKeyMode());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setTriggerKeyMode(" + scannerLibraryProperties.getTriggerKeyMode() + ") = " + retVal);

        retVal = scanLib.setNumberOfBarcodes(scannerLibraryProperties.getNumberOfBarcodes());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setNumberOfBarcodes(" + scannerLibraryProperties.getNumberOfBarcodes() + ") = " + retVal);

        retVal = scanLib.setDelimiter(scannerLibraryProperties.getDelimiter());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setDelimiter(" + scannerLibraryProperties.getDelimiter() + ") = " + retVal);

        retVal = scanLib.setTriggerKeyTimeout(scannerLibraryProperties.getTriggerKeyTimeout());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setTriggerKeyTimeout(" + scannerLibraryProperties.getTriggerKeyTimeout() + ") = " + retVal);

        retVal = scanLib.setScannerAPO(scannerLibraryProperties.getScannerAPOTime());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setScannerAPO(" + scannerLibraryProperties.getScannerAPOTime() + ") = " + retVal);

        retVal = scanLib.setCenteringWindow(scannerLibraryProperties.getCenteringWindow());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setCenteringWindow(" + scannerLibraryProperties.getCenteringWindow() + ") = " + retVal);

        retVal = scanLib.setDetectionAreaSize(scannerLibraryProperties.getDetectionAreaSize());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setDetectionAreaSize(" + scannerLibraryProperties.getDetectionAreaSize() + ") = " + retVal);

        retVal = scanLib.setLaserSwingWidth(scannerLibraryProperties.getLaserSwingWidth());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setLaserSwingWidth(" + scannerLibraryProperties.getLaserSwingWidth() + ") = " + retVal);

        retVal = scanLib.setLaserHighlightMode(scannerLibraryProperties.getLaserHighlightMode());
        if (retVal != ScannerLibrary.CONSTANT.RETURN.SUCCESS)
            Log.d(TAG_SCANSETTINGS, "setLaserHighlightMode(" + scannerLibraryProperties.getLaserHighlightMode() + ") = " + retVal);

    }
}
