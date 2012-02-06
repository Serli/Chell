
package com.serli.chell.framework.form.converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
// TODO : Ajouter toute l'introspection ici (rappatriement des infos isTabConverter)
public final class FieldConverterFactory {

    private static Map<Class<? extends FieldConverter>, TabFieldConverter> tabConverters = new ConcurrentHashMap<Class<? extends FieldConverter>, TabFieldConverter>();

    private FieldConverterFactory() {
    }

    public static TabFieldConverter getTabConverter(FieldConverter converter) {
        Class<? extends FieldConverter> converterClass = converter.getClass();
        TabFieldConverter result = tabConverters.get(converterClass);
        if (result == null) {
            result = new TabFieldConverter(converter);
            tabConverters.put(converterClass, result);
        }
        return result;
    }
}
