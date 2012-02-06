package com.serli.chell.framework.taglib;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class AbstractFieldTag extends AbstractFormTag {

    protected String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
