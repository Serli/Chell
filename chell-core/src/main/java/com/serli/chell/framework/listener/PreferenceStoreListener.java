
package com.serli.chell.framework.listener;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class PreferenceStoreListener<T> {

    public T beforeGet(T oldValue, T newValue) {
        return newValue;
    }

    public void afterGet(T newValue) {
    }

    public T beforeSet(T oldValue, T newValue) {
        return newValue;
    }

    public void afterSet(T newValue) {
    }

    public T beforeStore(T oldValue, T newValue) {
        return newValue;
    }

    public void afterStore(T newValue) {
    }

    public static boolean hasChanged(String oldValue, String newValue) {
        return true;
    }

    public static boolean hasChanged(String[] oldValues, String[] newValues) {
        return true;
    }
}
