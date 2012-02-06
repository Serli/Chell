package com.serli.chell.framework.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;



/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class MetaDataMethod<T> implements MetaDataInfo<T> {

    private T defaultValue;
    private Method method;

    public MetaDataMethod(Method method) {
        this.method = method;
        this.defaultValue = (T) method.getDefaultValue();
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue(Annotation annotation) {
        try {
            return (T) method.invoke(annotation);
        } catch (Exception ex) {
            return null;
        }
    }
}
