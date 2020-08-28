package jp.co.casio.edt.service.barcodescanner;

import android.content.Context;

import jp.co.casio.edt.service.barcodescanner.etl10.ScannerLibrary;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ScanLib {

    @SuppressWarnings({"unused", "RedundantSuppression"})
    private static final String TAG_SCANSETTINGS = "[CSS]";

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean getCurrentScanSettings(String settingsFilePath, Context context) {
        return false;
    }

    public static boolean setNewScanSettings(String settingsFilePath, Context context) {
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
            if (!setSettingsJson(fileNameNewJson, context))
                return setSettingsXml(fileNameNewXml, context);
            else return true;
        } else if (setJson) {
            return setSettingsJson(fileNameNewJson, context);
        } else {
            return setSettingsXml(fileNameNewXml, context);
        }
    }

    public static boolean getCurrentAndSetNewScanSettings(String settingsFilePath, Context context) {
        return setNewScanSettings(settingsFilePath, context);
    }

    private static boolean setSettingsJson(String jsonFilePath, Context context) {
        ScannerLibrary scanLib = new ScannerLibrary(context);
        Gson gson = new Gson();

        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) {
                return false;
            }
            Reader reader = new FileReader(jsonFilePath);
            ScannerLibraryProperties scannerLibraryProperties = gson.fromJson(reader, ScannerLibraryProperties.class);
            reader.close();
            ScanLib.setCommonSettings(scanLib, scannerLibraryProperties);
            for (Symbology symbology : scannerLibraryProperties.getSymbologies()) {
                ScanLib.setSymbologies(scanLib, symbology);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean setSettingsXml(String xmlFilePath, Context context) {
        ScannerLibrary scanLib = new ScannerLibrary(context);
        try {
            File file = new File(xmlFilePath);
            if (!file.exists()) {
                return false;
            }
            Serializer serializer = new Persister();
            ScannerLibraryProperties scannerLibraryProperties = serializer.read(ScannerLibraryProperties.class, file);
            ScanLib.setCommonSettings(scanLib, scannerLibraryProperties);
            for (Symbology symbology : scannerLibraryProperties.getSymbologies()) {
                ScanLib.setSymbologies(scanLib, symbology);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static void setSymbologies(ScannerLibrary scanLib, Symbology symbology) {
        scanLib.setSymbologyEnable(symbology.getId(), symbology.isEnabled() ? ScannerLibrary.CONSTANT.SYMBOLOGY_PARAMETER.ENABLE : ScannerLibrary.CONSTANT.SYMBOLOGY_PARAMETER.DISABLE);
    }

    static void setCommonSettings(ScannerLibrary scanLib, ScannerLibraryProperties scannerLibraryProperties) {
        scanLib.setNotificationLED(scannerLibraryProperties.getNotificationLED());
        scanLib.setNotificationVibrator(scannerLibraryProperties.getNotificationVibrator());
        scanLib.setNotificationSound(scannerLibraryProperties.getNotificationSound());
        scanLib.setFailureSound(scannerLibraryProperties.getFailureSound());
        scanLib.setOutputType(scannerLibraryProperties.getOutputType());
        scanLib.setSuffix(scannerLibraryProperties.getSuffix());
        scanLib.setTriggerKeyEnable(scannerLibraryProperties.getTriggerKeyEnable());
        scanLib.setTriggerKeyTimeout(scannerLibraryProperties.getTriggerKeyTimeout());
    }
}
