
package com.serli.chell.framework.loader;

import com.serli.chell.framework.message.MessageBundle;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DataModelKey implements DataModel {

    private String value;
    private String key;
    private String bundleName;

    public DataModelKey(String value, String key) {
        this(value, key, null);
    }

    public DataModelKey(String value, String key, String bundleName) {
        this.value = value;
        this.key = key;
        this.bundleName = bundleName;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        if (bundleName != null && bundleName.length() > 0) {
            return MessageBundle.getBundleMessage(bundleName, key);
        } else {
            return MessageBundle.getMessage(key);
        }
    }
}
