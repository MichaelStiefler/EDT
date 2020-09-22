package com.casioeurope.mis.edt.type;

/**
 * Scan Result class for use in {@link com.casioeurope.mis.edt.ScannerLibrary} methods
 */
public class ScanResult {
    /**
     * AIM ID of the barcode ( default 0 )
     */
    public byte aimID;
    /**
     * AIM ID modifier of the barcode ( default 0 )
     */
    public byte aimModifier;
    /**
     * Length of barcode ( default 0 )
     */
    public int length;
    /**
     * SymbologyID of the barcode ( default 0 )
     */
    public int symbologyID;
    /**
     * Symbol name of the barcode ( default null )
     */
    public String symbologyName;
    /**
     * Reading time ( default 0 )
     */
    public int time;
    /**
     * Scanned barcode data ( default null )
     */
    public byte[] value;

    /**
     * Create a instance of the ScanResult class.
     * All Fields will have their default values.
     */
    public ScanResult() {
        this.clear();
    }

    /**
     * Reset all fields of this ScanResult Object to default values.
     */
    public void clear() {
        this.symbologyID = 0;
        this.length = 0;
        this.time = 0;
        this.value = null;
        this.aimID = 0;
        this.aimModifier = 0;
        this.symbologyName = "";
    }
}
