package com.casioeurope.mis.edt.type;

import android.util.Log;

import com.casioeurope.mis.edt.BuildConfig;
import com.casioeurope.mis.edt.EDTLibrary;

import java.util.Arrays;
import java.util.Objects;

/**
 * A set of parameters used to hand over to the Access Point Name (APN) configuration related methods of the CASIO Enterprise Developer Tools:
 *
 * <p><ul>
 * <li>{@link EDTLibrary#createNewApn(APN, boolean) createNewApn}</li>
 * <li>{@link EDTLibrary#getAllApnList() getAllApnList}</li>
 * <li>{@link EDTLibrary#getApn(String) getApn}</li>
 * <li>{@link EDTLibrary#getApnId(String) getApnId}</li>
 * <li>{@link EDTLibrary#setPreferredApn(String) setPreferredApn}</li>
 * <li>{@link EDTLibrary#updateApn(APN) updateApn}</li>
 * <li>{@link EDTLibrary#verifyApn(String) verifyApn}</li>
 * </ul></p>
 *
 * <p>Since the above mentioned methods use a variable set of parameters with overlapping parameter types, overloading methods or using variable argument lists isn't feasible.<br/>
 * Instead, instances of this class hold sets of all required parameters for these methods.</p>
 *
 * <p>Use {@link APN.Builder} to create new instances.</p>
 *
 * @version 1.00
 * @since 1.00
 */
public class APN {
    /**
     * Unknown Authentication type - this is one of the possible values of the {@link APN#getAuthType() authType} field of the Access Point Name (APN) configuration
     *
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final int AUTH_TYPE_UNKNOWN = -1;
    /**
     * No Authentication type - this is one of the possible values of the {@link APN#getAuthType() authType} field of the Access Point Name (APN) configuration
     *
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final int AUTH_TYPE_NONE = 0;
    /**
     * Authentication type for PAP. - this is one of the possible values of the {@link APN#getAuthType() authType} field of the Access Point Name (APN) configuration
     *
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final int AUTH_TYPE_PAP = 1;
    /**
     * Authentication type for CHAP. - this is one of the possible values of the {@link APN#getAuthType() authType} field of the Access Point Name (APN) configuration
     *
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final int AUTH_TYPE_CHAP = 2;
    /**
     * Authentication type for either PAP or CHAP. - this is one of the possible values of the {@link APN#getAuthType() authType} field of the Access Point Name (APN) configuration
     *
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final int AUTH_TYPE_PAP_OR_CHAP = 3;
    /**
     * Invalid Authentication type (for internal use only). - this is one of the possible values of the {@link APN#getAuthType() authType} field of the Access Point Name (APN) configuration
     *
     * @since 1.00
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final int INVALID_APN = -1;
    private static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (APN)";
    int id;
    String name;
    String user;
    String password;
    String apn;
    String mcc;
    String mnc;
    String type;
    String server;
    String proxy;
    String port;
    String mmsProxy;
    String mmsPort;
    String mmsc;
    String current;
    int authType;

    APN() {
        logMethodEntranceExit(true);
        this.id = -1;
        this.name = "";
        this.user = "";
        this.password = "";
        this.apn = "";
        this.mcc = "";
        this.mnc = "";
        this.type = "default,supl";
        this.server = "";
        this.proxy = "";
        this.port = "";
        this.mmsProxy = "";
        this.mmsPort = "";
        this.mmsc = "";
        this.current = "";
        this.authType = AUTH_TYPE_NONE;
        logMethodEntranceExit(false);
    }

    private APN(APN.Builder builder) {
        logMethodEntranceExit(true);
        this.id = builder.id;
        this.name = Objects.requireNonNull(builder.name, "APN name must not be null!");
        this.user = builder.user;
        this.password = builder.password;
        this.apn = builder.apn;
        this.mcc = builder.mcc;
        this.mnc = builder.mnc;
        this.type = builder.type;
        this.server = builder.server;
        this.proxy = builder.proxy;
        this.port = builder.port;
        this.mmsProxy = builder.mmsProxy;
        this.mmsPort = builder.mmsPort;
        this.mmsc = builder.mmsc;
        this.current = builder.current;
        this.authType = builder.authType;
        logMethodEntranceExit(false);
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

    /**
     * Since APN uses the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> with a mandatory {@code name} field, there's no public constructor available.<br/>
     * Instead, this method is used to create a new instance of the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder</a> of this class.<br/>
     * In order to finally instantiate a {@link APN APN} object, call the {@link APN.Builder#build() build()} method of the builder when all optional <a href="https://en.wikipedia.org/wiki/Builder_pattern">chaining</a> has been done.
     *
     * @param name {@link String String}: The name to apply to the new Access Point Name (APN) configuration. Providing a name is mandatory.
     * @return The instance object of this class, with a mandatory {@link String String} name set, and further optional fields set from <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder</a> chaining.
     * @since 1.00
     */
    public static APN.Builder setName(String name) {
        return new APN.Builder(name);
    }

