
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
