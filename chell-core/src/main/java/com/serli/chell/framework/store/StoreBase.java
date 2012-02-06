
package com.serli.chell.framework.store;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface StoreBase {
    void endRequest();

    public static enum StoreType {
        REQUEST, FLASH, SESSION
    }
}
