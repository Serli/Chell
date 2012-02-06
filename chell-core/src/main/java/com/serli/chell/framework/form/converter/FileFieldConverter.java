
package com.serli.chell.framework.form.converter;

import java.io.File;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FileFieldConverter implements FieldConverter<File, String> {

    public String fromObject(File file) {
        return file.getAbsolutePath();
    }

    public File toObject(String path) {
        return new File(path);
    }
}
