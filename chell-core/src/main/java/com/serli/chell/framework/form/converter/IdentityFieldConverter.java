
package com.serli.chell.framework.form.converter;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class IdentityFieldConverter implements FieldConverter<Object, Object> {

    public static final IdentityFieldConverter INSTANCE = new IdentityFieldConverter();

    private IdentityFieldConverter() {
    }

    public Object fromObject(Object objectValue) {
        return objectValue;
    }

    public Object toObject(Object fieldValue) {
        return fieldValue;
    }
}
