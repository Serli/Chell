
package com.serli.chell.framework.form.converter;

import com.serli.chell.framework.util.ClassUtils;
import java.lang.reflect.Array;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class TabFieldConverter<ObjectType> implements FieldConverter<ObjectType[], String[]> {
    
    private FieldConverter<ObjectType, String> fieldConverter;
    private Class<ObjectType> objectClass;

    public TabFieldConverter(FieldConverter<ObjectType, String> fieldConverter) {
        this.fieldConverter = fieldConverter;
        this.objectClass = (Class<ObjectType>) ClassUtils.getGenericArgumentClass(fieldConverter.getClass(), FieldConverter.class, 0);
    }

    public String[] fromObject(ObjectType[] objectValues) {
        String[] result = new String[objectValues.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = fieldConverter.fromObject(objectValues[i]);
        }
        return result;
    }

    public ObjectType[] toObject(String[] fieldValues) {
        ObjectType[] result = (ObjectType[]) Array.newInstance(objectClass, fieldValues.length);
        for (int i = 0; i < result.length; i++) {
            result[i] = fieldConverter.toObject(fieldValues[i]);
        }
        return result;
    }
}
