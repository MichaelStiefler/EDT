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
 * The {@link ReadWriteFileParams ReadWriteFileParams} class is used to hand over parameters to the {@link EDTLib#readFile(ReadWriteFileParams) readFile} and {@link EDTLib#writeFile(ReadWriteFileParams) writeFile} functions of the CASIO Enterprise Developer Tools
 */
public class ReadWriteFileParams {

    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (ReadWriteFileParams)";

    /**
     * Returns the {@link java.nio.file.Path path} of this object
     * @return {@link java.nio.file.Path String}: The {@link java.nio.file.Path path} of this object
     */
    @SuppressWarnings("unused")
    /*public*/ Path getPath() {
        return path;
    }

    /**
     * Returns the {@link java.nio.file.Path path} of this object converted to a {@link java.lang.String String}
     * @return {@link java.lang.String String}: The {@link java.nio.file.Path path} of this object converted to a {@link java.lang.String String}
     */
    @SuppressWarnings("unused")
    /*public*/ String getPathString() {
        return path.toString();
    }

    /**
     * Returns the buffer holding the data to be read or written
     * @return {@link java.lang.reflect.Array Array} of {@link java.lang.Byte Bytes}: The buffer holding the data to be read or written
     */
    @SuppressWarnings("unused")
    public byte[] getData() {
        return data;
    }

    /**
     * Applies a new buffer holding the data to be read or written
     * @param newData {@link java.lang.reflect.Array Array} of {@link java.lang.Byte Bytes}: The buffer holding the data to be read or written
     */
    @SuppressWarnings("unused")
    public void setData(byte[] newData) {
        this.data = newData;
    }

    /**
     * Creates a new buffer holding the data to be read or written
     * @param newDataLength {@link java.lang.Integer int}: The size of the new buffer holding the data to be read or written
     */
    @SuppressWarnings("unused")
    public void newData(int newDataLength) {
        this.data = new byte[newDataLength];
    }

    /**
     * Returns the Offset inside the file where reading/writing starts
     * @return {@link java.lang.Integer int}: Offset inside the file where reading/writing starts
     */
    @SuppressWarnings("unused")
    /*public*/ int getFileOffset() {
        return fileOffset;
    }

    /**
     * Returns the Offset inside the data buffer where reading/writing starts
     * @return {@link java.lang.Integer int}: Offset inside the data buffer where reading/writing starts
     */
    @SuppressWarnings("unused")
    /*public*/ int getDataOffset() {
        return dataOffset;
    }

    /**
     * Returns the Length of data to be read or written
     * @return {@link java.lang.Integer int}: Length of data to be read or written
     */
    @SuppressWarnings("unused")
    /*public*/ int getLength() {
        return length;
    }

    /**
     * Returns the {@link java.util.List List} of {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} to apply
     * @return {@link java.util.List List} of {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions}: {@link java.util.List List} of {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} to apply
     */
    @SuppressWarnings("unused")
    /*public*/ List<OpenOption> getOptions() {
        return options;
    }

    /**
     * Returns the {@link java.util.List List} containing Names of the {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} to apply
     * @return {@link java.util.List List} of {@link java.lang.String Strings}: {@link java.util.List List} containing Names and/of the {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} to apply
     */
    @SuppressWarnings("unused")
    /*public*/ List<String> getOptionStrings() {
        List<String> openOptionStrings;
        try {
            openOptionStrings = options == null?null: options.stream().map(Object::toString).collect(Collectors.toList());
            return openOptionStrings;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return null;
    }

    Path path;
    byte[] data;
    int fileOffset;
    int dataOffset;
    int length;
    List<OpenOption> options;

    ReadWriteFileParams() {
        path = null;
        data = null;
        fileOffset = -1;
        dataOffset = -1;
        length = -1;
        options = null;
    }

    @SuppressWarnings("unused")
    private ReadWriteFileParams (Builder builder) {
        path = Objects.requireNonNull(builder.path, "File Path must not be null!");
        data = builder.data;
        fileOffset = builder.fileOffset;
        dataOffset = builder.dataOffset;
        length = builder.length;
        options = builder.options;
    }

    /**
     * Since ReadWriteFileParams uses the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>, there's no public constructor available.<br/>
     * Instead, this is used to create a new instance of the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder</a> of this class.<br/>
     * In order to finally instanciate a {@link ReadWriteFileParams ReadWriteFileParams} object, call the {@link Builder#build() build()} method of the builder when all optional <a href="https://en.wikipedia.org/wiki/Builder_pattern">chaining</a> has been done.
     * @param path {@link java.nio.file.Path Path}: The file to be read from or written to. Providing a file path is mandatory.
     * @return The instance object of this class, with a mandatory file {@link java.nio.file.Path path} set, and further optional fields set from <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder</a> chaining.
     */
    @SuppressWarnings("unused")
    public static Builder path(Path path) {
        return new Builder(path);
    }

    /**
     * The {@link ReadWriteFileParams.Builder ReadWriteFileParams.Builder} class is used to create new Instances of the {@link ReadWriteFileParams ReadWriteFileParams} class following the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>
     */
    public static final class Builder {

        private final Path path;
        private byte[] data;
        private int fileOffset;
        private int dataOffset;
        private int length;
        private List<OpenOption> options;

        private Builder(Path val) {
            data = null;
            fileOffset = dataOffset = length = -1;
            options = null;
            path = val;
        }

        /**
         * Adds a buffer holding the data to be read/written, using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.<br/>
         * For write methods, providing data is mandatory.<br/>
         * For read methods, if a data buffer is provided, that buffer will get filled with data accordingly. Otherwise, a new buffer will be created at runtime.
         * @param data {@link java.lang.reflect.Array Array} of {@link java.lang.Byte Bytes}: Buffer holding the data to be read/written.
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a data Buffer provided.
         */
        @SuppressWarnings("unused")
        public Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        /**
         * Adds a File Offset value using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         * @param fileOffset {@link java.lang.Integer int}: Offset inside the file where reading/writing starts
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a File Offset field added.
         */
        @SuppressWarnings("unused")
        public Builder fileOffset(int fileOffset) {
            this.fileOffset = fileOffset;
            return this;
        }

        /**
         * Adds a Data Offset value using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         * @param dataOffset {@link java.lang.Integer int}: Offset inside the data buffer where reading/writing starts
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a Data Offset field added.
         */
        @SuppressWarnings("unused")
        public Builder dataOffset(int dataOffset) {
            this.dataOffset = dataOffset;
            return this;
        }

        /**
         * Adds a Data Length value using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         * @param length {@link java.lang.Integer int}: Length of data to be read or written
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and a Data Length field added.
         */
        @SuppressWarnings("unused")
        public Builder length(int length) {
            this.length = length;
            return this;
        }

        /**
         * Adds optional {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} using <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a> method chaining.
         * @param openOptions {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions}: Variable number of Options to be applied
         * @return The instance object of this {@link Builder Builder}, with a mandatory file {@link java.nio.file.Path path} set, and further {@link java.nio.file.StandardOpenOption StandardOpenOptions} and/or {@link java.nio.file.LinkOption LinkOptions} applied.
         */
        @SuppressWarnings("unused")
        public Builder options(OpenOption... openOptions) {
            if (options == null) this.options = new ArrayList<>();
            if (options.addAll(Arrays.asList(openOptions))) this.options = this.options.stream().distinct().collect(Collectors.toList());
            return this;
        }

        /**
         * This method is used to finally instanciate a {@link ReadWriteFileParams ReadWriteFileParams} object from this {@link Builder Builder}, after all optional <a href="https://en.wikipedia.org/wiki/Builder_pattern">chaining</a> has been done.
         * @return {@link ReadWriteFileParams ReadWriteFileParams}: instance object created from <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>.
         */
        @SuppressWarnings("unused")
        public ReadWriteFileParams build() {
            return new ReadWriteFileParams(this);
        }
    }
}
