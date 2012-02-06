
package com.serli.chell.framework;

import com.serli.chell.framework.store.RequestStore;
import com.serli.chell.framework.store.SessionStore;
import com.serli.chell.framework.store.Store;
import com.serli.chell.framework.store.StoreBase.StoreType;
import com.serli.chell.framework.store.StoreBase;
import java.io.Serializable;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
// TODO : Mettre automatiquement le scope dans la vue
public class Scope implements Serializable {

    public static Store request() {
        return RequestStore.getUserStore();
    }

    public static Store flash(boolean uniqueByPortletMode) {
        return SessionStore.getUserStore(StoreType.FLASH, uniqueByPortletMode);
    }

    public static Store flash() {
        return SessionStore.getUserStore(StoreType.FLASH, true);
    }

    public static Store session(boolean uniqueByPortletMode) {
        return SessionStore.getUserStore(StoreType.SESSION, uniqueByPortletMode);
    }

    public static Store session() {
        return SessionStore.getUserStore(StoreType.SESSION, true);
    }

    protected static void endRequest() {
        for (StoreBase store : listStoreBase()) {
            if (store != null) {
                store.endRequest();
            }
        }
    }

    protected static StoreBase[] listStoreBase() {
        return new StoreBase[] {
            RequestStore.getUserStoreBase(false),
            SessionStore.getUserStoreBase(false)
        };
    }
}
