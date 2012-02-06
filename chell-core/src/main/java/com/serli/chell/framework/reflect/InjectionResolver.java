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

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class InjectionResolver {

    protected Map<Class, InjectionType> injectionByTypes = new HashMap<Class, InjectionType>();
    
    public <T> void setInjectionType(Class<T> type, InjectionType<T> injectionType) {
        injectionByTypes.put(type, injectionType);
    }
    
    public <T> T getInstance(Class<T> type, Annotation... qualifiers) {
        InjectionType<T> it = resolveType(type);
        if (it != null) {
            return it.getInstance(type, qualifiers);
        }
        return null;
    }

    public <T> InjectionType<T> resolveType(Class<T> type) {
        InjectionType<T> it = injectionByTypes.get(type);
        if (it == null && !injectionByTypes.containsKey(type)) {
            it = resolveInheritedType(type);
            injectionByTypes.put(type, it);
        }
        return it;
    }

    public <T> InjectionType<T> resolveInheritedType(Class<?> type) {
        if (type != null) {
            InjectionType result = injectionByTypes.get(type);
            if (result != null) {
                return result;
            } else {
                for (Class<?> itf : type.getInterfaces()) {
                    result = resolveInheritedType(itf);
                    if (result != null) {
                        return result;
                    }
                }
                return resolveInheritedType(type.getSuperclass());
            }
        }
        return null;
    }
}
