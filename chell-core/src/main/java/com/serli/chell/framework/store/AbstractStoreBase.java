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

package com.serli.chell.framework.store;

import com.serli.chell.framework.PortletHelper;
import java.io.Serializable;
import java.util.HashMap;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class AbstractStoreBase<TE> extends HashMap<String, TE> implements Serializable, StoreBase {

    private static final long serialVersionUID = -3256476494742474699L;

    public abstract void set(StoreType storeType, String key, Object bean, boolean uniqueByPortletMode);
    public abstract void set(StoreType storeType, Object bean, boolean uniqueByPortletMode);
    public abstract Object get(StoreType storeType, String key, boolean uniqueByPortletMode);
    public abstract <T> T get(StoreType storeType, Class<T> type, boolean uniqueByPortletMode);
    public abstract Object remove(StoreType storeType, String key, boolean uniqueByPortletMode);
    public abstract <T> T remove(StoreType storeType, Class<T> type, boolean uniqueByPortletMode);

    protected String getStoreKey(StoreType storeType, Class<?> beanType, boolean uniqueByPortletMode) {
        return createStoreKey(storeType, beanType.getName(), uniqueByPortletMode, true);
    }

    protected String getStoreKey(StoreType storeType, String baseKey, boolean uniqueByPortletMode) {
        return createStoreKey(storeType, baseKey, uniqueByPortletMode, false);
    }

    protected String createStoreKey(StoreType storeType, String baseKey, boolean uniqueByPortletMode, boolean byClass) {
        StringBuilder b = new StringBuilder();
        b.append(storeType.name()).append(baseKey);
        b.append(byClass).append(uniqueByPortletMode);
        if (uniqueByPortletMode) {
            b.append(PortletHelper.getMode().toString());
        }
        return b.toString();
    }
}
