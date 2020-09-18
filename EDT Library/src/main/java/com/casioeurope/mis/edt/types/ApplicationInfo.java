package com.casioeurope.mis.edt.types;

/**
 * Application information class for use in {@link com.casioeurope.mis.edt.KeyLibrary} methods
 */
public class ApplicationInfo {
    /**
     * Class name of the launch application.
     */
    public String activityName;
    /**
     * Package name of the launch application.
     */
    public String packageName;

    /**
     * Create a instance of the ApplicationInfo class.
     */
    public ApplicationInfo() {
        this.activityName = "";
        this.packageName = "";
    }

    /**
     * Create a instance of the ApplicationInfo class with custom parameters given for {@link #packageName} and {@link #activityName}.
     * @param packageName {@link String}: Package name of the launch application.
     * @param activityName {@link String}: Class name of the launch application.
     */
    public ApplicationInfo(String packageName, String activityName) {
        this.packageName = packageName;
        this.activityName = activityName;
    }
}
