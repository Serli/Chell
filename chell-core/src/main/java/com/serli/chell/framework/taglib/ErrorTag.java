package com.serli.chell.framework.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ErrorTag extends AbstractFieldTag {

    private boolean raw = false;

    @Override
    public int doStartTag() throws JspException {
        String content;
        if (raw) {
            content = getFormInstance().getError().get(field);
        } else {
            content = getFormInstance().getErrors().get(field);
        }
        if (content != null) {
            print(content);
        }
        return SKIP_BODY;
    }

    public boolean isRaw() {
        return raw;
    }

    public void setRaw(boolean raw) {
        this.raw = raw;
    }
}
