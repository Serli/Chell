package com.serli.chell.framework.reflect;

import java.lang.annotation.Annotation;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface MetaDataInfo<T> {
    T getDefaultValue();
    T getValue(Annotation annotation);
}
