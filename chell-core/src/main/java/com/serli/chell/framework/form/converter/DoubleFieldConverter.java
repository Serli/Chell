
package com.serli.chell.framework.form.converter;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DoubleFieldConverter implements FieldConverter<Double, String> {

    public String fromObject(Double objectValue) {
        return objectValue.toString();
    }

    public Double toObject(String fieldValue) {
        return Double.valueOf(fieldValue);
    }
}
