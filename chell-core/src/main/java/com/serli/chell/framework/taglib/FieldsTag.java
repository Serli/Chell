package com.serli.chell.framework.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FieldsTag extends AbstractFormTag {

    @Override
    public int doStartTag() throws JspException {
        print(getFormInstance().getHtml());
        return SKIP_BODY;
    }
}
