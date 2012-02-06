
package com.serli.chell.framework.util;

import com.serli.chell.framework.message.MessageBundle;
import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class Size implements Serializable {

    public static final Size ZERO = new Size(0, Unit.B);
    private static final String UNIT_KEY_PREFIX = "chell.unit.";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.##");

    public enum Unit {
        B("b", 1, 1000),
        KB("kb", 1024, 1000000),
        MB("mb", 1048576, 1000000000),
        GB("gb", 1073741824, 1000000000000L),
        TB("tb", 1099511627776L, 1000000000000000L);

        private String suffixKey;
        private long factor;
        private long displayValue;

        public static final int UNDEFINED = -1;

        private Unit(String suffixKey, long factor, long displayValue) {
            this.suffixKey = suffixKey;
            this.factor = factor;
            this.displayValue = displayValue;
        }

        @Override
        public String toString() {
            return MessageBundle.getMessage(UNIT_KEY_PREFIX + suffixKey);
        }

        public String getDisplay() {
            return suffixKey;
        }

        public long toBytes(double size) {
            long result = ((long) (size * (double) factor));
            if (result >= 0) {
                return result;
            }
            return UNDEFINED;
        }

        public long toBytes(String size) {
            if (size != null && size.length() > 0) {
                try {
                    return toBytes(Double.valueOf(size));
                } catch (NumberFormatException ex) {
                }
            }
            return UNDEFINED;
        }
    }

    private double value;
    private Unit unit;

    public Size(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static Size fromBytes(long bytes) {
        if (bytes > 0) {
            Unit[] units = Unit.values();
            double result = (double) bytes;
            long min = 0L, max;
            for (int i = 0, end = units.length - 1; i < end; i++) {
                max = units[i].displayValue;
                if (result >= min && result < max) {
                    return new Size(result / units[i].factor, units[i]);
                }
                min = max;
            }
        }
        return Size.ZERO;
    }

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    public long toBytes() {
        return unit.toBytes(value);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(DECIMAL_FORMAT.format(value)).append(' ').append(unit);
        return b.toString();
    }
}
