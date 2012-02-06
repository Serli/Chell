
package com.serli.chell.framework.store;

import com.serli.chell.framework.PortletHelper;
import java.util.HashMap;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class RequestStore extends HashMap<String, Object> implements Store, StoreBase {

    private static ThreadLocal<RequestStore> requestStores = new ThreadLocal<RequestStore>();

    public static Store getUserStore() {
        return getRequestStore(true);
    }

    public static StoreBase getUserStoreBase(boolean create) {
        return getRequestStore(create);
    }

    protected static RequestStore getRequestStore(boolean create) {
        RequestStore store = requestStores.get();
        if (create && store == null) {
            store = new RequestStore();
            requestStores.set(store);
        }
        return store;
    }

    public Store set(String key, Object bean) {
        put(getStoreKey(key), bean);
        return this;
    }

    public Store set(Object bean) {
        put(getStoreKey(bean.getClass()), bean);
        return this;
    }

    public Object get(String key) {
        return super.get(getStoreKey(key));
    }

    public <T> T get(Class<T> type) {
        return (T) super.get(getStoreKey(type));
    }

    public Object remove(String key) {
        return super.remove(getStoreKey(key));
    }

    public <T> T remove(Class<T> type) {
        return (T) super.remove(getStoreKey(type));
    }

    public void endRequest() {
        clear();
    }

    protected String getStoreKey(Class<?> beanType) {
        return createStoreKey(beanType.getName(), true);
    }

    protected String getStoreKey(String baseKey) {
        return createStoreKey(baseKey, false);
    }

    protected String createStoreKey(String baseKey, boolean byClass) {
        StringBuilder b = new StringBuilder();
        b.append(PortletHelper.getNamespace());
        b.append(baseKey).append(byClass);
        return b.toString();
    }
}
