/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

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
