package com.serli.chell.framework.util;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.constant.Constant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class FileUtils {

    private static final int BUFFER_SIZE = 1024;

    private FileUtils() {
    }

    public static String readFileAsString(File file, String charset) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuilder data = readFromBufferedReader(reader);
            return new String(data.toString().getBytes(), charset);
        } catch (IOException ex) {
            throw new ChellException("File " + file + " not found.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private static StringBuilder readFromBufferedReader(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[BUFFER_SIZE];
        int numRead = 0;
        while ((numRead = reader.read(buffer)) != -1) {
            builder.append(String.valueOf(buffer, 0, numRead));
            buffer = new char[BUFFER_SIZE];
        }
        return builder;
    }

    public static String withoutExtension(File file) {
        return withoutExtension(file.getName());
    }

    public static String withoutExtension(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        }
        return filename;
    }

    public static String extension(File file) {
        return extension(file.getName());
    }

    public static String extension(String filename) {
        int index = filename.lastIndexOf(".") + 1;
        if (index != 0 && index != filename.length()) {
            return filename.substring(index);
        }
        return Constant.EMPTY;
    }
}
