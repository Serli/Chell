
package com.serli.chell.framework.form.converter;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class LongFieldConverter implements FieldConverter<Long, String> {

    public String fromObject(Long objectValue) {
        return objectValue.toString();
    }

    public Long toObject(String fieldValue) {
        return Long.valueOf(fieldValue);
    }
}
