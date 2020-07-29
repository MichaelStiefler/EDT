package com.casioeurope.mis.edt;

import android.util.Log;

import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A set of parameters used to hand over to the {@link EDTLib#readFile(ReadWriteFileParams) readFile} and {@link EDTLib#writeFile(ReadWriteFileParams) writeFile} methods of the CASIO Enterprise Developer Tools
 *
 * <p>Since {@link EDTLib#readFile(ReadWriteFileParams) readFile} and {@link EDTLib#writeFile(ReadWriteFileParams) writeFile} methods use a variable set of parameters with overlapping parameter types, overloading methods or using variable argument lists isn't feasible.<br/>
 * Instead, instances of this class hold sets of all required parameters for these methods.</p>
 *
 * <p>Use {@link ReadWriteFileParams.Builder} to create new instances.</p>
 *
 * @version 1.00
 * @since 1.00
 */
public class ReadWriteFileParams {

    private static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (ReadWriteFileParams)";
    Path path;
    byte[] data;
    int fileOffset;
    int dataOffset;
    int length;
    List<OpenOption> options;

    ReadWriteFileParams() {
        logMethodEntranceExit(true);
        path = null;
        data = null;
        fileOffset = -1;
        dataOffset = -1;
        length = -1;
        options = null;
        logMethodEntranceExit(false);
    }

    private ReadWriteFileParams(Builder builder) {
        logMethodEntranceExit(true);
        path = Objects.requireNonNull(builder.path, "File Path must not be null!");
        data = builder.data;
        fileOffset = builder.fileOffset;
        dataOffset = builder.dataOffset;
        length = builder.length;
        options = builder.options;
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
     * Since ReadWriteFileParams uses the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> with a mandatory {@code path} field, there's no public constructor available.<br/>
     * Instead, this method is used to create a new instance of the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder</a> of this class.<br/>
     * In order to finally instantiate a {@link ReadWriteFileParams ReadWriteFileParams} object, call the {@link Builder#build() build()} method of the builder when all optional <a href="https://en.wikipedia.org/wiki/Builder_pattern">chaining</a> has been done.
     *
     * @param path {@link java.nio.file.Path Path}: The file to be read from or written to. Providing a file path is mandatory.
     * @return The instance object of this class, with a mandatory file {@link java.nio.file.Path path} set, and further optional fields set from <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder</a> chaining.
     * @since 1.00
     */
    @SuppressWarnings("unused")
    public static Builder setPath(Path path) {
        return new Builder(path);
    }

    /**
     * Returns the buffer holding the data from/for {@link EDTLib#readFile(ReadWriteFileParams) readFile} and {@link EDTLib#writeFile(ReadWriteFileParams) writeFile} methods.
     *
     * <p>When using the {@link EDTLib#writeFile(ReadWriteFileParams) writeFile} method, providing a data buffer (and data accordingly, logically) is mandatory.<br/>
     * In contrast, when using the {@link EDTLib#readFile(ReadWriteFileParams) readFile} method, providing a data buffer is optional.<br/>
     * If {@link EDTLib#readFile(ReadWriteFileParams) readFile} method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to hold the data being read.<br/>
     * If {@link EDTLib#readFile(ReadWriteFileParams) readFile} method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will dynamically allocate a buffer holding the data being read.
     * </p>
     *
     * @return {@link java.lang.reflect.Array Array} of {@link java.lang.Byte Bytes}: The buffer holding the data to be read or written, or {@code null} if no buffer is provided.
     * @since 1.00
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Returns the Offset inside the {@link #getData() Data Buffer} at which read/write Operations start
     * @return {@link java.lang.Integer int}: The Offset inside the {@link #getData() Data Buffer} at which read/write Operations start
     */
    public int getDataOffset() {
        return dataOffset;
    }

    /**
     * Returns the File Offset at which read/write Operations start
     * @return {@link java.lang.Integer int}: The File Offset at which read/write Operations start
     */
    public int getFileOffset() {
        return fileOffset;
    }

    /**
     * Returns the Length of Data to be read or written
     * @return {@link java.lang.Integer int}: The Length of Data to be read or written
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the {@link java.nio.file.OpenOption OpenOptions} used for the read/write file operations.
     * @return {@link List List} of {@link java.nio.file.OpenOption OpenOptions}: The {@link java.nio.file.OpenOption OpenOptions} used for the read/write file operations.
     */
    public List<OpenOption> getOptions() {
        return options;
    }

    /**
     * Returns the {@link java.nio.file.Path Path} used for the read/write file operations.
     * @return {@link java.nio.file.Path Path}: The {@link java.nio.file.Path Path} used for the read/write file operations.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Creates a new buffer holding the data to be read or written
     *
     * @param newDataLength {@link java.lang.Integer int}: The size of the new buffer holding the data to be read or written
     * @since 1.00
     */
    public void newData(int newDataLength) {
        logMethodEntranceExit(true);
        this.data = new byte[newDataLength];
        logMethodEntranceExit(false);
    }

    /**
     * Provides a convenient way to set the fields of a {@link ReadWriteFileParams} object when creating a new
     * instance, following the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>. The following setting is required to build a {@link ReadWriteFileParams} object:
     *
     * <ul><li>{@code path}</li></ul>
     *
     * <p>The example below shows how you might create a new {@link ReadWriteFileParams} object instance:
     *
     * <pre><code>
     * // Create a ReadWriteFileParams object instance with a data buffer of 4K,
     * // for read operation starting after 1 byte, accessing the buffer from index 2 onwards, reading a hundred bytes.
     * byte[] testData = new byte[4096];
     * ReadWriteFileParams readWriteFileParams = ReadWriteFileParams.setPath(Paths.get("/sdcard/Download/devinfo.html"))
     *       .setData(testData)
     *       .setFileOffset(1)
     *       .setDataOffset(2)
     *       .setLength(100)
     *       .setOptions(StandardOpenOption.READ)
     *       .build();
     * </code></pre>
     *
     * @since 1.00
     */
    public static final class Builder {

        private final Path path;
        private byte[] data;
        private int fileOffset;
        private int dataOffset;
        private int length;
        private List<OpenOption> options;

        private Builder(Path val) {
            logMethodEntranceExit(true);
            data = null;
            fileOffset = dataOffset = length = -1;
            options = null;
            path = Objects.requireNonNull(val, "File Path must not be null!");
            logMethodEntranceExit(false);
        }

        /**
         * This method is used to finally instantiate a {@link ReadWriteFileParams ReadWriteFileParams} object from this {@link Builder Builder}, after all optional <a href="https://en.wikipedia.org/wiki/Builder_pattern">chaining</a> has been done.
         *
         * @return {@link ReadWriteFileParams ReadWriteFileParams}: instance object created from <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>.
         * @since 1.00
         */
        @SuppressWarnings("unused")
        public ReadWriteFileParams build() {
            logMethodEntranceExit(true);
            logMethodEntranceExit(false);
            return new ReadWriteFileParams(this);
        }

        /**
         * Adds a buffer holding the data to be read/written, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         *
         * <p>When using the {@link EDTLib#writeFile(ReadWriteFileParams) writeFile} method, providing a data buffer (and data accordingly, logically) is mandatory.<br/>
         * In contrast, when using the {@link EDTLib#readFile(ReadWriteFileParams) readFile} method, providing a data buffer is optional.<br/>
         * If {@link EDTLib#readFile(ReadWriteFileParams) readFile} method is called with a data buffer provided, the method call will fail if the data buffer is insufficient to hold the data being read.<br/>
         * If {@link EDTLib#readFile(ReadWriteFileParams) readFile} method is called <i>without providing a data buffer</i> i.e. when getData() equals {@code null}, the method call will dynamically allocate a buffer holding the data being read.
         * </p>
         *
         * @param data {@link java.lang.reflect.Array Array} of {@link java.lang.Byte Bytes}: Buffer holding the data to be read/written.
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a data Buffer provided.
         * @since 1.00
         */
        @SuppressWarnings("unused")
        public Builder setData(byte[] data) {
            logMethodEntranceExit(true);
            this.data = data;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Adds a Data Offset value using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         *
         * @param dataOffset {@link java.lang.Integer int}: Offset inside the data buffer where reading/writing starts
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a Data Offset field added.
         * @since 1.00
         */
        @SuppressWarnings("unused")
        public Builder setDataOffset(int dataOffset) {
            logMethodEntranceExit(true);
            this.dataOffset = dataOffset;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Adds a File Offset value using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         *
         * @param fileOffset {@link java.lang.Integer int}: Offset inside the file where reading/writing starts
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a File Offset field added.
         * @since 1.00
         */
        @SuppressWarnings("unused")
        public Builder setFileOffset(int fileOffset) {
            logMethodEntranceExit(true);
            this.fileOffset = fileOffset;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Adds a Data Length value using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         *
         * @param length {@link java.lang.Integer int}: Length of data to be read or written
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a Data Length field added.
         * @since 1.00
         */
        @SuppressWarnings("unused")
        public Builder setLength(int length) {
            logMethodEntranceExit(true);
            this.length = length;
            logMethodEntranceExit(false);
            return this;
        }

        /**
         * Adds optional {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         *
         * @param openOptions {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions}: Variable number of Options to be applied
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and further {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} applied.
         * @since 1.00
         */
        @SuppressWarnings("unused")
        public Builder setOptions(OpenOption... openOptions) {
            logMethodEntranceExit(true);
            if (options == null) this.options = new ArrayList<>();
            if (options.addAll(Arrays.asList(openOptions)))
                this.options = this.options.stream().distinct().collect(Collectors.toList());
            logMethodEntranceExit(false);
            return this;
        }
    }
}