    /**
     * Returns the APN name of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The APN name of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getApn() {
        return apn;
    }

    /**
     * Returns the Authentication Type of the Access Point Name (APN) configuration.
     *
     * @return {@code int}: The Authentication Type of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public int getAuthType() {
        return authType;
    }

    /**
     * Returns the APN currently being in use of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The APN currently being in use of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getCurrent() {
        return current;
    }

    /**
     * Returns the "id" field of the Access Point Name (APN) configuration.
     *
     * @return {@code int}: The "id" field of the Access Point Name (APN) configuration or {@link APN#INVALID_APN INVALID_APN (-1)} if no ID has been set.
     * @since 1.00
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the Mobile Country Code of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The Mobile Country Code of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getMcc() {
        return mcc;
    }

    /**
     * Returns the MMS Proxy Port of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The MMS Proxy Port of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getMmsPort() {
        return mmsPort;
    }

    /**
     * Returns the MMS Proxy URL of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The MMS Proxy URL of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getMmsProxy() {
        return mmsProxy;
    }

    /**
     * Returns the MMSC (Multimedia Messaging Service Center) of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The MMSC (Multimedia Messaging Service Center) of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getMmsc() {
        return mmsc;
    }

    /**
     * Returns the Mobile Network Code of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The Mobile Network Code of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getMnc() {
        return mnc;
    }

    /**
     * Returns the name of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The name of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getName() {
        return name;
    }

    /**
     * Returns APN password of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The APN password of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the Server's Port of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The Server's Port of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getPort() {
        return port;
    }

    /**
     * Returns the Proxy URL of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The Proxy URL of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getProxy() {
        return proxy;
    }

    /**
     * Returns the APN Server of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The APN Server of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getServer() {
        return server;
    }

    /**
     * Returns the Type of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The Type of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @apiNote Valid settings are e.g. "default", "supl" and "mms".<br/>
     * Multiple strings are separated by comma "," character.
     * Default setting is "default,supl".
     * @since 1.00
     */
    public String getType() {
        return type;
    }

    /**
     * RReturns the APN username of the Access Point Name (APN) configuration.
     *
     * @return {@link String String}: The APN username of the Access Point Name (APN) configuration.<br/>
     * If this field has not been set, the return value is an empty {@link String String}.
     * @since 1.00
     */
    public String getUser() {
        return user;
    }

    /**
     * Provides a convenient way to set the fields of a {@link APN} object when creating a new
     * instance, following the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>. The following setting is required to build a {@link APN} object:
     *
     * <ul><li>{@code name}</li></ul>
     *
     * <p>The example below shows how you might create a new {@link APN} object instance:
     *
     * <pre><code>
     * // Create a APN object instance with the name "TEST" for use with the german "Vodafone" network
     * APN apn = APN.setName("TEST")
     *       .setApn("web.vodafone.de")
     *       .setAuthType(APN.AUTH_TYPE_NONE)
     *       .build();
     * </code></pre>
     *
     * @since 1.00
     */
    public static final class Builder {

        private int id;
        private String name;
        private String user;
        private String password;
        private String apn;
        private String mcc;
        private String mnc;
        private String type;
        private String server;
        private String proxy;
        private String port;
        private String mmsProxy;
        private String mmsPort;
        private String mmsc;
        private String current;
        private int authType;

        private Builder(String name) {
            logMethodEntranceExit(true);
            this.id = -1;
            this.name = Objects.requireNonNull(name, "APN name must not be null!");
            this.user = "";
            this.password = "";
            this.apn = "";
            this.mcc = "";
            this.mnc = "";
            this.type = "default,supl";
            this.server = "";
            this.proxy = "";
            this.port = "";
            this.mmsProxy = "";
            this.mmsPort = "";
            this.mmsc = "";
            this.current = "";
            this.authType = AUTH_TYPE_NONE;
            logMethodEntranceExit(false);
        }

