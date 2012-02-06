package com.serli.chell.framework.reflect;

import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import java.lang.annotation.Annotation;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface AnnotationDefaultValues {
    
    DefaultValue[] getDefaultValues();
    
    public static class DefaultValue implements MetaDataInfo<Object> {
        
        private Type type;
        private Object value;

        public DefaultValue(Type type, Object value) {
            this.type = type;
            this.value = value;
        }

        public Type getType() {
            return type;
        }

        public Object getDefaultValue() {
            return value;
        }

        public Object getValue(Annotation annotation) {
            return value;
        }
    }
}
