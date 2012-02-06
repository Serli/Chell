
package com.serli.chell.framework.loader;

import java.util.List;


/**
 * A loader allow to provide data for UI elements like
 * select, radio buttons, checkboxes.
 * Each type of loader is a singleton.
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface DataLoader {

    List<DataModel> getData();

    public static class EmptyDataLoader implements DataLoader {
        public List<DataModel> getData() {
            return null;
        }
    }
}
