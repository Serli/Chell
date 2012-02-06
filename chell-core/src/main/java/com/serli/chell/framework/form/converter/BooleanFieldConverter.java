
package com.serli.chell.framework.form.converter;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class BooleanFieldConverter implements FieldConverter<Boolean, String> {

    public String fromObject(Boolean objectValue) {
        return objectValue.toString();
    }

    public Boolean toObject(String fieldValue) {
        return Boolean.valueOf(fieldValue);
    }
}
