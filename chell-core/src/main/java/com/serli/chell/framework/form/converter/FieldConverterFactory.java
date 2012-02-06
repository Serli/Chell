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
