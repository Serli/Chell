
package com.serli.chell.framework.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SingletonFactory {

    private static Map<Class<?>, Object> singletonsByClass = new ConcurrentHashMap<Class<?>, Object>();

    public static <T> T get(Class<T> singletonClass) {
        T singleton = (T) singletonsByClass.get(singletonClass);
        if (singleton == null) {
            singleton = ClassUtils.newInstance(singletonClass);
            singletonsByClass.put(singletonClass, singleton);
        }
        return singleton;
    }
}
