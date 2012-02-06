/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.serli.chell.framework.util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ConvertUtils {
    
    protected ConvertUtils() {
    }

    public static <T> List<T> toList(T... values) {
        List<T> result = new ArrayList<T>(values.length);
        for (T value : values) {
            result.add(value);
        }
        return result;
    }

    public static <T> Set<T> toSet(T... values) {
        Set<T> result = new HashSet<T>(values.length);
        for (T value : values) {
            result.add(value);
        }
        return result;
    }

    public static <T> Set<T> toOrderedSet(T... values) {
        Set<T> result = new LinkedHashSet<T>(values.length);
        for (T value : values) {
            result.add(value);
        }
        return result;
    }

    public static int toInt(String value) {
        return toInt(value, 0);
    }

    public static int toInt(String value, int defaultValue) {
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (Exception ex) {
            }
        }
        return defaultValue;
    }

    public static String fromInt(int value) {
        return String.valueOf(value);
    }
    
    public static Integer toInteger(String value) {
        return toInteger(value, null);
    }

    public static Integer toInteger(String value, Integer defaultValue) {
        if (value != null) {
            try {
                return Integer.valueOf(value);
            } catch (Exception ex) {
            }
        }
        return defaultValue;
    }

    public static String fromInteger(Integer value) {
        return fromInteger(value, null);
    }

    public static String fromInteger(Integer value, String defaultValue) {
        if (value != null) {
            return value.toString();
        }
        return defaultValue;
    }

    public static int[] toInts(String[] values) {
        return toInts(values, 0);
    }

    public static int[] toInts(String[] values, int defaultValue) {
        int[] result = new int[values == null ? 0 : values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = toInt(values[i], defaultValue);
        }
        return result;
    }

    public static String[] fromInts(int[] values) {
        String[] result = new String[values == null ? 0 : values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = fromInt(values[i]);
        }
        return result;
    }

    public static Integer[] toIntegers(String[] values) {
        return toIntegers(values, null);
    }

    public static Integer[] toIntegers(String[] values, Integer defaultValue) {
        Integer[] result = new Integer[values == null ? 0 : values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = toInteger(values[i], defaultValue);
        }
        return result;
    }

    public static String[] fromIntegers(Integer[] values) {
        return fromIntegers(values, null);
    }

    public static String[] fromIntegers(Integer[] values, String defaultValue) {
        String[] result = new String[values == null ? 0 : values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = fromInteger(values[i], defaultValue);
        }
        return result;
    }

    public static boolean toBool(String value) {
        return toBool(value, false);
    }

    public static boolean toBool(String value, boolean defaultValue) {
        if (value == null || value.length() == 0) {
            return defaultValue;
        } else if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        return defaultValue;
    }

    public static String fromBool(boolean value) {
        return Boolean.toString(value);
    }

    public static Boolean toBoolean(String value) {
        return toBoolean(value, Boolean.FALSE);
    }

    public static Boolean toBoolean(String value, Boolean defaultValue) {
        if (value == null || value.length() == 0) {
            return defaultValue;
        } else if ("true".equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        } else if ("false".equalsIgnoreCase(value)) {
            return Boolean.FALSE;
        }
        return defaultValue;
    }

    public static String fromBoolean(Boolean value) {
        return fromBoolean(value, null);
    }

    public static String fromBoolean(Boolean value, String defaultValue) {
        if (value != null) {
            return value.toString();
        }
        return defaultValue;
    }

    public static boolean fromCheckbox(String value) {
        return (value != null && value.length() > 0);
    }

    public static String toCheckbox(boolean value) {
        return fromBool(value);
    }

    public static String toCheckbox(Boolean value) {
        return fromBoolean(value, null);
    }

    public static Date toDate(DateFormat format, String value) {
        return toDate(format, value, null);
    }

    public static Date toDate(DateFormat format, String value, Date defaultValue) {
        if (value != null) {
            try {
                return format.parse(value);
            } catch (Exception ex) {
            }
        }
        return defaultValue;
    }

    public static String fromDate(DateFormat format, Date value) {
        return fromDate(format, value, null);
    }

    public static String fromDate(DateFormat format, Date value, String defaultValue) {
        if (value != null) {
            return format.format(value);
        }
        return defaultValue;
    }

    public static Date[] toDates(DateFormat format, String[] values) {
        return toDates(format, values, null);
    }

    public static Date[] toDates(DateFormat format, String[] values, Date defaultValue) {
        Date[] result = new Date[values == null ? 0 : values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = toDate(format, values[i], defaultValue);
        }
        return result;
    }

    public static String[] fromDates(DateFormat format, Date[] values) {
        return fromDates(format, values, null);
    }

    public static String[] fromDates(DateFormat format, Date[] values, String defaultValue) {
        String[] result = new String[values == null ? 0 : values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = fromDate(format, values[i], defaultValue);
        }
        return result;
    }
}