        /**
         * This method is used to finally instantiate an {@link APN APN} object from this {@link APN.Builder Builder}, after all optional <a href="https://en.wikipedia.org/wiki/Builder_pattern">chaining</a> has been done.
         *
         * @return {@link APN APN}: instance object created from <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>.
         * @since 1.00
         */
        public APN build() {
            logMethodEntranceExit(true);
            logMethodEntranceExit(false);
            return new APN(this);
        }

        /**
         * Sets the APN name of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param apn {@link String String}: The APN name of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an APN name provided.
         * @since 1.00
         */
        public Builder setApn(String apn) {
            logMethodEntranceExit(true);
            this.apn = apn;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the Authentication Type of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param authType {@code int}: The Authentication Type of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an Authentication Type provided.
         * @since 1.00
         */
        public Builder setAuthType(int authType) {
            logMethodEntranceExit(true);
            this.authType = authType;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the APN currently being in use of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param current {@link String String}: The APN currently being in use of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an APN currently being in use provided.
         * @since 1.00
         */
        public Builder setCurrent(String current) {
            logMethodEntranceExit(true);
            this.current = current;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the ID of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param id {@code int}: The ID of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an ID provided.
         * @since 1.00
         */
        public Builder setId(int id) {
            logMethodEntranceExit(true);
            this.id = id;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the Mobile Country Code of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param mcc {@link String String}: The Mobile Country Code of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and a Mobile Country Code provided.
         * @since 1.00
         */
        public Builder setMcc(String mcc) {
            logMethodEntranceExit(true);
            this.mcc = mcc;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the MMS Proxy Port of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param mmsPort {@link String String}: The MMS Proxy Port of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an MMS Proxy Port provided.
         * @since 1.00
         */
        public Builder setMmsPort(String mmsPort) {
            logMethodEntranceExit(true);
            this.mmsPort = mmsPort;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the MMS Proxy URL of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param mmsProxy {@link String String}: The MMS Proxy URL of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an MMS Proxy URL provided.
         * @since 1.00
         */
        public Builder setMmsProxy(String mmsProxy) {
            logMethodEntranceExit(true);
            this.mmsProxy = mmsProxy;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the MMSC (Multimedia Messaging Service Center) of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param mmsc {@link String String}: The MMSC (Multimedia Messaging Service Center) of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an MMSC (Multimedia Messaging Service Center) provided.
         * @since 1.00
         */
        public Builder setMmsc(String mmsc) {
            logMethodEntranceExit(true);
            this.mmsc = mmsc;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the Mobile Network Code of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param mnc {@link String String}: The Mobile Network Code of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and a Mobile Network Code provided.
         * @since 1.00
         */
        public Builder setMnc(String mnc) {
            logMethodEntranceExit(true);
            this.mnc = mnc;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the APN password of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param password {@link String String}: The APN password of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an APN password provided.
         * @since 1.00
         */
        public Builder setPassword(String password) {
            logMethodEntranceExit(true);
            this.password = password;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the Server's Port of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param port {@link String String}: The Server's Port of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and a Server's Port provided.
         * @since 1.00
         */
        public Builder setPort(String port) {
            logMethodEntranceExit(true);
            this.port = port;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the Proxy URL of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param proxy {@link String String}: The Proxy URL of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and a Proxy URL provided.
         * @since 1.00
         */
        public Builder setProxy(String proxy) {
            logMethodEntranceExit(true);
            this.proxy = proxy;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the APN Server of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param server {@link String String}: The APN Server of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and an APN Server provided.
         * @since 1.00
         */
        public Builder setServer(String server) {
            logMethodEntranceExit(true);
            this.server = server;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the Type of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param type {@link String String}: The Type of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and a Type provided.
         * @since 1.00
         */
        public Builder setType(String type) {
            logMethodEntranceExit(true);
            this.type = type;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Sets the Username of the Access Point Name (APN) configuration, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         *
         * @param user {@link String String}: The Username of the Access Point Name (APN) configuration.
         * @return The instance object of this {@link APN.Builder Builder}, with a mandatory {@link String String} name set, and a Username provided.
         * @since 1.00
         */
        public Builder setUser(String user) {
            logMethodEntranceExit(true);
            this.user = user;
            logMethodEntranceExit(false);
            return this;
        }
    }
}
