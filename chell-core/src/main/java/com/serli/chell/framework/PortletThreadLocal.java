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

package com.serli.chell.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class PortletThreadLocal<T> {

    private ThreadLocal<Map<String, T>> threadLocal = new ThreadLocal<Map<String, T>>() {
        @Override
        protected Map<String, T> initialValue() {
            return new HashMap<String, T>();
        }
    };

    public T get() {
        return get(PortletHelper.getNamespace());
    }

    public T get(String namespace) {
        return threadLocal.get().get(namespace);
    }

    public void set(T value) {
        set(PortletHelper.getNamespace(), value);
    }

    public void set(String namespace, T value) {
        threadLocal.get().put(namespace, value);
    }

    public void remove() {
        remove(PortletHelper.getNamespace());
    }

    public void remove(String namespace) {
        Map<String, T> data = threadLocal.get();
        data.remove(namespace);
        if (data.size() == 0) {
            threadLocal.remove();
        }
    }
}
