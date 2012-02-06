
package com.serli.chell.framework.store;

import com.serli.chell.framework.store.StoreBase.StoreType;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class StoreWrapper implements Store {

    private AbstractStoreBase<?> storeBase;
    private StoreType storeType;
    private boolean uniqueByPortletMode;

    public StoreWrapper(AbstractStoreBase storeBase, StoreType storeType, boolean uniqueByPortletMode) {
        this.storeBase = storeBase;
        this.storeType = storeType;
        this.uniqueByPortletMode = uniqueByPortletMode;
    }

    public Store set(String key, Object bean) {
        storeBase.set(storeType, key, bean, uniqueByPortletMode);
        return this;
    }

    public Store set(Object bean) {
        storeBase.set(storeType, bean, uniqueByPortletMode);
        return this;
    }

    public Object get(String key) {
        return storeBase.get(storeType, key, uniqueByPortletMode);
    }

    public <T> T get(Class<T> beanType) {
        return storeBase.get(storeType, beanType, uniqueByPortletMode);
    }

    public Object remove(String key) {
        return storeBase.remove(storeType, key, uniqueByPortletMode);
    }

    public <T> T remove(Class<T> beanType) {
        return storeBase.remove(storeType, beanType, uniqueByPortletMode);
    }
}
