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
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.store.SessionStore.SessionObject;
import java.io.Serializable;
import java.util.Iterator;
import javax.portlet.PortletSession;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SessionStore extends AbstractStoreBase<SessionObject> {

    private static final int NEVER_EXPIRE = -1;

    public static Store getUserStore(StoreType storeType, boolean uniqueByPortletMode) {
        return new StoreWrapper(getUserStoreBase(true), storeType, uniqueByPortletMode);
    }

    public static SessionStore getUserStoreBase(boolean create) {
        PortletSession session = PortletHelper.getSession();
        SessionStore store = (SessionStore) session.getAttribute(Constant.KEY_STORE);
        if (create && store == null) {
            store = new SessionStore();
            session.setAttribute(Constant.KEY_STORE, store);
        }
        return store;
    }

    public void set(StoreType storeType, String key, Object bean, boolean uniqueByPortletMode) {
        doSet(storeType, getStoreKey(storeType, key, uniqueByPortletMode), bean);
    }

    public void set(StoreType storeType, Object bean, boolean uniqueByPortletMode) {
        doSet(storeType, getStoreKey(storeType, bean.getClass(), uniqueByPortletMode), bean);
    }

    public Object get(StoreType storeType, String key, boolean uniqueByPortletMode) {
        return doGet(getStoreKey(storeType, key, uniqueByPortletMode));
    }

    public <T> T get(StoreType storeType, Class<T> type, boolean uniqueByPortletMode) {
        return (T) doGet(getStoreKey(storeType, type, uniqueByPortletMode));
    }

    public Object remove(StoreType storeType, String key, boolean uniqueByPortletMode) {
        return doRemove(getStoreKey(storeType, key, uniqueByPortletMode));
    }

    public <T> T remove(StoreType storeType, Class<T> type, boolean uniqueByPortletMode) {
        return (T) doRemove(getStoreKey(storeType, type, uniqueByPortletMode));
    }

    public void endRequest() {
        SessionObject so;
        Iterator<SessionObject> it = values().iterator();
        while (it.hasNext()) {
            so = it.next();
            if (so.expiration == 0) {
                it.remove();
            } else if (so.expiration != NEVER_EXPIRE) {
                so.expiration--;
            }
        }
    }

    protected void doSet(StoreType storeType, String storeKey, Object bean) {
        SessionObject so = null;
        switch (storeType) {
            case REQUEST: so = new SessionObject(bean, 0);
            case FLASH: so = new SessionObject(bean, 1);
            default: so = new SessionObject(bean, NEVER_EXPIRE);
        }
        put(storeKey, so);
    }

    protected Object doGet(String storeKey) {
        SessionObject so = get(storeKey);
        if (so != null) {
            return so.value;
        }
        return null;
    }

    protected Object doRemove(String storeKey) {
        SessionObject so = remove(storeKey);
        if (so != null) {
            return so.value;
        }
        return null;
    }

    protected class SessionObject implements Serializable {
        private Object value;
        private int expiration;

        public SessionObject(Object value, int expiration) {
            this.value = value;
            this.expiration = expiration;
        }
    }
}
