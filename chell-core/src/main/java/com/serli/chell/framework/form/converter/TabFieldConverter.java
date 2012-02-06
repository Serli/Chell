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
