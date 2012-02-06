
package com.serli.chell.framework.store;

import java.io.Serializable;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface Store extends Serializable {

    Store set(String key, Object bean);
    Store set(Object bean);

    Object get(String key);
    <T> T get(Class<T> type);

    Object remove(String key);
    <T> T remove(Class<T> type);
}
