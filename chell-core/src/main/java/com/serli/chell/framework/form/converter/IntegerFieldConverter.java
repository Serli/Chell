
package com.serli.chell.framework.form.converter;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class IntegerFieldConverter implements FieldConverter<Integer, String> {

    public String fromObject(Integer objectValue) {
        return objectValue.toString();
    }

    public Integer toObject(String fieldValue) {
        return Integer.valueOf(fieldValue);
    }
}
