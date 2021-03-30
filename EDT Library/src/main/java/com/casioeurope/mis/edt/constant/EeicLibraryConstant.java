package com.casioeurope.mis.edt.constant;

import com.casioeurope.mis.edt.EeicLibrary;

/**
 * The <b>CASIO Enterprise Developer Tools</b> External Expansion Interface Control (EEIC) Library Constants<br/>
 * Constants to be used in the {@link EeicLibrary} class.<br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class EeicLibraryConstant {
    /**
     * Constants for controlling GPIO using the {@link EeicLibrary.GpioDevice} class.
     */
    public static class GPIO_DEVICE {
        /**
         * GPIO number assigned to GPIO1.
         */
        public static final int GPIO1 = 1;
        /**
         * GPIO number assigned to GPIO2.
         */
        public static final int GPIO2 = 2;
        /**
         * GPIO number assigned to GPIO3.
         */
        public static final int GPIO3 = 3;
        /**
         * GPIO number assigned to GPIO4.
         */
        public static final int GPIO4 = 4;
        /**
         * GPIO number assigned to GPIO5.
         */
        public static final int GPIO5 = 5;
        /**
         * Specify high impedance for input pin status.
         */
        public static final int INPUT_FLOATING = 0;
        /**
         * Specify pull up for input pin status.
         */
        public static final int INPUT_PULL_UP = 1;
        /**
         * Specify pull down for input pin status.
         */
        public static final int INPUT_PULL_DOWN = 2;
        /**
         * Specify output to low.
         */
        public static final int OUTPUT_LOW = 0;
        /**
         * Specify output to high.
         */
        public static final int OUTPUT_HIGH = 1;
        /**
         * Do not use edge interrupt.
         */
        public static final int EDGE_NOTHING = 0;
        /**
         * Specify falling edge interrupt.
         */
        public static final int EDGE_FALLING = 1;
        /**
         * Specify rising edge interrupt.
         */
        public static final int EDGE_RISING = 2;
        /**
         * Specify both falling and rising edge interrupts.
         */
        public static final int EDGE_BOTH = 3;
    }

    /**
     * Constants for controlling Serial Interface using the {@link EeicLibrary.SerialDevice} class.
     */
    public static class SERIAL_DEVICE {
        /**
         * Baud rate: 9600 bps
         */
        public static final int BAUDRATE_9600 = 9600;
        /**
         * Baud rate: 19200 bps
         */
        public static final int BAUDRATE_19200 = 19200;
        /**
         * Baud rate: 38400 bps
         */
        public static final int BAUDRATE_38400 = 38400;
        /**
         * Baud rate: 57600 bps
         */
        public static final int BAUDRATE_57600 = 57600;
        /**
         * Baud rate: 115200 bps
         */
        public static final int BAUDRATE_115200 = 115200;
        /**
         * Data length: 7 Bit
         */
        public static final int BIT_LENGTH_7 = 7;
        /**
         * Data length: 8 Bit
         */
        public static final int BIT_LENGTH_8 = 8;
        /**
         * Parity bit: None
         */
        public static final int PARITY_NONE = 0;
        /**
         * Parity bit: Even
         */
        public static final int PARITY_EVEN = 1;
        /**
         * Parity bit: Odd
         */
        public static final int PARITY_ODD = 2;
        /**
         * Stop bit: 1 Bit
         */
        public static final int STOP_BIT_1 = 0;
        /**
         * Stop bit: 2 Bit
         */
        public static final int STOP_BIT_2 = 1;
    }

    /**
     * Constants for controlling Serial Interface using the {@link EeicLibrary.SpiDevice} class.
     */
    public static class SPI_DEVICE {
        /**
         * Clock Polarity (CPOL) set to 0 and Clock Phase (CPHA) set to 0.
         */
        public static final int MODE0 = 0;
        /**
         * Clock Polarity (CPOL) set to 0 and Clock Phase (CPHA) set to 1.
         */
        public static final int MODE1 = 1;
        /**
         * Clock Polarity (CPOL) set to 1 and Clock Phase (CPHA) set to 0.
         */
        public static final int MODE2 = 2;
        /**
         * Clock Polarity (CPOL) set to 1 and Clock Phase (CPHA) set to 1.
         */
        public static final int MODE3 = 3;
        /**
         * Set the chip select (CS) to active low.
         */
        public static final int CS_LOW = 0;
        /**
         * Set the chip select (CS) to active high.
         */
        public static final int CS_HIGH = 1;
    }


    /**
     * Return Codes to be returned by the methods of the {@link EeicLibrary} class.
     */
    public static class RESULT {
        /**
         * The method has been called successfully
         */
        public static final int SUCCESS = 0;
        /**
         * The Method call is unsupported
         */
        public static final int ERROR_NOT_SUPPORT = -1;
        /**
         * An illegal argument has been passed to the method
         */
        public static final int ERROR_ILLEGAL_ARGUMENT = -2;
        /**
         * An internal error occurred while calling the method
         */
        public static final int ERROR_INTERNAL = -3;
        /**
         * The target device has not been initialized
         */
        public static final int ERROR_NOT_INITIALIZE = -4;
        /**
         * A read timeout occured while reading serial data
         */
        public static final int ERROR_TIMEOUT = -5;
    }

}
