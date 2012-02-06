
package com.serli.chell.framework.loader;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DataModelMessage implements DataModel {

    private String value;
    private String message;

    public DataModelMessage(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public String getLabel() {
        return message;
    }
}
