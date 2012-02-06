package com.serli.chell.framework.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ButtonTag extends AbstractFormTag {

    private String type;

    @Override
    public int doStartTag() throws JspException {
        print(getFormInstance().getButton().get(type));
        return SKIP_BODY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
