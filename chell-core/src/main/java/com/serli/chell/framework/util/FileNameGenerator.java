
package com.serli.chell.framework.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class FileNameGenerator {

    private static volatile int counter = 0;
    private static final int MAX_COUNTER = 100000;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmssSSS");

    public static String get(String initialFileName) {
        StringBuilder b = new StringBuilder();
        b.append(DATE_FORMAT.format(new Date()));
        b.append(counter).append('-').append(initialFileName);
        counter = (counter + 1) % MAX_COUNTER;
        return b.toString();
    }

    public static String extract(String generatedFileName) {
        int index = generatedFileName.indexOf("-");
        if (index != -1) {
            return generatedFileName.substring(index + 1);
        } else {
            return generatedFileName;
        }
    }
}
